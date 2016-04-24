package colourmemory.vicente.com.colourmemory.view;

import java.util.List;

import colourmemory.vicente.com.colourmemory.model.ScoreViewModel;
import colourmemory.vicente.com.colourmemory.model.repo.Score;

/**
 * Created by Asus on 4/23/2016.
 */
public class HighScoreContract {
    public interface View extends BaseView<Presenter> {
        void showHighScore();
    }

    public interface Presenter {
        List<ScoreViewModel> getAllScore();
    }
}
