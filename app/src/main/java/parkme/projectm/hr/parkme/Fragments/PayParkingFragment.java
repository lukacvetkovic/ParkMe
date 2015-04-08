package parkme.projectm.hr.parkme.Fragments;

/**
 * Created by Mihael on 8.4.2015..
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import parkme.projectm.hr.parkme.Activities.ParkingPaymentActivity;
import parkme.projectm.hr.parkme.Activities.PaymentMenuActivity;
import parkme.projectm.hr.parkme.CustomViewModels.ActiveCarView;
import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;

/**
 * Class where is current car selected, list of favorite parking payment and button for new payment
 */
public class PayParkingFragment extends Fragment {

    private Context context;

    private Button parkingPayButton;
    private ActiveCarView activeCarView;

    private PrefsHelper prefsHelper;
    private PaymentMenuActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parking_pay, container, false);
        this.context = getActivity();

        prefsHelper = new PrefsHelper(this.context);
        String activeCarPlates = prefsHelper.getString(PrefsHelper.ActiveCarPlates, null);

        activeCarView = (ActiveCarView) rootView.findViewById(R.id.activeCarView);

        DatabaseManager dbManager = DatabaseManager.getInstance();

        FavouriteCar activeCar = dbManager.getFavoriteCarFromPlates(activeCarPlates);
        activeCarView.setCarTablesText(activeCarPlates);
        activeCarView.getActiveCarImage().setImageResource(activeCar.getCarIcon());

        parkingPayButton = (Button) rootView.findViewById(R.id.btnPayParking);

        parkingPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ParkingPaymentActivity.class);
                startActivity(i);

            }
        });

        //Log.w("PAYPARKING", "ovdje je on resume");

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (PaymentMenuActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.w("TAG", "onResume");
    }
}
