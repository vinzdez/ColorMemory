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

import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.adapter.HighScoreAdapter;
import colormemory.vicente.com.colormemory.view.CardContract;
import colormemory.vicente.com.colormemory.view.HighScoreContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Vicente on 4/23/2016.
 */
public class HighScoreFragment extends Fragment implements HighScoreContract.View {

    public static final String COLOR_HIGH_SCORE_FRAGMENT =
            "colourmemory.vicente.com.colourmemory.activity.COLOUT_HIGH_SCORE_FRAGMENT";

    private Context context;

    private HighScoreContract.Presenter highScorePresenter;
    private View highScoreView;
    private HighScoreAdapter highScoreAdapter;

    @BindView(R.id.id_recycler_highScore)
    RecyclerView recyclerView;
    private static CardContract.ScoreView scoreView;

    public static HighScoreFragment newInstance(CardContract.ScoreView updateTBar) {
        scoreView = updateTBar;
        return new HighScoreFragment();
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (highScoreView == null) {
            this.context = getActivity();
            this.highScoreView = inflater.inflate(R.layout.fragment_highscore_list, container, false);
            ButterKnife.bind(this, highScoreView);
            this.highScoreAdapter = new HighScoreAdapter();
        }

        showHighScore();
        return highScoreView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        scoreView.showScore(false);
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
