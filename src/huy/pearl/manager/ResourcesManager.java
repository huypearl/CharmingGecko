package huy.pearl.manager;

import java.io.IOException;

import huy.pearl.activity.GameActivity;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.graphics.Color;

public class ResourcesManager {
	private static final ResourcesManager INSTANCE = new ResourcesManager();
	// Variable
	public GameActivity mActivity;
	public Engine mEngine;
	public Camera mCamera;
	public VertexBufferObjectManager vbom;
	public Context mContext;
	// Resources for Splash Scene
	public ITextureRegion splash_region;
	public BitmapTextureAtlas splashTextureAtlas;

	// Resources for Menu Scene
	public ITextureRegion menu_background_region;
	public ITextureRegion playbtn_region;
	public ITextureRegion settingbtn_region;
	public ITextureRegion scorebtn_region;
	public ITextureRegion exitbtn_region;
	public BuildableBitmapTextureAtlas menuTextureAtlas;

	// Resources for Game Scene
	public ITiledTextureRegion bird_region;
	public ITiledTextureRegion gecko_region;
	public ITiledTextureRegion fly_region;
	public ITiledTextureRegion fire_region;
	public ITextureRegion fireball_region;

	public BuildableBitmapTextureAtlas gameTextureAtlas;
	public BuildableBitmapTextureAtlas birdTextureAtlas;
	public BuildableBitmapTextureAtlas flyTextureAtlas;
	public BuildableBitmapTextureAtlas fireballTextureAtlas;
	public BuildableBitmapTextureAtlas fireTextureAtlas;

	public ITiledTextureRegion btn_region;
	public BuildableBitmapTextureAtlas btntextureAtlas;

	public ITextureRegion choang_region;
	public BuildableBitmapTextureAtlas choangtextureAtlas;
	public ITextureRegion Cup_icon_region;
	public BuildableBitmapTextureAtlas cupTextureAtlas;
	public ITextureRegion egg_region;
	public BuildableBitmapTextureAtlas eggTextureAtlas;

	// Resources for Background Scene
	public ITextureRegion background_region;
	public ITextureRegion cloud_region1;
	public ITextureRegion cloud_region2;
	public BuildableBitmapTextureAtlas backgroundTextureAtlas;

	// Resources for GameOver
	public ITextureRegion gameover_region;
	public BuildableBitmapTextureAtlas gameoverTextureAtlas;

	// Resources for Font
	public Font font;
	public Font fontgame;
	// Resources for Sound
	public Sound mfire;

	// Main Methods
	public void loadMenuResources() {
		loadMenuGraphics();
		loadMenuAudios();
		loadMenuFonts();
	}

	public void loadGameResources() {
		loadBackgroundGraphics();
		loadGameGraphics();
		loadGameAudios();
		loadGameFonts();
		loadgameover();
	}

	private void loadgameover() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("game/");
		gameoverTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR);
		this.gameover_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameoverTextureAtlas, mActivity,
						"gameover.png");
		try {
			gameoverTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			gameoverTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
	}

	private void loadBackgroundGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		backgroundTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		this.background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, mActivity,
						"background.png");
		this.cloud_region1 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, mActivity,
						"cloud1.png");
		this.cloud_region2 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(backgroundTextureAtlas, mActivity,
						"cloud2.png");
		try {
			backgroundTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			backgroundTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
	}

	// Sub Methods
	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		menu_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuTextureAtlas, mActivity, "menu.png");
		playbtn_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuTextureAtlas, mActivity, "txt_play.png");
		settingbtn_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuTextureAtlas, mActivity, "txt_setting.png");
		scorebtn_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuTextureAtlas, mActivity, "txt_top10.png");
		exitbtn_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuTextureAtlas, mActivity,
						"txt_exitgame.png");
		try {
			menuTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			menuTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadMenuAudios() {
	}

	private void loadMenuFonts() {
		FontFactory.setAssetBasePath("font/");
		final ITexture mainfontTexture = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createStrokeFromAsset(mActivity.getFontManager(),
				mainfontTexture, mActivity.getAssets(), "font.ttf", 50, true,
				Color.WHITE, 2, Color.BLACK);
		font.load();
	}

	private void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("game/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 680, 512,
				TextureOptions.BILINEAR);
		gecko_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(gameTextureAtlas, mActivity, "gecko.png",
						3, 3);
		birdTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR);
		bird_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(birdTextureAtlas, mActivity, "bird.png",
						2, 2);

		fireTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR);
		fire_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fireTextureAtlas, mActivity, "fire.png",
						5, 1);

		fireballTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR);
		fireball_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(fireballTextureAtlas, mActivity,
						"fire_ball.png");

		flyTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR);
		fly_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(flyTextureAtlas, mActivity, "fly.png", 3,
						1);
		btntextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		btn_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(btntextureAtlas, mActivity,
						"action_button.png", 2, 1);

		choangtextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		choang_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				btntextureAtlas, mActivity, "choang.png");

		cupTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Cup_icon_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(btntextureAtlas, mActivity, "cup.png");

		eggTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		egg_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				btntextureAtlas, mActivity, "eggs.png");

		try {
			this.gameTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.gameTextureAtlas.load();

			this.birdTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.birdTextureAtlas.load();

			this.flyTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.flyTextureAtlas.load();

			this.btntextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.btntextureAtlas.load();

			this.choangtextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.choangtextureAtlas.load();

			this.cupTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.cupTextureAtlas.load();

			this.eggTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.eggTextureAtlas.load();

			this.fireTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.fireTextureAtlas.load();

			this.fireballTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.fireballTextureAtlas.load();

		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
	}

	private void loadGameAudios() {
		SoundFactory.setAssetBasePath("sfx/");
		try {
			mfire = SoundFactory.createSoundFromAsset(
					mActivity.getSoundManager(), mContext, "shoot.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadGameFonts() {
		FontFactory.setAssetBasePath("font/");
		final ITexture gamefontTexture = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		fontgame = FontFactory.createStrokeFromAsset(
				mActivity.getFontManager(), gamefontTexture,
				mActivity.getAssets(), "font.ttf", 25, true, Color.WHITE, 2,
				Color.BLACK);
		fontgame.load();
	}

	// Other methods
	// Splash
	public void loadSplashScene() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTextureAtlas, mActivity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}

	public void unloadSplashscene() {
		splashTextureAtlas.unload();
		splash_region = null;
	}

	// Menu
	public void loadMenuTextures() {
		menuTextureAtlas.load();
	}

	public void unloadMenuTextures() {
		menuTextureAtlas.unload();
	}

	// Game
	public void unloadGameTextures() {
		gameTextureAtlas.unload();
		backgroundTextureAtlas.unload();
		birdTextureAtlas.unload();
		btntextureAtlas.unload();
		flyTextureAtlas.unload();
		gameoverTextureAtlas.unload();
	}

	// Prepare
	public static void prepareManager(Engine engine, GameActivity activity,
			Camera camera, VertexBufferObjectManager vbom) {
		getInstance().mEngine = engine;
		getInstance().mActivity = activity;
		getInstance().mCamera = camera;
		getInstance().vbom = vbom;
	}

	public static void getContext(Context pContext) {
		getInstance().mContext = pContext;
	}

	// Getter and Setter
	public static ResourcesManager getInstance() {
		return INSTANCE;
	}

}
