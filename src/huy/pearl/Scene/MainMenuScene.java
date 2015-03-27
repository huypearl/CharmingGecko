package huy.pearl.Scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import huy.pearl.activity.GameActivity;
import huy.pearl.base.BaseScene;
import huy.pearl.manager.SceneManager;
import huy.pearl.manager.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements
		IOnMenuItemClickListener {
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;

	// private final int MENU_SETTING = 1;

	@Override
	public void createScene() {
		createBackground();
		createBestScore();
		createMenuChildScene();
	}

	private void createBestScore() {
		
	}

	@Override
	public void disposeScene() {

	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	// Methods

	private void createBackground() {
		final SpriteBackground spriteBackground = new SpriteBackground(
				new Sprite(GameActivity.WIDTH / 2, GameActivity.HEIGHT / 2,
						resourcesManager.menu_background_region, vbom) {
					@Override
					protected void preDraw(GLState pGLState, Camera pCamera) {
						super.preDraw(pGLState, pCamera);
						pGLState.enableDither();
					}
				});
		setBackground(spriteBackground);
	}

	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(GameActivity.WIDTH / 2,
				GameActivity.HEIGHT / 2);

		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_PLAY, resourcesManager.playbtn_region,
						vbom), 1.2f, 1f);
		// final IMenuItem settingMenuItem = new ScaleMenuItemDecorator(
		// new SpriteMenuItem(MENU_SETTING,
		// resourcesManager.settingbtn_region, vbom), 1.2f, 1f);

		menuChildScene.addMenuItem(playMenuItem);
		// menuChildScene.addMenuItem(settingMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(150, -120);
		// settingMenuItem.setPosition(150, -120);

		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);

	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_PLAY:
			SceneManager.getInstance().loadGameScene(engine);
			return true;
			// case MENU_SETTING:
			// return true;
		default:
			break;
		}
		return false;
	}
}
