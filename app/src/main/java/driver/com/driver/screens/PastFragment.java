package driver.com.driver.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.AllShipmentResponse;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;

/**
 * Created by kalaivani on 4/28/2016.
 */
public class PastFragment extends Fragment implements PastFragmentAdapter.OnItemClickListener {
    View rootView;
    RecyclerView recycler_view;
    Context mContext;
    SharedPreferences preferences;
    RequestQueue queue;
    SharedPreferences.Editor editor;
    private List<AllShipmentResponse> allShipment;
    PastFragmentAdapter pastFragmentAdapter;

    public PastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_past, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListShipment();

        recycler_view=(RecyclerView)view.findViewById(R.id.fragment_recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        final LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(llm);
     /* ArrayList<CustomModel> list = new ArrayList<>();
      CustomModel model = new Cus
        model.se
       list.add();*/
        //currentFragmentAdapter = new CurrentFragmentAdapter(mContext);
        //recycler_view.setAdapter(currentFragmentAdapter);
        //Log.d("AdapterSize", currentFragmentAdapter.getItemCount() + "");
    }

    private void ListShipment() {
        preferences = getActivity().getSharedPreferences("Trucker", Context.MODE_PRIVATE);
        String userId = preferences.getString("Userid", null);
        String authToken = preferences.getString("authToken", null);
        Log.d("Test", userId + " : " + authToken);
//        DriverApplication.customProgressDialog.show();
        queue = Volley.newRequestQueue(getActivity());
        String url = "http://truck.sdiphp.com/public/ws-all-shippment";
        JSONObject object = null;
        try {

            if (userId != null && authToken != null) {
                object = new JSONObject();
                object.put("UserType", IConstant.UserType);
                object.put("UserId", userId);
                object.put("AuthToken", authToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                 Log.d("Response:", response.toString());


                try {

                    allShipment=new ArrayList<>();


                    String isSuccess = response.getString("Message");
                    if(isSuccess.equals("Success")){
                        JSONArray array = response.getJSONArray("Shipment");

                        for (int i=0;i<array.length();i++){
                            JSONObject object1 = array.getJSONObject(i);
                            String orderTime = object1.getString("OrderTime");
                            String fromAdress = object1.getString("FromAddress");
                            String totalamount = object1.getString("TotalAmount");
                            String Orderdate = object1.getString("OrderDate");
                            String Orderstatus = object1.getString("OrderStatus");
                            String Orderid = object1.getString("OrderId");
                            AllShipmentResponse model = new AllShipmentResponse();
                            model.setOrderTime(orderTime);
                            model.setFromAddress(fromAdress);
                            model.setTotalAmount(totalamount);
                            model.setOrderDate(Orderdate);
                            model.setOrderStatus(Orderstatus);
                            model.setOrderId(Orderid);

                            if(Orderstatus.equals("Delivered") || Orderstatus.equals("Cancelled")){
                                Log.d("Test",Orderstatus);
                                allShipment.add(model);
                            }
                        }

                        System.out.println("ARRAY Size :" + allShipment.size());
                        createList();
                    }
                    /*allShipment = new ArrayList<>();
                    preferences = getActivity().getSharedPreferences("Joblist", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    String userId = response.getString("UserId");
                    String authToken = response.getString("AuthToken");*/
                    //String orderid = response.getString("OrderId");
                    //String ship =response.getString("Shipment");


                    // Log.d("Test", userId + " : " + authToken);

                    // editor.putString("Userid", userId);
                    //editor.putString("authToken", authToken);
                    //editor.putString("orderid", orderid);
                    //editor.commit();
                } catch (Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                }

                //DriverApplication.customProgressDialog.dismiss();
                //JSONArray array = response.getJSONArray("Shipment");

                //System.out.println("value :" + array.length());


                    /*for(int x = 0; x < array.length(); x++)

                    {

                        try {
                       *//* System.out.println("value :"+x);
                        System.out.println("OrderId :" + array.getJSONObject(x).getString("OrderId"));
                        System.out.println("OrderStatus :" + array.getJSONObject(x).getString("OrderStatus"));
                        System.out.println("OrderDate :" + array.getJSONObject(x).getString("OrderDate"));
                        System.out.println("OrderTime :"+array.getJSONObject(x).getString("OrderTime"));
                        System.out.println("FromAddress :"+array.getJSONObject(x).getString("FromAddress"));
                        System.out.println("TotalAmount :"+array.getJSONObject(x).getString("TotalAmount"));
                        System.out.println("---------------------------------------------------------------");*//*


                            AllShipmentResponse allShipmentResponse = new AllShipmentResponse();
                            allShipmentResponse.setOrderId(array.getJSONObject(x).getString("OrderId"));
                            allShipmentResponse.setOrderStatus(array.getJSONObject(x).getString("OrderStatus"));
                            allShipmentResponse.setOrderDate(array.getJSONObject(x).getString("OrderDate"));
                            allShipmentResponse.setOrderTime(array.getJSONObject(x).getString("OrderTime"));
                            allShipmentResponse.setFromAddress(array.getJSONObject(x).getString("FromAddress"));
                            allShipmentResponse.setTotalAmount(array.getJSONObject(x).getString("TotalAmount"));

                            allShipment.add(allShipmentResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }*/

                   /* System.out.println("ARRAY Size :" + allShipment.size());
                    currentFragmentAdapter = new CurrentFragmentAdapter(mContext,allShipment);
                    recycler_view.setAdapter(currentFragmentAdapter);
                    currentFragmentAdapter.notifyDataSetChanged();
*/

                //System.out.println("Out Response : " + allShipment);


                GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                int StatusCode = Integer.parseInt(String.valueOf(responseData.getStatusCode()));
                String Message = responseData.getMessage();
                // jobalertdialog(mContext, IConstant.title, Message, StatusCode);


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void createList() {
        pastFragmentAdapter = new PastFragmentAdapter(mContext,allShipment);
        pastFragmentAdapter.setOnItemClickListener(this);
        recycler_view.setAdapter(pastFragmentAdapter);
        pastFragmentAdapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(View itemView, int position) {
        Log.d("Test",position+"");
        AllShipmentResponse response = allShipment.get(position);


        String orderId = response.getOrderId();

        Log.d("Trucker", response.getOrderId());
         if(allShipment.get(position).getOrderStatus().equals("Delivered")){
            Intent intent=new Intent(getActivity(),DeliveredJob.class);
             intent.putExtra("OrderId", orderId);
             intent.putExtra("Status", "Delivered");
            startActivity(intent);}
        else if(allShipment.get(position).getOrderStatus().equals("Cancelled")){
            Intent intent=new Intent(getActivity(),Cancelled.class);
             intent.putExtra("OrderId", orderId);
             intent.putExtra("Status", "Cancelled");
            startActivity(intent);}
    }
}
