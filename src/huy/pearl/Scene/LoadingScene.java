package huy.pearl.Scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import huy.pearl.activity.GameActivity;
import huy.pearl.base.BaseScene;
import huy.pearl.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene{

	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));
		attachChild(new Text(GameActivity.WIDTH /2, GameActivity.HEIGHT/2, resourcesManager.font, "Loading...", vbom));
	}

	@Override
	public void disposeScene() {
	}

	@Override
	public void onBackKeyPressed() {
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

}
