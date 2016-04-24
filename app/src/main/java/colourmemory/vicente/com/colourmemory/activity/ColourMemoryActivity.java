package colourmemory.vicente.com.colourmemory.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import colourmemory.vicente.com.colourmemory.R;


public class ColourMemoryActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_memory);
    }


    @Override
    public void onResume() {
        super.onResume();
        initToolBar();
        showColourMemoryFragment();
    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible() && ColourHighScoreFragment.COLOUR_HIGH_SCORE_FRAGMENT == fragment.getTag()) {
                showColourMemoryFragment();
                break;
            }
        }
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        View logoView = getToolbarLogoIcon(toolbar);
        logoView.setOnClickListener(new LocgoClickListener());
        setSupportActionBar(toolbar);
    }

    public class LocgoClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            showColourMemoryFragment();
        }
    }

}
