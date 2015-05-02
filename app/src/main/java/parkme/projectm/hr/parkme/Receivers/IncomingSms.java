package parkme.projectm.hr.parkme.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.OrmLiteDatabaseHelper;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleZonePrice;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.Helpers.Rest.ApiConstants;
import parkme.projectm.hr.parkme.Helpers.Rest.PutRestService;
import parkme.projectm.hr.parkme.SmsParser.MasterParser;
import parkme.projectm.hr.parkme.SmsParser.SmsData;
import parkme.projectm.hr.parkme.SmsParser.SmsParser;

/**
 * Created by Cveki on 29.3.2015..
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();


    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        Log.d("Citanje","poruke");
        try {

            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                DatabaseManager.init(context);
                DatabaseManager databaseManager = DatabaseManager.getInstance();
                if (0 < pdusObj.length) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    PrefsHelper prefsHelper = new PrefsHelper(context);


                    String senderNum = phoneNumber;
                    Log.d("senderNum-->",phoneNumber );
                    String numberToActOn = prefsHelper.getString(PrefsHelper.PhoneNumber, "NULL");
                    Log.d("numberToActOn-->",numberToActOn);

                    //Broj na koji reagiramo
                    if (senderNum.equals(numberToActOn)) {
                        String message = currentMessage.getDisplayMessageBody();

                        SmsParser smsParser= new MasterParser();
                        SmsData smsData= smsParser.parse(message);

                        Log.d("UPISI U TABLICU", "DO KAD TRAJE PARKING I TO"); //TODO

                        //Update baze na serveru
                        if(smsData.getZoneName()!=null){
                            int parkingZoneId=prefsHelper.getInt("parkingZoneId",0);
                            updateZoneName(parkingZoneId, smsData);

                        }

                        //Update cijene ako je potrebno
                        String priceString=prefsHelper.getString("priceString", "NULL");
                        if(priceString!="NULL"){
                            float price=Float.valueOf(priceString);
                            float priceSMS=Float.valueOf(String.valueOf(smsData.getCijenaKn()) + "." + String.valueOf(smsData.getCijenaLp()));
                            if(price!=priceSMS){
                                int zonePriceId=prefsHelper.getInt("zonePriceId",0);
                                if(zonePriceId!=0) {
                                    updateZonePrice(zonePriceId,smsData);
                                }
                            }
                        }



                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }

    public void updateZoneName(int parkingZoneId, SmsData smsData){


        if(parkingZoneId!=0) {
            Log.d("UPDATE IMENA","POCINJE");
            Gson gson = new Gson();
            final PutRestService putRestService = new PutRestService(ApiConstants.updateZoneName+parkingZoneId+".json", gson.toJson(new ParkingZoneUpdate(parkingZoneId,smsData.getZoneName())));
            Thread thread= new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                       String response= putRestService.execute();
                        Log.d("RESPONSE -->",response);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
    }

    public void updateZonePrice(int zonePriceId, SmsData smsData) {
        if(zonePriceId!=0) {
            Log.d("UPDATE CIJENE","POCINJE");
            Gson gson = new Gson();
            final PutRestService putRestService = new PutRestService(ApiConstants.updateZonePrice+zonePriceId+".json", gson.toJson(new ZonePriceUpdate(zonePriceId,smsData.getCijenaKn(),smsData.getCijenaLp())));
            Thread thread= new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String response= putRestService.execute();
                        Log.d("RESPONSE -->",response);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
    }

    private class ZonePriceUpdate{
        private int id;
        private int price_trimmed;
        private int price_decimal;

        private ZonePriceUpdate(int id, int price_trimmed, int price_decimal) {
            this.id = id;
            this.price_trimmed = price_trimmed;
            this.price_decimal = price_decimal;
        }
    }

    private class ParkingZoneUpdate{
        private int id;
        private String name;

        private ParkingZoneUpdate(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
