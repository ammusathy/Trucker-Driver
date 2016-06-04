package driver.com.driver.screens;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import driver.com.driver.R;

public class DeliveredJob extends AppCompatActivity {
    private TabLayout tabLayout;
        private ViewPager viewPager;
       ImageView image_back;
    TextView delivered,number;
    Typeface Gibson_Light, HnBold, HnThin, HnLight, Gibson_Regular, GillSansStd;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.delivered_job);
            image_back=(ImageView)findViewById(R.id.deliverd_image_back);
            delivered=(TextView)findViewById(R.id.delivered_txt_deliveredjobs) ;
            number=(TextView)findViewById(R.id.delivered_txt_number) ;
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        HnBold = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Bold.ttf");
        delivered.setTypeface(HnThin);
        number.setTypeface(HnThin);
            image_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(DeliveredJob.this,SlidingDrawer.class);
                    startActivity(i);
                }
            });
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.delivered_tablayout);
            tabLayout.setupWithViewPager(viewPager);

        }

        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new DeliverDetailsFragment(), "DETAIL");
            adapter.addFragment(new DeliveredMessageFragment(), "MESSAGE");
            viewPager.setAdapter(adapter);

        }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
    public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
        super(manager);
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

