package driver.com.driver.fragment;

/**
 * Created by kalaivani on 3/26/2016.
 */

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;
import driver.com.driver.model.ResponseParams.ActionCallBack;

public class MyDialogFragment extends DialogFragment implements
        OnItemClickListener {
public ActionCallBack callBack;
    TextView txt_view;
    public MyDialogFragment(TextView txt_view){
      this.txt_view=txt_view;

    }

    //String[] listitems = { "item01", "item02", "item03", "item04" };

    ListView mylist;
    ArrayList<String> arrayList;
    String mSourceString;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment, null, false);
        mylist = (ListView) view.findViewById(R.id.list);

        arrayList=getArguments().getStringArrayList("mSource");
        mSourceString=getArguments().getString("value");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, arrayList);

        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        dismiss();
       // Toast.makeText(getActivity(), arrayList.get(position), Toast.LENGTH_SHORT)
              //  .show();
       if(mSourceString.equalsIgnoreCase("Source")){
           Log.i("SourceSelected",mSourceString);
           DriverApplication.mSelectedSource = position;
           txt_view.setText(arrayList.get(position).toString());
       }else if(mSourceString.equalsIgnoreCase("Destination")){
           Log.i("SourceSelected",mSourceString);
           DriverApplication.mSelectedDestination = position;
       }else if(mSourceString.equalsIgnoreCase("TruckType")){
           LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
                   new Intent("SOME_ACTION"));
       }else if(mSourceString.equalsIgnoreCase("AdditionalLabel")){
           LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
                   new Intent("ADDLABEL"));
       }else if(mSourceString.equalsIgnoreCase("SubAdditionalLabel")){

       }
        txt_view.setText(arrayList.get(position).toString());
        /*callBack =(ActionCallBack) getTargetFragment();
        callBack.onSelectedItem(position);*/

    }

}