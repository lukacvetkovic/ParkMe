package parkme.projectm.hr.parkme.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Helpers.SMSHelper;
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
    TextView tvCarPlate;

    String city;
    String parkingZone;
    float price;
    String duration;
    String maxDuarton;
    String carPlate;
    String parkingZoneNumber;
    int parkingZoneId;
    int parkingMethodId;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getActivity());
        final View inflator = linf.inflate(R.layout.dialog_confirm_payment, null);

        builder.setView(inflator);

        tvTime=(TextView)inflator.findViewById(R.id.tvTime);
        tvCity=(TextView)inflator.findViewById(R.id.tvCity);
        tvParkingZone=(TextView)inflator.findViewById(R.id.tvParkingZone);
        tvPrice=(TextView)inflator.findViewById(R.id.tvPrice);
        tvDuration=(TextView)inflator.findViewById(R.id.tvDuration);
        tvMaxDuration=(TextView)inflator.findViewById(R.id.tvMaxDuration);
        tvCarPlate=(TextView)inflator.findViewById(R.id.tvCarPlate);


        carPlate=/*TODO dohvti iz share prefs*/"ZG636FH";

        city=getArguments().getString("city");
        parkingZone=getArguments().getString("zone");
        price=getArguments().getFloat("price");
        duration=getArguments().getString("duration");
        maxDuarton=getArguments().getString("maxDuration");
        parkingZoneId=getArguments().getInt("parkingZoneId");
        parkingMethodId=getArguments().getInt("parkingMethodId");

        Date date = new Date();
        tvTime.setText(date.getHours()+":"+date.getMinutes());
        tvCity.setText(city);
        tvParkingZone.setText(parkingZone);
        tvPrice.setText(String.valueOf(price)+ " kn");
        tvDuration.setText(duration + " sati");
        tvMaxDuration.setText(maxDuarton + " sati");
        tvCarPlate.setText(carPlate);



        /*ParkingZone parkingZone=TODO iz aplija dohvati po id-u*/



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setPositiveButton("Plati", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                PaymentMode paymentMode = new PaymentMode(1,"1","","",1); /*TODO uzmi iz baze po ID-u*/

                String message = paymentMode.getSms_prefix()+" "+carPlate+" "+paymentMode.getSms_sufix();

                //SMSHelper.sendSMS(parkingZoneNumber, "BOK, ti si najpametniji, covjece =)");
                parkingZoneNumber="MOCKANI BROJ";
                Context context = getActivity();
                CharSequence text = "Broj : "+parkingZoneNumber+" Poruka:"+ message;
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                getActivity().finish();

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
