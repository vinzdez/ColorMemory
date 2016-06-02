package colormemory.vicente.com.colormemory.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.service.ScoreService;
import colormemory.vicente.com.colormemory.util.ActivityUtils;
import colormemory.vicente.com.colormemory.view.HighScorePresenter;

/**
 * Created by Vicente on 6/2/2016.
 */
public class HighScoreActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_highscore);

        initToolBar();
    }

    @Override
    protected void onStart(){
        super.onStart();
        /*getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        showHighScore();
    }

    public void showHighScore() {
        HighScoreFragment highScoreFragment =
                (HighScoreFragment) getSupportFragmentManager().findFragmentByTag(ColorMemoryFragment.COLOR_MEMORY_FRAGMENT);

        if (highScoreFragment == null) {
            highScoreFragment = HighScoreFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), highScoreFragment, R.id.contentFrame);
        }
        HighScorePresenter highScorePresenter = new HighScorePresenter(highScoreFragment, new ScoreService(this));
    }
}
