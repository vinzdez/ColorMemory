package colormemory.vicente.com.colormemory.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.service.ScoreService;
import colormemory.vicente.com.colormemory.util.ActivityUtils;
import colormemory.vicente.com.colormemory.view.CardPresenter;

public class ColorMemoryActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.score)
    TextView score;


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
}
