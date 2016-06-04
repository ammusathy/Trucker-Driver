package driver.com.driver.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;
import driver.com.driver.model.ResponseParams.LoginResponseParams;


/**
 * Created by kalaivani on 3/23/2016.
 */
public class ChangeMobileNumber extends Activity

{
    TextView changeback,change_title;
    EditText forgotpassword;
    Button submit;
    public  Context ctx;
    Dialog dialog;

    ProgressDialog pDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RequestQueue queue;
    int StatusCode = 100;
    private Typeface Gibson_Light, HnThin, HnLight,Gibson_regular;


    String authvalue, mobile, useridvalue;
    public static String Tag = ChangeMobileNumber.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_mobilenumber);
        init();
        process();
    }


    private void init() {
        ctx=ChangeMobileNumber.this;
        submit = (Button) findViewById(R.id.change_btn_submit);
        forgotpassword=(EditText)findViewById(R.id.change_et_emailaddress);
        submit=(Button)findViewById(R.id.change_btn_submit) ;
        change_title=(TextView)findViewById(R.id.change_txt_title) ;
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        Gibson_regular=Typeface.createFromAsset(getAssets(),"Gibson-Regular.ttf");
        forgotpassword.setTypeface(HnThin);
        submit.setTypeface(Gibson_Light);
        change_title.setTypeface(HnThin);




    }

    private void process() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changemobilenumber();
            }
        });
    }

    private void changemobilenumber() {
        preferences = getSharedPreferences("NewInfo", Context.MODE_PRIVATE);// get shared preference and mode to be selected
        String mobile = preferences.getString("Mobile", null);//getting a userid and authtoken in login screen
        String userId = preferences.getString("UserId", null);
        String authToken = preferences.getString("AuthToken", null);
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-change-mobile";
        JSONObject object = null;

        try {
            object = new JSONObject();
            object.put("Mobile", mobile);
            object.put("UserId", userId);
            object.put("AuthToken", authToken);
            Log.d("Testing", mobile + userId + authToken);

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

                changealertdialog(ctx, IConstant.alert, Message, StatusCode);

            }


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


    public void changealertdialog(final Context context, String Title, String Content, final int Status) {

        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alerthead);

        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alertcontent);
        alertContent.setText(Content);
        dialog.show();

        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);
        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StatusCode == 100) {
                    dialog.dismiss();
                    Intent i = new Intent(ctx, VerifyMobileNumber.class);
                    startActivity(i);
//                     Intent intent = new Intent(Login.this, Login.class);
//                     startActivity(intent);
                } else
                    dialog.dismiss();
            }

            // DriverApplication.customProgressDialog.dismiss();


        });

    }
}






