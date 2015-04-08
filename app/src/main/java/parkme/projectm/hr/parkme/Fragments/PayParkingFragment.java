package parkme.projectm.hr.parkme.Fragments;

/**
 * Created by Mihael on 8.4.2015..
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import parkme.projectm.hr.parkme.Activities.ParkingPaymentActivity;
import parkme.projectm.hr.parkme.R;

/**
 * Class where is current car selected, list of favorite parking payment and button for new payment
 */
public class PayParkingFragment extends Fragment {

    Button parkingPayButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parking_pay, container, false);

        parkingPayButton = (Button) rootView.findViewById(R.id.btnPayParking);

        final Context context = getActivity();

        parkingPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ParkingPaymentActivity.class);
                startActivity(i);

            }
        });

        return rootView;
    }
}
