package driver.com.driver.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.Constant;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;

/**
 * Created by kalaivani on 3/7/2016.
 */
public class ForgotPassword extends Activity {

    @Bind(R.id.forgot_et_emailaddress)
    EditText emailAddress;
    @Bind(R.id.forgot_btn_submit)
    Button submit;
    @Bind(R.id.forgot_btn_backtosignin)
    Button backToSignUp;
    @Bind(R.id.forgot_txt_label)
    TextView label;
    String emailAddressValue;
    Dialog dialog;
    int Success = 1, Error = 0;
    Typeface Gibson_Light, HnBold, HnThin, HnLight;
    RequestQueue queue;
    private Context mContext;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        init();
        process();
    }

    public void init() {
        mContext = ForgotPassword.this;
        ButterKnife.bind(this);
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        submit.setTypeface(Gibson_Light);
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        emailAddress.setTypeface(HnThin);
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        label.setTypeface(HnThin);
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        backToSignUp.setTypeface(Gibson_Light);

        emailAddress.setText("kalai@yopmail.com");
    }

    public void process() {
        backToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  backMethod();*/
                intent = new Intent(mContext, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddressValue = emailAddress.getText().toString().trim();
                if (emailAddressValue.length() <= 0)
                    DriverApplication.alertDialog(mContext, IConstant.alert, "Please enter your email id");
                else if (Constant.isConnectingToInternet(mContext)) {
                    ForgotPaassword();//Method declaration
                    //DriverApplication.progressdialogpopup(mContext, getResources().getString(R.string.loading));//loader
                   // DriverApplication.customProgressDialog.show();
                }
            }
        });
    }

            // volley time out error
            /*request.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);

        }
*/

    private void ForgotPaassword() {
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-reset-password";
        JSONObject object = null;

        try {
            object = new JSONObject();
            object.put("Email", emailAddressValue);
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
                forgotalertdialog(mContext, IConstant.alert, Message, StatusCode);
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
                        Log.e("Error login-->", json);
                        try {
//                             Parsing json object response response will be a json object
                            if (json != null) {
//
                                JSONObject jsonObject = new JSONObject(json);
//
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ForgotPassword.this);
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


    public void forgotalertdialog(final Context mContext, String Title, String Content, final int Status) {
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
                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                    startActivity(intent);
                } else if (Status == 101)
                    dialog.dismiss();
                DriverApplication.customProgressDialog.dismiss();
            }
        });
        dialog.show();

    }
}

