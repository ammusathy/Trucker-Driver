package driver.com.driver.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import driver.com.driver.R;

/**
 * Created by kalaivani on 3/9/2016.
 */

    public class Notification extends Fragment {

        RelativeLayout headerLayout;

        public Notification() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.notification, container, false);
            // Inflate the layout for this fragment
            Toast.makeText(getContext(), "Under Development", Toast.LENGTH_SHORT).show();
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
    }


