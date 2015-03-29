package parkme.projectm.hr.parkme.Helpers;

import android.telephony.SmsManager;

/**
 * Created by Cveki on 29.3.2015..
 */
public class SMSHelper {

    public static void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
