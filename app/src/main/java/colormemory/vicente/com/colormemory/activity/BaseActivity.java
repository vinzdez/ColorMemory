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
        String tag = HighScoreFragment.COLOR_HIGH_SCORE_FRAGMENT;
        HighScoreFragment highScoreFragment = HighScoreFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), highScoreFragment, tag);

        HighScorePresenter highScorePresenter = new HighScorePresenter(highScoreFragment, new ScoreService(this));
    }

    public void showColourMemoryFragment() {
        String tag = ColorMemoryFragment.COLOR_MEMORY_FRAGMENT;
        ColorMemoryFragment colorMemoryFragment = ColorMemoryFragment.newInstance();
        colorMemoryFragment.setNavigator(this);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), colorMemoryFragment, tag);
        CardPresenter cardPresenter = new CardPresenter(colorMemoryFragment, new ScoreService(this));
    }


}
