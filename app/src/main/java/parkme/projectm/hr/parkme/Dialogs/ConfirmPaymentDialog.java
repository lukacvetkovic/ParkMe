package parkme.projectm.hr.parkme.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.apache.http.client.params.ClientPNames;

import parkme.projectm.hr.parkme.R;

/**
 * Created by Cveki on 29.3.2015..
 */
public class ConfirmPaymentDialog extends DialogFragment {

    TextView tvTime;
    TextView tvCity;
    TextView tvParkingZone;
    TextView tvPrice;
    TextView tvDuration;
    TextView tvMaxDuration;

    String city;
    String parkingZone;
    float price;
    String duration;
    String maxDuarton;
    String carPlate;
    String parkingZoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTime=(TextView)getView().findViewById(R.id.tvTime);
        tvCity=(TextView)getView().findViewById(R.id.tvCity);
        tvParkingZone=(TextView)getView().findViewById(R.id.tvParkingZone);
        tvPrice=(TextView)getView().findViewById(R.id.tvPrice);
        tvDuration=(TextView)getView().findViewById(R.id.tvDuration);
        tvMaxDuration=(TextView)getView().findViewById(R.id.tvMaxDuration);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_confirm_payment, null))
                // Add action buttons
                .setPositiveButton("Plati", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmPaymentDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
