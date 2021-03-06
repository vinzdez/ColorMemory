package colormemory.vicente.com.colormemory.view;

import android.support.annotation.NonNull;
import colormemory.vicente.com.colormemory.model.ScoreViewModel;
import colormemory.vicente.com.colormemory.service.ScoreService;
import colormemory.vicente.com.colormemory.util.CardUtil;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Vicente on 4/23/2016.
 */
public class CardPresenter implements CardContract.Presenter {

  private Map<Integer, Integer> cards;
  private ScoreService scoreService;

  public CardPresenter(@NonNull CardContract.GameSpanView cardAction, @NonNull ScoreService scoreService) {
    CardContract.GameSpanView cardActionGameSpanView = cardAction;
    this.scoreService = scoreService;
    cardAction = checkNotNull(cardAction, "Card GameSpanView cannot be null!");
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
