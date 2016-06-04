package driver.com.driver.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.Constant;
import driver.com.driver.constants.IConstant;
import driver.com.driver.model.ResponseParams.LoginResponseParams;
import driver.com.driver.model.ResponseParams.ProfileResponse;
import driver.com.driver.screens.CreateNewPasswordSetting;
import driver.com.driver.screens.CropImage;
import driver.com.driver.screens.InternalStorageContentProvider;
import driver.com.driver.screens.Login;
import driver.com.driver.screens.PreferenceConnector;
import driver.com.driver.screens.VerifyMobileNumber;

/**
 * Created by kalaivani on 3/9/2016.
 */

public class Settings extends Fragment {

    private static final int _GaleryCode = 101;
    private static final int _CROP_FROM_CAMERA = 102;
    private static final int _CameraCode = 100;
    EditText editTextEmail, editTextMobileNo, editTextFirstName, editTextLastName, editinsurancedoc;
    View rootView;
    Intent intent;
    ImageView profileImage;
    TextView txt_accept;
    Dialog dialog;
    RequestQueue queue;
    Context ctx;
    Button btn_signout;
    TextView txt_password;
    SharedPreferences preferences;
    LinearLayout password;
    SharedPreferences.Editor editor;
    String personal;
    int Success = 100;
    String profile = "";
    String email, mobile, firstname, lastname, insurance, accept;
    String ba1;
    String mobileNoValue;
    String setting;
    String img_str;
    String imageString = "";
    Dialog imageoptionDialog;
    private Switch acceptJobStatus;
    private File mFileTemp;
    Typeface Gibson_Light, HnBold, HnThin, HnLight, Gibson_Regular, GillSansStd;



    public Settings() {
        // Required empty public constructor
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting, container, false);
        // Inflate the layout for this fragment
        //Getprofileupdate();
        ctx = getContext();
        init();
        process();

        return rootView;
    }

    public void init() {
        btn_signout = (Button) rootView.findViewById(R.id.setting_btn_signout);
        acceptJobStatus = (Switch) rootView.findViewById(R.id.mySwitch);
        editTextEmail = (EditText) rootView.findViewById(R.id.setting_et_emailaddress);
        editTextMobileNo = (EditText) rootView.findViewById(R.id.setting_et_mobile);
        editTextFirstName = (EditText) rootView.findViewById(R.id.setting_et_firstname);
        editTextLastName = (EditText) rootView.findViewById(R.id.setting_et_lastname);
        txt_accept = (TextView) rootView.findViewById(R.id.setting_txt_acceptjobs);
        editinsurancedoc = (EditText) rootView.findViewById(R.id.setting_et_insurancedoc);
        profileImage = (ImageView) rootView.findViewById(R.id.setting_profile_icon);
        password = (LinearLayout) rootView.findViewById(R.id.setting_ll_password);
        txt_password = (TextView) rootView.findViewById(R.id.setting_txt_password);

        Gibson_Light = Typeface.createFromAsset(getActivity().getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Light.ttf");

        editTextEmail.setTypeface(HnThin);
        editTextLastName.setTypeface(HnThin);
        editTextMobileNo.setTypeface(HnThin);
        editTextFirstName.setTypeface(HnThin);
        txt_password.setTypeface(HnThin);
        txt_accept.setTypeface(HnThin);
        editinsurancedoc.setTypeface(HnThin);








        //btnSave = (Button) rootView.findViewById(R.id.btnsave);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), IConstant.TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getActivity().getFilesDir(), IConstant.TEMP_PHOTO_FILE_NAME);
        }


        //Toast.makeText(getContext(), "Under Development", Toast.LENGTH_LONG).show();

        String userInfo = PreferenceConnector.readString(getContext(), PreferenceConnector._PREF_USER_INFO, "");
        if (userInfo.length() > 0) {
            LoginResponseParams loginRequestParams = (LoginResponseParams) DriverApplication.getFromJSON(userInfo, LoginResponseParams.class);

            LoginResponseParams.Login login = loginRequestParams.new Login();
            editTextEmail.setText(login.getEmail());
            editTextMobileNo.setText(login.getMobileNumber());
            editTextFirstName.setText(login.getFirstname());
            editTextLastName.setText(login.getLastname());
            txt_accept.setText(login.getAcceptNewJob());
            editinsurancedoc.setText(login.getInsuranceDoc());
        }

        if (Constant.settingEditFlag == 1) {
            editTextEmail.setEnabled(true);
            editTextMobileNo.setEnabled(true);
            editTextFirstName.setEnabled(true);
            editTextLastName.setEnabled(true);
            profileImage.setEnabled(true);
        } else {
            editTextEmail.setEnabled(false);
            editTextMobileNo.setEnabled(false);
            editTextFirstName.setEnabled(false);
            editTextLastName.setEnabled(false);
            profileImage.setEnabled(false);
        }


//        if (editTextEmail.getText().toString().length()<=1&&editTextEmail.getText().toString()=="" )
        GetprofileInfo();
        //Getprofileupdate();

        // Toast.makeText(ctx, "clicked save", Toast.LENGTH_SHORT).show();

    }

    //Personal personal;
    private void GetprofileInfo() {
        preferences = getActivity().getSharedPreferences("Trucker", Context.MODE_PRIVATE);// get shared preference and mode to be selected
        String authToken = preferences.getString("authToken", null);//getting a userid and authtoken in login screen
        final String userId = preferences.getString("Userid", null);
        Log.d("Test", userId + " : " + authToken);//print both the result
        queue = Volley.newRequestQueue(getActivity());
        final String url = "http://truck.sdiphp.com/public/ws-get-profile";
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("AuthToken", authToken);
            object.put("UserId", userId);
            object.put("UserType", IConstant.UserType);
            Log.d("Test", authToken + " : " + userId + ":" + IConstant.UserType);

        } catch (Exception e) {
            e.printStackTrace();

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response:", response.toString());

                ProfileResponse mProfileResponse;
                Gson gson = new Gson();
                mProfileResponse = gson.fromJson(response.toString(), ProfileResponse.class);


                editTextFirstName.setText(mProfileResponse.getPersonal().getFirstname());
                editTextEmail.setText(mProfileResponse.getPersonal().getEmail());
                //txt_accept.setText(mProfileResponse.getPersonal().getAcceptJobs());
                editinsurancedoc.setText(mProfileResponse.getPersonal().getInsurance());
                editTextLastName.setText(mProfileResponse.getPersonal().getLastname());
                editTextMobileNo.setText(mProfileResponse.getPersonal().getMobileNumber());


                if (mProfileResponse.getPersonal().getAcceptJobs().equals("1")) {
                    acceptJobStatus.setChecked(true);

                } else {
                    acceptJobStatus.setChecked(false);

                }


                Picasso.with(getActivity())
                        .load(mProfileResponse.getPersonal().getProfilePicture())
                        .into(profileImage);

                Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();
                ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

                preferences = getActivity().getSharedPreferences("NewInfo", Context.MODE_PRIVATE);//call shared Preference
                editor = preferences.edit();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject personal = jsonObject.getJSONObject("Personal");

                    String authToken = jsonObject.getString("AuthToken");
                    String userid = jsonObject.getString("UserId");
                    String usertype = jsonObject.getString("UserType");

                    editor.putString("AuthToken", authToken);
                    editor.putString("UserId", userid);
                    editor.putString("UserType", usertype);

                    System.out.println("personal = " + personal);

                    String fn = personal.getString("Firstname");
                    String ln = personal.getString("Lastname");
                    String email = personal.getString("Email");
                    String mobile = personal.getString("MobileNumber");
                    String accept = personal.getString("AcceptJobs");
                    String insurance = personal.getString("Insurance");

                    System.out.println("fn+ln+email+mobile = " + fn + " >> " + ln + " >> " + email + " >> " + mobile + " >> " + accept + " >> " + insurance + ">>" +ba1);

                    editor.putString("FirstName", fn);
                    editor.putString("LastName", ln);
                    editor.putString("Email", email);
                    editor.putString("Mobile", mobile);
                    editor.putString("ProfilePicture", ba1);
                    editor.putString("AcceptJobs", accept);
                    editor.putString("Insurance", insurance);
                    System.out.println("TestSettings= " + fn + " >> " + ln + " >> " + email + " >> " + mobile + " >> " + accept + " >> " + insurance + ">>" +ba1);

                    editor.commit();

                } catch (JSONException e1) {

                }
            }


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

    public void process() {


        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CreateNewPasswordSetting.class);

                startActivity(i);
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Under Development", Toast.LENGTH_LONG).show();
                CustomImageDialog();
            }
        });
        editTextMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, VerifyMobileNumber.class);
                startActivity(intent);
            }
        });
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogout();
            }
        });
    }

    private void CustomImageDialog() {
        imageoptionDialog = new Dialog(ctx, R.style.Dialog);
        imageoptionDialog.setCancelable(false);
        imageoptionDialog.setContentView(R.layout.dialog_camera_or_gallery);
        TextView dialog_title = (TextView) imageoptionDialog.findViewById(R.id.tvalerthead);
        TextView camera = (TextView) imageoptionDialog.findViewById(R.id.btncamera);
        TextView gallery = (TextView) imageoptionDialog.findViewById(R.id.btngallery);
        Button cancel = (Button) imageoptionDialog.findViewById(R.id.btncancel);
        imageoptionDialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageoptionDialog.dismiss();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    Uri mImageCaptureUri = null;
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state))
                        mImageCaptureUri = Uri.fromFile(mFileTemp);
                    else
                        mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, _CameraCode);
                } catch (ActivityNotFoundException e) {
                    Log.d("CreateAccount", "cannot take picture", e);
                }
                imageoptionDialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.setType("image/*");
                startActivityForResult(pickPhoto, _GaleryCode);
                imageoptionDialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_OK)
            return;
        switch (requestCode) {
            case _CameraCode:
                startCropImage();
                break;
            case _GaleryCode:
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    startCropImage();
                } catch (Exception e) {
                    Log.e("CreateAccount", "Error while creating temp file", e);
                }
                break;
            case _CROP_FROM_CAMERA:
                String path = data.getStringExtra(CropImage.IMAGE_PATH);
                if (path == null) {
                    return;
                } else {
                    Bitmap photo = BitmapFactory.decodeFile(mFileTemp.getPath());
                    profileImage.setImageBitmap(DriverApplication.getRoundedCornerImage(photo));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageString = Constant.encodeImage(stream.toByteArray());
                    Log.d("Image", imageString);
                }
                break;
        }
    }

    private void startCropImage() {
        Intent intent = new Intent(ctx, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 5);
        intent.putExtra(CropImage.ASPECT_Y, 5);
        startActivityForResult(intent, _CROP_FROM_CAMERA);
    }

    private void logout() {
        Log.d("clicked", "");
        dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alerthead);
        alertHead.setText(getResources().getString(R.string.app_name));
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
        alertContent.setText(getResources().getString(R.string.logoutconf));

        // To hide cancel and line separator
        View line = (View) dialog.findViewById(R.id.centerLineDialog);
        Button btnDialogCancel = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_cancel);

        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);


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
        preferences = getActivity().getSharedPreferences("Trucker", Context.MODE_PRIVATE);
        String userId = preferences.getString("Userid", null);
        String authToken = preferences.getString("authToken", null);
        Log.d("Test", userId + " : " + authToken);
        queue = Volley.newRequestQueue(getActivity());
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







           /*  try {

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

                }
*/
                LoginResponseParams responseData = (LoginResponseParams) DriverApplication.getFromJSON(response.toString(), LoginResponseParams.class);
                int StatusCode = Integer.parseInt(responseData.getStatusCode());
                String message = responseData.getMessage();
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

                alertdialog(ctx, IConstant.alert, message, StatusCode);
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
                        Log.e("Error login-->", json);
                        try {
//                             Parsing json object response response will be a json object
                            if (json != null) {
//
                                JSONObject jsonObject = new JSONObject(json);
//
                                String message = jsonObject.getString("message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
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


    public void alertdialog(final Context ctx, String Title, String Content, final int Status) {
        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
        alertContent.setText("Do you wish to log out from app?");
        Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);
        Button btnDialogcancel = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_cancel);
        btnDialogcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
}







        /*public void deleteAccount() {
            dialog = new Dialog(getContext(), R.style.Dialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_alertdialog);
            TextView alertHead = (TextView) dialog.findViewById(R.id.tvalerthead);
            alertHead.setText(getResources().getString(R.string.app_name));
            TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
            alertContent.setText(getResources().getString(R.string.deleteaccountconf));

            // To hide cancel and line separator
            View line = (View) dialog.findViewById(R.id.centerLineDialog);
            Button btnDialogCancel = (Button) dialog.findViewById(R.id.btncancel);

            Button btnDialogOk = (Button) dialog.findViewById(R.id.custom_alertdialog_btn_ok);


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
                    dialog.dismiss();
                    GeneralRequestParams generalRequestParams = new GeneralRequestParams();
                    generalRequestParams.setUserId(PreferenceConnector.readString(getContext(), PreferenceConnector._PREF_USER_ID, "0"));
                    generalRequestParams.setAuthToken(PreferenceConnector.readString(getContext(), PreferenceConnector._PREF_AUTH_TOKEN, "0"));
                    String inputParams = DriverApplication.getToJSON(generalRequestParams, null);
                    new DeleteAccountAsync(inputParams).execute();
                }
            });
            dialog.show();
        }
*/




