package parkme.projectm.hr.parkme.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
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

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    PrefsHelper prefsHelper = new PrefsHelper(context);

                    String senderNum = phoneNumber;
                    Log.d("senderNum-->",phoneNumber );
                    String numberToActOn = prefsHelper.getString(PrefsHelper.PhoneNumber, "NULL");
                    Log.d("numberToActOn-->",numberToActOn);
                    if (senderNum.equals(numberToActOn)) {
                        String message = currentMessage.getDisplayMessageBody();

                        SmsParser smsParser= new MasterParser();
                        SmsData smsData= smsParser.parse(message);

                        Log.d("UPISI U TABLICU", "DO KAD TRAJE PARKING I TO");

                        if(smsData.getZoneName()!=null){

                            Log.d("POSALJI U BAZU", "PODATKE");

                        }

                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }

}
