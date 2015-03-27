package huy.pearl.manager;

public class GameManager {
	private static GameManager INSTANCE;
	// Variable
	int mCurrentScore;
	int mCurrentBullet;

	// Constructor
	public GameManager() {
	}

	// Methods
	public int getCurrentScore() {
		return mCurrentScore;
	}

	public int getCurrentBullet() {
		return mCurrentBullet;
	}

	public void incrementScore(int pIncrementBy) {
		mCurrentScore += pIncrementBy;
	}

	public void incrementBullet(int pIncrementBy) {
		if (mCurrentBullet != 5) {
			mCurrentBullet += pIncrementBy;
		}
	}

	public void decrementBullet(int pDecrementBy) {
		if (mCurrentBullet != 0) {
			mCurrentBullet -= pDecrementBy;
		}
	}

	public void resetGame() {
		this.mCurrentScore = 0;
		this.mCurrentBullet = 0;
	}

	// singleton design pattern
	public static GameManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GameManager();
		}
		return INSTANCE;
	}
}
