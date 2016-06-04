package driver.com.driver.screens;

/**
 * Created by kalaivani on 3/6/2016.
 */

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.Constant;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.LoginResponseParams;


public class Login extends Activity {

    @Bind(R.id.login_et_emailaddress)
    EditText emailAddress;
    @Bind(R.id.login_et_password)
    EditText password;
    @Bind(R.id.login_btn_submit)
    Button submit;
    @Bind(R.id.login_btn_forgotpassword)
    Button forgotPassword;
    @Bind(R.id.login_chk_rememberme)
    CheckBox rememberMe;
    String emailAddressValue;
    Dialog dialog;
    int Success = 100, Error = 99;
    int Mobilenumber = 103;
    private Typeface Gibson_Light, HnThin, HnLight;
    String userTypeValue = "";
    String deviceUDIDValue;
    String regIDValue = "";
    String url = "";
    RequestQueue queue;
    int ChangePassword = 104;
    int Verifymobilenumber = 105;

    int API_ID = 1;
    int ResponseCode;
    int StatusCode;
    String userNameValue = "", passwordValue = "";
    SharedPreferences preferencese;
    SharedPreferences.Editor editor;
    private Context ctx;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_page);
        this.setFinishOnTouchOutside(true);

        setResult(RESULT_OK,intent);

        init();
        process();
    }

    public void init() {
        ctx = Login.this;
        ButterKnife.bind(this);
        try {
            Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
            HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
            HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        submit.setTypeface(Gibson_Light);
        emailAddress.setTypeface(HnThin);
        password.setTypeface(HnThin);
        rememberMe.setTypeface(HnLight);
        forgotPassword.setTypeface(HnLight);

        int rememberMeValue = Integer.parseInt(PreferenceConnector.readStringRemember(ctx, PreferenceConnector._PREF_REMEMBER_ME, "0"));
        if (rememberMeValue == 1) {
            rememberMe.setChecked(true);
            emailAddress.setText(PreferenceConnector.readStringRemember(ctx, PreferenceConnector._PREF_EMAIL_ID, ""));
            password.setText(PreferenceConnector.readStringRemember(ctx, PreferenceConnector._PREF_PASSWORD, ""));
        } else {
            try {
                rememberMe.setChecked(false);
                emailAddress.setText("");
                password.setText("");

                // Added by karthick
                emailAddress.setText("trucker_twilight@yopmail.com");
                password.setText("12345678");
                deviceUDIDValue = Constant.getDeviceID(ctx);
                getGCM_ID();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*REGISTER AND GET THE GCM ID*/
    private void getGCM_ID() {
        if (regIDValue.length() <= 0)
            regIDValue = DriverApplication.getGCMRegistrationId(ctx);
        Log.d("GCM_REG_ID ", regIDValue);
    }


    public void process() {

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ForgotPassword.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddressValue = emailAddress.getText().toString().trim();
                passwordValue = password.getText().toString().trim();
                if (emailAddressValue.length() <= 0)
                    DriverApplication.alertDialog(ctx, IConstant.alert, "Please enter your email id.");
                else if (!emailAddressValue.matches(IConstant._EMAIL_PATTERN))
                    DriverApplication.alertDialog(ctx, IConstant.alert, ctx.getResources().getString(R.string.invalidemailid));
                else if (passwordValue.length() <= 0)
                    DriverApplication.alertDialog(ctx, IConstant.alert, "Please enter your password.");
                else if (passwordValue.length() < 8)
                    DriverApplication.alertDialog(ctx, IConstant.alert, "Please enter valid Password");

                else if (Constant.isConnectingToInternet(ctx)) {
                    Loginrequest();//Method Declaration
                    //DriverApplication.progressdialogpopup(ctx, getResources().getString(R.string.loading));
                    //DriverApplication.customProgressDialog.show();
                   /* Intent i=new Intent(Login.this,F)*/
                }
            }

         /*   private void Loginrequest() {

                String usertype = getIntent().getExtras().getString("usertype");
                String username = getIntent().getExtras().getString("username");
                String password = getIntent().getExtras().getString("password");
                String deviceudid = getIntent().getExtras().getString("deviceudid");
                String devicetype = getIntent().getExtras().getString("devicetype");
                String regid = getIntent().getExtras().getString("regid");
                JSONObject parentData = new JSONObject();


                try {
                    parentData.put("UserType", usertype);
                    parentData.put("Username", username);
                    parentData.put("Password", password);
                    parentData.put("DeviceUDID", deviceudid);
                    parentData.put("DeviceType", devicetype);
                    parentData.put("RegId", regid);
                    System.out.println("LoginRequest >" + parentData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String url = "http://truck.sdiphp.com/public/ws-login";
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, parentData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("resend code", response.toString());
                        try {
                            // Parsing json object response response will be a json object
                            if (response != null) {
                                response.getString("Message");
                                System.out.println("parant josn object id -->" + response.getString("Message"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("verify page", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        // hide the progress dialog
                    }
                }) {

                };

                // Adding request to request queue

                AppController.getInstance().addToRequestQueue(jsonObjReq);
            }
        });*/
        });
    }

    public void Loginrequest() {

        queue = Volley.newRequestQueue(this);


        String url = "http://truck.sdiphp.com/public/ws-login";
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("UserType", IConstant.UserType);
            JSONObject loginDetails = new JSONObject();
            loginDetails.put("Username", emailAddressValue);
            loginDetails.put("Password", passwordValue);
            loginDetails.put("DeviceUDID", deviceUDIDValue);
            loginDetails.put("DeviceType", IConstant.DeviceType);
            loginDetails.put("RegId", regIDValue);
            object.put("LoginDetails", loginDetails);
            PreferenceConnector.writeString(getApplicationContext(), PreferenceConnector._PREF_TEMP_PASSWORD, passwordValue);
            Log.d("Test", passwordValue + " : ");
        } catch (Exception e) {
            e.printStackTrace();

        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response:", response.toString());

                // DriverApplication.customProgressDialog.dismiss();
                preferencese = getSharedPreferences("Trucker", MODE_PRIVATE);//call shared Preference
                editor = preferencese.edit();//editor preferences
                String userId = null;//Getting values from user Id and authToken
                try {
                    userId = response.getString("UserId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String authToken = null;
                try {
                    authToken = response.getString("AuthToken");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("Test", userId + " : " + authToken);
                editor.putString("Userid", userId);
                editor.putString("authToken", authToken);
                editor.commit();


               /* try {

                    String isACtivated = response.getString("isActivated");
                    String isPasswordChanged = response.getString("isPasswordChanged");
                    if (isACtivated.equals("true")) {
                        preferencese = getSharedPreferences("Trucker", MODE_PRIVATE);
                        editor = preferencese.edit();
                        String userId = response.getString("UserId");
                        String authToken = response.getString("AuthToken");
                        Log.d("Test", userId + " : " + authToken);
                        editor.putString("Userid", userId);
                        editor.putString("authToken", authToken);
                        editor.commit();
                        dialog.show();
                        Intent intent = new Intent(Login.this, SlidingDrawer.class);
                        startActivity(intent);
                        dialog.dismiss();
                    } else {
                        Intent intent = new Intent(Login.this, CreateNewPassword.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }
*/
                LoginResponseParams responseData = (LoginResponseParams) DriverApplication.getFromJSON(response.toString(), LoginResponseParams.class);//Loginresponse model class
                int StatusCode = Integer.parseInt(responseData.getStatusCode());
                String message = responseData.getMessage();

                if (StatusCode == Success) {
                    Intent intent = new Intent(getApplicationContext(), SlidingDrawer.class);
                    startActivity(intent);
                } else {
                    //DriverApplication.customProgressDialog.isShowing();
                    //DriverApplication.customProgressDialog.dismiss();
                    //if (StatusCode == Success) {
                    //  intent = new Intent(ctx, SlidingDrawer.class);
                    //startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    //finish();
                    //} else if (StatusCode == ChangePassword) {
                    //  intent = new Intent(ctx, ForgotPassword.class);
                    //startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    //finish();
                    //} else {

                    alertdialog(ctx, IConstant.alert, message, StatusCode);//alertdialog for login process
                    // DriverApplication.customProgressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Response: ", error.toString());//print if it is error response
                String json;
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        Log.e("Error login-->", json);
                        try {
//                             Parsing json object response response will be a json object
                            if (json != null) {
//
                                JSONObject jsonObject = new JSONObject(json);//creating a new json object
                                String message = jsonObject.getString("message");//print message in json
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();//toast message
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Login.this);
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

                    } catch (UnsupportedEncodingException e) {//when a stack trace and detail message filled in this msg will occur
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

    public void alertdialog(final Context ctx, String Title, String Content, final int Status) {

        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alertcontent);
        alertContent.setText(Content);

        // To hide cancel and line separator
        View line = (View) dialog.findViewById(R.id.centerLineDialog);

        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);
        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status == Verifymobilenumber) {
                    dialog.dismiss();
                    Intent intent = new Intent(Login.this, VerifyMobileNumber.class);
                    startActivity(intent);
                }
                if (Status == ChangePassword) {
                    dialog.dismiss();
                    Intent intent = new Intent(Login.this, CreateNewPassword.class);
                    startActivity(intent);

                } else if (Status == Mobilenumber) {
                    dialog.dismiss();
                    Intent intent = new Intent(Login.this, VerifyMobileNumber.class);
                    startActivity(intent);

                } else {
                    dialog.dismiss();
//                     Intent intent = new Intent(Login.this, Login.class);
//                     startActivity(intent);

                }
            }
        });
        dialog.show();
        // DriverApplication.customProgressDialog.dismiss();

    }

}