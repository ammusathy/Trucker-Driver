package driver.com.driver.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.Constant;
import driver.com.driver.constants.IConstant;
import driver.com.driver.fragment.AboutUs;
import driver.com.driver.fragment.Help;
import driver.com.driver.fragment.Settings;
import driver.com.driver.model.ResponseParams.LoginResponseParams;

/**
 * Created by kalaivani on 3/9/2016.
 */
public class SlidingDrawer extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    public static TextView mTitle;
    private static String TAG = SlidingDrawer.class.getSimpleName();
    Menu mMenu;
    Context ctx;
    Dialog dialog;
    Fragment fragment = null;
    int invisible = 99;
    int notification = 100;
    int settings = 101;
    int StatusCode;
    int Success = 100;
    SharedPreferences preferences;
    RequestQueue queue;
    int actionBar_Item_Flag;
    private Toolbar mToolbar;
    SharedPreferences.Editor editor;

    private FragmentDrawer drawerFragment;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.drawerlayout_toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawerlayout), mToolbar);

        drawerFragment.setDrawerListener(this);


        // display the first navigation drawer view on app launch
        displayView(0);
        ctx = SlidingDrawer.this;
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        /*MenuItem item = menu.findItem(R.id.action_notification);
        if (notification_Flag == 0)
            item.setVisible(true);
        else
            item.setVisible(false);*/
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_notification);
        if (actionBar_Item_Flag == notification) {
            item.setTitle(getString(R.string.notification));
            item.setVisible(true);
            item.setIcon(R.drawable.bell_icon);
        } else if (actionBar_Item_Flag == settings && Constant.settingEditFlag == 0) {
            item.setTitle(getString(R.string.edit));
            item.setVisible(true);
            item.setIcon(R.drawable.edit_icon);
        } else if (actionBar_Item_Flag == settings) {
            item.setTitle(getString(R.string.save));
            item.setVisible(true);
            item.setIcon(R.drawable.save);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    Getprofileupdate();
                    return true;
                }
            });
        } else if (actionBar_Item_Flag == invisible)
            item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    private void Getprofileupdate() {
        preferences = getSharedPreferences("NewInfo", Context.MODE_PRIVATE);// get shared preference and mode to be selected
        String authToken = preferences.getString("AuthToken", null);//getting a userid and authtoken in login screen
        String userid = preferences.getString("UserId", null);
        String usertype = preferences.getString("UserType", null);

        String fn = preferences.getString("FirstName", null);
        String ln = preferences.getString("LastName", null);
        String email = preferences.getString("Email", null);
        String mobile = preferences.getString("Mobile", null);
        String ba1 = preferences.getString("ProfilePicture", null);
        String accept = preferences.getString("AcceptJobs", null);
        String insurance = preferences.getString("Insurance", null);

        System.out.println("insurance = " + insurance);
        System.out.println("userid = " + userid);
        System.out.println("FirstName = " + fn);
        System.out.println("LastName = " + ln);


        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-update-profile";
        JSONObject object = new JSONObject();
        JSONObject personal = new JSONObject();

        try {

            object.put("AuthToken", authToken);
            object.put("UserId", userid);
            object.put("UserType", usertype);
            personal.put("FirstName", fn);
            personal.put("LastName", ln);
            personal.put("Email", email);
            personal.put("Mobile", mobile);
            personal.put("Insurance", insurance);
            personal.put("AcceptJobs", accept);
            personal.put("ProfilePicture",ba1);

            object.put("Personal", personal);

            System.out.println(TAG + object );

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response:", response.toString());
             /*   preferences = getSharedPreferences("Update", MODE_PRIVATE);//call shared Preference
                editor = preferences.edit();
                String mobile = null;//Getting values from user Id and authToken
                try {
                    mobile = response.getString("Mobile");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String userId = null;
                try {
                    userId = response.getString("UserId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Test", userId + " : " + mobile);
                editor.putString("UserId", userId);
                editor.putString("Mobile", mobile);
                editor.commit();*/

                LoginResponseParams responseData = (LoginResponseParams) DriverApplication.getFromJSON(response.toString(), LoginResponseParams.class);//Loginresponse model class
                int StatusCode = Integer.parseInt(responseData.getStatusCode());
                String message = responseData.getMessage();

                profilealertdialog(ctx, IConstant.alert, message, StatusCode);


            }

            //forgotalertdialog(ctx, IConstant.title, message, StatusCode);

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                                System.out.println("jsonObject.toString() = " + jsonObject.toString());
                                String message = jsonObject.getString("message");
                                //Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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

        public void profilealertdialog(final Context ctx, String Title, String Content, final int Status) {

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

                        dialog.dismiss();
//                     Intent intent = new Intent(Login.this, Login.class);
//                     startActivity(intent);

                    }

            });
            dialog.show();
            // DriverApplication.customProgressDialog.dismiss();

        }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Toast.makeText(getApplicationContext(),"id: "+id,Toast.LENGTH_SHORT).show();

        if (id == R.id.action_notification && actionBar_Item_Flag == notification) {
            displayView(7);
            return true;
        } else if (id == R.id.action_notification && actionBar_Item_Flag == settings) {
            if (Constant.settingEditFlag == 0)
                Constant.settingEditFlag = 1;
            else
                Constant.settingEditFlag = 0;
            Constant.settingEditFlag=1;
            displayView(1);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        Toast.makeText(getApplicationContext(), "pos: " + position, Toast.LENGTH_SHORT).show();


        if (position == 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, new JobList());
            fragmentTransaction.commit();
        }
        displayView(position);
    }

    private void displayView(int position) {
        String title = getString(R.string.app_name);
        actionBar_Item_Flag = notification;
        switch (position) {
            case 0:
                /*FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.drawerlayout_frame, new JobList());
                fragmentTransaction.commit();*/
                fragment = new JobList();
                title = getString(R.string.title_home);
                break;
            case 1:
                actionBar_Item_Flag = settings;
                fragment = new Settings();
                title = getString(R.string.title_settings);
                break;
            case 2:
                fragment = new AboutUs();
                title = getString(R.string.title_aboutus);
                break;
            case 3:
                fragment = new Help();
                title = getString(R.string.title_help);
                break;

            case 5:
                actionBar_Item_Flag = invisible;
                fragment = new Notification();
                title = getString(R.string.notification);
            default:
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle("");
            mTitle.setText(title);
        }
        supportInvalidateOptionsMenu();
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
        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_dialog_btn_ok);
        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status == 100) {
                    dialog.dismiss();
                    Intent intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                } else {
                    dialog.dismiss();

                }
            }
        });
        dialog.show();

    }

    /*private void logOut() {
        Log.d("clicked","");
        dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);
        TextView alertHead = (TextView) dialog.findViewById(R.id.tvalerthead);
        alertHead.setText(getResources().getString(R.string.app_name));
        TextView alertContent = (TextView) dialog.findViewById(R.id.tvalertcontent);
        alertContent.setText(getResources().getString(R.string.logoutconf));

        // To hide cancel and line separator
        TextView line = (TextView) dialog.findViewById(R.id.centerLineDialog);
        Button btnDialogCancel = (Button) dialog.findViewById(R.id.btncancel);

        Button btnDialogOk = (Button) dialog.findViewById(R.id.btnok);


        // if button is clicked, close the custom dialog
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogout();
                DriverApplication.progressdialogpopup(ctx, getResources().getString(R.string.loading));
                DriverApplication.customProgressDialog.show();
            }
        });
        dialog.show();
    }

    private void UserLogout() {
        preferences = getSharedPreferences("Trucker", Context.MODE_PRIVATE);
        String userId = preferences.getString("Userid", null);
        String authToken = preferences.getString("authToken", null);
        Log.d("Test", userId + " : " + authToken);
        queue = Volley.newRequestQueue(this);
        String url = "http://truck.sdiphp.com/public/ws-logout";
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("AuthToken", authToken);
            object.put("UserId", userId);
        } catch (Exception e) {
            e.printStackTrace();

        }


    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d("Response:", response.toString());

           *//* try {

                String isACtivated = response.getString("isActivated");
                if (isACtivated.equals("true")) {
                    preferencese = getSharedPreferences("Trucker",MODE_PRIVATE);
                    editor = preferencese.edit();

                    String userId = response.getString("UserId");
                    String authToken = response.getString("AuthToken");
                    Log.d("Test",userId+" : "+authToken);
                    editor.putString("Userid",userId);
                    editor.putString("authToken",authToken);
                    editor.commit();

                    dialog.show();
                    Intent intent = new Intent(Login.this, SlidingDrawer.class);
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    Intent intent = new Intent(Login.this, Login.class);
                    startActivity(intent);
                }
            } catch (Exception e) {

            }*//*

            GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);
            int StatusCode = Integer.parseInt(String.valueOf(responseData.getStatusCode()));
            String message = responseData.getMessage();
            alertdialog(ctx, IConstant.title, message, StatusCode);
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
                    Log.e("Error login-->", json);
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

    public void alertdialog(final Context ctx, String Title, String Content, final int Status) {
        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.tvalerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.tvalertcontent);
        alertContent.setText(Content);

        // To hide cancel and line separator
        TextView line = (TextView) dialog.findViewById(R.id.centerLineDialog);
        Button btnDialogCancel = (Button) dialog.findViewById(R.id.btncancel);
        line.setVisibility(View.GONE);
        btnDialogCancel.setVisibility(View.GONE);


        Button btnDialogOk = (Button) dialog.findViewById(R.id.btnok);
        // if button is clicked, close the custom dialog
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status == 100) {

                    dialog.dismiss();
                    Intent intent = new Intent(ctx, SlidingDrawer.class);
                    startActivity(intent);
                } else if (Status == 101) {
                    dialog.dismiss();

                }
            }
        });
        dialog.show();

    }*/

}

