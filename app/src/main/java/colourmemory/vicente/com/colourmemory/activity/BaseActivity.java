package colourmemory.vicente.com.colourmemory.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

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
    public void switchFragment() {
        String tag = ColourHighScoreFragment.COLOUR_HIGH_SCORE_FRAGMENT;
        ColourHighScoreFragment colourHighScoreFragment = ColourHighScoreFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), colourHighScoreFragment, tag);

        HighScorePresenter highScorePresenter = new HighScorePresenter(colourHighScoreFragment, new ScoreService(this));
    }

    public static View getToolbarLogoIcon(Toolbar toolbar) {
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    public void showColourMemoryFragment() {
        String tag = ColourMemoryFragment.COLOUR_MEMORY_FRAGMENT;
        ColourMemoryFragment colourMemoryFragment = ColourMemoryFragment.newInstance();
        colourMemoryFragment.setNavigator(this);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), colourMemoryFragment, tag);


        CardPresenter cardPresenter = new CardPresenter(colourMemoryFragment, new ScoreService(this));
    }


}
