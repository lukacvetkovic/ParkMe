package parkme.projectm.hr.parkme.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import parkme.projectm.hr.parkme.Activities.FragmentMenuActivity;
import parkme.projectm.hr.parkme.Activities.MainMenuActivity;
import parkme.projectm.hr.parkme.CustomViewModels.ActiveTicketView;
import parkme.projectm.hr.parkme.CustomViewModels.FavoriteCarsArrayAdapter;
import parkme.projectm.hr.parkme.CustomViewModels.PastPaymentsArrayAdapter;
import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PastParkingPayment;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;
import parkme.projectm.hr.parkme.Services.ParkingServiceHelper;

/**
 * Created by Mihael on 8.4.2015..
 */
public class PaymentHistoryFragment extends Fragment {

    private FragmentMenuActivity parentActivity;

    private ActiveTicketView activeTicketView;

    private List<PastParkingPayment> pastParkingPaymentList;
    private PastPaymentsArrayAdapter pastPaymentsArrayAdapter;
    private ListView pastParkingListView;

    private Timer activeTicketTimer;

    private PaymentHistoryFragmentCallback paymentHistoryFragmentCallback;

    private ParkingServiceHelper parkingServiceHelper = new ParkingServiceHelper();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_history, container, false);
        activeTicketView = (ActiveTicketView) rootView.findViewById(R.id.activeTicketView);

        pastParkingListView = (ListView) rootView.findViewById(R.id.pastPaymentListView);

        final DatabaseManager dbManager = DatabaseManager.getInstance();
        pastParkingPaymentList = dbManager.getAllPastparkingPayments();

        if(pastParkingPaymentList != null){
            Log.w("PaymentFragment", "pastParkingList nije null - number of values : " + pastParkingPaymentList.size());
            pastPaymentsArrayAdapter = new PastPaymentsArrayAdapter(parentActivity,
                    pastParkingPaymentList.toArray(new PastParkingPayment[pastParkingPaymentList.size()]));
        }
        else{
            Log.w("PaymentFragment", "pastParkingList je null - nema past paymenta");
            pastPaymentsArrayAdapter = new PastPaymentsArrayAdapter(parentActivity, new PastParkingPayment[0]);
        }

        pastPaymentsArrayAdapter.setCallback(new PastPaymentsArrayAdapter.PastPaymentArrayAdapterCallback() {
            @Override
            public void refreshFragment() {
                if(paymentHistoryFragmentCallback != null){
                    paymentHistoryFragmentCallback.refreshActivity();
                }
            }
        });

        pastParkingListView.setAdapter(pastPaymentsArrayAdapter);

        pastParkingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PastParkingPayment pastParkingPayment = pastPaymentsArrayAdapter.getItem(position);

                Toast toast = Toast.makeText(getActivity(), "" + pastParkingPayment.getCapPlates() + " - " + pastParkingPayment.getStartOfPayment(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        // TODO - provjera dal imamo aktivnu kartu
        boolean hasActiveTicket = false;
        if(hasActiveTicket){
            activeTicketTimer = new Timer();
            activeTicketTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                }
            }, 0, 3000);
            activeTicketView.showStuff();
        }
        else{
            activeTicketView.hideStuff();
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (FragmentMenuActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO popunit activeTicketView ako imamo aktivnu kartu, ako ne ne - hideat ga ili nesto
        // TODO refreshati adapter za past parking payment ako treba
    }

    public void setPaymentHistoryFragmentCallback(PaymentHistoryFragmentCallback paymentHistoryFragmentCallback) {
        this.paymentHistoryFragmentCallback = paymentHistoryFragmentCallback;
    }
}
