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
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.adapter.CardAdapter;
import colormemory.vicente.com.colormemory.model.ScoreViewModel;
import colormemory.vicente.com.colormemory.util.AlertManager;
import colormemory.vicente.com.colormemory.view.CardContract;
import colormemory.vicente.com.colormemory.view.Navigator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Vicente on 4/23/2016.
 */
public class ColorMemoryFragment extends Fragment implements CardContract.View {

    public static final String COLOR_MEMORY_FRAGMENT =
            "colourmemory.vicente.com.colourmemory.activity.ColorMemoryFragment";

    public static ColorMemoryFragment newInstance() {
        return new ColorMemoryFragment();
    }

    private Context context;

    @BindView(R.id.gridview)
    GridView gridView;

    private SwipeRefreshLayout colorFragView;
    private Navigator navigator;

    private CardAdapter cardAdapter;
    private CardContract.Presenter cardPresenter;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (colorFragView == null) {
            this.context = getActivity();
            this.colorFragView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_color, container, false);
            ButterKnife.bind(this, colorFragView);
            colorFragView.setOnRefreshListener(this);
            colorFragView.setColorSchemeColors(Color.GRAY, Color.BLACK, Color.BLUE, Color.RED);
            this.cardAdapter = new CardAdapter(context, this);
        }

        reshuffleCards();
        colorFragView.setRefreshing(true);
        showProgressBar();
        gridView.setAdapter(cardAdapter);
        return colorFragView;
    }

    @Override
    public void updateScoreBoard(String score) {
        String value = getString(R.string.score_label).concat(": ").concat(score);

        //TODO:update Activity
        // textScore.setText(value);
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

    public void showProgressBar() {
        Toast.makeText(getActivity(), getString(R.string.progress_message), Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                colorFragView.setRefreshing(false);
            }
        }, 5000);
    }

    @Override
    public void setPresenter(@NonNull CardContract.Presenter presenter) {
        cardPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        reshuffleCards();
        cardAdapter.notifyDataSetChanged();
        showProgressBar();
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
                    getNavigator().showHighScore();
                    dialog.dismiss();
                }
            }
        });
    }

    private void reshuffleCards() {
        cardPresenter.shuffleCards();
        cardAdapter.setCards(cardPresenter.getCards());
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
