package colormemory.vicente.com.colormemory.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;


public class ColorMemoryActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_color_memory);
        initToolBar();
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        showColourMemoryFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible() && HighScoreFragment.COLOR_HIGH_SCORE_FRAGMENT == fragment.getTag()) {
                showColourMemoryFragment();
                break;
            }
        }
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        showColourMemoryFragment();
        shouldDisplayHomeUp();
        return true;
    }

    public void shouldDisplayHomeUp() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if ((getSupportActionBar() != null && fragment != null) && fragment.isVisible()) {
                if (HighScoreFragment.COLOR_HIGH_SCORE_FRAGMENT == fragment.getTag()) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else if (ColorMemoryFragment.COLOR_MEMORY_FRAGMENT == fragment.getTag()) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }

        }
    }


}
