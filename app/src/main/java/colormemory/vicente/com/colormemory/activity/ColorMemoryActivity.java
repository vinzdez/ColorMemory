package colormemory.vicente.com.colormemory.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.service.ScoreService;
import colormemory.vicente.com.colormemory.util.ActivityUtils;
import colormemory.vicente.com.colormemory.view.CardPresenter;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class ColorMemoryActivity extends BaseActivity {

    private static final String TAG = ColorMemoryActivity.class.getName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.score)
    TextView score;

    @BindString(R.string.got_it)
    String gotIt;

    @BindString(R.string.intro_score)
    String introScore;

    @BindString(R.string.intro_score_board)
    String introScoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_memory);
        ButterKnife.bind(this);
        initToolBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showColourMemoryFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_high_score:
                showHighScore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showColourMemoryFragment() {
        ColorMemoryFragment colorMemoryFragment =
                (ColorMemoryFragment) getSupportFragmentManager().findFragmentByTag(ColorMemoryFragment.COLOR_MEMORY_FRAGMENT);

        if (colorMemoryFragment == null) {
            colorMemoryFragment = ColorMemoryFragment.newInstance(this, this);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), colorMemoryFragment);
        }
        new CardPresenter(colorMemoryFragment, new ScoreService(this));
    }

    @Override
    public void updateScore(String score) {
        this.score.setText(score);
    }

    @Override
    public void showScore(boolean show) {
        score.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void runShowCase() {
        ShowcaseConfig config = new ShowcaseConfig();
        View menuHighScore = findViewById(R.id.id_menu_high_score);
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, TAG);
        sequence.setConfig(config);

        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTitleText("Score")
                .setTarget(score)
                .setContentText(introScore)
                .setDismissText(gotIt)
                .setDismissOnTouch(true)
                .withCircleShape()
                .build());
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(menuHighScore)
                        .setDismissText(gotIt)
                        .setContentText(introScoreBoard)
                        .withCircleShape()
                        .build()
        );

        sequence.start();
    }
}
