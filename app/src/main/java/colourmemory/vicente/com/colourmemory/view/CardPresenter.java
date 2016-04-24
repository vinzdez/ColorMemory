package colourmemory.vicente.com.colourmemory.view;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import colourmemory.vicente.com.colourmemory.model.ScoreViewModel;
import colourmemory.vicente.com.colourmemory.model.repo.Score;
import colourmemory.vicente.com.colourmemory.service.ScoreService;
import colourmemory.vicente.com.colourmemory.util.CardUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Vicente on 4/23/2016.
 */
public class CardPresenter implements CardContract.Presenter {

    private final CardContract.View cardActionView;
    private Map<Integer, Integer> cards;
    private ScoreService scoreService;

    public CardPresenter(@NonNull CardContract.View cardAction , @NonNull ScoreService scoreService) {
        this.cardActionView = cardAction;
        this.scoreService = scoreService;
        cardAction = checkNotNull(cardAction, "Card View cannot be null!");
        cardAction.setPresenter(this);
    }

    @Override
    public void shuffleCards() {
       this.cards = CardUtil.shuffleCards();
    }

    @Override
    public Map<Integer, Integer> getCards() {
        return cards;
    }

    @Override
    public void saveScore(ScoreViewModel score) {
        scoreService.saveScore(score);
        System.out.print("asd");
    }

}
