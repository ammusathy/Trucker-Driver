package driver.com.driver.screens;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.fragment.MyDialogFragment;

/**
 * Created by kalaivani on 3/27/2016.
 */
public class AddAdditionalServices extends Activity implements View.OnClickListener {
    private ArrayList<String> stringList;
    private ArrayList<String> sublist;
    String[] additional_services;
    TextView add_additional_text1, add_additional_text2;
    Button btn_ok, btn_cancel;
    View rootView;
    private BroadcastReceiver AdditionalBroadcastReceiver;
    private EditText edit_additional_text2;
    String text1, text2;
    Typeface Gibson_Light, HnBold, HnThin, HnLight, Gibson_Regular, GillSansStd;

    private CurrentDetailsFragment.AdditionalServiceListener additionalServiceListener;

    public void setAdditionalServiceListener(CurrentDetailsFragment.AdditionalServiceListener additionalServiceListener) {
        this.additionalServiceListener = additionalServiceListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_additional_services);


        sublist = new ArrayList<String>();
        //additional_services = this.getResources().getStringArray(R.array.additional_services);

        // stringList = new ArrayList<String>(Arrays.asList(TrukrApplication.getArrayList()));
        stringList = DriverApplication.getArrayList();
        AdditionalBroadcastReceiver = new LocalAdditionalServiceReceiver();
        add_additional_text1 = (TextView) findViewById(R.id.add_additional_text1);
        add_additional_text2 = (TextView) findViewById(R.id.add_additional_text2);
        edit_additional_text2 = (EditText) findViewById(R.id.edit_additional_text2);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);


        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        HnBold = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");

        add_additional_text1.setTypeface(HnThin);
        add_additional_text2.setTypeface(HnThin);
        btn_ok.setTypeface(Gibson_Light);
        btn_cancel.setTypeface(Gibson_Light);






        add_additional_text1.setOnClickListener(this);
        add_additional_text2.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_additional_text1:

                if (stringList.size() > 0) {
                    showDialog(stringList, "AdditionalLabel", add_additional_text1);
                    add_additional_text2.setText("");
                    edit_additional_text2.setText("");
                }


                break;
            case R.id.add_additional_text2:
                if (!TextUtils.isEmpty(add_additional_text1.getText().toString())) {
                    if (add_additional_text1.getText().toString().equals("Load straps") ||
                            add_additional_text1.getText().toString().equals("Stops-in-transit")) {


                    } else {
                        if (sublist.size() > 0) {
                            showDialog(sublist, "SubAdditionalLabel", add_additional_text2);
                        }

                    }

                }

                break;
            case R.id.btn_ok:
                text1 = add_additional_text1.getText().toString().trim();
                text2 = edit_additional_text2.getText().toString().trim();


//                if(!TextUtils.isEmpty(add_additional_text1.getText().toString()) && (!TextUtils.isEmpty(add_additional_text2.getText().toString()) || !TextUtils.isEmpty(edit_additional_text2.getText().toString()))) {
//                    DriverApplication.removeAdditionalService(add_additional_text1.getText().toString());
//                    getFragmentManager().popBackStackImmediate();//after entering a two text it again send to home screen
//                    DriverApplication.mSelAdditionallabel =add_additional_text1.getText().toString();//getting values from label for text1
//                    if(!TextUtils.isEmpty(add_additional_text2.getText().toString())){
//                        DriverApplication.mSelAdditionalValue =add_additional_text2.getText().toString();//getting values from label for text2
//                    }
//                    if(!TextUtils.isEmpty(edit_additional_text2.getText().toString())){
//                        DriverApplication.mSelAdditionalValue =edit_additional_text2.getText().toString();//getting a values from edit additional text
//                    }
                Intent intent = new Intent();
                intent.putExtra("String1", text1);
                intent.putExtra("String2", text2);
                setResult(RESULT_OK, intent);
                finish();

                break;
//                }
            //SetResult(Resu)
//                if (!TextUtils.isEmpty(add_additional_text1.getText().toString()) && (!TextUtils.isEmpty(add_additional_text2.getText().toString()) || !TextUtils.isEmpty(edit_additional_text2.getText().toString()))) {
//                    DriverApplication.removeAdditionalService(add_additional_text1.getText().toString());
//                    getActivity().getSupportFragmentManager().popBackStackImmediate();
//                    DriverApplication.mSelAdditionallabel = add_additional_text1.getText().toString();
//                    if (!TextUtils.isEmpty(add_additional_text2.getText().toString())) {
//                        DriverApplication.mSelAdditionalValue = add_additional_text2.getText().toString();
//                    }
//
//                    }
//                   // additionalServiceListener.refereshAditionalServiceView();
//                    CurrentDetailsFragment currentDetailsFragment=new CurrentDetailsFragment();
////                    currentDetailsFragment.addDynamicView();
//                    //getActivity().finish();
//
//                    //additionalServiceListener.refereshAditionalServiceView();
//                   /* CurrentDetailsFragment h=new CurrentDetailsFragment();
//                    h.addDynamicView(add_additional_text1.getText().toString(),
//                            add_additional_text2.getText().toString());*/
//                } else {
//                    Toast.makeText(getActivity(), "Please Complete Selection", Toast.LENGTH_LONG).show();
//                }
//                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    public void showDialog(ArrayList<String> mArrayList, String mSource, TextView txt_view) {

        MyDialogFragment dialog = new MyDialogFragment(txt_view);
        Bundle b = new Bundle();
        b.putStringArrayList("mSource", mArrayList);
        b.putString("value", mSource);
        dialog.setArguments(b);
        dialog.show(getFragmentManager(), "Dialog");

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                AdditionalBroadcastReceiver,
                new IntentFilter("ADDLABEL"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                AdditionalBroadcastReceiver);
    }

    private class LocalAdditionalServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // safety check
            if (intent == null || intent.getAction() == null) {
                return;
            }

            if (intent.getAction().equals("ADDLABEL")) {
                // doSomeAction();
                Log.i("AdditionalServices", "Called!!!");
                if (add_additional_text1.getText().toString().equals("LayOver")) {
                    sublist.add("WeekDays");
                    sublist.add("Solo");
                    add_additional_text2.setVisibility(View.GONE);
                    edit_additional_text2.setVisibility(View.VISIBLE);

                }
                //if (add_additional_text1.getText().toString().equals("Border Crossing fee")) {
                //Bordercross();
                //} else {
                //  sublist.clear();
                //sublist.add("YES");
                //sublist.add("NO");
            }

            if (add_additional_text1.getText().toString().equals("TollCharge") ||
                    add_additional_text1.getText().toString().equals("WeightFine") ||
                    add_additional_text1.getText().toString().equals("YardStorage") ||
                    add_additional_text1.getText().toString().equals("Reweighing") ||
                    add_additional_text1.getText().toString().equals("LumperCharge") ||
                    add_additional_text1.getText().toString().equals("PowerUsageHours")) {

                add_additional_text2.setVisibility(View.GONE);
                edit_additional_text2.setVisibility(View.VISIBLE);

            } else {
                add_additional_text2.setVisibility(View.VISIBLE);
                edit_additional_text2.setVisibility(View.GONE);
            }


        }
    }


}
