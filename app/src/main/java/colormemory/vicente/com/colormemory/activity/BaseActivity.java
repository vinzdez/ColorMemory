package colormemory.vicente.com.colormemory.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import colormemory.vicente.com.colormemory.service.ScoreService;
import colormemory.vicente.com.colormemory.util.ActivityUtils;
import colormemory.vicente.com.colormemory.view.CardPresenter;
import colormemory.vicente.com.colormemory.view.HighScorePresenter;
import colormemory.vicente.com.colormemory.view.Navigator;

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
