package colormemory.vicente.com.colormemory.adapter;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.model.Card;
import colormemory.vicente.com.colormemory.view.CardContract;

/**
 * Created by Vicente on 4/23/2016.
 */
public class CardAdapter extends BaseAdapter {

    private Map<Integer, Card> cardMap;
    private Map<Integer, Integer> cards;
    private Context context;

    private int gameSpan;
    private int score;

    private Animation frontAnimation;
    private Animation backAnimation;

    private CardContract.View cardViewAction;

    private boolean allowFutureClick;

    public CardAdapter(Context context, CardContract.View cardViewAction) {
        this.cardMap = new HashMap<>();
        this.context = context;
        this.allowFutureClick = true;
        this.cardViewAction = cardViewAction;
        this.frontAnimation = AnimationUtils.loadAnimation(context, R.anim.to_left);
        this.backAnimation = AnimationUtils.loadAnimation(context, R.anim.to_right);
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

        ViewHolder holder;
        if (convertView == null) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.card_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (cardMap.get(position) == null) {
            cardMap.put(position, new Card(holder.image, position));
            showImageView(holder.image, position);
        } else if (cardMap.get(position) != null) {
            showImageView(holder.image, position);
        }

        holder.image.setOnClickListener(new ImageViewListener(position));
        holder.image.setEnabled(false);
        if (position == (getCount() - 1)) {
            cardViewAction.showRefresh(new Runnable() {
                @Override
                public void run() {
                    for (int key : cardMap.keySet()) {
                        ImageView imageView = cardMap.get(key).getImageView();
                        imageView.setEnabled(true);
                        showDeafaultImage(imageView);
                        cardViewAction.hideRefresh();
                    }
                }
            });
            score = 0;
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

        private Card currentCard;
        private Card previousCard;

        public ImageViewListener(int currentPosition) {
            this.currentPosition = currentPosition;
        }

        @Override
        public void onClick(View v) {
            frontAnimation.setAnimationListener(this);
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
            if (animation == frontAnimation) {

                if (currentCard != null && (currentCard.isCardInitialyClick() && allowFutureClick)) {
                    //shows the first card
                    showImageView(currentCard.getImageView(), currentPosition);
                } else if (currentCard != null && (!currentCard.isCardInitialyClick() && !allowFutureClick)) {
                    //shows the second card
                    showImageView(currentCard.getImageView(), currentPosition);
                } else if (previousCard != null && (!previousCard.isVisible())) {
                    //if both card are not equal
                    showDeafaultImage(previousCard.getImageView());
                    showDeafaultImage(currentCard.getImageView());
                    previousCard.getImageView().clearAnimation();
                    previousCard.getImageView().setAnimation(backAnimation);
                    previousCard.getImageView().startAnimation(backAnimation);
                }

                if (currentCard != null) {
                    currentCard.getImageView().clearAnimation();
                    currentCard.getImageView().setAnimation(backAnimation);
                    currentCard.getImageView().startAnimation(backAnimation);
                }

            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

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
         */
        private void animateCard(ImageView imageView) {
            imageView.clearAnimation();
            imageView.setAnimation(frontAnimation);
            imageView.startAnimation(frontAnimation);
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

    class ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}