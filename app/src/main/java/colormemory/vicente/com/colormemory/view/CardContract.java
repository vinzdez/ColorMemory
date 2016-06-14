package colormemory.vicente.com.colormemory.view;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.Map;

import colormemory.vicente.com.colormemory.model.ScoreViewModel;

/**
 * Created by Vicente on 4/23/2016.
 */
public interface CardContract {

    interface View extends BaseView<Presenter>, SwipeRefreshLayout.OnRefreshListener {
        void updateScoreBoard(String score);

        void showUserInputDialog(int score);

        void showRefresh(Runnable runnable);

        void dismissedRefresh();
    }

    interface UpdateToolBar {
        void updateScore(String score);

        void showScore(boolean show);
    }

    interface Presenter {
        void shuffleCards();

        Map<Integer, Integer> getCards();

        void saveScore(ScoreViewModel score);
    }
}
