package driver.com.driver.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.Constant;
import driver.com.driver.constants.IConstant;

/**
 * Created by kalaivani on 3/7/2016.
 */
public class SplashScreen extends Activity {

    Context ctx;
    Handler handlerSplash;
    Runnable runnableSplash;
    Dialog dialog;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        init();
    }

    public void init() {
        ctx = SplashScreen.this;
        handlerSplash = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("isConnected***", Constant.isConnectingToInternet(ctx) + "");
        if (Constant.isConnectingToInternet(ctx)) {
            process();
        } else {
            dialog = new Dialog(ctx, R.style.Dialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.customdialog);

            TextView alertHead = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alerthead);
            alertHead.setText(ctx.getResources().getString(R.string.app_name));
            TextView alertContent = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alertcontent);
            alertContent.setText(IConstant._ERR_NO_INTERNET_CONNECTION);


            Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);
            btnDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.show();
        }
    }
/*Gcm Registration process*/
    public void process() {
        GCMRegistrar.checkDevice(ctx);
        GCMRegistrar.checkManifest(ctx);
        String regId = DriverApplication.getGCMRegistrationId(ctx);
        Log.d("GCM_REG_ID ", regId);

        handlerSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                int UserId = Integer.valueOf(PreferenceConnector.readString(ctx, PreferenceConnector._PREF_USER_ID, "0"));
                if (UserId == 0) {
                    intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    finish();
                } else {
                    intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    finish();
                }
            }
        }, 3000);
    }
}
