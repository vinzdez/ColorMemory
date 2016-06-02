package colormemory.vicente.com.colormemory.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

public class AlertManager {

  public static AlertDialog.Builder getAlertDialogBuilder(Context context, View dialogView) {
    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
    alertDialogBuilderUserInput.setView(dialogView);
    return alertDialogBuilderUserInput;
  }

  public static void showDialog(AlertDialog.Builder alertDialogBuilderUserInput,
      final ButtonPositiveCallback buttonPositiveCallback) {
    alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Save", null);

    final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
      @Override
      public void onShow(DialogInterface dialog) {
        Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            buttonPositiveCallback.onSelect(alertDialog);
          }
        });
      }
    });
    alertDialog.show();
  }

  public interface ButtonPositiveCallback {
    void onSelect(AlertDialog alertDialog);
  }
}
