package colormemory.vicente.com.colormemory.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.service.ScoreService;
import colormemory.vicente.com.colormemory.util.ActivityUtils;
import colormemory.vicente.com.colormemory.view.CardPresenter;

public class ColorMemoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_memory);
        ButterKnife.bind(this);
        initToolBar();

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
            colorMemoryFragment = ColorMemoryFragment.newInstance();
            colorMemoryFragment.setNavigator(this);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), colorMemoryFragment, R.id.contentFrame);
        }
        CardPresenter cardPresenter = new CardPresenter(colorMemoryFragment, new ScoreService(this));
    }
}
