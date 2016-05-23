package colormemory.vicente.com.colormemory.view;


import java.util.Map;

import colormemory.vicente.com.colormemory.model.ScoreViewModel;

/**
 * Created by Vicente on 4/23/2016.
 */
public interface CardContract {

    interface View extends BaseView<Presenter> {
        void updateScoreBoard(String score);

        void showUserInputDialog(int score);
    }

    interface Presenter {
        void shuffleCards();

        Map<Integer, Integer> getCards();

        void saveScore(ScoreViewModel score);

    }
}
