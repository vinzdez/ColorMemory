package colormemory.vicente.com.colormemory.adapter;

import android.app.Activity;
import android.content.Context;
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

    private Map<Integer, Card> cardMap;
    private Map<Integer, Integer> cardPositionMap;
    private Context context;


    private int gameSpan;
    private int score;
    private List<ImageWidget> imageWidgets;
    private CardContract.View cardViewAction;


    private SettingsPreferences settingsPreferences;

    public CardAdapter(Context context, CardContract.View cardViewAction) {
        this.cardMap = new HashMap<>();
        this.context = context;
        this.imageWidgets = new ArrayList<>();
        this.cardViewAction = cardViewAction;
        this.settingsPreferences = new SettingsPreferences(context);
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
            imageWidget.showImageView(holder.image, position);
            imageWidgets.add(imageWidget);
        } else if (cardMap.get(position) != null) {
            imageWidgets.add(imageWidget);
            imageWidget.showImageView(holder.image, position);
        }

        holder.image.setOnClickListener(imageWidget);
        holder.image.setEnabled(false);
        if (position == (getCount() - 1)) {
            //  if (!settingsPreferences.isShowCaseAppeared()) {
            //todo separate showShowCase

            //     settingsPreferences.disableShowCaseAppearance();
            //  } else {
            flipDownCards(imageWidget);
            //  }


        }
        return convertView;
    }

    public Map<Integer, Integer> getCardPositionMap() {
        return cardPositionMap;
    }

    public void flipDownCards(final ImageWidget imageWidget) {
        cardViewAction.showRefresh(new Runnable() {
            @Override
            public void run() {
                allowFutureClick(true);
                for (int key : cardMap.keySet()) {
                    ImageView imageView = cardMap.get(key).getImageView();
                    imageView.setEnabled(true);
                    imageWidget.showDeafaultImage(imageView);
                    cardViewAction.dismissedRefresh();
                }
            }
        });
        score = 0;
    }

    public void setCardPositionMap(Map<Integer, Integer> cardPositionMap) {
        this.cardPositionMap = cardPositionMap;
    }

    @Override
    public void updateScoreBoard(int value) {
        if (value == 2) {
            this.score += 2;
            this.gameSpan++;
        } else if (value == 1) {
            this.score -= 1;
        }

        cardViewAction.updateScoreBoard(String.valueOf(score));
        if (isGameEnded()) {
            cardViewAction.showUserInputDialog(score);
        }
        allowFutureClick(true);
    }

    @Override
    public void allowFutureClick(boolean allow) {
        for (ImageWidget imageWidget : imageWidgets) {
            imageWidget.setAllowFutureClick(allow);
        }
    }


    private boolean isGameEnded() {
        return (gameSpan * 2) == getCount();
    }

    public List<ImageWidget> getImageWidgets() {
        return imageWidgets;
    }

    class ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


}