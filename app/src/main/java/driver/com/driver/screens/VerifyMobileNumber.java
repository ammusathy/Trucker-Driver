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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.BuildConfig;
import driver.com.driver.R;
import driver.com.driver.constants.AppController;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;
import driver.com.driver.model.ResponseParams.LoginResponseParams;

public class VerifyMobileNumber extends Activity implements View.OnClickListener {

    ImageView back;
    TextView content,submit,title,txt_one,txt_two,txt_three,txt_four;
    Button one,two,three,four,five,six,seven,eight,nine,zero,del,resend,changenumber;
    Intent intent;
    String verificationNumber = "";
   public Context ctx;
    Dialog dialog;
    int Success = 1, Error = 0;
    StringBuilder stringBuilder;
    String UserId = "", authToken = "", Mobile="";
    String message;
    public Typeface HelveticaNeuebold;
    int StatusCode;
    SharedPreferences preferences;
    RequestQueue queue;
    String mobileNoValue,userid;
    SharedPreferences.Editor editor;
    ProgressDialog pDialog;
    Typeface Gibson_Light, HnBold, HnThin, HnLight, Gibson_Regular, GillSansStd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_mobilenumber);
        Intent intent = getIntent();

        init();
        process();
    }

    public void init() {
        ctx = VerifyMobileNumber.this;
        back = (ImageView) findViewById(R.id.verify_txt_backicon);
        submit = (TextView) findViewById(R.id.verify_txt_submit);
        txt_one = (TextView) findViewById(R.id.verify_txt_one);
        txt_two = (TextView) findViewById(R.id.verify_txt_two);
        txt_three = (TextView) findViewById(R.id.verify_txt_three);
        txt_four = (TextView) findViewById(R.id.verify_txt_four);
        one = (Button) findViewById(R.id.verify_btn_one);
        two = (Button) findViewById(R.id.verify_btn_two);
        three = (Button) findViewById(R.id.verify_btn_three);
        four = (Button) findViewById(R.id.verify_btn_four);
        five = (Button) findViewById(R.id.verify_btn_five);
        six = (Button) findViewById(R.id.verify_btn_six);
        seven = (Button) findViewById(R.id.verify_btn_seven);
        eight = (Button) findViewById(R.id.verify_btn_eight);
        nine = (Button) findViewById(R.id.verify_btn_nine);
        zero = (Button) findViewById(R.id.verify_btn_zero);
        del = (Button) findViewById(R.id.verify_btn_del);
        resend = (Button) findViewById(R.id.verify_btn_resend);
        changenumber = (Button) findViewById(R.id.verify_btn_changenumber);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        del.setOnClickListener(this);

        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        submit.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        one.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        two.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        three.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        four.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        five.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        six.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        seven.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        eight.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        nine.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        zero.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        del.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        resend.setTypeface(HelveticaNeuebold);
        HelveticaNeuebold= Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        changenumber.setTypeface(HelveticaNeuebold);


    }

    public void process() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMethod();
            }
        });

        changenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ChangeMobileNumber.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjectRequest();
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendObjRequest();
            }
        });
    }

    private void ResendObjRequest() {
        preferences = getSharedPreferences("Trucker", Context.MODE_PRIVATE);// get shared preference and mode to be selected
        String mobile = preferences.getString("Mobile", null);//getting a userid and authtoken in login screen
        String userId = preferences.getString("UserId", null);
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-resend-code";
        JSONObject object = null;

        try {
            object=new JSONObject();
            object.put("Mobile", mobile);
            object.put("UserId", userId);

        } catch (Exception e) {
            e.printStackTrace();

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response:", response.toString());
                LoginResponseParams responseData = (LoginResponseParams) DriverApplication.getFromJSON(response.toString(), LoginResponseParams.class);//Loginresponse model class
                int StatusCode = Integer.parseInt(responseData.getStatusCode());
                String message = responseData.getMessage();

               resendalertdialog(ctx, IConstant.alert, message, StatusCode);


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

        public void resendalertdialog(final Context ctx, String Title, String Content, final int Status) {

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
                    dialog.show();

            Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);
            // if button is clicked, close the custom dialog
            btnDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Status == 100) {
                        dialog.dismiss();

//                     Intent intent = new Intent(Login.this, Login.class);
//                     startActivity(intent);
                    } else {
                        dialog.dismiss();
                    }

                    // DriverApplication.customProgressDialog.dismiss();

                }


            });
        }




    private void makeJsonObjectRequest() {
        preferences = getSharedPreferences("Trucker", Context.MODE_PRIVATE);// get shared preference and mode to be selected
        String mobile = preferences.getString("Mobile", null);//getting a userid and authtoken in login screen
        String userId = preferences.getString("UserId", null);
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-mobile-verify";
        JSONObject object = null;

        try {
            object = new JSONObject();
            object.put("Mobile", mobile);
            object.put("UserId", userId);
            object.put("ActivationCode",verificationNumber);

            Log.d("Mobile",mobile +userId +verificationNumber);

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

                verifydialog(ctx, IConstant.alert, Message, StatusCode);



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

        public void verifydialog(final Context ctx, String Title, String Content, final int Status) {

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
                        Intent i = new Intent(VerifyMobileNumber.this, Login.class);
                        startActivity(i);
//                     Intent intent = new Intent(Login.this, Login.class);
//                     startActivity(intent);
                    } else {
                        dialog.dismiss();
                    }

                    // DriverApplication.customProgressDialog.dismiss();

                }


            });
        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backMethod();
    }

    public void backMethod() {
        intent = new Intent(ctx, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.verify_btn_one:
                setNumber("1");
                break;
            case R.id.verify_btn_two:
                setNumber("2");
                break;
            case R.id.verify_btn_three:
                setNumber("3");
                break;
            case R.id.verify_btn_four:
                setNumber("4");
                break;
            case R.id.verify_btn_five:
                setNumber("5");
                break;
            case R.id.verify_btn_six:
                setNumber("6");
                break;
            case R.id.verify_btn_seven:
                setNumber("7");
                break;
            case R.id.verify_btn_eight:
                setNumber("8");
                break;
            case R.id.verify_btn_nine:
                setNumber("9");
                break;
            case R.id.verify_btn_zero:
                setNumber("0");
                break;
            case R.id.verify_btn_del:
                int size = verificationNumber.length();
                if (size > 0) {
                    String temp = verificationNumber;
                    verificationNumber = "";
                    for (int i = 0; i < size - 1; i++) {
                        verificationNumber = verificationNumber + temp.charAt(i);
                    }
                }
                setVerifyBackground();
                break;
        }
        setVerifyBackground();
    }

    public void setNumber(String value) {
        if (verificationNumber.length() < 4) {
            verificationNumber = verificationNumber + value;
        }
    }

    private void setVerifyBackground() {
        txt_one.setBackgroundResource(R.drawable.whitecirclebackground);
        txt_two.setBackgroundResource(R.drawable.whitecirclebackground);
        txt_three.setBackgroundResource(R.drawable.whitecirclebackground);
        txt_four.setBackgroundResource(R.drawable.whitecirclebackground);
        int size = verificationNumber.length();
        switch (size) {
            case 1:
                txt_one.setBackgroundResource(R.drawable.redcirclebackground);
                break;
            case 2:
                txt_one.setBackgroundResource(R.drawable.redcirclebackground);
                txt_two.setBackgroundResource(R.drawable.redcirclebackground);
                break;
            case 3:
                txt_one.setBackgroundResource(R.drawable.redcirclebackground);
                txt_two.setBackgroundResource(R.drawable.redcirclebackground);
                txt_three.setBackgroundResource(R.drawable.redcirclebackground);
                break;
            case 4:
                txt_one.setBackgroundResource(R.drawable.redcirclebackground);
                txt_two.setBackgroundResource(R.drawable.redcirclebackground);
                txt_three.setBackgroundResource(R.drawable.redcirclebackground);
                txt_four.setBackgroundResource(R.drawable.redcirclebackground);
                break;
        }
    }

    }



