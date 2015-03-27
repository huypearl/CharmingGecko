package huy.pearl.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import huy.pearl.Scene.GameScene;
import huy.pearl.Scene.LoadingScene;
import huy.pearl.Scene.MainMenuScene;
import huy.pearl.Scene.SplashScene;
import huy.pearl.base.BaseScene;

public class SceneManager {
	private static final SceneManager INSTANCE = new SceneManager();
	// Scene Collection
	BaseScene splashScene;
	BaseScene menuScene;
	BaseScene loadingScene;
	BaseScene gameScene;

	// Variable
	public enum SceneType {
		SCENE_SPLASH, SCENE_MENU, SCENE_LOADING, SCENE_GAME,
	}

	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().mEngine;

	// Class Logic
	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}

	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_SPLASH:
			setScene(splashScene);
			break;
		case SCENE_MENU:
			setScene(menuScene);
			break;
		case SCENE_LOADING:
			setScene(loadingScene);
			break;
		case SCENE_GAME:
			setScene(gameScene);
			break;
		default:
			break;
		}
	}

	// Methods
	// Splash
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().loadSplashScene();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}

	public void disposeSplashScene() {
		ResourcesManager.getInstance().unloadSplashscene();
		splashScene.disposeScene();
		splashScene = null;
	}

	// Menu
	public void createMenuScene() {
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		loadingScene = new LoadingScene();
		SceneManager.getInstance().setScene(menuScene);
		disposeSplashScene();
	}

	// Handler
	public void loadMenuScene(final Engine engine) {
		setScene(loadingScene);
		gameScene.disposeScene();
		gameScene = null;
		ResourcesManager.getInstance().unloadGameTextures();
		engine.registerUpdateHandler(new TimerHandler(1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						engine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadMenuTextures();
						setScene(menuScene);
					}
				}));
	}

	// Game(Handler)
	public void loadGameScene(final Engine engine) {
		setScene(loadingScene);
		ResourcesManager.getInstance().unloadMenuTextures();
		engine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				engine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadGameResources();
				gameScene = new GameScene();
				setScene(gameScene);
			}
		}));
	}

	// Getter and Setter
	public static SceneManager getInstance() {
		return INSTANCE;
	}

	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}

	public BaseScene getCurrentScene() {
		return currentScene;
	}
}
