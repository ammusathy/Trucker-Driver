package driver.com.driver.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import driver.com.driver.R;
import driver.com.driver.constants.IConstant;

/**
 * Created by kalaivani on 23-May-16.
 */
public class CancelledDetailsFragment extends Fragment {

    ImageView iv, arrow;
    View rootView;
    SharedPreferences preferences;
    TextView txt_border,txt_nostraps,txt_stopintransit,txt_hazardousmaterial,price,imagedocuments,txt_order,et_source,et_destination,divider7,divider8,divider9,divider10;
    RequestQueue queue;
    String orderId;
    TextView txt_trucktype,txt_pickdate,txt_deliverydate,additional,notes,etnotes,documents,txt_total,txt_price,txt_includes,txt_shipper;
    Button btn_pickdate,btn_picktime,btn_deliverydate,btn_deliverytime,viewdetails,btn_close;
    TextView txt_canceltrucktype,txt_cancelborder,txt_cancelstops,txt_cancelhazadarous,txt_cancelnostraps,txt_canceldocuments,txt_canceladditional;
    private Typeface Gibson_Light, HnThin, HnLight,Gibson_regular;

    public CancelledDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.canceldetail, container, false);
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        orderId = intent.getStringExtra("OrderId");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ReadShipment();
        iv = (ImageView) rootView.findViewById(R.id.fragment_btn_currentjob);
        arrow = (ImageView) rootView.findViewById(R.id.fragment_image_arrow);
        et_source = (TextView) rootView.findViewById(R.id.cancel_et_source);
        et_destination = (TextView) rootView.findViewById(R.id.cancel_et_destination);
        txt_canceltrucktype = (TextView) rootView.findViewById(R.id.cancel_btn_trucktype);
        btn_pickdate = (Button) rootView.findViewById(R.id.cancel_btn_pickupdate);
        btn_picktime = (Button) rootView.findViewById(R.id.cancel_btn_pickuptime);
        btn_deliverydate = (Button) rootView.findViewById(R.id.cancel_btn_deliverydate);
        btn_deliverytime = (Button) rootView.findViewById(R.id.cancel_btn_deliverytime);
        txt_cancelborder = (TextView) rootView.findViewById(R.id.cancel_btn_border);
        txt_cancelnostraps = (TextView) rootView.findViewById(R.id.cancel_btn_nostraps);
        txt_cancelstops = (TextView) rootView.findViewById(R.id.cancel_btn_stopintransit);
        txt_cancelhazadarous = (TextView) rootView.findViewById(R.id.cancel_btn_hazardousmaterial);
        txt_canceladditional = (TextView) rootView.findViewById(R.id.cancel_btn_additionalservices);
        txt_canceldocuments = (TextView) rootView.findViewById(R.id.cancel_btn_documents);
        viewdetails = (Button) rootView.findViewById(R.id.cancel_btn_viewdetails);
        txt_hazardousmaterial = (TextView) rootView.findViewById(R.id.canceltxt_hazardousmaterial);
        txt_border=(TextView)rootView.findViewById(R.id.canceltxt_border);
        txt_nostraps=(TextView)rootView.findViewById(R.id.canceltxt_nostraps);
        txt_stopintransit=(TextView)rootView.findViewById(R.id.canceltxt_stopintransit);
        price=(TextView)rootView.findViewById(R.id.cancel_txt_price);
        etnotes=(TextView) rootView.findViewById(R.id.cancel_et_notes) ;
        btn_close=(Button)rootView.findViewById(R.id.cancel_btn_close) ;
        divider7=(TextView)rootView.findViewById(R.id.cancel_txt_divider7);
        divider8=(TextView)rootView.findViewById(R.id.cancel_txt_divider8);
        divider9=(TextView)rootView.findViewById(R.id.cancel_txt_divider9);
        divider10=(TextView)rootView.findViewById(R.id.cancel_txt_divider10);
        txt_trucktype=(TextView)rootView.findViewById(R.id.cancel_txt_trucktype);
        txt_pickdate=(TextView)rootView.findViewById(R.id.cancel_txt_pickupdate) ;
        txt_deliverydate=(TextView)rootView.findViewById(R.id.cancel_txt_deliverydate);
        additional=(TextView)rootView.findViewById(R.id.cancel_btn_additionalservices);
        notes=(TextView)rootView.findViewById(R.id.cancel_txt_notes);
        documents=(TextView)rootView.findViewById(R.id.cancel_btn_documents);
        txt_total=(TextView)rootView.findViewById(R.id.cancel_txt_total);
        txt_price=(TextView)rootView.findViewById(R.id.cancel_txt_price);
        txt_includes=(TextView)rootView.findViewById(R.id.cancel_txt_includes);
        txt_shipper=(TextView)rootView.findViewById(R.id.txt_cancelshipper);




        Gibson_Light = Typeface.createFromAsset(getActivity().getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Light.ttf");
        Gibson_regular=Typeface.createFromAsset(getActivity().getAssets(),"Gibson-Regular.ttf");

        et_source.setTypeface(HnThin);
        et_destination.setTypeface(HnThin);
        txt_canceltrucktype.setTypeface(HnThin);
        btn_pickdate.setTypeface(HnThin);
        btn_picktime.setTypeface(HnThin);
        btn_deliverydate.setTypeface(HnThin);
        btn_deliverytime.setTypeface(HnThin);
        txt_cancelborder.setTypeface(HnThin);
        txt_cancelnostraps.setTypeface(HnThin);
        txt_cancelstops.setTypeface(HnThin);
        txt_cancelhazadarous.setTypeface(HnThin);
        txt_canceladditional.setTypeface(HnThin);
        txt_canceldocuments.setTypeface(HnThin);
        viewdetails.setTypeface(HnThin);
        txt_hazardousmaterial.setTypeface(HnThin);
        txt_border.setTypeface(HnThin);
        txt_nostraps.setTypeface(HnThin);
        txt_stopintransit.setTypeface(HnThin);
        price.setTypeface(HnThin);
        notes.setTypeface(HnThin);
        btn_close.setTypeface(Gibson_Light);
        txt_trucktype.setTypeface(HnThin);
        txt_pickdate.setTypeface(HnThin);
        txt_deliverydate.setTypeface(HnThin);
        documents.setTypeface(HnThin);
        txt_includes.setTypeface(HnThin);
        txt_price.setTypeface(HnThin);
        notes.setTypeface(HnThin);
        additional.setTypeface(HnThin);
        txt_shipper.setTypeface(HnThin);

















        super.onViewCreated(view, savedInstanceState);




     /*   arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = et_source.getText().toString();
                String text2 = et_destination.getText().toString();
                et_source.setText(text2);
                et_destination.setText(text1);
            }
        });*/
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void ReadShipment() {
        preferences = getActivity().getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        String userId = preferences.getString("UserId", "");
        String authToken = preferences.getString("AuthToken", "");
        Log.d("Test", orderId + ":" + userId + " : " + authToken);
        queue = Volley.newRequestQueue(getActivity());
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
                        price.setText(total);
                        String trucktype = response.getString("TruckType");
                        if ((response.getString("TruckType")).equals("1")){
                            txt_canceltrucktype.setText("Vented Van");
                        }

                        String pickdate = response.getString("PickupDate");
                        btn_pickdate.setText(pickdate.toString());
                        String deliverydate = response.getString("DeliveryDate");
                        btn_deliverydate.setText(deliverydate.toString());
                        String picktime = response.getString("PickupTime");
                        btn_picktime.setText(picktime.toString());
                        String deliverytime = response.getString("DeliveryTime");
                        btn_deliverytime.setText(deliverytime.toString());
                       // String order=response.getString("OrderId");
                        //txt_order.setText(order);
                        String etnotes = response.getString("Notes");
                        notes.setText(etnotes.toString());
                        JSONObject accessories = response.getJSONObject("Accessories");
                            String accessory = accessories.getString("HazardousMaterial");
                        if ((accessories.getString("HazardousMaterial")).length() <= 0) {
                            txt_cancelhazadarous.setVisibility(View.GONE);
                            txt_hazardousmaterial.setVisibility(View.GONE);
                            divider10.setVisibility(View.GONE);
                        } else {
                            txt_hazardousmaterial.setText(accessory);
                        }
                        String border = accessories.getString("BorderCrossing");
                        if ((accessories.getString("BorderCrossing")).length() <= 0) {
                            txt_cancelborder.setVisibility(View.GONE);
                            txt_border.setVisibility(View.GONE);
                            divider7.setVisibility(View.GONE);
                        } else {
                            txt_border.setText(border);
                        }
                            String straps = accessories.getString("NoOfStraps");
                        if ((accessories.getString("NoOfStraps")).length() <= 0) {
                            txt_cancelnostraps.setVisibility(View.GONE);
                            txt_nostraps.setVisibility(View.GONE);
                            divider8.setVisibility(View.GONE);
                        } else {
                            txt_nostraps.setText(straps);
                        }
                            String transit = accessories.getString("StopsinTransit");
                        if ((accessories.getString("StopsinTransit")).length() <= 0) {
                            txt_cancelstops.setVisibility(View.GONE);
                            txt_stopintransit.setVisibility(View.GONE);
                            divider9.setVisibility(View.GONE);
                        } else {
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
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
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
                            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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


}

