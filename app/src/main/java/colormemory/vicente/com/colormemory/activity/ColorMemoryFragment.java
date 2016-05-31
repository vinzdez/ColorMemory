package colormemory.vicente.com.colormemory.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    public static final String COLOR_MEMORY_FRAGMENT = "colourmemory.vicente.com.colourmemory.activity.ColorMemoryFragment";

    public static ColorMemoryFragment newInstance() {
        return new ColorMemoryFragment();
    }

    private Context context;

    @BindView(R.id.gridview)
    GridView gridView;
    @BindView(R.id.score)
    TextView textScore;

    @BindView(R.id.progressbar_layout)
    LinearLayout linearLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private View colorFragView;
    private Navigator navigator;

    private CardAdapter cardAdapter;
    private CardContract.Presenter cardPresenter;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (colorFragView == null) {
            this.context = getActivity();
            this.colorFragView = inflater.inflate(R.layout.fragment_color, container, false);
            ButterKnife.bind(this, colorFragView);
            this.cardAdapter = new CardAdapter(context, this);
        }
        reshuffleCards();
        gridView.setAdapter(cardAdapter);
        return colorFragView;
    }

    @Override
    public void updateScoreBoard(String score) {
        String value = getString(R.string.score_label).concat(": ").concat(score);
        textScore.setText(value);
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

    @Override
    public void showProgressBar() {
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setProgress(100);
        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(5000, 500);
        myCountDownTimer.start();
    }

    @Override
    public void setPresenter(@NonNull CardContract.Presenter presenter) {
        cardPresenter = checkNotNull(presenter);
    }

    private void showAlertDialog(AlertDialog.Builder alertDialogBuilderUserInput, final EditText userInputDialogEditText, final int score) {
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

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished / 50);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            linearLayout.setVisibility(View.GONE);
            progressBar.setProgress(0);
        }

    }

}
