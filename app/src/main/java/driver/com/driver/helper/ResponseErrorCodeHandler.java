package driver.com.driver.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import driver.com.driver.BuildConfig;
import driver.com.driver.R;
import driver.com.driver.screens.Login;
import driver.com.driver.screens.PreferenceConnector;

public class ResponseErrorCodeHandler {
    public static int _DISMISS_DIALOG_ALONE = 1;
    public static int _RESET_ALL_ACTIVITY = 2;

    // SecretQuestion Error Handler
    public static void errorHandler(Context ctx, JSONObject j, int errorCode, int API_ID) {
        String msg;
        try {
            msg = j.getString(BuildConfig.RESPONSE_MSG);
            switch (errorCode) {
                case BuildConfig._ERR_UNAUTHORIZED_TOKEN:
                    showAlertDialog(ctx, msg, _RESET_ALL_ACTIVITY);
                    break;
                case BuildConfig._ERR_NULL_PARAMS:
                case BuildConfig._ERR_INTERNAL_SERVER:
                    Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    break;
                default: {
                    showAlertDialog(ctx, msg, _DISMISS_DIALOG_ALONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, ctx.getString(R.string.error_alert), Toast.LENGTH_SHORT).show();
        }

    }

    public static void showAlertDialog(final Context ctx, String msg, final int dismissflag) {
        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);

        TextView alertHead = (TextView) dialog.findViewById(R.id.tvalerthead);
        alertHead.setText(ctx.getString(R.string.error_alert_title));
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
        alertContent.setText(msg);

        View line = (View) dialog.findViewById(R.id.centerLineDialog);
        Button btnCancel = (Button) dialog.findViewById(R.id.btncancel);
        Button btnOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);
        line.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dismissflag == ResponseErrorCodeHandler._RESET_ALL_ACTIVITY) {
                    PreferenceConnector.clearAllPreferences(ctx);
                    Intent intent = new Intent(ctx, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ctx.startActivity(intent);
                    ((Activity) ctx).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    ((Activity) ctx).finish();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
