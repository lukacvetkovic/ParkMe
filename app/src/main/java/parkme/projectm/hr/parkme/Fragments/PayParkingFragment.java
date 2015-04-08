package parkme.projectm.hr.parkme.Fragments;

/**
 * Created by Mihael on 8.4.2015..
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Activities.ParkingPaymentActivity;
import parkme.projectm.hr.parkme.Activities.PaymentMenuActivity;
import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;

/**
 * Class where is current car selected, list of favorite parking payment and button for new payment
 */
public class PayParkingFragment extends Fragment {

    private Context context;

    private Button parkingPayButton;
    private TextView activeCarTextView;

    private PrefsHelper prefsHelper;
    private PaymentMenuActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parking_pay, container, false);
        this.context = getActivity();

        prefsHelper = new PrefsHelper(this.context);
        String activeCarPlates = prefsHelper.getString(PrefsHelper.ActiveCarPlates, null);
        activeCarTextView = (TextView) rootView.findViewById(R.id.txtActiveCar);
        activeCarTextView.setText(activeCarPlates);

        parkingPayButton = (Button) rootView.findViewById(R.id.btnPayParking);

        parkingPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ParkingPaymentActivity.class);
                startActivity(i);

            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (PaymentMenuActivity) activity;
    }
}
