package driver.com.driver.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.Constant;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;

/**
 * Created by androidusr1 on 27/5/16.
 */
public class CreateNewPasswordSetting extends Activity {
    Button btnsubmit, settings;
    EditText editnewpassword, editoldpassword, editconfirmpassword;
    TextView createnewpasswordlabel;
    RequestQueue queue;
    int type;
    SharedPreferences preferences;
    PreferenceConnector mPreferenceConnector;
    String newPasswordValue, confirmpasswordvalue, authtokenvalue, userIdvalue, oldpasswordvalue;
    Typeface Gibson_Light, HnBold, HnThin, HnLight, Gibson_Regular, GillSansStd;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewsetting);
        init();
        process();
        Log.i("CreateNewPassword :", "CreateNewPassword");

    }

    private void init() {
        ctx = CreateNewPasswordSetting.this;
        btnsubmit = (Button) findViewById(R.id.newpassword_btn_submit);
        settings = (Button) findViewById(R.id.newpassword_btn_settings);
        createnewpasswordlabel = (TextView) findViewById(R.id.txt_label);
        editoldpassword = (EditText) findViewById(R.id.newpassword_et_oldpassword);
        editconfirmpassword = (EditText) findViewById(R.id.newpassword_et_confirmpassword);
        editnewpassword = (EditText) findViewById(R.id.newpassword_et_newpassword);
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        btnsubmit.setTypeface(Gibson_Light);
        settings.setTypeface(Gibson_Light);
//        createnewpasswordlabel.setTypeface(HnBold);
        editconfirmpassword.setTypeface(HnThin);
        editnewpassword.setTypeface(    HnThin);
        editoldpassword.setTypeface(HnThin);

    }

    private void process() {

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type <= 0)
                    newPasswordValue = editnewpassword.getText().toString();
                confirmpasswordvalue = editconfirmpassword.getText().toString();

                if (newPasswordValue.length() <= 0)
                    DriverApplication.alertDialog(ctx, getResources().getString(R.string.error), "Please enter new  password");
                else if (confirmpasswordvalue.length() <= 0)
                    DriverApplication.alertDialog(ctx, getResources().getString(R.string.error), "Please enter confirm password");
                else if (newPasswordValue.length() <= 7)
                    DriverApplication.alertDialog(ctx, getResources().getString(R.string.error), "Password must be between 8-20 characters");

                else if (!newPasswordValue.equals(confirmpasswordvalue))
                    DriverApplication.alertDialog(ctx, getResources().getString(R.string.error), "Mismatched password and confirm password");

                else if (Constant.isConnectingToInternet(ctx)) {
                    Createnewpassword();//Method declaration
                    DriverApplication.progressdialogpopup(ctx, getResources().getString(R.string.loading));//Loader can be set
                    DriverApplication.customProgressDialog.show();
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Createnewpassword() {
        preferences = getSharedPreferences("Trucker", Context.MODE_PRIVATE);// get shared preference and mode to be selected
        String authToken = preferences.getString("authToken", null);//getting a userid and authtoken in login screen
        String userId = preferences.getString("Userid", null);
        String pass = PreferenceConnector.readString(ctx, PreferenceConnector._PREF_TEMP_PASSWORD, "");
        Log.d("Test", userId + " : " + authToken + pass + ":");//print both the result
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-update-password";
        JSONObject object = null;


        try {
            object = new JSONObject();
            object.put("AuthToken", authToken);
            object.put("UserId", userId);
            object.put("OldPassword", pass);
            object.put("NewPassword", newPasswordValue);
            Log.d("Test", authToken + " : " + userId + ":" + pass + newPasswordValue + ":");

        } catch (Exception e) {
            e.printStackTrace();

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response:", response.toString());
                GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                int StatusCode = Integer.parseInt(String.valueOf(responseData.getStatusCode()));
                String Message = responseData.getMessage();

                createalertdialog(ctx, IConstant.alert, Message, StatusCode);

            }

            //forgotalertdialog(ctx, IConstant.title, message, StatusCode);
            //

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Response: ", error.toString());
                String json;
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        Log.e("Error password-->", json);
                        try {
//                             Parsing json object response response will be a json object
                            if (json != null) {
//
                                JSONObject jsonObject = new JSONObject(json);
//
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ctx);
                                dlgAlert.setMessage(message);
                                dlgAlert.setTitle("Login ");
                                dlgAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //dismiss the dialog
                                                dialog.dismiss();

                                            }
                                        });
                                dlgAlert.setCancelable(true);
                                dlgAlert.create().show();
//

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    } catch (UnsupportedEncodingException e) {
                        Log.e("Error 111", e.getMessage());
                    }
                }

            }
        });

        // volley time out error
        request.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }


    public void createalertdialog(final Context mContext, String Title, String Content, final int Status) {
        final Dialog dialog = new Dialog(mContext, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alertcontent);
        alertContent.setText(Content);


        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);
        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status == 100) {
                    dialog.dismiss();
                    Intent intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                } else if (Status == 101)
                    dialog.dismiss();
                Intent intent = new Intent(ctx, Login.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }


}


