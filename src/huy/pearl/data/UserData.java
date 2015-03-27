package huy.pearl.data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
	private static UserData INSTANCE;
	private static final String PRES_NAME = "GAME_USERDATA";
	private static final String BEST_SCORE = "bestScore";
	//
	private SharedPreferences mSettings;
	private SharedPreferences.Editor mEditor;

	public UserData() {
	}

	public synchronized static UserData getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserData();
		}
		return INSTANCE;
	}

	public synchronized void init(Context pContext) {
		if (mSettings == null) {
			mSettings = pContext.getSharedPreferences(PRES_NAME,
					Context.MODE_PRIVATE);
			mEditor = mSettings.edit();
			mEditor.putInt(BEST_SCORE, 0);
			mEditor.commit();
		}
	}

	public synchronized void setHighScore(int score) {
		if (score > mSettings.getInt(BEST_SCORE, 0)) {
			mEditor.putInt(BEST_SCORE, score);
			mEditor.commit();
		}
	}

	public synchronized int getHightScore() {
		return mSettings.getInt(BEST_SCORE, 0);
	}
}
