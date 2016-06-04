package driver.com.driver.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
 * Created by mansoor on 17/05/16.
 */
public class Cancelled  extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView image_back;
    TextView txt_order,cancelled_order,cancelledjob;
    String orderId;
    private Typeface Gibson_Light, HnThin, HnLight,Gibson_regular;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelled);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("OrderId");
        image_back=(ImageView)findViewById(R.id.cancelled_image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SlidingDrawer.class);
                startActivity(i);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.cancelled_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        cancelled_order=(TextView)findViewById(R.id.current_txtorder) ;
        cancelled_order.setText(orderId);
        txt_order=(TextView)findViewById(R.id.cancel_txtorder_cancel);
        cancelledjob=(TextView)findViewById(R.id.cancelled_txt_cancelledjobs);
        Gibson_Light = Typeface.createFromAsset(getAssets(), "Gibson_Light.otf");
        HnThin = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Thin.otf");
        HnLight = Typeface.createFromAsset(getAssets(), "HelveticaNeue-Light.ttf");
        Gibson_regular=Typeface.createFromAsset(getAssets(),"Gibson-Regular.ttf");
        cancelled_order.setTypeface(Gibson_regular);
        cancelledjob.setTypeface(Gibson_regular);
        txt_order.setTypeface(Gibson_regular);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CancelledDetailsFragment(), "DETAIL");
        viewPager.setAdapter(adapter);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
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


