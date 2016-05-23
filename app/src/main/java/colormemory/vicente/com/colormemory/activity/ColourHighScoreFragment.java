package colormemory.vicente.com.colormemory.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.adapter.HighScoreAdapter;
import colormemory.vicente.com.colormemory.view.HighScoreContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Asus on 4/23/2016.
 */
public class ColourHighScoreFragment extends Fragment implements HighScoreContract.View {

    public static final String COLOUR_HIGH_SCORE_FRAGMENT = "colourmemory.vicente.com.colourmemory.activity.COLOUT_HIGH_SCORE_FRAGMENT";

    private Context context;

    private HighScoreContract.Presenter highScorePresenter;
    private View highScoreView;
    private HighScoreAdapter highScoreAdapter;

    private RecyclerView recyclerView;

    public static ColourHighScoreFragment newInstance() {
        return new ColourHighScoreFragment();
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (highScoreView == null) {
            this.context = getActivity();
            this.highScoreView = inflater.inflate(R.layout.fragment_highscore_list, container, false);
            this.recyclerView = (RecyclerView) highScoreView.findViewById(R.id.id_recycler_highScore);
            this.highScoreAdapter = new HighScoreAdapter(context);
        }

        showHighScore();
        return highScoreView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.id_menu_high_score).setVisible(false);
    }
    @Override
    public void showHighScore() {
        highScoreAdapter.setScoreList(highScorePresenter.getAllScore());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(highScoreAdapter);
        highScoreAdapter.notifyDataSetChanged();
    }



    @Override
    public void setPresenter(@NonNull HighScoreContract.Presenter presenter) {
        highScorePresenter = checkNotNull(presenter);
    }
}
