package colormemory.vicente.com.colormemory.widget;

import android.content.Context;

import java.util.Map;

import colormemory.vicente.com.colormemory.model.Card;

public class ImageWidgetBuilder {
    private Context context;
    private int currentPosition;
    private Map<Integer, Integer> cardPositionMap;
    private Map<Integer, Card> cardMap;
    private ImageCallBack imageCallBack;

    public ImageWidgetBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public ImageWidgetBuilder setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        return this;
    }

    public ImageWidgetBuilder setCardPositionMap(Map<Integer, Integer> cardPositionMap) {
        this.cardPositionMap = cardPositionMap;
        return this;
    }

    public ImageWidgetBuilder setCardMap(Map<Integer, Card> cardMap) {
        this.cardMap = cardMap;
        return this;
    }

    public ImageWidgetBuilder setImageCallBack(ImageCallBack imageCallBack) {
        this.imageCallBack = imageCallBack;
        return this;
    }

    public ImageWidget createImageWidget() {
        return new ImageWidget(context, currentPosition, cardPositionMap, cardMap, imageCallBack);
    }
}