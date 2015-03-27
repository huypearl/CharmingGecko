package huy.pearl.Scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import huy.pearl.activity.GameActivity;
import huy.pearl.base.BaseScene;
import huy.pearl.manager.SceneManager.SceneType;

public class SplashScene extends BaseScene {
	private Sprite splash;

	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));
		splash = new Sprite(GameActivity.WIDTH / 2, GameActivity.HEIGHT / 2, resourcesManager.splash_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		splash.setScale(1.5f);
		attachChild(splash);
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public void onBackKeyPressed() {
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

}
