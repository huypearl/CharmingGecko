package huy.pearl.Scene;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import huy.pearl.activity.GameActivity;
import huy.pearl.base.BaseScene;
import huy.pearl.data.UserData;
import huy.pearl.manager.SceneManager;
import huy.pearl.manager.SceneManager.SceneType;

public class GameScene extends BaseScene {

	// Variable
	private static int numberofMiss = 0;
	private static boolean stunstatus = false;
	private static boolean spawnBird = false;
	private static int numberSpawn = 0;
	// code Test game
	// private static float Speed = 4f;
	// private static float Time_delay = 0.8f;
	private static float Speed = 6f;
	private static float Time_delay = 4f;

	private HUD gameHUD;
	private Text scoreText;
	private int score = 0;
	private Text bulletText;
	private int bullet = 0;
	Sprite cupSprite;
	private Text cupText;

	// private TimerHandler spawnHandler;

	AnimatedSprite button;
	Sprite stun;
	Sprite gameover;
	// Position
	private static float firstX = GameActivity.WIDTH / 3 + 110;
	private static float firstY = GameActivity.HEIGHT / 2 - 20;
	private static float x1 = GameActivity.WIDTH / 3 - 100;
	private static float x2 = GameActivity.WIDTH / 3;

	AnimatedSprite geckoSprite;
	ArrayList<AnimatedSprite> arrBird = new ArrayList<>();
	ArrayList<AnimatedSprite> arrFly = new ArrayList<>();

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		// For Fly
		if (arrFly.size() > 0) {
			for (AnimatedSprite p : arrFly) {
				if (p.collidesWith(geckoSprite)) {
					numberofMiss = 0;
					addToBullet(1);
					removeFly(p);
					break;
				}
			}
		}
		// For Bird
		if (arrBird.size() > 0) {
			for (AnimatedSprite p : arrBird) {
				if (p.getX() == GameActivity.WIDTH / 3 && p.isVisible()) {
					spawnBird = false;
					numberSpawn = 0;
					camera.setHUD(null);
					geckoSprite.stopAnimation();
					geckoSprite.setCurrentTileIndex(8);
					UserData.getInstance().setHighScore(score);
					createGameOver();
					final Text txtGameover = new Text(GameActivity.WIDTH / 2,
							130, resourcesManager.font, "Score: " + score, vbom);
					attachChild(txtGameover);

				}
			}
		}
	}

	@Override
	public void createScene() {
		registerUpdateHandler(new FPSLogger());
		createBackground();
		createEggs();
		createFirstText();
		createChoang();
		createGeckoSprite();
		createFlySpawn();
		createBirdSpawn();

		setTouchAreaBindingOnActionDownEnabled(true);
		setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(Scene pScene,
					TouchEvent pSceneTouchEvent) {
				if (pSceneTouchEvent.isActionDown()
						&& pSceneTouchEvent.getX() > GameActivity.WIDTH / 3 - 50
						&& !stunstatus && spawnBird && bullet > 0) {
					resourcesManager.mfire.play();
					minusToBullet(1);
					firebird(pSceneTouchEvent.getX() - 20,
							pSceneTouchEvent.getY());
				}
				return false;
			}
		});
	}

	// Function touching the scene
	private void removefire(final Sprite fireball) {
		engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				if (fireball.isDisposed()) {
					fireball.detachSelf();
					fireball.dispose();
				}
			}
		});
	}

	private void firebird(final float x, final float y) {
		// Fire Ball
		final Sprite fireballSprite = new Sprite(firstX, firstY,
				resourcesManager.fireball_region, vbom) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				for (AnimatedSprite a : arrBird) {
					if (this.collidesWith(a) && !stunstatus) {
						addToScore(1);
						createFire(a.getX(), a.getY());
						removebird(a);
						removefire(this);

						if (score % 5 == 0 && Speed > 4f) {
							Speed -= 0.5f;
							if (Speed <= 4f) {
								Speed = 4f;
							}
						}

						if (score % 7 == 0 && Time_delay > 0.8f) {
							Time_delay -= 0.5f;
							if (Time_delay < 0.8f) {
								Time_delay = 0.8f;
							} else {
								if (numberSpawn < 3) {
									numberSpawn += 1;
								}
								createBirdSpawn();
							}
						}

					}
				}
			}

		};
		fireballSprite.setScale(0.5f);
		float angle = 0;
		if (x == firstX) {
			if (y > firstY) {
				fireballSprite.setRotation(-90);
			} else
				fireballSprite.setRotation(90);
		} else {
			angle = (float) Math.atan2(-(y - firstY), x - firstX);
			fireballSprite.setRotation((float) (angle * 180 / Math.PI));
		}
		// float lastX = firstX + (float)
		// Math.cos(-fireballSprite.getRotation())
		// * 500;
		// float lastY = firstY + (float)
		// Math.sin(-fireballSprite.getRotation())
		// * 500;
		// MoveModifier mod = new MoveModifier(2f, firstX, firstY, lastX,
		// lastY) {
		// MoveModifier((x - geckoSprite.getX()) / 1000,
		MoveModifier mod = new MoveModifier(
				(x - geckoSprite.getX() > 0) ? ((x - geckoSprite.getX()) / 500)
						: ((geckoSprite.getX() - x) / 250), firstX, firstY, x,
				y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				// remove fire ball
				if (!fireballSprite.isDisposed()) {
					engine.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							if (!fireballSprite.isDisposed()) {
								fireballSprite.detachSelf();
								fireballSprite.dispose();
							}
						}
					});
				}
			}
		};
		attachChild(fireballSprite);
		fireballSprite.registerEntityModifier(mod);
	}

	private void createFire(float x, float y) {
		final AnimatedSprite fireSprite = new AnimatedSprite(x, y,
				resourcesManager.fire_region, vbom);
		fireSprite.setScale(1.8f);
		fireSprite.animate(new long[] { 100, 100, 100 }, 2, 4, 1);
		fireSprite.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (fireSprite.getCurrentTileIndex() == 4) {
					engine.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							fireSprite.detachSelf();
							fireSprite.dispose();
						}
					});
				}
			}

			@Override
			public void reset() {
			}
		});
		attachChild(fireSprite);
	}

	// Override BaseScene
	@Override
	public void disposeScene() {
		clearUpdateHandlers();
		camera.setHUD(null);
		camera.setCenter(GameActivity.WIDTH / 2, GameActivity.HEIGHT / 2);
	}

	@Override
	public void onBackKeyPressed() {
		arrBird = new ArrayList<>();
		arrFly = new ArrayList<>();
		numberofMiss = 0;
		numberSpawn = 0;
		stunstatus = false;
		SceneManager.getInstance().loadMenuScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	// Methods for Game components
	private void createBackground() {
		final AutoParallaxBackground background = new AutoParallaxBackground(0,
				0, 0, 5);
		background.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(
				GameActivity.WIDTH / 2, GameActivity.HEIGHT / 2,
				resourcesManager.background_region, vbom)));
		background.attachParallaxEntity(new ParallaxEntity(7f, new Sprite(0,
				300, resourcesManager.cloud_region1, vbom)));
		background.attachParallaxEntity(new ParallaxEntity(15f, new Sprite(0,
				250, resourcesManager.cloud_region2, vbom)));
		setBackground(background);
	}

	private void createGameOver() {
		clearUpdateHandlers();
		detachChildren();
		gameover = new Sprite(GameActivity.WIDTH / 2, GameActivity.HEIGHT / 2,
				resourcesManager.gameover_region, vbom);
		attachChild(gameover);
	}

	private void createHD() {
		gameHUD = new HUD();
		scoreText = new Text(20, 390, resourcesManager.font,
				"Score: 0123456789", new TextOptions(HorizontalAlign.LEFT),
				vbom);
		cupSprite = new Sprite(GameActivity.WIDTH / 2, 450,
				resourcesManager.Cup_icon_region, vbom);
		cupSprite.setScale(0.5f);
		cupText = new Text(cupSprite.getX() + 40, cupSprite.getY(),
				resourcesManager.fontgame, "0123456789", vbom);

		bulletText = new Text(GameActivity.WIDTH / 2, 30,
				resourcesManager.font, "*****", new TextOptions(
						HorizontalAlign.RIGHT), vbom);
		button = new AnimatedSprite(70, 50, resourcesManager.btn_region, vbom) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {

				super.onManagedUpdate(pSecondsElapsed);
			}

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					if (numberofMiss < 2) {
						this.setCurrentTileIndex(0);
						this.setScale(1.7f);
						Path p = new Path(3).to(x2, geckoSprite.getY())
								.to(x1, geckoSprite.getY())
								.to(x2, geckoSprite.getY());
						IPathModifierListener pathmodifier = new IPathModifierListener() {

							@Override
							public void onPathWaypointStarted(
									PathModifier pPathModifier,
									IEntity pEntity, int pWaypointIndex) {
							}

							@Override
							public void onPathWaypointFinished(
									PathModifier pPathModifier,
									IEntity pEntity, int pWaypointIndex) {
							}

							@Override
							public void onPathStarted(
									PathModifier pPathModifier, IEntity pEntity) {
								geckoSprite.animate(
										new long[] { 200, 200, 200 }, 3, 5, 1);
							}

							@Override
							public void onPathFinished(
									PathModifier pPathModifier, IEntity pEntity) {
								geckoSprite.animate(
										new long[] { 200, 200, 200 }, 0, 2,
										true);
								++numberofMiss;
								if (numberofMiss >= 2) {
									stunstatus = true;
									stun.setVisible(true);
									geckoSprite.stopAnimation();
									geckoSprite.setCurrentTileIndex(7);
									geckoSprite
											.registerUpdateHandler(new TimerHandler(
													3f, new ITimerCallback() {
														@Override
														public void onTimePassed(
																TimerHandler pTimerHandler) {
															geckoSprite
																	.animate(
																			new long[] {
																					200,
																					200,
																					200 },
																			0,
																			2,
																			true);
															numberofMiss = 0;
															stunstatus = false;
															stun.setVisible(false);
														}

													}));
								}
							}
						};
						PathModifier path = new PathModifier(0.5f, p,
								pathmodifier);
						if (numberofMiss < 2) {
							stunstatus = false;
							if (stun.isVisible()) {
								numberofMiss = 0;
								stun.setVisible(false);
							}
							geckoSprite.registerEntityModifier(path);
						}
					}
				}
				if (pSceneTouchEvent.isActionUp()) {
					this.setCurrentTileIndex(1);
					this.setScale(1.5f);
				}
				return true;
			}
		};

		scoreText.setAnchorCenter(0, 0);
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);

		bulletText.setAnchorCenter(0, 0);
		bulletText.setText("");
		gameHUD.attachChild(bulletText);

		button.setCurrentTileIndex(1);
		button.setScale(1.5f);
		gameHUD.attachChild(button);
		gameHUD.registerTouchArea(button);
		cupText.setText(UserData.getInstance().getHightScore() + "");
		gameHUD.attachChild(cupSprite);
		gameHUD.attachChild(cupText);
		camera.setHUD(gameHUD);

	}

	private void createChoang() {
		stun = new Sprite(GameActivity.WIDTH / 3 - 20,
				GameActivity.HEIGHT / 2 + 70, resourcesManager.choang_region,
				vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				this.setPosition(geckoSprite.getX() - 20,
						geckoSprite.getY() + 70);
				// this.setRotation(this.getRotation()+pSecondsElapsed*360/2f);
			}
		};
		attachChild(stun);
		stun.setScale(0.5f);
		stun.setVisible(false);
	}

	// Methods for Game Objects
	private void createGeckoSprite() {
		geckoSprite = new AnimatedSprite(GameActivity.WIDTH / 3,
				GameActivity.HEIGHT / 2, resourcesManager.gecko_region, vbom);
		geckoSprite.animate(new long[] { 200, 200, 200 }, 0, 2, true);
		attachChild(geckoSprite);
		this.registerTouchArea(geckoSprite);
	}

	private void createFlySprite() {
		Random rand = new Random();
		float pos = rand.nextInt(GameActivity.HEIGHT);
		float time = 8 + rand.nextInt(2);
		final AnimatedSprite flySprite = new AnimatedSprite(x1 - 70, pos,
				resourcesManager.fly_region, vbom) {
		};
		flySprite.setScale(0.4f);
		flySprite.animate(new long[] { 100, 100, 100 }, 0, 2, true);
		attachChild(flySprite);
		final Path pathFly = new Path(4).to(x1 - 70, pos).to(x1 - 70, 400)
				.to(x1 - 70, 100).to(x1 - 70, pos);
		final Path pathFly2 = new Path(4).to(x1 - 70, pos).to(x1 - 70, 100)
				.to(x1 - 70, 400).to(x1 - 70, pos);
		if (rand.nextInt(1) == 0) {
			flySprite.registerEntityModifier(new LoopEntityModifier(
					new PathModifier(time, pathFly)));
		} else {
			flySprite.registerEntityModifier(new LoopEntityModifier(
					new PathModifier(time, pathFly2)));
		}
		arrFly.add(flySprite);
	}

	private void removeFly(final AnimatedSprite fly) {
		engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				arrFly.remove(fly);
				fly.detachSelf();
				fly.dispose();
			}
		});
	}

	private void createFlySpawn() {
		TimerHandler sprTimerHandler = new TimerHandler(0.2f, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						if (arrFly.size() == 0 && spawnBird == true) {
							createFlySprite();
							createFlySprite();
							createFlySprite();
						}
					}
				});
		engine.registerUpdateHandler(sprTimerHandler);
	}

	private void createBirdSprite() {
		Random rand = new Random();
		int pos = rand.nextInt(5);
		final AnimatedSprite birdSprite = new AnimatedSprite(
				GameActivity.WIDTH, 20 + pos * 100,
				resourcesManager.bird_region, vbom);
		birdSprite.setScale(0.8f);
		birdSprite.animate(new long[] { 200, 200 }, 0, 1, true);
		attachChild(birdSprite);
		arrBird.add(birdSprite);
		registerTouchArea(birdSprite);
		MoveXModifier mod = new MoveXModifier(Speed, birdSprite.getX(),
				GameActivity.WIDTH / 3);
		birdSprite.registerEntityModifier(mod);
	}

	private void removebird(final AnimatedSprite bird) {
		engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				arrBird.remove(bird);
				bird.detachSelf();
				bird.dispose();
			}
		});
	}

	private void createBirdSpawn() {
		// Time
		// //////////
		TimerHandler sprTimerHandler = new TimerHandler(Time_delay, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						if (spawnBird) {
							createBirdSprite();
						}
					}
				});
		engine.registerUpdateHandler(sprTimerHandler);
		// engine.registerUpdateHandler(sprTimerHandler = new
		// TimerHandler(Time_delay, true,
		// new ITimerCallback() {
		//
		// @Override
		// public void onTimePassed(TimerHandler pTimerHandler) {
		// if (spawnBird) {
		// createBirdSprite();
		// }
		// }
		// }));
	}

	//
	private void createEggs() {
		final Sprite egg1 = new Sprite(GameActivity.WIDTH / 3, 20,
				resourcesManager.egg_region, vbom);
		attachChild(egg1);
		egg1.setScale(0.7f);
		final Sprite egg2 = new Sprite(GameActivity.WIDTH / 3, 20 + 1 * 100,
				resourcesManager.egg_region, vbom);
		attachChild(egg2);
		egg2.setScale(0.7f);
		final Sprite egg3 = new Sprite(GameActivity.WIDTH / 3, 20 + 2 * 100,
				resourcesManager.egg_region, vbom);
		attachChild(egg3);
		egg3.setScale(0.7f);
		final Sprite egg4 = new Sprite(GameActivity.WIDTH / 3, 20 + 3 * 100,
				resourcesManager.egg_region, vbom);
		attachChild(egg4);
		egg4.setScale(0.7f);
		final Sprite egg5 = new Sprite(GameActivity.WIDTH / 3, 20 + 4 * 100,
				resourcesManager.egg_region, vbom);
		attachChild(egg5);
		egg5.setScale(0.7f);
	}

	// Methods for game information
	private void createFirstText() {
		final Text firsttext = new Text(GameActivity.WIDTH / 2,
				GameActivity.WIDTH / 2 - 250, resourcesManager.font,
				"Tap to start", vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (this.isVisible()) {
						this.setVisible(false);
						spawnBird = true;
						createHD();
						removefirstText(this);
					}
				}
				return false;
			}
		};
		attachChild(firsttext);
		registerTouchArea(firsttext);
	}

	private void removefirstText(final Text txt) {
		engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				txt.detachSelf();
				txt.dispose();
			}
		});
	}

	private void addToScore(int i) {
		score += i;
		scoreText.setText("Score: " + score);
	}

	private void addToBullet(int i) {
		if (bullet != 5)
			bullet += 1;
		showBullet();
	}

	private void minusToBullet(int i) {
		if (bullet != 0)
			bullet -= 1;
		showBullet();
	}

	private void showBullet() {
		switch (bullet) {
		case 0:
			bulletText.setText("");
			break;
		case 1:
			bulletText.setText("*");
			break;
		case 2:
			bulletText.setText("**");
			break;
		case 3:
			bulletText.setText("***");
			break;
		case 4:
			bulletText.setText("****");
			break;
		case 5:
			bulletText.setText("*****");
			break;
		default:
			break;
		}
	}
}