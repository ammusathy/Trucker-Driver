package driver.com.driver.screens;

import android.graphics.Typeface;
import  android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import driver.com.driver.R;

/**
 * Created by kalaivani on 3/8/2016.
 */


public class CurrentJob extends AppCompatActivity {

    ImageView image_back,button;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView txt_current,in_transit;
    Typeface Gibson_Light, HnBold, HnThin, HnLight, Gibson_Regular, GillSansStd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_job);

        image_back = (ImageView) findViewById(R.id.current_image_back);
        txt_current=(TextView)findViewById(R.id.current_txt_current);
        in_transit=(TextView)findViewById(R.id.current_txt_transit) ;
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        HnBold = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        txt_current.setTypeface(HnThin);
        in_transit.setTypeface(HnThin);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.current_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CurrentDetailsFragment(), "DETAIL");
        adapter.addFragment(new CurrentMessageFragment(), "MESSAGE");
        adapter.addFragment(new CurrentDirectionsFragment(),"DIRECTIONS");
          /*  adapter.addFragment(new CurrentDirectionsFragment(),"DIRECTIONS");*/

        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


