package driver.com.driver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import driver.com.driver.R;

/**
 * Created by kalaivani on 4/26/2016.
 */
public class Home extends Fragment {
    View rootView;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.jobs_list, container, false);
        // Inflate the layout for this fragment

        return rootView;
    }
}
