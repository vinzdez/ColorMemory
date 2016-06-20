package colormemory.vicente.com.colormemory.widget;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Map;

import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.model.Card;

/**
 * Created by Vicente on 6/18/2016.
 * Handles Image Clicks And Animation
 */
public class ImageWidget implements View.OnClickListener, Animation.AnimationListener, Runnable {


    private Animation frontAnimation;
    private Animation backAnimation;
    private boolean allowFutureClick;

    private Context context;
    private int currentPosition;

    private Card currentCard;
    private Card previousCard;

    private Map<Integer, Card> cardMap;
    private Map<Integer, Integer> cardPositionMap;

    private ImageCallBack imageCallBack;

    public ImageWidget(Context context, int currentPosition, Map<Integer, Integer> cardPositionMap, Map<Integer, Card> cardMap, ImageCallBack imageCallBack) {
        this.context = context;
        this.currentPosition = currentPosition;
        this.frontAnimation = AnimationUtils.loadAnimation(context, R.anim.to_left);
        this.backAnimation = AnimationUtils.loadAnimation(context, R.anim.to_right);
        this.cardPositionMap = cardPositionMap;
        this.cardMap = cardMap;
        this.imageCallBack = imageCallBack;
    }


    @Override
    public void onClick(View v) {
        if (isAllowFutureClick()) {
            handleImageClick();
        }
    }

    /**
     * Check Previous and Current Card Equality
     * Update Score Bored
     * Calls a callback to HighScore if game has ended
     */
    @Override
    public void run() {
        if (getCardPosition().get(previousCard.getPosition()).equals(getCardPosition().get(currentPosition))) {
            //Disable cardPositionMap
            previousCard.setDisabled(true);
            currentCard.setDisabled(true);

            //Disable cardPositionMap
            previousCard.getImageView().setClickable(false);
            currentCard.getImageView().setClickable(false);

            //Add 2 points
            imageCallBack.updateScoreBoard(2);

        } else {
            //Card no longer the firs cardPositionMap click
            currentCard.setVisible(false);
            previousCard.setVisible(false);

            animateCard(previousCard.getImageView());
            //Minus 1 point
            imageCallBack.updateScoreBoard(1);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == frontAnimation) {

            if (currentCard != null && (currentCard.isCardInitialyClick()) && isAllowFutureClick()) {
                //shows the first cardPositionMap
                imageCallBack.revealImage(currentCard.getImageView(), currentPosition);
            } else if (currentCard != null && (!currentCard.isCardInitialyClick() && !isAllowFutureClick())) {
                //shows the second cardPositionMap
                imageCallBack.revealImage(currentCard.getImageView(), currentPosition);
            } else if (previousCard != null && (!previousCard.isVisible())) {
                //if both cardPositionMap are not equal
                imageCallBack.flipDownImage(previousCard.getImageView());
                imageCallBack.flipDownImage(currentCard.getImageView());
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
     * Calculate's cardPositionMap to show or not
     */
    public void handleImageClick() {
        this.currentCard = getCard(currentPosition);

        //stop user to click initial cardPositionMap, and while evaluating cardPositionMap equality
        if (!currentCard.isCardInitialyClick()) {

            frontAnimation.setAnimationListener(this);
            backAnimation.setAnimationListener(this);

            ImageView currentCardImage = currentCard.getImageView();
            animateCard(currentCardImage);

            for (int i = 0; i <= getCount(); i++) {

                //if i reached max count, set cardPositionMap as visible, current cardPositionMap is first click
                if (i == getCount()) {
                    currentCard.setVisible(true);
                    break;
                }

                this.previousCard = getCard(i);
                if (previousCard.isCardInitialyClick()) {
                    imageCallBack.allowFutureClick(false);
                    currentCardImage.postDelayed(this, 1000);
                    break;
                }
            }
        }
    }

    private Card getCard(int index) {
        return cardMap.get(index);
    }

    private int getCount() {
        return cardPositionMap.size();
    }

    private Map<Integer, Integer> getCardPosition() {
        return cardPositionMap;
    }

    /**
     * Start Animation
     */
    private void animateCard(ImageView imageView) {
        imageView.clearAnimation();
        imageView.setAnimation(frontAnimation);
        imageView.startAnimation(frontAnimation);
    }



    public boolean isAllowFutureClick() {
        return allowFutureClick;
    }

    public void setAllowFutureClick(boolean allowFutureClick) {
        this.allowFutureClick = allowFutureClick;
    }
}
