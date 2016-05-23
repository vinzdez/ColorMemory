package colormemory.vicente.com.colormemory.model;

import android.widget.ImageView;

/**
 * Created by Vicente on 4/23/2016.
 */
public class Card {
    private int position;

    private ImageView imageView;

    private boolean isDisabled;
    private boolean isVisible;


    public Card(ImageView imageView, int position) {
        this.imageView = imageView;
        this.position = position;
    }


    public int getPosition() {
        return position;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isCardInitialyClick() {
        return !isDisabled() && isVisible();
    }

}
