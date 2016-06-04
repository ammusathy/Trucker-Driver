package driver.com.driver.screens;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import driver.com.driver.Application.DriverApplication;
import driver.com.driver.R;

public class JobList extends Fragment {

    View v1, v2;
    TextView tv1, tv2;
    LayoutInflater inflater1;
    LinearLayout l1, l2;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public JobList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.jobs_list, null);
        inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v1 = inflater1.inflate(R.layout.layout_tab, null);
        l1 = (LinearLayout) v1.findViewById(R.id.ll);
        tv1 = (TextView) v1.findViewById(R.id.text_title);
        tv1.setText("Current");
        v2 = inflater1.inflate(R.layout.layout_tab, null);
        l2 = (LinearLayout) v2.findViewById(R.id.ll);
        tv2 = (TextView) v1.findViewById(R.id.text_title);
        tv2.setText("Past");
        DriverApplication.addAdditionalServices();
        viewPager = (ViewPager) view.findViewById(R.id.jobs_viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
//        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.jobs_tabs);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setCustomView(v1);
//        tabLayout.getTabAt(1).setCustomView(v2);
        tabLayout.getTabAt(0).setText("Current");
        tabLayout.getTabAt(1).setText("Past");
        return view;
    }



      /*  tabLayout.getTabAt(0).setText("Current");


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(),tab.getPosition()+" ",Toast.LENGTH_SHORT).show();

                int position = tab.getPosition();
                if(position == 1){
                    l1.setBackgroundColor(getActivity().getResources().getColor(R.color.unselectedred));
                    l2.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                }
                if(position == 0){
                    l2.setBackgroundColor(getActivity().getResources().getColor(R.color.unselectedred));
                    l1.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
//        adapter.addFragment(new CurrentFragment(), "CURRENT");
//        adapter.addFragment(new PastFragment(), "PAST");
//            /*adapter.addFragment(new CurrentDirectionsFragment(),"DIRECTIONS");*/
//          /*  adapter.addFragment(new CurrentDirectionsFragment(),"DIRECTIONS");*/
//
//        viewPager.setAdapter(adapter);
//
//    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) return new CurrentFragment();
            if (position == 1) return new PastFragment();

            Toast.makeText(getActivity(), " " + position, Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
    }

}



