package colormemory.vicente.com.colormemory.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.service.ScoreService;
import colormemory.vicente.com.colormemory.util.ActivityUtils;
import colormemory.vicente.com.colormemory.view.HighScorePresenter;

/**
 * Created by Vicente on 6/2/2016.
 */
public class HighScoreActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.score)
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        ButterKnife.bind(this);
        initToolBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showHighScore();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void showHighScore() {
        HighScoreFragment highScoreFragment =
                (HighScoreFragment) getSupportFragmentManager().findFragmentByTag(HighScoreFragment.COLOR_HIGH_SCORE_FRAGMENT);

        if (highScoreFragment == null) {
            highScoreFragment = HighScoreFragment.newInstance(this);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), highScoreFragment);
        }
         new HighScorePresenter(highScoreFragment, new ScoreService(this));
    }

    @Override
    public void updateScore(String score) {
        //Not Applicable
    }

    @Override
    public void showScore(boolean show) {
        score.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
