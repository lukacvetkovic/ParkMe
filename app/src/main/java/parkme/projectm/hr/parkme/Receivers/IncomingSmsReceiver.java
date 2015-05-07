package parkme.projectm.hr.parkme.Receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PastParkingPayment;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.Helpers.Rest.ApiConstants;
import parkme.projectm.hr.parkme.Helpers.Rest.PutRestService;
import parkme.projectm.hr.parkme.Services.ParkingServiceHelper;
import parkme.projectm.hr.parkme.SmsParser.MasterParser;
import parkme.projectm.hr.parkme.SmsParser.SmsData;
import parkme.projectm.hr.parkme.SmsParser.SmsParser;

/**
 * Created by Cveki on 29.3.2015..
 */
public class IncomingSmsReceiver extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    private SmsReceiverCallback smsReceiverCallback;

    public interface SmsReceiverCallback{
        void updateTicketCounter(int remainingTime);
    }

    public IncomingSmsReceiver() {
    }

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

                        // Upis u bazu pastPayment
                        upisiUTablicuPastPayment(context,smsData);
                        // Setupanje countera u serviceu
                        upisiUService(context,smsData);

                        //Update baze na serveru
                        if(smsData.getZoneName()!=null){
                            int parkingZoneId=prefsHelper.getInt(PrefsHelper.parkingZoneId,0);
                            updateZoneName(parkingZoneId, smsData);
                        }

                        //Update cijene ako je potrebno
                        String priceString=prefsHelper.getString("priceString", "NULL");
                        if(priceString!="NULL"){
                            float price=Float.valueOf(priceString);
                            float priceSMS=Float.valueOf(String.valueOf(smsData.getCijenaKn()) + "." + String.valueOf(smsData.getCijenaLp()));
                            if(price!=priceSMS){
                                int zonePriceId=prefsHelper.getInt(PrefsHelper.zonePriceId,0);
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
            e.printStackTrace();
        }
    }

    private void upisiUTablicuPastPayment(Context context, SmsData smsData) {
        Log.d("Upisivanje","Tablice past payment");
        PrefsHelper prefsHelper= new PrefsHelper(context);
        int citiyId=prefsHelper.getInt(PrefsHelper.citiyId, 0);
        int parkingZoneId=prefsHelper.getInt(PrefsHelper.parkingZoneId, 0);
        int paymentModeId=prefsHelper.getInt(PrefsHelper.paymentModeId, 0);
        String carPlates= prefsHelper.getString(PrefsHelper.ActiveCarPlates, "NULL");

        DatabaseManager.init(context);
        DatabaseManager databaseManager= DatabaseManager.getInstance();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        FavouriteCar favouriteCar = databaseManager.getFavoriteCarFromPlates(carPlates);

        Date dateOfEndOfpayment = smsData.getDateTime();
        String endOfPaymnet;
        if(dateOfEndOfpayment == null){
            endOfPaymnet = dateFormat.format(new Date());
        }
        else{
            endOfPaymnet = dateFormat.format(smsData.getDateTime());
        }

        /*String capPlates, int carIcon, String endOfPayment, int gradId, int pastParkingPaymentId, int paymentMethodId, String startOfPayment, int zoneID*/

        PastParkingPayment pastParkingPayment = new PastParkingPayment(carPlates, favouriteCar.getCarIcon(), endOfPaymnet, citiyId,0,paymentModeId,dateFormat.format(date),parkingZoneId);
        databaseManager.addPastparkingPayment(pastParkingPayment);

        Log.d("Upisivanje gotovo","Tablice past payment");


    }

    private void upisiUService(Context context, SmsData smsData) {
        Log.d("Upisivanje","Servisa");
        PrefsHelper prefsHelper= new PrefsHelper(context);
        String trajanje = prefsHelper.getString(PrefsHelper.trajanje, "NULL");
        String[]pom=trajanje.split(":");
        int trajanjeMin=Integer.valueOf(pom[0])*60+Integer.valueOf(pom[1]);

        if(smsReceiverCallback != null){
            smsReceiverCallback.updateTicketCounter(trajanjeMin);
        }
        Log.d("Upisivanje gotovo","Servisa");
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

    public void setSmsReceiverCallback(SmsReceiverCallback smsReceiverCallback) {
        this.smsReceiverCallback = smsReceiverCallback;
    }
}
