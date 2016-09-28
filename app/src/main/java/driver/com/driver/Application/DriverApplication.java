package driver.com.driver.Application;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;

import driver.com.driver.R;
import driver.com.driver.login.Login;
import driver.com.driver.model.GeneralParams.RequestKeyParams;


public class DriverApplication extends Application {

    public static Typeface Gibson_Light;
    public static String AuthToken = null;
    public static String userId = null;


    /**
     * CUSTOM PROGRESS BAR METHOD
     */
    public static Context mContext;

    public static ProgressDialog progressDialog;
    public static DriverApplication instance;
    public static RequestQueue mRequestQueue;
    public static int mSelectedSource = -1;
    public static int mSelectedDestination = -1;
    public static String mSelAdditionallabel = null;
    public static String mSelAdditionalValue = null;
    public static String mSelDocuments = null;
    /**
     * CUSTOM PROGRESS BAR METHOD
     */
    public static Dialog customProgressDialog = null;
    public static AnimationDrawable frameAnimation;
    public static ArrayList<String> array_additional_services = new ArrayList<String>();
    public static String regId = null;
    protected static String SENDER_ID = "543023108033";
    static String errorAlert;
    static String msg = "";
    private static GoogleCloudMessaging gcm = null;
    ;
    RetryPolicy policy;

    public static synchronized DriverApplication getInstance() {
        return instance;
    }

    public static void addAdditionalServices() {
        array_additional_services.add("Toll Charge");
        array_additional_services.add("Weight Fine");
        array_additional_services.add("Yard Storage");
        array_additional_services.add("Reweighing");
        array_additional_services.add("Lumper Charge");
        array_additional_services.add("PowerUsage Hours");
        array_additional_services.add("LayoverWeekday");
        array_additional_services.add("LayoverSolo");
    }

    public static void removeAdditionalService(String removeString) {
        array_additional_services.remove(removeString);

    }


    public static ArrayList<String> getArrayList() {
        return array_additional_services;
    }

    public static void alertDialog(final Context ctx, String Title, String Content) {
        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customdialog);
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alertcontent);
        alertContent.setText(Content);

        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);


        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }

        });
        dialog.show();

    }

    public static void getRegisterationID() {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                // TODO Auto-generated method stub

                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    regId = gcm.register(SENDER_ID);
                    Log.d("RegId", regId);
                    msg = "Device registered, registration ID=" + regId;


                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }
        }.execute(null, null, null);

    }

    public static void unauthorisedAlertDialog(final Context ctx, String Title, String Content) {
        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
        alertContent.setText(Content);

        // To hide cancel and line separator
        View line = (View) dialog.findViewById(R.id.centerLineDialog);
        Button btnDialogCancel = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_cancel);
        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);
        line.setVisibility(View.GONE);
        btnDialogCancel.setVisibility(View.GONE);
        btnDialogOk.setBackgroundResource(R.drawable.dialogbtnbackground);

        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        dialog.show();
    }

    /**
     * Default android progress bar
     */
    public static void showProgressDialog(Context ctx, String string) {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage(ctx.getString(R.string.loading));
        progressDialog.setCancelable(false);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public static void progressdialogpopup(Context ctx, String content) {
        if (customProgressDialog == null) {
            Log.d("newDialog", "newDialog");
            customProgressDialog = new Dialog(ctx, R.style.Dialog);
            customProgressDialog.setCancelable(false);
            customProgressDialog.setContentView(R.layout.custom_progressdialog_iphone);
            TextView msg = (TextView) customProgressDialog.findViewById(R.id.progress_message);
            final ImageView progress = (ImageView) customProgressDialog.findViewById(R.id.spinnerImageView);
            //progress.setBackground(R.anim.customprogressspin);
            progress.post(new Runnable() {
                @Override
                public void run() {
                    frameAnimation = (AnimationDrawable) progress.getBackground();
                    frameAnimation.start();
                }
            });

            msg.setText(content);
        }
    }

    /* Form the rquest params with key value*/
    public static String getToJSON(Object src, ArrayList<RequestKeyParams> additionalKeyValue) {
        Gson gDataBean = new Gson();
        String responseObj = null;
        responseObj = gDataBean.toJson(src);
        if (additionalKeyValue != null) {
            JsonElement jsonElement = gDataBean.toJsonTree(src);
            for (int i = 0; i < additionalKeyValue.size(); i++)
                jsonElement.getAsJsonObject()
                        .addProperty(additionalKeyValue.get(i).getRequestKey(), additionalKeyValue.get(i).getRequestValue());

            responseObj = gDataBean.toJson(jsonElement);
        }
        return responseObj;
    }

    public static Object getFromJSON(String responseValue, Class<?> classname) {
        Gson gDataBean = new Gson();
        return gDataBean.fromJson(responseValue, classname);
    }

    public static Bitmap getRoundedCornerImage(Bitmap bitmap) {
        int targetWidth = 300;
        int targetHeight = 300;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth),
                ((float) targetHeight)) / 2), Path.Direction.CCW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = bitmap;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    public static void volleyErrorResponse(Context ctx, VolleyError error) {
        if (DriverApplication.progressDialog.isShowing())
            DriverApplication.progressDialog.dismiss();
        //Toast.makeText(ctx, "Error: data" + error.toString(), Toast.LENGTH_LONG).show();
        if (error instanceof NetworkError)
            errorAlert = "Unable to connect the server, Please check your internet connection";
        else if ((error instanceof ServerError) || (error instanceof ParseError))
            errorAlert = "Something went wrong in server";
        else if (error instanceof AuthFailureError)
            errorAlert = "Server Authentication failure";
        else if (error instanceof NoConnectionError)
            errorAlert = "No Such Connection Exist";
        else if (error instanceof TimeoutError)
            errorAlert = "Connection Timed out";
      //  Toast.makeText(ctx, "Error: data" + errorAlert, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        instance = this;


    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
            Cache cache = new DiskBasedCache(instance.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(policy);

        getmRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object t) {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(t);
    }

    public class GetRegID extends AsyncTask<Void, Void, Void> {

        public GetRegID(Context mContext) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }


}