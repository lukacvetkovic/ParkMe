package parkme.projectm.hr.parkme.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import parkme.projectm.hr.parkme.CustomViewModels.ActiveTicketView;
import parkme.projectm.hr.parkme.R;
import parkme.projectm.hr.parkme.Services.ParkingServiceHelper;

/**
 * Created by Mihael on 8.4.2015..
 */
public class PaymentHistoryFragment extends Fragment {

    private Button startServiceButton;
    private Button stopServiceButton;

    private Button getRemainingTimeButton;
    private Button setRemainingTimeButton;
    private TextView remainingTimeTextView;

    private ActiveTicketView activeTicketView;

    private ParkingServiceHelper parkingServiceHelper = new ParkingServiceHelper();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_history, container, false);
        startServiceButton = (Button) rootView.findViewById(R.id.btnStartService);
        stopServiceButton = (Button) rootView.findViewById(R.id.btnStopService);
        remainingTimeTextView = (TextView) rootView.findViewById(R.id.txtRemainingTime);
        getRemainingTimeButton = (Button) rootView.findViewById(R.id.btnGetTime);
        setRemainingTimeButton = (Button) rootView.findViewById(R.id.btnSetTime);
        activeTicketView = (ActiveTicketView) rootView.findViewById(R.id.activeTicketView);

        parkingServiceHelper.setServiceListener(new ParkingServiceHelper.ParkingServiceListener() {
            @Override
            public void serviceStatus(ParkingServiceHelper.ServiceStatus status) {
                if(status != null){
                    remainingTimeTextView.setText("" + status.getActiveParkingRemainingTime());
                }
            }
        });

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingServiceHelper.startService(getActivity());
            }
        });

        getRemainingTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingServiceHelper.getServiceStatus(getActivity());
            }
        });

        setRemainingTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingServiceHelper.setActiveParkingTime(15, getActivity());
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingServiceHelper.stopService(getActivity());
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO popunit activeTicketView ako imamo aktivnu kartu, ako ne ne - hideat ga ili nesto
    }
}
