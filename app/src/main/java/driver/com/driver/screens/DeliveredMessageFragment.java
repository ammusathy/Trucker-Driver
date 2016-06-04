package driver.com.driver.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import driver.com.driver.R;

/**
 * Created by kalaivani on 3/8/2016.
 */

    /**
     * Created by kalaivani on 2/17/2016.
     */
    public class DeliveredMessageFragment extends Fragment {


        public DeliveredMessageFragment() {}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.deliverfragment_message, container, false);
        }

    }

