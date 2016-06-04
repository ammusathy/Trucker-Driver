package driver.com.driver.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;

/**
 * Created by kalaivani on 3/7/2016.
 */
public class NewJob extends AppCompatActivity {
    Button btnaccept, btndecline, btn_pickupdate, btn_pickuptime, btn_deliverydate, btn_deliverytime,  btn_viewdetails;
    ImageView image_back, change_order;
    TextView txt_border, txt_nostraps, txt_stopintransit, txt_hazardous,txt_order,notes,et_source,et_destination;
    SharedPreferences preferences;
    RequestQueue queue;
    TextView txt_trucktype,borders,nostraps,stopintransit,hazardous,additional ,shipper;
    Context context;
    TextView txt_price, txt_label;
    SharedPreferences.Editor editor;
    String userId, authToken, orderId;
    int Success = 100;
    TextView divider5,divider8,divider9,divider10,divider11;
    String trucktype = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_job);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("OrderId");
        init();
        process();

        Log.i("NewJob :", "NewJob");
    }


    private void init() {
        context = NewJob.this;
        divider5=(TextView)findViewById(R.id.newjob_txt_divider5);
        divider8=(TextView)findViewById(R.id.newjob_txt_divider8);
        divider9=(TextView)findViewById(R.id.newjob_txt_divider9);
        divider10=(TextView)findViewById(R.id.newjob_txt_divider10);
        divider11=(TextView)findViewById(R.id.newjob_txt_divider11);
        image_back = (ImageView) findViewById(R.id.newjob_image_back);
        btnaccept = (Button) findViewById(R.id.newjob_btn_accept);
        btndecline = (Button) findViewById(R.id.newjob_btn_decline);
        txt_trucktype = (TextView) findViewById(R.id.newjob_btn_trucktype);
        btn_pickupdate = (Button) findViewById(R.id.newjob_btn_pickupdate);
        btn_pickuptime = (Button) findViewById(R.id.newjob_btn_pickuptime);
        btn_deliverydate = (Button) findViewById(R.id.newjob_btn_deliverydate);
        btn_deliverytime = (Button) findViewById(R.id.newjob_btn_deliverytime);
        additional = (TextView) findViewById(R.id.newjob_btn_additionalservices);
        btn_viewdetails = (Button) findViewById(R.id.newjob_btn_viewdetails);
        change_order = (ImageView) findViewById(R.id.newjob_image_changeorder);
        et_source = (TextView) findViewById(R.id.newjob_et_source);
        et_destination = (TextView) findViewById(R.id.newjob_et_destination);
        notes = (TextView) findViewById(R.id.newjob_et_notes);
        txt_price = (TextView) findViewById(R.id.newjob_txt_price);
        txt_label = (TextView) findViewById(R.id.newjob_txt_newjob);
        shipper=(TextView)findViewById(R.id.btn_shipperservice);
        borders = (TextView) findViewById(R.id.newjob_btn_border);
        nostraps = (TextView) findViewById(R.id.newjob_btn_nostraps);
        txt_order=(TextView)findViewById(R.id.newjob_txtorder) ;
        stopintransit = (TextView) findViewById(R.id.newjob_btn_stopintransit);
        hazardous = (TextView) findViewById(R.id.newjob_btn_hazardousmaterial);
        txt_border = (TextView) findViewById(R.id.txt_border);
        txt_nostraps = (TextView) findViewById(R.id.txt_nostraps);
        txt_hazardous = (TextView) findViewById(R.id.txt_hazardousmaterial);
        txt_stopintransit = (TextView) findViewById(R.id.txt_stopintransit);

    }


    private void process() {
        ReadShipment();
        TruckType();
        DriverApplication.progressdialogpopup(context, getResources().getString(R.string.loading));
        DriverApplication.customProgressDialog.show();
        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accept();
                DriverApplication.progressdialogpopup(context, getResources().getString(R.string.loading));
                DriverApplication.customProgressDialog.show();
            }

        });


      /*  change_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = et_source.getText().toString();
                String text2 = et_destination.getText().toString();
                et_source.setText(text2);
                et_destination.setText(text1);
            }
        });*/
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btndecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Declinejob();
                DriverApplication.progressdialogpopup(context, getResources().getString(R.string.loading));
                DriverApplication.customProgressDialog.show();
            }
        });


    }

    private void Declinejob() {
        preferences = getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        String userId = preferences.getString("UserId", "");
        String authToken = preferences.getString("AuthToken", "");
        Log.d("Test", orderId + ":" + userId + " : " + authToken);
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-cancel-order";
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("UserType", IConstant.UserType);
            object.put("UserId", userId);
            object.put("AuthToken", authToken);
            object.put("OrderId", orderId);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Test", response.toString());
                DriverApplication.customProgressDialog.dismiss();
                GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                int StatusCode = responseData.getStatusCode();
                String Message = responseData.getMessage();

                declinejobalert(context, IConstant.alert, Message, StatusCode);
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


    public void declinejobalert(final Context mContext, String Title, String Content, final int Status) {
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

    private void TruckType() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-truck-type";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Test", "response" + response);
                        Log.d("Test", response.toString());


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
    }








    private void ReadShipment() {
        preferences = getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        String userId = preferences.getString("UserId", "");
        String authToken = preferences.getString("AuthToken", "");
        Log.d("Test", orderId + ":" + userId + " : " + authToken);
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-read-order";
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("UserType", IConstant.UserType);
            object.put("UserId", userId);
            object.put("AuthToken", authToken);
            object.put("OrderId", orderId);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Test", response.toString());
                DriverApplication.customProgressDialog.dismiss();
//                 ReadShipmentResponse readShipmentResponse;
                try {
                    if (response.getString("Message").equals("Success")) {
                        JSONObject shipment = response.getJSONObject("Shipment");
                        JSONObject source = shipment.getJSONObject("Source");
                        JSONObject destination = shipment.getJSONObject("Destination");
                        String toAdd = destination.getString("ToAddress");
                        String fromAdd = source.getString("FromAddress");
                        et_destination.setText(toAdd);
                        et_source.setText(fromAdd);
                        Log.d("Test", fromAdd);
                        JSONObject payment = response.getJSONObject("Payment");
                        String total = payment.getString("TotalPayable");
                        txt_price.setText(total);
                        String trucktype = response.getString("TruckType");
                        if ((response.getString("TruckType")).equals("1")){
                            txt_trucktype.setText("Vented Van");
                        }
                        String pickdate = response.getString("PickupDate");
                        btn_pickupdate.setText(pickdate.toString());
                        String deliverydate = response.getString("DeliveryDate");
                        btn_deliverydate.setText(deliverydate.toString());
                        String picktime = response.getString("PickupTime");
                        btn_pickuptime.setText(picktime.toString());
                        String deliverytime = response.getString("DeliveryTime");
                        btn_deliverytime.setText(deliverytime.toString());
                        String orderid=response.getString("OrderId");
                        txt_order.setText(orderid);
                        String etnotes = response.getString("Notes");
                        notes.setText(etnotes.toString());
                        JSONObject accessories = response.getJSONObject("Accessories");
                        String accessory = accessories.getString("HazardousMaterial");
                        if ((accessories.getString("HazardousMaterial")).length()<=0){
                            txt_hazardous.setVisibility(View.GONE);
                            hazardous.setVisibility(View.GONE);
                            divider10.setVisibility(View.GONE);
                        }else{
                        txt_hazardous.setText(accessory);}
                        String border = accessories.getString("BorderCrossing");
                        if ((accessories.getString("BorderCrossing")).length()<=0){
                            txt_border.setVisibility(View.GONE);
                             borders.setVisibility(View.GONE);
                            divider5.setVisibility(View.GONE);
                        }else{
                        txt_border.setText(border);}
                        String straps = accessories.getString("NoOfStraps");
                        if ((accessories.getString("NoOfStraps")).length()<=0){
                            nostraps.setVisibility(View.GONE);
                            txt_nostraps.setVisibility(View.GONE);
                            divider8.setVisibility(View.GONE);
                        }else{
                        txt_nostraps.setText(straps);}
                        String transit = accessories.getString("StopsinTransit");
                        if ((accessories.getString("StopsinTransit")).length()<=0){
                            stopintransit.setVisibility(View.GONE);
                            txt_stopintransit.setVisibility(View.GONE);
                            divider9.setVisibility(View.GONE);
                        }else {
                            txt_stopintransit.setText(transit);
                        }
                        /* JSONObject accessories=response.getJSONObject("Accessories");
                        String stopintransit=accessories.getString("StopsinTransit");
                        btn_additionalservice.setText(stopintransit);*/
                       /* String noofstraps=accessories.getString("NoOfStraps");
                        btn_additionalservice.setText(noofstraps);
                        String hazardous=accessories.getString("HazardousMaterial");
                        btn_additionalservice.setText(hazardous);
                        String bordercrossing=accessories.getString("BorderCrossing");
                        btn_additionalservice.setText(bordercrossing);*/

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                PaymentResponse paymentResponse;
//
//                Gson gson = new Gson();
//                readShipmentResponse = gson.fromJson(response.toString(), ReadShipmentResponse.class);
//
//
//
//                System.out.println("read = " + readShipmentResponse.getMessage());
//                btn_pickupdate.setText(readShipmentResponse.PickupDate);
//                btn_pickuptime.setText(readShipmentResponse.PickupTime);
//                btn_deliverydate.setText(readShipmentResponse.DeliveryDate);
//                btn_deliverytime.setText(readShipmentResponse.DeliveryTime);
//                notes.setText(readShipmentResponse.Notes);
//                btntrucktype.setText(readShipmentResponse.TruckType);
//                txt_label.setText(readShipmentResponse.OrderStatusLabel);
//                et_source.setText(readShipmentResponse.getShipmentResponse().getSource().FromAddress);

                /*txt_price.setText(readShipmentResponse.getPayment().TotalPayable);
                txt_price.setText(readShipmentResponse.getPayment().Total);
                destination.setText(shipmentResponse.getDestinationResponse().ToAddress);*/

              /*  btn_additionalservice.setText(readShipmentResponse.getAccessoriesResponse().BorderCrossing);
                btn_additionalservice.setText(readShipmentResponse.getAccessoriesResponse().HazardousMaterial);
                btn_additionalservice.setText(readShipmentResponse.getAccessoriesResponse().NoOfStraps);
                btn_additionalservice.setText(readShipmentResponse.getAccessoriesResponse().StopsinTransit);
               */

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


    private void Accept() {
        preferences = getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        String authToken = preferences.getString("AuthToken", null);//getting a userid and authtoken in login screen
        String userId = preferences.getString("UserId", null);

        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-accept-job";
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("UserType", IConstant.UserType);
            object.put("UserId", userId);
            object.put("AuthToken", authToken);
            object.put("OrderId", orderId);
            Log.d("Test", orderId);
            Log.d("Test", IConstant.UserType);
            Log.d("Test", userId);
            Log.d("Test", authToken);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Test", response.toString());
                DriverApplication.customProgressDialog.dismiss();

                try {
                    if (response.getInt("StatusCode") == 100) {
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                    if(response.getString("Message").equals())

                //GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                //int StatusCode = responseData.getStatusCode();
                //String Message = responseData.getMessage();

                //newjobalert(context, IConstant.alert, Message, StatusCode);


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

    public void newjobalert(final Context mContext, String Title, String Content, final int Status) {
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

       /* public void newalertdialog(final Context mContext, String Title, String Content, final int Status) {
            final Dialog dialog = new Dialog(mContext, R.style.Dialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_alertdialog);

            // set the custom dialog components - title and content
            TextView alertHead = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alerthead);
            alertHead.setText(Title);
            TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
            alertContent.setText(Content);

            // To hide cancel and line separator
            View line = (View) dialog.findViewById(R.id.centerLineDialog);
            Button btnDialogCancel = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_cancel);
            line.setVisibility(View.GONE);
            btnDialogCancel.setVisibility(View.GONE);


            Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);
            // if button is clicked, close the custom dialog
            btnDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Status == 100) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, Login.class);
                        startActivity(intent);
                    } else if (Status == 101)
                        dialog.dismiss();
                    Intent intent = new Intent(context, Login.class);
                    startActivity(intent);
                }
            });
            dialog.show();

        }*/





    /*private void acceptalertdialog(final Context mContext, String Title, String Content,final int StatusCode,String Message) {
        final Dialog dialog = new Dialog(mContext, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
        alertContent.setText(Content);

        // To hide cancel and line separator
        View line = (View) dialog.findViewById(R.id.centerLineDialog);
        Button btnDialogCancel = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_cancel);
        line.setVisibility(View.GONE);
        btnDialogCancel.setVisibility(View.GONE);


        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);
        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialog.dismiss();

            }
        });
        dialog.show();

    }
*/


// private void acceptalertdialog(Context context, String alert, String message, int statusCode)










