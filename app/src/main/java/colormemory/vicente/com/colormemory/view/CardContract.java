package colormemory.vicente.com.colormemory.view;

import android.support.v4.widget.SwipeRefreshLayout;
import colormemory.vicente.com.colormemory.model.ScoreViewModel;
import java.util.Map;

/**
 * Created by Vicente on 4/23/2016.
 */
public interface CardContract {

  interface View extends BaseView<Presenter>, SwipeRefreshLayout.OnRefreshListener {
    void updateScoreBoard(String score);

    void showUserInputDialog(int score);
  }

  interface Presenter {
    void shuffleCards();

    Map<Integer, Integer> getCards();

    void saveScore(ScoreViewModel score);
  }
}
