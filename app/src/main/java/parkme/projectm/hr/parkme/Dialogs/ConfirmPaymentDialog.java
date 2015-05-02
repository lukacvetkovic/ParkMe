package parkme.projectm.hr.parkme.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
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
    int paymentModeId;
    int citiyId;
    boolean favs;

    DatabaseManager databaseManager;

    PrefsHelper prefsHelper;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getActivity());
        final View inflator = linf.inflate(R.layout.dialog_confirm_payment, null);

        builder.setView(inflator);

        DatabaseManager.init(builder.getContext());
        databaseManager= DatabaseManager.getInstance();
        prefsHelper= new PrefsHelper(getActivity());


        tvTime=(TextView)inflator.findViewById(R.id.tvTime);
        tvCity=(TextView)inflator.findViewById(R.id.tvCity);
        tvParkingZone=(TextView)inflator.findViewById(R.id.tvParkingZone);
        tvPrice=(TextView)inflator.findViewById(R.id.tvPrice);
        tvDuration=(TextView)inflator.findViewById(R.id.tvDuration);
        tvMaxDuration=(TextView)inflator.findViewById(R.id.tvMaxDuration);
        tvCarPlate=(TextView)inflator.findViewById(R.id.tvCarPlate);


        carPlate=prefsHelper.getString(PrefsHelper.ActiveCarPlates,"NULL");

        city=getArguments().getString("city");
        parkingZone=getArguments().getString("zone");
        price=getArguments().getFloat("price");
        duration=getArguments().getString("duration");
        maxDuarton=getArguments().getString("maxDuration");
        parkingZoneId=getArguments().getInt("parkingZoneId");
        paymentModeId=getArguments().getInt("paymentModeId");
        citiyId=getArguments().getInt("citiyId");
        favs=getArguments().getBoolean("favs");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        String formatted = format1.format(cal.getTime());
        tvTime.setText(formatted);
        tvCity.setText(city);
        tvParkingZone.setText(parkingZone);
        tvPrice.setText(String.valueOf(price)+ " kn");
        tvDuration.setText(duration + " sati");
        tvMaxDuration.setText(maxDuarton + " sati");
        tvCarPlate.setText(carPlate);



        final ParkingZone parkingZone=databaseManager.getParkingZoneFromId(parkingZoneId);



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setPositiveButton("Plati", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                PaymentMode  paymentMode = databaseManager.getPaymentModeFromId(paymentModeId);


                String message = paymentMode.getSmsPrefix()+carPlate+paymentMode.getSmsSufix();

                //SMSHelper.sendSMS(parkingZoneNumber, message);

                parkingZoneNumber=parkingZone.getPhoneNumber();
                prefsHelper.putString(PrefsHelper.PhoneNumber,parkingZoneNumber);
                Context context = getActivity();
                CharSequence text = "Broj : "+parkingZoneNumber+" Poruka:"+ message;
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                getActivity().finish();

                if(favs){
                    DatabaseManager.init(context);
                    DatabaseManager databaseManager = DatabaseManager.getInstance();
                    databaseManager.addFavoritePayment(new FavoritePayment(citiyId,parkingZoneId,paymentModeId));
                    Log.d("UPISANO U BAZU FAVS : ", "CityId :"+String.valueOf(citiyId)+" , "+" parkingZoneId :"+String.valueOf(parkingZoneId)+" paymentModeId :"+String.valueOf(paymentModeId));
                }

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
