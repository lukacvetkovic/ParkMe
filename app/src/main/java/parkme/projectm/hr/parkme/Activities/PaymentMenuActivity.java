package parkme.projectm.hr.parkme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Fragments.ChooseCarFragment;
import parkme.projectm.hr.parkme.Fragments.PayParkingFragment;
import parkme.projectm.hr.parkme.Fragments.PaymentHistoryFragment;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Cveki on 8.3.2015..
 */
public class PaymentMenuActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments representing
     * each object in a collection. We use a {@link android.support.v4.app.FragmentStatePagerAdapter}
     * derivative, which will destroy and re-create fragments as needed, saving and restoring their
     * state in the process. This is important to conserve memory and is a best practice when
     * allowing navigation between objects in a potentially large collection.
     */
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_menu_header_fragment);

        // Create an adapter that when requested, will return a fragment representing an object in
        // the collection.
        //
        // ViewPager and its adapters use support library fragments, so we must use
        // getSupportFragmentManager.
        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            //Log.e("FragAdap", "getItem");
            if (i == 0) {
                fragment = new ChooseCarFragment();
                return fragment;
            } else if (i == 1) {
                fragment = new PayParkingFragment();
                return fragment;
            } else {
                fragment = new PaymentHistoryFragment();
                return fragment;
            }
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 3-object collection. (Izbor auta,plati parking, povijest placanja)
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Log.e("FragAdap", "getTitle");
            switch (position) {
                case 0:
                    return "Izbor Auta";
                case 1:
                    return "Plati parking";
                case 2:
                    return "Povijest plačanja";
                default:
                    return "Undefined";

            }
        }
    }

}
