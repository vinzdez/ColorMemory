package colormemory.vicente.com.colormemory.view;

import java.util.List;

import colormemory.vicente.com.colormemory.model.ScoreViewModel;

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
