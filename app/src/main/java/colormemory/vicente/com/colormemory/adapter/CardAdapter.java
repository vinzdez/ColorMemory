package colormemory.vicente.com.colormemory.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.model.Card;
import colormemory.vicente.com.colormemory.util.SettingsPreferences;
import colormemory.vicente.com.colormemory.view.CardContract;
import colormemory.vicente.com.colormemory.widget.ImageCallBack;
import colormemory.vicente.com.colormemory.widget.ImageWidget;
import colormemory.vicente.com.colormemory.widget.ImageWidgetBuilder;

/**
 * Created by Vicente on 4/23/2016.
 */
public class CardAdapter extends BaseAdapter implements ImageCallBack {

    private static int ADD_TWO_POINTS = 2;
    private static int MINUS_ONE_POINTS = 1;

    private Map<Integer, Card> cardMap;
    private Map<Integer, Integer> cardPositionMap;
    private Context context;

    private int gameSpan;
    private int score;
    private List<ImageWidget> imageWidgets;
    private CardContract.GameSpanView cardGameSpanViewAction;


    private SettingsPreferences settingsPreferences;

    public CardAdapter(Context context, CardContract.GameSpanView cardGameSpanViewAction) {
        this.cardMap = new HashMap<>();
        this.context = context;
        this.imageWidgets = new ArrayList<>();
        this.cardGameSpanViewAction = cardGameSpanViewAction;
    }

    @Override
    public int getCount() {
        return getCardPositionMap().size();
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

        ImageWidget imageWidget = new ImageWidgetBuilder()
                .setContext(context)
                .setCurrentPosition(position)
                .setCardPositionMap(cardPositionMap)
                .setCardMap(cardMap)
                .setImageCallBack(this).createImageWidget();


        if (cardMap.get(position) == null) {
            cardMap.put(position, new Card(holder.image, position));
            revealImage(holder.image, position);
            imageWidgets.add(imageWidget);
        } else if (cardMap.get(position) != null) {
            imageWidgets.add(imageWidget);
            revealImage(holder.image, position);
        }

        holder.image.setOnClickListener(imageWidget);
        holder.image.setEnabled(false);
        if (position == (getCount() - 1)) {
            if (!getSettingsPreferences().isShowCaseAppeared()) {
                cardGameSpanViewAction.dismissedRefresh();
                cardGameSpanViewAction.runShowCase();
                getSettingsPreferences().disableShowCaseAppearance();
            } else {
                flipDownCards();
            }


        }
        return convertView;
    }

    public Map<Integer, Integer> getCardPositionMap() {
        return cardPositionMap;
    }

    public void flipDownCards() {
        cardGameSpanViewAction.showRefresh(new Runnable() {
            @Override
            public void run() {
                allowFutureClick(true);
                for (int key : cardMap.keySet()) {
                    ImageView imageView = cardMap.get(key).getImageView();
                    imageView.setEnabled(true);
                    flipDownImage(imageView);
                }
                cardGameSpanViewAction.dismissedRefresh();
            }
        });

        score = 0;
    }

    public void setCardPositionMap(Map<Integer, Integer> cardPositionMap) {
        this.cardPositionMap = cardPositionMap;
    }

    @Override
    public void updateScoreBoard(int value) {
        if (value == ADD_TWO_POINTS) {
            this.score += ADD_TWO_POINTS;
            this.gameSpan++;
        } else if (value == MINUS_ONE_POINTS) {
            this.score -= MINUS_ONE_POINTS;
        }

        cardGameSpanViewAction.updateScoreBoard(String.valueOf(score));
        if (isGameEnded()) {
            cardGameSpanViewAction.showUserInputDialog(score);
        } else {
            allowFutureClick(true);
        }

    }

    @Override
    public void allowFutureClick(boolean allow) {
        for (ImageWidget imageWidget : imageWidgets) {
            imageWidget.setAllowFutureClick(allow);
        }
    }

    @Override
    public void revealImage(ImageView imageView, int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageDrawable(context.getDrawable(cardPositionMap.get(index)));
        } else {
            imageView.setImageDrawable(context.getResources().getDrawable(cardPositionMap.get(index)));
        }
    }

    @Override
    public void flipDownImage(ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.card_bg));
        } else {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.card_bg));
        }
    }


    private boolean isGameEnded() {
        return (gameSpan * 2) == getCount();
    }

    public List<ImageWidget> getImageWidgets() {
        return imageWidgets;
    }

    public SettingsPreferences getSettingsPreferences() {
        if (settingsPreferences == null) {
            this.settingsPreferences = new SettingsPreferences(context);
        }
        return settingsPreferences;
    }


    class ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


}