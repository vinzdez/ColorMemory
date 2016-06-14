package colormemory.vicente.com.colormemory.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.adapter.CardAdapter;
import colormemory.vicente.com.colormemory.model.ScoreViewModel;
import colormemory.vicente.com.colormemory.util.AlertManager;
import colormemory.vicente.com.colormemory.view.CardContract;
import colormemory.vicente.com.colormemory.view.Navigator;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Vicente on 4/23/2016.
 */
public class ColorMemoryFragment extends Fragment implements CardContract.View {

    public static final String TAG = ColorMemoryFragment.class.getName();

    public static final String COLOR_MEMORY_FRAGMENT =
            "colormemory.vicente.com.colormemory.activity.ColorMemoryFragment";
    private Context context;

    @BindView(R.id.gridview)
    GridView gridView;

    private SwipeRefreshLayout colorFragView;

    private CardAdapter cardAdapter;
    private CardContract.Presenter cardPresenter;

    private Handler handler;
    private Runnable resetCardRunnable;

    private static CardContract.UpdateToolBar updateToolBar;
    private static Navigator navigator;

    public static ColorMemoryFragment newInstance(CardContract.UpdateToolBar toolBar, Navigator nav) {
        updateToolBar = toolBar;
        navigator = nav;
        return new ColorMemoryFragment();
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (colorFragView == null) {
            this.context = getActivity();
            this.handler = new Handler();
            this.cardAdapter = new CardAdapter(context, this);
            this.colorFragView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_color, container, false);

            colorFragView.setOnRefreshListener(this);
            colorFragView.setColorSchemeColors(Color.GRAY, Color.BLACK, Color.BLUE, Color.RED);

            updateToolBar.showScore(true);

            ButterKnife.bind(this, colorFragView);


        }

        reshuffleCards();
        this.gridView.setAdapter(cardAdapter);
        return colorFragView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //High Score
//        View highScore = getActivity().findViewById(R.id.id_menu_high_score);
  //      presentShowcaseSequence(highScore);

        colorFragView.setRefreshing(false);
        colorFragView.post(new Runnable() {
            @Override
            public void run() {
                colorFragView.setRefreshing(true);
            }
        });
    }

    @Override
    public void onPause() {
        if (resetCardRunnable != null) {
            handler.removeCallbacks(resetCardRunnable);
        }

        cardAdapter.notifyDataSetInvalidated();
        gridView.setAdapter(null);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        dismissedRefresh();
        colorFragView.clearAnimation();
        colorFragView.destroyDrawingCache();
        super.onDestroy();

    }

    @Override
    public void updateScoreBoard(String score) {
        updateToolBar.updateScore(MessageFormat.format(getString(R.string.score), score));
    }

    @Override
    public void showUserInputDialog(int score) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View dialogView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
        TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialogTitle);
        dialogTitle.setText(dialogTitle.getText().toString().concat(" ").concat(String.valueOf(score)));
        EditText userInputDialogEditText = (EditText) dialogView.findViewById(R.id.userInputDialog);
        showAlertDialog(AlertManager.getAlertDialogBuilder(context, dialogView), userInputDialogEditText, score);
    }

    //On Load
    @Override
    public void showRefresh(Runnable runnable) {
        this.resetCardRunnable = runnable;
        handler.postDelayed(resetCardRunnable, 5000);
        updateToolBar.updateScore(MessageFormat.format(getString(R.string.score), 0));
        Toast.makeText(getActivity(), getString(R.string.progress_message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissedRefresh() {
        colorFragView.post(new Runnable() {
            @Override
            public void run() {
                colorFragView.setRefreshing(false);
            }
        });
    }

    //OnSwipe Down
    @Override
    public void onRefresh() {
        reshuffleCards();
        cardAdapter.notifyDataSetChanged();

        updateToolBar.updateScore(MessageFormat.format(getString(R.string.score), 0));
        Toast.makeText(getActivity(), getString(R.string.progress_message), Toast.LENGTH_LONG).show();
        colorFragView.postDelayed(getProgressBarRunnable(), 5000);
    }

    @Override
    public void setPresenter(@NonNull CardContract.Presenter presenter) {
        cardPresenter = checkNotNull(presenter);
    }

    //Move to Presenter?
    private void presentShowcaseSequence( View highScoreMenu) {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), TAG);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);

//        sequence.addSequenceItem(card, "This is a card", "GOT IT");

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(getActivity())
                        .setTarget(highScoreMenu)
                        .setDismissText("GOT IT")
                        .setContentText("This is high score menu")
                        .withRectangleShape(true)
                        .build()
        );

        /*sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(getActivity())
                        .setTarget(mButtonThree)
                        .setDismissText("GOT IT")
                        .setContentText("This is button three")
                        .withRectangleShape()
                        .build()
        );*/

        sequence.start();

    }


    private Runnable getProgressBarRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                dismissedRefresh();
            }
        };
    }

    private void showAlertDialog(AlertDialog.Builder alertDialogBuilderUserInput, final EditText userInputDialogEditText,
                                 final int score) {
        AlertManager.showDialog(alertDialogBuilderUserInput, new AlertManager.ButtonPositiveCallback() {
            @Override
            public void onSelect(AlertDialog dialog) {
                if (TextUtils.isEmpty(userInputDialogEditText.getText())) {
                    userInputDialogEditText.setError(getString(R.string.user_name_warning));
                } else {
                    cardPresenter.saveScore(new ScoreViewModel(userInputDialogEditText.getText().toString(), score));
                    userInputDialogEditText.setError(null);
                    navigator.showHighScore();
                    dialog.dismiss();
                }
            }
        });
    }

    private void reshuffleCards() {
        cardPresenter.shuffleCards();
        cardAdapter.setCards(cardPresenter.getCards());
    }
}
