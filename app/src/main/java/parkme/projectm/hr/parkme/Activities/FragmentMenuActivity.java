package parkme.projectm.hr.parkme.Activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Dialogs.AddCarDialog;
import parkme.projectm.hr.parkme.Dialogs.UpdateOrRemoveCarDialog;
import parkme.projectm.hr.parkme.Fragments.ChooseCarFragment;
import parkme.projectm.hr.parkme.Fragments.ChooseCarFragmentCallback;
import parkme.projectm.hr.parkme.Fragments.PayParkingFragment;
import parkme.projectm.hr.parkme.Fragments.PayParkingFragmentCallback;
import parkme.projectm.hr.parkme.Fragments.PaymentHistoryFragment;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;
import parkme.projectm.hr.parkme.Receivers.IncomingSms;

/**
 * Created by Cveki on 8.3.2015..
 */
public class FragmentMenuActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments representing
     * each object in a collection. We use a {@link android.support.v4.app.FragmentStatePagerAdapter}
     * derivative, which will destroy and re-create fragments as needed, saving and restoring their
     * state in the process. This is important to conserve memory and is a best practice when
     * allowing navigation between objects in a potentially large collection.
     */
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

    private RelativeLayout rootRelativeView;
    private AddCarDialog addCarDialog;
    private UpdateOrRemoveCarDialog updateorRemoveCarDialog;
    private Context context;

    private PrefsHelper prefsHelper;

    private int pageToTurnTo = 1;

    public static String INTENT_FROM_SERVICE_NOTIFICATION = "intent_from_service_notification";

    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_menu_header_fragment);
        this.context = this;

        prefsHelper = new PrefsHelper(this.context);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        if(getIntent() != null){
            if(getIntent().getBooleanExtra(INTENT_FROM_SERVICE_NOTIFICATION, false) == true){
                this.pageToTurnTo = 3;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager, attaching the adapter.
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setCurrentItem(pageToTurnTo, true);     // postavlja inicijalno na srednji, TODO - ovisno o tome dal postoji parkirna karta

        String activeCarPlates = prefsHelper.getString(PrefsHelper.ActiveCarPlates, null);
        if(activeCarPlates == null){
            // trying to recover
            DatabaseManager dbManager = DatabaseManager.getInstance();
            List<FavouriteCar> favoriteCarList = dbManager.getAllFavouriteCars();

            if(favoriteCarList != null && favoriteCarList.size() > 0) {     // lazy evaluation and we recovered, fuck yeah
                FavouriteCar favouriteCar = favoriteCarList.get(0);
                activeCarPlates = favouriteCar.getCarRegistration();
                prefsHelper.putString(PrefsHelper.ActiveCarPlates, activeCarPlates);
            }
            else {  // fuck it
                Intent startMainForNewCar = new Intent(this, MainMenuActivity.class);
                startActivity(startMainForNewCar);
                finish();
            }
        }
    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                ChooseCarFragment fragment = new ChooseCarFragment();
                fragment.setChooseCarFragmentCallback(new ChooseCarFragmentCallback() {
                    @Override
                    public void displayAddCarDialog() {
                        rootRelativeView = (RelativeLayout) findViewById(R.id.rootRelativeView);
                        addCarDialog = new AddCarDialog(context);
                        addCarDialog.setDialogActive(true);
                        addCarDialog.setDismissCallback(new AddCarDialog.FirstTimeAddCarCallback() {
                            @Override
                            public void dismissThisDialog() {
                                removeAddCarDialog();
                            }
                        });
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        rootRelativeView.addView(addCarDialog, params);
                    }

                    @Override
                    public void refreshActivity() {     // when fav car is selected as active
                        pageToTurnTo = 0;
                        onResume();
                    }

                    @Override
                    public void updateOrRemoveFavoriteCar(FavouriteCar favoriteCar) {
                        rootRelativeView = (RelativeLayout) findViewById(R.id.rootRelativeView);
                        updateorRemoveCarDialog = new UpdateOrRemoveCarDialog(context);
                        updateorRemoveCarDialog.setDialogActive(true);
                        updateorRemoveCarDialog.setFavoriteCarToUpdate(favoriteCar);
                        updateorRemoveCarDialog.setDismissCallback(new UpdateOrRemoveCarDialog.UpdateOrRemoveCarCallback() {
                            @Override
                            public void dismissThisDialog() {
                                removeUpdateOrDeleteCarDIalog();
                            }
                        });
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        rootRelativeView.addView(updateorRemoveCarDialog, params);
                    }
                });
                return fragment;
            } else if (i == 1) {
                PayParkingFragment fragment = new PayParkingFragment();
                fragment.setPayParkingFragmentCallback(new PayParkingFragmentCallback() {
                    @Override
                    public void refreshActivity() {
                        pageToTurnTo = 1;
                        onResume();
                    }

                    @Override
                    public void swipeToChooseCarFragment() {
                        mViewPager.setCurrentItem(0, true);
                    }
                });
                return fragment;
            } else {
                PaymentHistoryFragment fragment = new PaymentHistoryFragment();
                return fragment;
            }
        }

        @Override
        public int getCount() {
            // (Izbor auta,plati parking, povijest placanja)
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
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

    @Override
    public void onBackPressed() {
        if(addCarDialog != null && addCarDialog.isDialogActive()){
            this.removeAddCarDialog();
        }

        else if(updateorRemoveCarDialog != null && updateorRemoveCarDialog.isDialogActive()) {
            this.removeUpdateOrDeleteCarDIalog();
        }

        else{
            super.onBackPressed();
        }
    }

    private void removeAddCarDialog(){
        addCarDialog.setDialogActive(false);
        rootRelativeView.removeView(addCarDialog);
        addCarDialog.setVisibility(View.GONE);
        pageToTurnTo = 0;
        onResume();
    }

    private void removeUpdateOrDeleteCarDIalog(){
        updateorRemoveCarDialog.setDialogActive(false);
        rootRelativeView.removeView(addCarDialog);
        updateorRemoveCarDialog.setVisibility(View.GONE);
        pageToTurnTo = 0;
        onResume();
    }


}
