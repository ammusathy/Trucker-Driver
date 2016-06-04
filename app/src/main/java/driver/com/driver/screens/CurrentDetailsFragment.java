package driver.com.driver.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.ActionCallBack;
import driver.com.driver.model.ResponseParams.AdditionalResponse;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;
import driver.com.driver.model.TruckTypeModel;

/**
 * Created by kalaivani on 2/12/2016.
 */
public class CurrentDetailsFragment extends Fragment implements CurrentFragmentAdapter.OnItemClickListener
        , ActionCallBack {

    private static final int _GaleryCode = 101;
    public Activity act;
    ImageView iv, arrow;
    View rootView;
    SharedPreferences preferences;
    RequestQueue queue;
    String orderId;
    SharedPreferences.Editor editor;
    String OrderStatus;
    Context mContext;
    LinearLayout linear, add;
    Context ctx;
    LayoutInflater vi;
    Dialog dialog;
    ImageView image_add;
    String drivercontact;
    ImageView image_documents;
    Dialog imageoptionDialog;
    RecyclerView recycler;
    Button btn_pickdate, btn_picktime, btn_deliverydate, btn_deliverytime, viewdetails;
    String service;
    AdditionalAdapter additionalAdapter;
    private TextView txt_border, txt_nostraps, txt_stopintransit, txt_hazardousmaterial, price, imagedocuments, et_source, et_destination, notes, txt_documents, divider6,text_trucktype;
    private TextView currentborders, hazadous, current_stopintransit, current_nostraps, current_documents, additional, txt_trucktype, current_divider8, current_divider9, current_divider10, current_divider7, txt_save;
    private BroadcastReceiver localBroadcastReceiver;
    private RelativeLayout add_service_values;
    private List<AdditionalResponse> list_response;
    private TextView text_pick,text_delivery,text_shipper,text_notes,text_total,text_price,text_includes;
    private File mFileTemp;
    private ImageView imageView;
    Typeface Gibson_Light, HnBold, HnThin, HnLight, Gibson_Regular, GillSansStd;
    int statuscode=100;


    private ArrayList<TruckTypeModel> additionalService = new ArrayList<>();

    public CurrentDetailsFragment() {
    }

    @Override
    public void onSelectedItem(String value, String subvalue) {
        Log.i("OnSelected", "Called!!!");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(View itemView, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();
        SlidingDrawer.mTitle.setText(getString(R.string.title_trucker));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                localBroadcastReceiver,
                new IntentFilter("SOME_ACTION"));

        Log.i("OnResume", "Called!!");
        if (DriverApplication.mSelAdditionallabel != null &&
                DriverApplication.mSelAdditionalValue != null) {
            if (add_service_values != null) {
                addDynamicView(DriverApplication.mSelAdditionallabel,
                        DriverApplication.mSelAdditionalValue);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                localBroadcastReceiver);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastReceiver = new LocalBroadcastReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        // Inflate the layout for this fragment

        list_response = new ArrayList<>();
        Intent intent = getActivity().getIntent();
        orderId = intent.getStringExtra("OrderId");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ctx = getContext();
        act = getActivity();
        ReadShipment();

        if (getActivity() != null) {
            vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } else {
            vi = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        image_documents = (ImageView) rootView.findViewById(R.id.current_documents_add);
        add = (LinearLayout) rootView.findViewById(R.id.linear_add);
        iv = (ImageView) rootView.findViewById(R.id.fragment_btn_currentjob);
        arrow = (ImageView) rootView.findViewById(R.id.fragment_image_arrow);
        et_source = (TextView) rootView.findViewById(R.id.fragment_et_source);
        image_add = (ImageView) rootView.findViewById(R.id.current_image_add);
        et_destination = (TextView) rootView.findViewById(R.id.fragment_et_destination);
        txt_trucktype = (TextView) rootView.findViewById(R.id.fragment_btn_trucktype);
        btn_pickdate = (Button) rootView.findViewById(R.id.fragment_btn_pickupdate);
        btn_picktime = (Button) rootView.findViewById(R.id.fragment_btn_pickuptime);
        btn_deliverydate = (Button) rootView.findViewById(R.id.fragment_btn_deliverydate);
        btn_deliverytime = (Button) rootView.findViewById(R.id.fragment_btn_deliverytime);
        currentborders = (TextView) rootView.findViewById(R.id.current_btn_border);
        current_nostraps = (TextView) rootView.findViewById(R.id.current_btn_nostraps);
        add_service_values = (RelativeLayout) rootView.findViewById(R.id.add_service_values);
        current_stopintransit = (TextView) rootView.findViewById(R.id.current_btn_stopintransit);
        hazadous = (TextView) rootView.findViewById(R.id.current_btn_hazardousmaterial);
        additional = (TextView) rootView.findViewById(R.id.btn_additionalservices);
        current_documents = (TextView) rootView.findViewById(R.id.fragment_btn_documents);
        viewdetails = (Button) rootView.findViewById(R.id.fragment_btn_viewdetails);
        txt_hazardousmaterial = (TextView) rootView.findViewById(R.id.txtcurrent_hazardousmaterial);
        txt_border = (TextView) rootView.findViewById(R.id.txtcurrent_border);
        txt_nostraps = (TextView) rootView.findViewById(R.id.txtcurrent_nostraps);
        txt_stopintransit = (TextView) rootView.findViewById(R.id.txtcurrent_stopintransit);
        price = (TextView) rootView.findViewById(R.id.fragment_txt_price);
        notes = (TextView) rootView.findViewById(R.id.fragment_et_notes);
        text_trucktype=(TextView)rootView.findViewById(R.id.fragment_txt_trucktype) ;
        text_delivery=(TextView)rootView.findViewById(R.id.fragment_txt_deliverydate);
        linear = (LinearLayout) rootView.findViewById(R.id.fragment_linear_layout);
        current_divider7 = (TextView) rootView.findViewById(R.id.current_txt_divider7);
        current_divider8 = (TextView) rootView.findViewById(R.id.current_txt_divider8);
        current_divider9 = (TextView) rootView.findViewById(R.id.current_txt_divider9);
        current_divider10 = (TextView) rootView.findViewById(R.id.current_txt_divider10);
        txt_save = (TextView) rootView.findViewById(R.id.current_txt_save);
        txt_documents = (TextView) rootView.findViewById(R.id.txt_documents_add);
        divider6 = (TextView) rootView.findViewById(R.id.fragment_txt_divider6);
        text_shipper=(TextView)rootView.findViewById(R.id.txt_shipper);
        text_pick=(TextView)rootView.findViewById(R.id.fragment_txt_pickupdate);
        text_notes=(TextView)rootView.findViewById(R.id.fragment_txt_notes);
        text_total=(TextView)rootView.findViewById(R.id.fragment_txt_total);
        text_price=(TextView)rootView.findViewById(R.id.fragment_txt_price);
        text_includes=(TextView)rootView.findViewById(R.id.fragment_txt_includes);







        Gibson_Light = Typeface.createFromAsset(getActivity().getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Light.ttf");
        et_source.setTypeface(HnThin);
        et_destination.setTypeface(HnThin);
        txt_trucktype.setTypeface(HnThin);
        text_trucktype.setTypeface(Gibson_Regular);
        text_pick.setTypeface(Gibson_Regular);
        btn_pickdate.setTypeface(HnThin);
        btn_picktime.setTypeface(HnThin);
        btn_deliverydate.setTypeface(HnThin);
        btn_deliverytime.setTypeface(HnThin);
        text_delivery.setTypeface(Gibson_Regular);
        text_shipper.setTypeface(Gibson_Regular);
        currentborders.setTypeface(Gibson_Regular);
        current_nostraps.setTypeface(Gibson_Regular);
        current_stopintransit.setTypeface(Gibson_Regular);
        hazadous.setTypeface(HnThin);
        additional.setTypeface(HnThin);
        txt_border.setTypeface(HnThin);
        txt_hazardousmaterial.setTypeface(HnThin);
        txt_nostraps.setTypeface(HnThin);
        txt_stopintransit.setTypeface(HnThin);
        text_notes.setTypeface(Gibson_Regular);
        notes.setTypeface(HnThin);
        current_documents.setTypeface(HnThin);
        text_total.setTypeface(Gibson_Regular);
        viewdetails.setTypeface(Gibson_Regular);
        txt_save.setTypeface(Gibson_Light);




        recycler.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(mContext);
        recycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

//         additionalAdapter = new AdditionalAdapter(mContext,response);
//        recycler.setAdapter(additionalAdapter);
//        Log.d("AdapterSize", additionalAdapter.getItemCount() + "");


        //imagedocuments=(TextView)rootView.findViewById(R.id.fragment_txt_image);


        super.onViewCreated(view, savedInstanceState);


        final BottomSheetLayout bottomSheet = (BottomSheetLayout) rootView.findViewById(R.id.bottomsheet);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MenuSheetView menuSheetView =
                        new MenuSheetView(getContext(), MenuSheetView.MenuType.LIST, "", new MenuSheetView.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("Change Job Status")) {
                                    DriverApplication.progressdialogpopup(ctx, getResources().getString(R.string.loading));
                                    DriverApplication.customProgressDialog.show();
                                    Intent intent = new Intent(getContext(), ChangeJobStatus.class);
                                    startActivity(intent);
                                } else if (item.getTitle().equals("Contact Shipper")) {
                                    DriverApplication.progressdialogpopup(ctx, getResources().getString(R.string.loading));
                                    DriverApplication.customProgressDialog.show();
                                    contactalertDialog(ctx, IConstant.alert, ctx.getResources().getString(R.string.contactalert));
                                    //contactalertdialog(getActivity(), IConstant.alert, , StatusCode);
                                } else if (item.getTitle().equals("cancel Job")) {
                                    DriverApplication.progressdialogpopup(ctx, getResources().getString(R.string.loading));
                                    DriverApplication.customProgressDialog.show();
                                    CancelJob();
                                } else if (item.getTitle().equals("Edit shipment")) {
                                    EditShipment();
                                    bottomSheet.dismissSheet();
                                } else if (item.getTitle().equals("Cancel")) {
                                    getActivity().finish();
                                }

                                return true;

                            }

                        });
                menuSheetView.inflateMenu(R.menu.menu_popup);
                bottomSheet.showWithSheetView(menuSheetView);
            }
        });


       /* if (additionalService.isEmpty()) {
        } else {*/
        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }

            private void save() {

                preferences = getActivity().getSharedPreferences("Fragment", Context.MODE_PRIVATE);
                String userId = preferences.getString("UserId", "");
                String authToken = preferences.getString("AuthToken", "");
                queue = Volley.newRequestQueue(getActivity());
                String url = "http://truck.sdiphp.com/public/ws-driver-edit-shipment";
                JSONObject object = null;
                JSONObject accessories = new JSONObject();
                JSONObject layover = new JSONObject();
                try {
                    object = new JSONObject();
                    object.put("UserType", IConstant.UserType);
                    object.put("UserId", userId);
                    object.put("AuthToken", authToken);
                    object.put("OrderId", orderId);
                    object.put("Images", "");
                    accessories.put("TollCharge", "");
                    accessories.put("WeightFine", "");
                    accessories.put("YardStorage", "");
                    accessories.put("Reweighing", "");
                    accessories.put("LumperCharge", "");
                    accessories.put("PowerUsageHours", "");
                    layover.put("WeekDays", "");
                    layover.put("Solo", "");
                    object.put("Accessories", accessories);
                    object.put("LayOver", layover);
                    Log.d("Test", object.toString());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test", response.toString());
                        GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
                        int StatusCode = Integer.parseInt(String.valueOf(responseData.getStatusCode()));
                        String Message = responseData.getMessage();
                        if (statuscode == 100) {
                            editalertdialog(ctx, IConstant.alert, Message, StatusCode);
                        } else {

                        }

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

        });
    }


        public void editalertdialog(final Context ctx, String Title, String Content, final int Status) {

            final Dialog dialog = new Dialog(ctx, R.style.Dialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.customdialog);

            // set the custom dialog components - title and content
            TextView alertHead = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alerthead);

            alertHead.setText(Title);
            TextView alertContent = (TextView) dialog.findViewById(R.id.custom_dialog_tv_alertcontent);
            alertContent.setText("Profile saved successfully");
            dialog.show();

            Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);
            // if button is clicked, close the custom dialog
            btnDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (statuscode == 100) {
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



    private void EditShipment() {

        //additionalService.add;

        iv.setVisibility(View.GONE);
        txt_save.setVisibility(View.VISIBLE);
        image_add.setVisibility(View.VISIBLE);
        image_documents.setVisibility(View.VISIBLE);
        divider6.setVisibility(View.VISIBLE);
        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycler.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getActivity(), AddAdditionalServices.class);
                startActivityForResult(intent, 656);
//                getActivity().startActivityForResult(intent,656);

//                final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(android.R.id.content, new AddAdditionalServices(), "NewFragmentTag");
//                ft.commit();
                // re1placeFragment(fragment);//method
            }
        });
        image_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.setType("image/*");
                startActivityForResult(pickPhoto, _GaleryCode);
                String inputImage = "D:/Photo/Pic1.jpg";
                String oututImage = "D:/Photo/Pic1.png";
                String formatName = "PNG";
                /*try {
                    boolean result = ImageConverter.convertFormat(inputImage,
                            oututImage, formatName);
                    if (result) {
                        System.out.println("Image converted successfully.");
                    } else {
                        System.out.println("Could not convert image.");
                    }
                } catch (IOException ex) {
                    System.out.println("Error during converting image.");
                    ex.printStackTrace();
                }
            }
    });*/

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 656 && resultCode == Activity.RESULT_OK) {
            String string1 = data.getStringExtra("String1");
            String string2 = data.getStringExtra("String2");
//                   Toast.makeText(getActivity(),string1+" "+string2,Toast.LENGTH_SHORT).show();

            Log.d("Testing", string1 + " " + string2);

            AdditionalResponse additionalResponse = new AdditionalResponse(string1, string2);
            list_response.add(additionalResponse);
            refreshView();
        }
      /*  if (requestCode == _GaleryCode && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);*/
        super.onActivityResult(requestCode, resultCode, data);

    }


    public void refreshView() {
        Log.d("Testing", list_response.size() + "");
        final AdditionalAdapter additionalAdapter = new AdditionalAdapter(getActivity(), list_response);
        recycler.setAdapter(additionalAdapter);
        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
//        additionalAdapter.notifyDataSetChanged();
    }

/*                if (DriverApplication.getArrayList().size() > 0) {//Arraylist size is greater than 0 condition
                    AddAdditionalServices fragment = new AddAdditionalServices();//Create a new Additional service
                    fragment.setAdditionalServiceListener(new AdditionalServiceListener() {
                        @Override
                        public void refereshAditionalServiceView() {//new method
                            Log.v("MyCallback", "Called");
                            if (DriverApplication.mSelAdditionallabel != null &&
                                    DriverApplication.mSelAdditionalValue != null) {//checking condition whether label and value is not equal to null
                                if (add_service_values != null) {//checking condition whether service is not equal to null
                                    TruckTypeModel model = new TruckTypeModel();//Creating a new class
                                    model.Id = DriverApplication.mSelAdditionallabel;//Id should be in label
                                    model.Name = DriverApplication.mSelAdditionalValue;//name should be in value
                                    additionalService.add(model);//add the additional service to model
                                    addDynamicView(DriverApplication.mSelAdditionallabel,
                                            DriverApplication.mSelAdditionalValue);//method
                                }
                            }
                        }
                    });
                    replaceFragment(fragment);//method
                } else {
                    Toast.makeText(getActivity(), "No Additional Services to added", Toast.LENGTH_LONG).show();//no service can be added to show a toast message
                }

                getActivity().startActivity(new Intent(getActivity(),AddAdditionalServices.class));
                //Bordercross();
            }
        });
    } //









    /*    arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = et_source.getText().toString();
                String text2 = et_destination.getText().toString();
                et_source.setText(text2);
                et_destination.setText(text1);

            }
        });
*/
       /* additionalservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TestAdditional service", "");
                if (DriverApplication.getArrayList().size() > 0) {

                    AddAdditionalServices fragment = new AddAdditionalServices();
                    fragment.setAdditionalServiceListener(new AdditionalServiceListener() {
                        @Override
                        public void refereshAditionalServiceView() {
                            Log.v("MyCallback", "Called");
                            if (DriverApplication.mSelAdditionallabel != null &&
                                    DriverApplication.mSelAdditionalValue != null) {
                                if (add_service_values != null) {
                                    TruckTypeModel model = new TruckTypeModel();
                                    model.Id = DriverApplication.mSelAdditionallabel;
                                    model.Name = DriverApplication.mSelAdditionalValue;
                                    additionalService.add(model);
                                    addDynamicView(DriverApplication.mSelAdditionallabel,
                                            DriverApplication.mSelAdditionalValue);
                                }
                            }
                        }
                    });
                    replaceFragment(fragment);
                } else {
                    Toast.makeText(getActivity(), "No Additional Services to added", Toast.LENGTH_LONG).show();
                }

                //getActivity().startActivity(new Intent(getActivity(),AddAdditionalServices.class));
                //Bordercross();
            }
        });*/


    public void contactalertDialog(final Context ctx, String Title, String Content) {
        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.contactalertdialog);
        TextView alertHead = (TextView) dialog.findViewById(R.id.tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.tv_alertcontent);
        alertContent.setText(Content);

        Button btnDialogOk = (Button) dialog.findViewById(R.id.btn_text);

        Button btnDialogCancel = (Button) dialog.findViewById(R.id.btn_call);

        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:" + drivercontact));
                ctx.startActivity(intent);
                dialog.dismiss();

               /* Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address",123456789);
                startActivity(smsIntent);
                dialog.dismiss();*/

            }

        });

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri phoneCall = Uri.parse("tel:" + drivercontact);
                Intent caller = new Intent(Intent.ACTION_DIAL, phoneCall);
                startActivity(caller);
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getActivity().getSupportFragmentManager();
        //boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        //if (!fragmentPopped){ //fragment not in back stack, create it.
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.content, fragment);
        ft.addToBackStack(backStateName);
        ft.commit();
        // }
    }




/*
                Log.d("TestAdditional service", "");
                if (DriverApplication.getArrayList().size() > 0) {

                    AddAdditionalServices fragment = new AddAdditionalServices();
                    fragment.setAdditionalServiceListener(new AdditionalServiceListener() {
                        @Override
                        public void refereshAditionalServiceView() {
                            Log.v("MyCallback", "Called");
                            if (DriverApplication.mSelAdditionallabel != null &&
                                    DriverApplication.mSelAdditionalValue != null) {
                                if (add_service_values != null) {
                                    TruckTypeModel model = new TruckTypeModel();
                                    model.Id = DriverApplication.mSelAdditionallabel;
                                    model.Name = DriverApplication.mSelAdditionalValue;
                                    additionalService.add(model);
                                    addDynamicView(DriverApplication.mSelAdditionallabel,
                                            DriverApplication.mSelAdditionalValue);
                                }
                            }
                        }
                    });
                    replaceFragment(fragment);
                } else {
                    Toast.makeText(getActivity(), "No Additional Services to added", Toast.LENGTH_LONG).show();
                }
*/

    //getActivity().startActivity(new Intent(getActivity(),AddAdditionalServices.class));
    //Bordercross();

    public void addDynamicView(String mAddLabel, String mAddValue) {
        Log.d("Test", "");
        View v = vi.inflate(R.layout.added_additional_item, null);

        // fill in any details dynamically here
        TextView txtLabel = (TextView) v.findViewById(R.id.add_services_label);
        TextView txtValue = (TextView) v.findViewById(R.id.add_services_value);
        txtLabel.setText(mAddLabel);
        txtValue.setText(mAddValue);

        add_service_values.addView(v);
    }


    /*public void contactalertdialog(final Context mContext, String Title, String Content, final int Status) {
            final Dialog dialog = new Dialog(mContext, R.style.Dialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.contactalertdialog);

            // set the custom dialog components - title and content
            TextView alertHead = (TextView) dialog.findViewById(R.id.tv_alerthead);
            alertHead.setText(Title);
            TextView alertContent = (TextView) dialog.findViewById(R.id.tv_alertcontent);
            alertContent.setText(Content);

            Button btnDialogOk = (Button) dialog.findViewById(R.id.btn_text);

           *//* Button btnDialogCancel=(Button)dialog.findViewById(R.id.custom_alertdialog_btn_call) ;

            btnDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });*//*
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

    private void CancelJob() {
        preferences = getActivity().getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        String userId = preferences.getString("UserId", "");
        String authToken = preferences.getString("AuthToken", "");
        Log.d("Test", orderId + ":" + userId + " : " + authToken);
        queue = Volley.newRequestQueue(getActivity());
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

                cancelalertdialog(getActivity(), IConstant.alert, Message, StatusCode);
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

    public void cancelalertdialog(final Context mContext, String Title, String Content, final int Status) {
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
                    getActivity().finish();
                    //Intent intent=new Intent(context,JobList.class);
                    //startActivity(intent);
                } else
                    dialog.dismiss();
                //DriverApplication.customProgressDialog.dismiss();

            }
        });
        dialog.show();

    }
        /*final BottomSheetLayout bottomSheet = (BottomSheetLayout) rootView.findViewById(R.id.bottomsheet);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MenuSheetView menuSheetView =
                        new MenuSheetView(getContext(), MenuSheetView.MenuType.LIST, "", new MenuSheetView.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("Change Job Status")) {
                                    Intent intent = new Intent(getContext(), ChangeJobStatus.class);
                                    startActivity(intent);
                                } else if (item.getTitle().equals("Contact Shipper")) {
                                    //alertdialog()
                                } else if (item.getTitle().equals("cancel Job")) {
                                    CancelJob();
                                } else if (item.getTitle().equals("Edit shipment")) {

                                } else if (item.getTitle().equals("Cancel")) {
                                    getActivity().finish();
                                }

                                return true;

                            }

                        });
                menuSheetView.inflateMenu(R.menu.menu_popup);
                bottomSheet.showWithSheetView(menuSheetView);
            }



        });
    }*/

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
                        JSONObject driver = response.getJSONObject("Driver");
                        String contact = driver.getString("DriverContact");
                        drivercontact = contact;
                        JSONObject payment = response.getJSONObject("Payment");
                        String total = payment.getString("TotalPayable");
                        price.setText(total);
                        String trucktype = response.getString("TruckType");
                        if ((response.getString("TruckType")).equals("1")) {
                            txt_trucktype.setText("Vented Van");
                        }
                        String pickdate = response.getString("PickupDate");
                        btn_pickdate.setText(pickdate.toString());
                        String deliverydate = response.getString("DeliveryDate");
                        btn_deliverydate.setText(deliverydate.toString());
                        String picktime = response.getString("PickupTime");
                        btn_picktime.setText(picktime.toString());
                        String deliverytime = response.getString("DeliveryTime");
                        btn_deliverytime.setText(deliverytime.toString());
                        String etnotes = response.getString("Notes");
                        notes.setText(etnotes.toString());
                        JSONObject accessories = response.getJSONObject("Accessories");
                        String accessory = accessories.getString("HazardousMaterial");
                        if ((accessories.getString("HazardousMaterial")).length() <= 0) {
                            hazadous.setVisibility(View.GONE);
                            txt_hazardousmaterial.setVisibility(View.GONE);
                            current_divider10.setVisibility(View.GONE);
                        } else {
                            txt_hazardousmaterial.setText(accessory);

                            if (txt_hazardousmaterial.getText().equals("0")) {
                                txt_hazardousmaterial.setText("No");
                            } else if (txt_hazardousmaterial.getText().equals("1")) {
                                txt_hazardousmaterial.setText("Yes");
                            }
                        }
                        String border = accessories.getString("BorderCrossing");
                        if ((accessories.getString("BorderCrossing")).length() <= 0) {
                            currentborders.setVisibility(View.GONE);
                            txt_border.setVisibility(View.GONE);
                            current_divider7.setVisibility(View.GONE);
                        } else {
                            txt_border.setText(border);
                        }
                        String straps = accessories.getString("NoOfStraps");
                        if ((accessories.getString("NoOfStraps")).length() <= 0) {
                            current_nostraps.setVisibility(View.GONE);
                            txt_nostraps.setVisibility(View.GONE);
                            current_divider8.setVisibility(View.GONE);
                        } else {
                            txt_nostraps.setText(straps);
                        }
                        String transit = accessories.getString("StopsinTransit");
                        if ((accessories.getString("StopsinTransit")).length() <= 0) {
                            current_stopintransit.setVisibility(View.GONE);
                            txt_stopintransit.setVisibility(View.GONE);
                            current_divider9.setVisibility(View.GONE);
                        } else {
                            txt_stopintransit.setText(transit);
                        }

                        preferences = getActivity().getSharedPreferences("Details", Context.MODE_PRIVATE);//call shared Preference
                        editor = preferences.edit();//editor preferences
                        String orderId = null;//Getting values from user Id and authToken
                        try {
                            orderId = response.getString("OrderId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String authToken = null;
                        try {
                            authToken = response.getString("AuthToken");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String userId = null;
                        try {
                            userId = response.getString("UserId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Test", userId + " : " + authToken + " : " + orderId);
                        editor.putString("UserId", userId);
                        editor.putString("AuthToken", authToken);
                        editor.putString("OrderId", orderId);
                        editor.commit();
                        DriverApplication.customProgressDialog.dismiss();
                        // PreferenceConnector.writeString(getContext(), PreferenceConnector._PREF_CHANGE_STATUS, OrderStatus);
                        //Log.d("Test", OrderStatus + " : " );
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

                DriverApplication.customProgressDialog.dismiss();
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


    interface AdditionalServiceListener {
        void refereshAditionalServiceView();
    }

    private class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // safety check
            if (intent == null || intent.getAction() == null) {
                return;
            }

            if (intent.getAction().equals("SOME_ACTION")) {
                // doSomeAction();
                Log.i("Home", "Called!!!");


            }
        }
    }
}




