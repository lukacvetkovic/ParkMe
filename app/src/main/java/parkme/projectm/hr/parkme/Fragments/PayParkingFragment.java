package parkme.projectm.hr.parkme.Fragments;

/**
 * Created by Mihael on 8.4.2015..
 */

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import parkme.projectm.hr.parkme.Activities.FragmentMenuActivity;
import parkme.projectm.hr.parkme.CustomViewModels.ActiveCarView;
import parkme.projectm.hr.parkme.CustomViewModels.FavoritePaymentArrayAdapter;
import parkme.projectm.hr.parkme.CustomViewModels.FavoritePaymentView;
import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Dialogs.ConfirmPaymentDialog;
import parkme.projectm.hr.parkme.Dialogs.PayParkingDialog;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;

/**
 * Class where is current car selected, list of favorite parking payment and button for new payment
 */
public class PayParkingFragment extends Fragment {

    private Context context;

    private ImageButton payParkingButton;
    private ActiveCarView activeCarView;

    private PrefsHelper prefsHelper;
    private FragmentMenuActivity parentActivity;

    private DatabaseManager dbManager;
    private List<FavoritePayment> favoritePaymentList;
    private FavoritePaymentArrayAdapter favoritePaymentArrayAdapter;
    private ListView favoritePaymentListView;

    private PayParkingDialog payParkingDialog;

    private PayParkingFragmentCallback payParkingFragmentCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parking_pay, container, false);
        this.context = getActivity();

        prefsHelper = new PrefsHelper(this.context);
        String activeCarPlates = prefsHelper.getString(PrefsHelper.ActiveCarPlates, null);

        activeCarView = (ActiveCarView) rootView.findViewById(R.id.activeCarView);

        favoritePaymentListView = (ListView) rootView.findViewById(R.id.favoritePaymentsList);

        DatabaseManager.init(context);
        dbManager = DatabaseManager.getInstance();
        favoritePaymentList = dbManager.getAllFavoritePayments();

        if(favoritePaymentList != null){
            Log.w("PRESS", "favoritePaymentList nije null - number of values : " + favoritePaymentList.size());
            favoritePaymentArrayAdapter = new FavoritePaymentArrayAdapter(parentActivity,
                    favoritePaymentList.toArray(new FavoritePayment[favoritePaymentList.size()]));
        }
        else{
            Log.w("PRESS", "favoritePaymentList je null - nema favs paymenta");
            favoritePaymentArrayAdapter = new FavoritePaymentArrayAdapter(parentActivity, new FavoritePayment[0]);
        }

        favoritePaymentArrayAdapter.setCallback(new FavoritePaymentArrayAdapter.FavoritePaymentArrayAdapterCallback() {
            @Override
            public void refreshFragment() {
                if(payParkingFragmentCallback != null){
                    payParkingFragmentCallback.refreshActivity();
                }
            }
        });

        favoritePaymentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FavoritePayment favoritePayment = favoritePaymentArrayAdapter.getItem(position);

                // TODO ovdje placanje

                payParkingDialog = new PayParkingDialog(context);

                payParkingDialog.setPayParkingDialogCallback(new PayParkingDialog.PayParkingDialogCallback() {
                    @Override
                    public void dismissDialog() {
                        if(payParkingFragmentCallback != null){
                            payParkingFragmentCallback.dismissPayParkingDialog(payParkingDialog);
                            payParkingFragmentCallback.refreshActivity();
                        }
                    }

                    @Override
                    public void showConfirmDialog(ConfirmPaymentDialog confirmPaymentDialog) {
                        payParkingFragmentCallback.displayConfirmPaymentDialog(confirmPaymentDialog);
                    }

                    @Override
                    public void dismissConfirmDialog(ConfirmPaymentDialog confirmPaymentDialog) {
                        payParkingFragmentCallback.dismissConfirmPaymentDialog(confirmPaymentDialog);
                    }
                });

                payParkingDialog.showConfirmPaymentDialogForFavoritepayment(favoritePayment);

                Toast toast = Toast.makeText(context, "" + favoritePayment.getGradId() + " - " + favoritePayment.getZoneID(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        favoritePaymentListView.setAdapter(favoritePaymentArrayAdapter);

        FavouriteCar activeCar = dbManager.getFavoriteCarFromPlates(activeCarPlates);
        activeCarView.setCarTablesText(activeCarPlates);
        activeCarView.getActiveCarImage().setImageResource(activeCar.getCarIcon());

        activeCarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(payParkingFragmentCallback != null) {
                    payParkingFragmentCallback.swipeToChooseCarFragment();
                }
            }
        });

        payParkingButton = (ImageButton) rootView.findViewById(R.id.imgBtnPayParking);

        payParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payParkingFragmentCallback != null){
                    payParkingDialog = new PayParkingDialog(context);
                    payParkingDialog.setPayParkingDialogCallback(new PayParkingDialog.PayParkingDialogCallback() {
                        @Override
                        public void dismissDialog() {
                            if(payParkingFragmentCallback != null){
                                payParkingFragmentCallback.dismissPayParkingDialog(payParkingDialog);
                                payParkingFragmentCallback.refreshActivity();
                            }
                        }

                        @Override
                        public void showConfirmDialog(ConfirmPaymentDialog confirmPaymentDialog) {
                            payParkingFragmentCallback.displayConfirmPaymentDialog(confirmPaymentDialog);
                        }

                        @Override
                        public void dismissConfirmDialog(ConfirmPaymentDialog confirmPaymentDialog) {
                            payParkingFragmentCallback.dismissConfirmPaymentDialog(confirmPaymentDialog);
                        }
                    });
                    payParkingFragmentCallback.displayPayParkingDialog(payParkingDialog);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (FragmentMenuActivity) activity;
    }

    public PayParkingFragmentCallback getPayParkingFragmentCallback() {
        return payParkingFragmentCallback;
    }

    public void setPayParkingFragmentCallback(PayParkingFragmentCallback payParkingFragmentCallback) {
        this.payParkingFragmentCallback = payParkingFragmentCallback;
    }
}
