package huy.pearl.base;

import huy.pearl.manager.ResourcesManager;
import huy.pearl.manager.SceneManager.SceneType;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

public abstract class BaseScene extends Scene{
	// Variable
	protected Camera camera;
	protected Engine engine;
	protected Activity activity;
	protected VertexBufferObjectManager vbom;
	protected ResourcesManager resourcesManager;
	// Constructor
	public BaseScene() {
		resourcesManager = ResourcesManager.getInstance();
		this.camera = resourcesManager.mCamera;
		this.engine = resourcesManager.mEngine;
		this.activity = resourcesManager.mActivity;
		this.vbom = resourcesManager.vbom;
		createScene();
	}
	// Abstraction
	public abstract void createScene();
	public abstract void disposeScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
}
