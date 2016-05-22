package colourmemory.vicente.com.colourmemory.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import colourmemory.vicente.com.colourmemory.service.ScoreService;
import colourmemory.vicente.com.colourmemory.util.ActivityUtils;
import colourmemory.vicente.com.colourmemory.view.CardPresenter;
import colourmemory.vicente.com.colourmemory.view.HighScorePresenter;
import colourmemory.vicente.com.colourmemory.view.Navigator;

/**
 * Created by Asus on 4/24/2016.
 */
public class BaseActivity extends AppCompatActivity implements Navigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showHighScore() {
        String tag = ColourHighScoreFragment.COLOUR_HIGH_SCORE_FRAGMENT;
        ColourHighScoreFragment colourHighScoreFragment = ColourHighScoreFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), colourHighScoreFragment, tag);

        HighScorePresenter highScorePresenter = new HighScorePresenter(colourHighScoreFragment, new ScoreService(this));
    }

    public void showColourMemoryFragment() {
        String tag = ColourMemoryFragment.COLOUR_MEMORY_FRAGMENT;
        ColourMemoryFragment colourMemoryFragment = ColourMemoryFragment.newInstance();
        colourMemoryFragment.setNavigator(this);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), colourMemoryFragment, tag);
        CardPresenter cardPresenter = new CardPresenter(colourMemoryFragment, new ScoreService(this));
    }


}
