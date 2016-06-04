package driver.com.driver.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

/**
 * Created by mansoor on 13/05/16.
 */
public class ChangeJobStatus extends AppCompatActivity {

    Button driver_enroute, driver_arriving, in_transit, delivered, cancelled, close;
    SharedPreferences preferences;
    String orderId;
    RequestQueue queue;
    int Status = 2;
    Context mContext;
    int Statusarrive = 3;
    int ontransit = 4;
    int statusdelivered = 5;
    int statuscancelled = 6;
    int requestaccepted = 1;
    int Success=100;
    private Typeface Gibson_Light, HnThin, HnLight,Gibson_regular;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changejobstatus);
        init();
        process();
        Intent intent = getIntent();
        orderId = intent.getStringExtra("OrderId");

    }

    private void init() {
        mContext = ChangeJobStatus.this;
        driver_enroute = (Button) findViewById(R.id.btn_driver_enroute);
        driver_arriving = (Button) findViewById(R.id.btn_driver_arriving);
        in_transit = (Button) findViewById(R.id.btn_on_transit);
        delivered = (Button) findViewById(R.id.btn_delivered);
        cancelled = (Button) findViewById(R.id.btn_cancelled);
        close = (Button) findViewById(R.id.btn_close);
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        Gibson_regular=Typeface.createFromAsset(getAssets(),"Gibson-Regular.ttf");
        delivered.setTypeface(Gibson_Light);
        driver_arriving.setTypeface(Gibson_Light);
        driver_enroute.setTypeface(Gibson_Light);
        cancelled.setTypeface(Gibson_Light);
        close.setTypeface(Gibson_Light);
        in_transit.setTypeface(Gibson_Light);
    }

    private void process() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        driver_enroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("Details", MODE_PRIVATE);
                String userId = preferences.getString("UserId", null);
                String authToken = preferences.getString("AuthToken", null);
                String orderId = preferences.getString("OrderId", null);
                Log.d("Test", orderId + ":" + userId + " : " + authToken);
                queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://truck.sdiphp.com/public/ws-driver-change-job-status";
                JSONObject object = null;
                try {
                    object = new JSONObject();
                    object.put("UserType", IConstant.UserType);
                    object.put("UserId", userId);
                    object.put("AuthToken", authToken);
                    object.put("OrderId", orderId);
                    object.put("ChangeStatus", Status);
                    Log.d("Test", String.valueOf(Status));
                    Log.d("Test", orderId);
                    Log.d("Test", IConstant.UserType);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test", response.toString());
                        if (response.toString().equals(Success)){
                            finish();
                        }
                        else{
                            finish();
                        }
                        //GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                        //int StatusCode = responseData.getStatusCode();
                        //String Message = responseData.getMessage();

                        //statusalertdialog(mContext, IConstant.alert, Message, StatusCode);
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
                                Log.e("Error list-->", json);
                                try {
//                             Parsing json object response response will be a json object
                                    if (json != null) {
//
                                        JSONObject jsonObject = new JSONObject(json);
//
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getApplicationContext());
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
        });


        driver_arriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("Details", MODE_PRIVATE);
                String userId = preferences.getString("UserId", null);
                String authToken = preferences.getString("AuthToken", null);
                String orderId = preferences.getString("OrderId", null);
                Log.d("Test", orderId + ":" + userId + " : " + authToken);
                queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://truck.sdiphp.com/public/ws-driver-change-job-status";
                JSONObject object = null;
                try {
                    object = new JSONObject();
                    object.put("UserType", IConstant.UserType);
                    object.put("UserId", userId);
                    object.put("AuthToken", authToken);
                    object.put("OrderId", orderId);
                    object.put("ChangeStatus", Statusarrive);
                    Log.d("Test", String.valueOf(Statusarrive));
                    Log.d("Test", orderId);
                    Log.d("Test", IConstant.UserType);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test", response.toString());
                        if (response.toString().equals(Success)){
                            finish();
                        }
                        else{
                            finish();
                        }
                        GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                        int StatusCode = responseData.getStatusCode();
                        String Message = responseData.getMessage();

                        //statusalertdialog(mContext, IConstant.alert, Message, StatusCode);
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
                                Log.e("Error list-->", json);
                                try {
//                             Parsing json object response response will be a json object
                                    if (json != null) {
//
                                        JSONObject jsonObject = new JSONObject(json);
//
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getApplicationContext());
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
        });


        in_transit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("Details", MODE_PRIVATE);
                String userId = preferences.getString("UserId", null);
                String authToken = preferences.getString("AuthToken", null);
                String orderId = preferences.getString("OrderId", null);
                Log.d("Test", orderId + ":" + userId + " : " + authToken);
                queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://truck.sdiphp.com/public/ws-driver-change-job-status";
                JSONObject object = null;
                try {
                    object = new JSONObject();
                    object.put("UserType", IConstant.UserType);
                    object.put("UserId", userId);
                    object.put("AuthToken", authToken);
                    object.put("OrderId", orderId);
                    object.put("ChangeStatus", ontransit);
                    Log.d("Test", String.valueOf(ontransit));
                    Log.d("Test", orderId);
                    Log.d("Test", IConstant.UserType);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test", response.toString());
                        if (response.toString().equals(Success)){
                            finish();
                        }
                        else{
                            finish();
                        }
                        GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                        int StatusCode = responseData.getStatusCode();
                        String Message = responseData.getMessage();

                        // statusalertdialog(mContext, IConstant.alert, Message, StatusCode);
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
                                Log.e("Error list-->", json);
                                try {
//                             Parsing json object response response will be a json object
                                    if (json != null) {
//
                                        JSONObject jsonObject = new JSONObject(json);
//
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getApplicationContext());
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
        });

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("Details", MODE_PRIVATE);
                String userId = preferences.getString("UserId", null);
                String authToken = preferences.getString("AuthToken", null);
                String orderId = preferences.getString("OrderId", null);
                Log.d("Trucker", orderId + ":" + userId + " : " + authToken);
                queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://truck.sdiphp.com/public/ws-driver-change-job-status";
                JSONObject object = null;
                try {
                    object = new JSONObject();
                    object.put("UserType", IConstant.UserType);
                    object.put("UserId", userId);
                    object.put("AuthToken", authToken);
                    object.put("OrderId", orderId);
                    object.put("ChangeStatus", statusdelivered);
                    Log.d("Test", String.valueOf(statusdelivered));
                    Log.d("Test", orderId);
                    Log.d("Test", IConstant.UserType);
                    Log.d("Trucker", object.toString());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test", response.toString());
                        if (response.toString().equals(Success)){
                            finish();
                        }
                        else{
                            finish();
                        }
                        GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                        int StatusCode = responseData.getStatusCode();
                        String Message = responseData.getMessage();

                        //statusalertdialog(mContext, IConstant.alert, Message, StatusCode);
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
                                Log.e("Error list-->", json);
                                try {
//                             Parsing json object response response will be a json object
                                    if (json != null) {
//
                                        JSONObject jsonObject = new JSONObject(json);
//
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getApplicationContext());
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
        });

        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("Details", MODE_PRIVATE);
                String userId = preferences.getString("UserId", null);
                String authToken = preferences.getString("AuthToken", null);
                String orderId = preferences.getString("OrderId", null);
                Log.d("Test", orderId + ":" + userId + " : " + authToken);
                queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://truck.sdiphp.com/public/ws-driver-change-job-status";
                JSONObject object = null;
                try {
                    object = new JSONObject();
                    object.put("UserType", IConstant.UserType);
                    object.put("UserId", userId);
                    object.put("AuthToken", authToken);
                    object.put("OrderId", orderId);
                    object.put("ChangeStatus", statuscancelled);
                    Log.d("Test", String.valueOf(statuscancelled));
                    Log.d("Test", orderId);
                    Log.d("Test", IConstant.UserType);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test", response.toString());
                        if (response.toString().equals(Success)){
                            finish();
                        }
                        else{
                            finish();
                        }
                        GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                        int StatusCode = responseData.getStatusCode();
                        String Message = responseData.getMessage();

                        // statusalertdialog(mContext, IConstant.alert, Message, StatusCode);
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
                                Log.e("Error list-->", json);
                                try {
//                             Parsing json object response response will be a json object
                                    if (json != null) {
//
                                        JSONObject jsonObject = new JSONObject(json);
//
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getApplicationContext());
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
        });
    }

    public void statusalertdialog(final Context mContext, String Title, String Content, final int Status) {
        final Dialog dialog = new Dialog(mContext, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
        alertContent.setText(Content);

        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);
        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status == 100) {
                    dialog.dismiss();
                    finish();
                    //Intent intent=new Intent(context,JobList.class);
                    //startActivity(intent);
                } else
                    dialog.dismiss();
                //DriverApplication.customProgressDialog.dismiss();

            }
        });
        dialog.show();

    }
}
