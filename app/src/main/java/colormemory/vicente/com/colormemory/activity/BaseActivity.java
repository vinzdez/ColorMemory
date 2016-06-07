package colormemory.vicente.com.colormemory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.view.CardContract;
import colormemory.vicente.com.colormemory.view.Navigator;

public abstract class BaseActivity extends AppCompatActivity implements Navigator, CardContract.UpdateToolBar {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View view = findViewById(R.id.id_menu_high_score);
        switch (item.getItemId()) {
            case R.id.id_menu_high_score:
                //TODO : add preferences if ShowCase appeared
                /*new MaterialShowcaseView.Builder(this)
                        .setTarget(view)
                        .setShapePadding(96)
                        .setDismissText("GOT IT")
                        .setContentText("Show Score Results.")
                        .setContentTextColor(getResources().getColor(R.color.cardview_light_background))
                        .setMaskColour(getResources().getColor(R.color.cardview_dark_background))
                        .show();*/
                showHighScore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showHighScore() {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }

    public void initToolBar(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

}
