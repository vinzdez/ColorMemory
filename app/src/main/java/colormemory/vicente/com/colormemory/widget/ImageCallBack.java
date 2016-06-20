package colormemory.vicente.com.colormemory.widget;

import android.widget.ImageView;

/**
 * Created by Vicente on 6/18/2016.
 */
public interface ImageCallBack {
    void updateScoreBoard(int score);

    void allowFutureClick(boolean allow);

    void revealImage(ImageView imageView, int index);

    void flipDownImage(ImageView imageView);

}
