package colormemory.vicente.com.colormemory.view;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import colormemory.vicente.com.colormemory.model.ScoreViewModel;
import colormemory.vicente.com.colormemory.service.ScoreService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Asus on 4/23/2016.
 */
public class HighScorePresenter implements HighScoreContract.Presenter {

    private final HighScoreContract.View highScoreView;
    private ScoreService scoreService;

    public HighScorePresenter(@NonNull HighScoreContract.View highScoreView, @NonNull ScoreService scoreService) {
        this.highScoreView = highScoreView;
        this.scoreService = scoreService;
        highScoreView = checkNotNull(highScoreView, "Card View cannot be null!");
        highScoreView.setPresenter(this);
    }

    @Override
    public List<ScoreViewModel> getAllScore() {
        List<ScoreViewModel> scoreViewModels = new ArrayList<>();

        for (int i = 0; i < scoreService.getAllScore().size(); i++) {
            ScoreViewModel scoreViewModel = new ScoreViewModel(scoreService.getAllScore().get(i).getName(), scoreService.getAllScore().get(i).getScore());
            scoreViewModel.setRank(i);
            scoreViewModels.add(scoreViewModel);
        }
        return scoreViewModels;
    }
}
