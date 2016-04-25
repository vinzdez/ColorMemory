package colourmemory.vicente.com.colourmemory.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


    public class ImageViewListener implements View.OnClickListener, Animation.AnimationListener, Runnable {

        private int currentPosition;

        private Animation frontAnumation;
        private Animation backAnimation;

        private Card currentCard;
        private Card previousCard;

        public ImageViewListener(int currentPosition) {
            this.currentPosition = currentPosition;
            this.frontAnumation = AnimationUtils.loadAnimation(context, R.anim.to_left);
            this.backAnimation = AnimationUtils.loadAnimation(context, R.anim.to_right);
        }

        @Override
        public void onClick(View v) {
            frontAnumation.setAnimationListener(this);
            backAnimation.setAnimationListener(this);
            handleImageClick();
        }

        /**
         * Check Previous and Current Card Equality
         * Update Score Bored
         * Calls a callback to HighScore if game has ended
         */
        @Override
        public void run() {
            checkCardEquality();
            cardViewAction.updateScoreBoard(String.valueOf(score));
            if (isGameEnded()) {
                cardViewAction.showUserInputDialog(score);
            } else {
                allowFutureClick = true;
            }
        }

        @Override
        public void onAnimationStart(Animation animation) {
            if (animation == frontAnumation) {

                if (currentCard.isCardInitialyClick() && allowFutureClick) {
                    //shows the first card
                    showImageView(currentCard.getImageView(), currentPosition);
                } else if (!currentCard.isCardInitialyClick() && !allowFutureClick) {
                    //shows the second card
                    showImageView(currentCard.getImageView(), currentPosition);
                } else if (!previousCard.isVisible()) {
                    //if both card are not equal
                    showDeafaultImage(previousCard.getImageView());
                    showDeafaultImage(currentCard.getImageView());
                    previousCard.getImageView().clearAnimation();
                    previousCard.getImageView().setAnimation(backAnimation);
                    previousCard.getImageView().startAnimation(backAnimation);
                }
                currentCard.getImageView().clearAnimation();
                currentCard.getImageView().setAnimation(backAnimation);
                currentCard.getImageView().startAnimation(backAnimation);

            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}

        /**
         * Calculate's card to show or not
         */
        public void handleImageClick() {
            this.currentCard = getItem(currentPosition);

            //stop user to click initial card, and while evaluating card equality
            if (!currentCard.isCardInitialyClick() && allowFutureClick) {
                ImageView currentCardImage = currentCard.getImageView();
                animateCard(currentCardImage);

                for (int i = 0; i <= getCount(); i++) {

                    //if i reached max count, set card as visible, current card is first click
                    if (i == getCount()) {
                        currentCard.setVisible(true);
                        break;
                    }

                    this.previousCard = getItem(i);
                    if (previousCard.isCardInitialyClick()) {
                        allowFutureClick = false;
                        currentCardImage.postDelayed(this, 1000);
                        break;
                    }
                }
            }
        }

        private boolean isGameEnded() {
            return (gameSpan * 2) == getCount();
        }

        private void checkCardEquality() {
            if (getCards().get(previousCard.getPosition()).equals(getCards().get(currentPosition))) {
                //Disable card
                previousCard.setDisabled(true);
                currentCard.setDisabled(true);

                //Disable card
                previousCard.getImageView().setClickable(false);
                currentCard.getImageView().setClickable(false);

                //Add 2 points
                score += 2;
                gameSpan++;
            } else {
                //Card no longer the firs card click
                currentCard.setVisible(false);
                previousCard.setVisible(false);

                animateCard(previousCard.getImageView());
                //Minus 1 point
                score -= 1;
            }
        }

        /**
         * Start Animation
         *
         * @param imageView
         */
        private void animateCard(ImageView imageView) {
            imageView.clearAnimation();
            imageView.setAnimation(frontAnumation);
            imageView.startAnimation(frontAnumation);
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