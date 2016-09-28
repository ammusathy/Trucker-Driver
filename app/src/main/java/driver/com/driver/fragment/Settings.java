package driver.com.driver.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

import driver.com.driver.Activity.CropImage;
import driver.com.driver.Activity.InternalStorageContentProvider;
import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.constants.Constant;
import driver.com.driver.constants.IConstant;
import driver.com.driver.helper.Common;
import driver.com.driver.helper.PreferenceConnector;
import driver.com.driver.login.ChangePassword;
import driver.com.driver.login.Login;
import driver.com.driver.model.ResponseParams.GeneralResponseParams;
import driver.com.driver.model.ResponseParams.LoginResponseParams;
import driver.com.driver.model.ResponseParams.ProfileResponse;

import static driver.com.driver.constants.Constant.encodeImage;

public class Settings extends Fragment {

    public static final String IMAGE_PATH = "image-path";
    private static final int _InsuranceCode = 103;
    public static String firstnameEditedFlag = "0";
    public static String lastnameEditedFlag = "0";
    public static String imageString = "";
    public static String insurancedoc = "";
    public static String encodedString = "";
    static TextView editinsurancedoc;
    static ImageView profileImage;
    static String filename;
    static String ba1, ins;
    static ToggleButton button;
    static String image;
    static String filename1, filename2;
    private static EditText editTextEmail, editTextMobileNo, editTextFirstName, editTextLastName;
    private final int _GaleryCode = 101;
    private final int _CROP_FROM_CAMERA = 102;
    private final int _CROP_FROM_CAMERA1 = 108;
    private final int _CROP_FROM_CAM = 105;
    private final int _CameraCode = 100;
    private final int _CamCode = 104;
    Typeface Gibson_Light, HnThin, HnLight;
    View rootView;
    TextView editaccept;
    Context ctx;
    Dialog dialog;
    Dialog imageoptionDialog;
    Dialog imageoptionDialog1;
    RequestQueue queue;
    TextView txt_password;
    Button btn_signout;
    LinearLayout password;
    String selectedImagePath;
    int unauthorized = 97;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String countrycode;
    String myImageData;
    boolean valueCursor;
    String mLastBeforeText;
    Bitmap bitmap;
    String photoPath;
    Uri uri;
    Cursor cursor;
    CursorLoader loader;
    String[] values;
    private ProgressDialog pd;
    private File mFileTemp, mFileTemp1;

    public Settings() {
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public static void getValuesFromUI() {

        Constant.email = editTextEmail.getText().toString();
        Constant.mobileNumber = editTextMobileNo.getText().toString();
        Constant.firstName = editTextFirstName.getText().toString();
        Constant.lastName = editTextLastName.getText().toString();
        Constant.insurance = encodedString;
        Constant.profilepicture = imageString;
        if (button.isChecked())
            Constant.accept = "1";
        else
            Constant.accept = "0";

        // Constant.ba1 = ba1;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting, container, false);

        ctx = getContext();
        init();
        process();
        if (Constant.isConnectingToInternet(ctx)) {

            GetprofileInfo();//Method Declaration


        } else {
            DriverApplication.alertDialog(ctx, IConstant.alert, IConstant._ERR_NO_INTERNET_CONNECTION);
        }


        return rootView;
    }

    public void init() {
        Gibson_Light = Typeface.createFromAsset(getActivity().getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeue-Light.ttf");

        btn_signout = (Button) rootView.findViewById(R.id.setting_btn_signout);
        countrycode = Common.GetTeleCountryCode(getActivity());
        button = (ToggleButton) rootView.findViewById(R.id.toggleButton1);
        editTextEmail = (EditText) rootView.findViewById(R.id.setting_et_emailaddress);
        editTextMobileNo = (EditText) rootView.findViewById(R.id.setting_et_mobile);
        editTextFirstName = (EditText) rootView.findViewById(R.id.setting_et_firstname);
        editTextLastName = (EditText) rootView.findViewById(R.id.setting_et_lastname);
        editaccept = (TextView) rootView.findViewById(R.id.setting_txt_acceptjobs);
        editinsurancedoc = (TextView) rootView.findViewById(R.id.setting_et_insurancedoc);
        profileImage = (ImageView) rootView.findViewById(R.id.setting_profile_icon);
        password = (LinearLayout) rootView.findViewById(R.id.setting_ll_password);
        txt_password = (TextView) rootView.findViewById(R.id.setting_txt_password);

        editTextEmail.setTypeface(HnThin);
        editTextLastName.setTypeface(HnThin);
        editTextMobileNo.setTypeface(HnThin);
        editTextFirstName.setTypeface(HnThin);
        txt_password.setTypeface(HnThin);
        editaccept.setTypeface(HnThin);
        editinsurancedoc.setTypeface(HnThin);


        editTextMobileNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!valueCursor) {
                        editTextMobileNo.setCursorVisible(true);
                    } else {
                        editTextMobileNo.setCursorVisible(false);
                    }
                } else {
                    editTextMobileNo.setCursorVisible(false);
                }
            }
        });
        editTextMobileNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    editTextMobileNo.setCursorVisible(false);
                    Common.hideSoftKeyboard(ctx, v);
                    return true;
                }
                return false;
            }
        });
        editTextMobileNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editTextMobileNo.setCursorVisible(true);
                return false;
            }
        });
        setPhLimit();
    }

    private void setPhLimit() {

        int maxLength;
        if (countrycode.length() == 2) {
            maxLength = 17;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            editTextMobileNo.setFilters(fArray);
        } else if (countrycode.length() == 3) {
            maxLength = 19;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            editTextMobileNo.setFilters(fArray);
        } else {
            maxLength = 20;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            editTextMobileNo.setFilters(fArray);
        }
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state))
            mFileTemp = new File(Environment.getExternalStorageDirectory(), IConstant.TEMP_PHOTO_FILE_NAME);
        else
            mFileTemp = new File(getActivity().getFilesDir(), IConstant.TEMP_PHOTO_FILE_NAME);

        String userInfo = PreferenceConnector.readString(getContext(), PreferenceConnector._PREF_USER_INFO, "");


        String state1 = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state1))
            mFileTemp1 = new File(Environment.getExternalStorageDirectory(), IConstant.TEMP_PHOTO_FILE_NAME1);
        else
            mFileTemp1 = new File(getActivity().getFilesDir(), IConstant.TEMP_PHOTO_FILE_NAME1);

        String userInfo1 = PreferenceConnector.readString(getContext(), PreferenceConnector._PREF_USER_INFO1, "");
        if (userInfo.length() > 0) {

            LoginResponseParams loginRequestParams = (LoginResponseParams) DriverApplication.getFromJSON(userInfo, LoginResponseParams.class);
            LoginResponseParams.Login login = loginRequestParams.new Login();


        }


        editTextMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                mLastBeforeText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Common.formetText(mLastBeforeText, s, countrycode, editTextMobileNo);

            }
        });


        if (Constant.settingEditFlag == 1) {
            editTextEmail.setFocusableInTouchMode(false);
            editTextMobileNo.setFocusableInTouchMode(true);
            editTextFirstName.setFocusableInTouchMode(true);
            editTextLastName.setFocusableInTouchMode(true);
            profileImage.setEnabled(true);
            editaccept.setFocusableInTouchMode(true);
            button.setEnabled(true);
            password.setFocusableInTouchMode(true);
            editinsurancedoc.setTextColor(Color.BLACK);
            editinsurancedoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomImageDialog();


                }
            });


        } else {
            editTextEmail.setFocusableInTouchMode(false);
            editTextMobileNo.setFocusableInTouchMode(false);
            editTextFirstName.setFocusableInTouchMode(false);
            editTextLastName.setFocusableInTouchMode(false);
            profileImage.setEnabled(false);
            editaccept.setFocusableInTouchMode(false);
            editinsurancedoc.setFocusableInTouchMode(false);
            editinsurancedoc.setTextColor(Color.BLACK);
            button.setEnabled(false);
            password.setEnabled(false);

        }

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {


            }
        });


    }

    public void GetprofileInfo() {
        pd = new ProgressDialog(ctx, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        preferences = getActivity().getSharedPreferences("Trucker", Context.MODE_PRIVATE);// get shared preference and mode to be selected
        final String authToken = preferences.getString("authToken", null);//getting a userid and authtoken in login screen
        final String userId = preferences.getString("Userid", null);


        Log.d("Test", userId + " : " + authToken);//print both the result

        queue = Volley.newRequestQueue(getActivity());
        JSONObject object = null;

        try {
            object = new JSONObject();
            object.put("AuthToken", authToken);
            object.put("UserId", userId);
            object.put("UserType", IConstant.UserType);
            Log.d("Settings json object", authToken + " : " + userId + ":" + IConstant.UserType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Volley service for getting user information
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, IConstant.GetProfileInfo, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response:", response.toString());

                // closeprogress();
                GeneralResponseParams responseData = (GeneralResponseParams) DriverApplication.getFromJSON(response.toString(), GeneralResponseParams.class);//Loginresponse model class
                int StatusCode = Integer.parseInt(String.valueOf(responseData.getStatusCode()));
                String message = responseData.getMessage();
                if (StatusCode == 97) {
                    infoalertdialog(ctx, IConstant.alert, message, StatusCode);

                }


                final ProfileResponse mProfileResponse;
                Gson gson = new Gson();
                mProfileResponse = gson.fromJson(response.toString(), ProfileResponse.class);
                try {
                    editTextFirstName.setText(mProfileResponse.getPersonal().getFirstname());
                    editTextEmail.setText(mProfileResponse.getPersonal().getEmail());
                    String fileName = mProfileResponse.getPersonal().getInsurance().substring(mProfileResponse.getPersonal().getInsurance().lastIndexOf('/') + 1);
                    editinsurancedoc.setText(fileName);
                    editinsurancedoc.setHintTextColor(Color.BLACK);

                    editTextLastName.setText(mProfileResponse.getPersonal().getLastname());
                    editTextMobileNo.setText(mProfileResponse.getPersonal().getMobileNumber());


                    if (mProfileResponse.getPersonal().getAcceptJobs().equals("1"))
                        button.setChecked(true);

                    else if (mProfileResponse.getPersonal().getAcceptJobs().equals("0"))
                        button.setChecked(false);
                    try {

                        if (mProfileResponse.getPersonal().getProfilePicture() != "" || mProfileResponse.getPersonal().getProfilePicture() != null) {
                            Picasso.with(getActivity())
                                    .load(mProfileResponse.getPersonal().getProfilePicture())
                                    .into(profileImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                    byte[] ba = bao.toByteArray();
                    ba1 = Base64.encodeToString(ba, Base64.DEFAULT);


                } catch (Exception e) {

                }
                try {


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

                        //  System.out.println("personal = " + personal);

                        String fn = personal.getString("Firstname");
                        String ln = personal.getString("Lastname");
                        String email = personal.getString("Email");
                        String mobile = personal.getString("MobileNumber");
                        String accept = personal.getString("AcceptJobs");
                        String insurance = personal.getString("Insurance");


                        editor.putString("FirstName", fn);
                        editor.putString("LastName", ln);
                        editor.putString("Email", email);
                        editor.putString("Mobile", mobile);
                        editor.putString("ProfilePicture", ba1);
                        editor.putString("AcceptJobs", accept);
                        editor.putString("Insurance", insurance);

                        editor.commit();

                    } catch (JSONException e1) {

                    }
                } catch (Exception e) {

                }
                closeprogress();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.d("Response: ", error.toString());
                closeprogress();
                String json;

                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        Log.e("Error password-->", json);
                        try {
                            if (json != null) {
                                JSONObject jsonObject = new JSONObject(json);
                                String message = jsonObject.getString("message");

                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ctx);
                                dlgAlert.setMessage(message);
                                dlgAlert.setTitle("Login ");
                                dlgAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                dlgAlert.setCancelable(true);
                                dlgAlert.create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    } catch (UnsupportedEncodingException e) {
                        Log.e("Error 111", e.getMessage());
                    }
                }
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void showProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(ctx);
            pd.setMessage("Loading. Please wait...");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
        }
        pd.show();

    }


    private void closeprogress() {
        // TODO Auto-generated method stub
        if (pd.isShowing())
            try {
                pd.cancel();
            } catch (IllegalArgumentException e) {

            }
    }


    public void process() {

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChangePassword.class);
                startActivity(i);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomImageDialog1();
            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.isConnectingToInternet(ctx)) {

                    UserLogout();//Method Declaration


                } else {
                    DriverApplication.alertDialog(ctx, IConstant.alert, IConstant._ERR_NO_INTERNET_CONNECTION);
                }


            }
        });
    }

    private void CustomImageDialog1() {
        imageoptionDialog1 = new Dialog(getActivity(), R.style.Dialog);
        imageoptionDialog1.setCancelable(false);
        imageoptionDialog1.setContentView(R.layout.dialog_camera_or_gallery);
        TextView dialog_title = (TextView) imageoptionDialog1.findViewById(R.id.tvalerthead);
        TextView camera = (TextView) imageoptionDialog1.findViewById(R.id.btncamera);
        TextView gallery = (TextView) imageoptionDialog1.findViewById(R.id.btngallery);
        Button cancel = (Button) imageoptionDialog1.findViewById(R.id.btncancel);

        camera.setTypeface(HnThin);
        gallery.setTypeface(HnThin);
        cancel.setTypeface(HnThin);
        imageoptionDialog1.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageoptionDialog1.dismiss();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
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
                    startActivityForResult(intent, _CamCode);

                } catch (ActivityNotFoundException e) {
                    Log.d("CreateAccount", "cannot take picture", e);
                }

                imageoptionDialog1.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, _InsuranceCode);
                imageoptionDialog1.dismiss();
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
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
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
                try {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, _GaleryCode);
                    imageoptionDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_OK)
            return;
        switch (requestCode) {
            case _CameraCode:
                Uri mImageCaptureUri = Uri.fromFile(mFileTemp);
                image = mFileTemp1.getPath();
                bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());


                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    encodedString = encodeImage(outputStream.toByteArray());
                    Constant.insurance = encodedString;
                    System.out.println("encodedString = " + encodedString);
                    Log.d("encodedString", encodedString);
                    filename1 = image.substring(image.lastIndexOf("/") + 1);
                    editinsurancedoc.setText(filename1);
                } catch (Exception e) {

                }

                break;


            case _GaleryCode:
                if (null == data)
                    return;


                Uri selectedImageUri1 = data.getData();

                //MEDIA GALLERY
                selectedImagePath = ImageFilePath.getPath(getContext(), selectedImageUri1);
                String path2 = selectedImagePath;//it contain your path of image..im using a temp string..
                filename1 = path2.substring(path2.lastIndexOf("/") + 1);
                editinsurancedoc.setText(filename1);
                String state2 = Environment.getExternalStorageState();

                if (Environment.MEDIA_MOUNTED.equals(state2))
                    mFileTemp1 = new File(Environment.getExternalStorageDirectory(), IConstant.TEMP_PHOTO_FILE_NAME1);
                else
                    mFileTemp1 = new File(getActivity().getFilesDir(), IConstant.TEMP_PHOTO_FILE_NAME1);

                String userInfo2 = PreferenceConnector.readString(getContext(), PreferenceConnector._PREF_USER_INFO1, "");

                bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
                    encodedString = encodeImage(byteArrayOutputStream2.toByteArray());
                    Constant.insurance = encodedString;
                    System.out.println("encodedString = " + encodedString);
                    Log.d("encodedString", encodedString);
                    editinsurancedoc.setText(filename1);
                } catch (Exception e) {

                }
                break;

            case _CROP_FROM_CAMERA1:
                String path = data.getStringExtra(IMAGE_PATH);
                editinsurancedoc.setText(path);

                break;
        }


        {

            if (resultCode != getActivity().RESULT_OK) return;
            switch (requestCode) {
                case _CamCode:
                    startCropImage();


                    break;
                case _InsuranceCode:
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                        FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                        copyStream(inputStream, fileOutputStream);
                        fileOutputStream.close();
                        inputStream.close();
                        startCropImage();
                    } catch (Exception e) {
                        Log.e("CreateAccount gallery", "Error while creating temp file", e);
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
                        Constant.profilepicture = imageString;
                        System.out.println("imageString = " + imageString);
                    }
                    break;
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();


        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private void startCropImage() {
        Intent intent = new Intent(getActivity(), CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 5);
        intent.putExtra(CropImage.ASPECT_Y, 5);
        startActivityForResult(intent, _CROP_FROM_CAMERA);
    }


    private void UserLogout() {
        pd = new ProgressDialog(ctx, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        preferences = getActivity().getSharedPreferences("Trucker", Context.MODE_PRIVATE);
        String userId = preferences.getString("Userid", null);
        String authToken = preferences.getString("authToken", null);
        Log.d("Test", userId + " : " + authToken);
        queue = Volley.newRequestQueue(getActivity());

        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("AuthToken", authToken);
            object.put("UserId", userId);
        } catch (Exception e) {
            e.printStackTrace();

        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, IConstant.UserLogout, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response:", response.toString());
                closeprogress();
                LoginResponseParams responseData = (LoginResponseParams) DriverApplication.getFromJSON(response.toString(), LoginResponseParams.class);
                int StatusCode = Integer.parseInt(responseData.getStatusCode());
                String message = responseData.getMessage();

                alertdialog(ctx, IConstant.signout, message, StatusCode);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Response: ", error.toString());
                closeprogress();
                String json;

                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        Log.e("Error login-->", json);
                        try {
                            if (json != null) {
                                JSONObject jsonObject = new JSONObject(json);
                                String message = jsonObject.getString("message");
                              //  Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
                                dlgAlert.setMessage(message);
                                dlgAlert.setTitle("Login ");
                                dlgAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                dlgAlert.setCancelable(true);
                                dlgAlert.create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                          //  Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        alertContent.setText("Are you sure to signout?");
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
                    SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor remEditor = preferences.edit();
                    remEditor.remove("userid");
                    remEditor.apply();
                    Intent intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                } else if (Status == 97) {
                    dialog.dismiss();
                    Intent intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                } else {
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public void infoalertdialog(final Context ctx, String Title, String Content, final int Status) {
        final Dialog dialog = new Dialog(ctx, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alertdialog);

        // set the custom dialog components - title and content
        TextView alertHead = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alerthead);
        alertHead.setText(Title);
        TextView alertContent = (TextView) dialog.findViewById(R.id.custom_alertdialog_tv_alertcontent);
        alertContent.setText("Are you sure to signout?");
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
                if (Status == 97) {
                    Intent intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}