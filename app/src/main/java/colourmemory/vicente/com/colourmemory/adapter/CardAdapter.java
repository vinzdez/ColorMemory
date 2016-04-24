package colourmemory.vicente.com.colourmemory.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import colourmemory.vicente.com.colourmemory.R;
import colourmemory.vicente.com.colourmemory.model.Card;
import colourmemory.vicente.com.colourmemory.view.CardContract;

/**
 * Created by Vicente on 4/23/2016.
 */
public class CardAdapter extends BaseAdapter {

    private ViewHolder holder;
    private Map<Integer, Card> cardMap;
    private Map<Integer, Integer> cards;
    private Context context;

    private int gameSpan;
    private int score;

    private CardContract.View cardViewAction;

    private boolean allowFutureClick;

    public CardAdapter(Context context, CardContract.View cardViewAction) {
        this.cardMap = new HashMap<>();
        this.context = context;
        this.allowFutureClick = true;
        this.cardViewAction = cardViewAction;
    }

    @Override
    public int getCount() {
        return getCards().size();
    }

    @Override
    public Card getItem(int position) {
        return cardMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.card_layout, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (cardMap.get(position) == null) {
            holder.image.setOnClickListener(new ImageViewListener(position));
            cardMap.put(position, new Card(holder.image, position));
        }

        return convertView;
    }


    public Map<Integer, Integer> getCards() {
        return cards;
    }

    public void setCards(Map<Integer, Integer> cards) {
        this.cards = cards;
    }

    class ImageViewListener implements View.OnClickListener {

        private int currentPosition;

        public ImageViewListener(int currentPosition) {
            this.currentPosition = currentPosition;

        }

        @Override
        public void onClick(View v) {


            final Card currentCard = getItem(currentPosition);
            //stop user to click initial card
            if (!currentCard.isCardInitialyClick() && allowFutureClick) {
                final ImageView currentCardImage = currentCard.getImageView();

                showImageView(currentCardImage, currentPosition);
                for (int i = 0; i <= getCount(); i++) {
                    //if i reached max count, set card as visible, current card is first click
                    if (i == getCount()) {
                        currentCard.setVisible(true);
                        break;
                    }

                    final Card initialCard = getItem(i);
                    if (initialCard.isCardInitialyClick()) {
                        allowFutureClick = false;
                        showImageView(initialCard.getImageView(), initialCard.getPosition());

                        currentCardImage.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkCardEquality(initialCard, currentCard);
                                cardViewAction.updateScoreBoard(String.valueOf(score));
                                if (isGameEnded()) {
                                    cardViewAction.showUserInputDialog(score);
                                } else {
                                    allowFutureClick = true;
                                }

                            }
                        }, 1000);
                        break;
                    }
                }
            }

        }


        private boolean isGameEnded() {
            return (gameSpan * 2) == getCount();
        }

        private void checkCardEquality(Card initialCard, Card currentCard) {
            if (getCards().get(initialCard.getPosition()).equals(getCards().get(currentPosition))) {
                //Disable card
                initialCard.setDisabled(true);
                currentCard.setDisabled(true);

                //Disable card
                initialCard.getImageView().setClickable(false);
                currentCard.getImageView().setClickable(false);

                //Add 2 points
                score += 2;
                gameSpan++;
            } else {
                //Card no longer the firs card click
                currentCard.setVisible(false);
                initialCard.setVisible(false);

                showDeafaultImage(currentCard.getImageView());
                showDeafaultImage(initialCard.getImageView());
                //Minus 1 point
                score -= 1;
            }
        }

        private void showDeafaultImage(ImageView imageView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(context.getDrawable(R.drawable.card_bg));
            } else {
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.card_bg));
            }
        }

        private void showImageView(ImageView imageView, int index) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(context.getDrawable(getCards().get(index)));
            } else {
                imageView.setImageDrawable(context.getResources().getDrawable(getCards().get(index)));
            }
        }
    }

    static class ViewHolder {
        ImageView image;
    }
}



