package parkme.projectm.hr.parkme.SmsParser;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Luka on 19.4.2015..
 */
public class MasterParser implements SmsParser {
    @Override
    public SmsData parse(String sms) {
        Matcher m = Pattern.compile("(?i)([0-9]+.[0-9]+ *kn)").matcher(sms);
        SmsData smsData= new SmsData();
        String date;

        if(m.find())
        {
            Log.d("Price entered: ", m.group(1));
            String price= m.group(1);
            m = Pattern.compile("([0-9]+.[0-9]+)").matcher(price);
            if(m.find()) {
                String[] KnLp = m.group(1).split("\\.|,");
                Log.d("KN", KnLp[0]);
                Log.d("LP", KnLp[1]);
                smsData.setCijenaKn(Integer.valueOf(KnLp[0]));
                smsData.setCijenaLp(Integer.valueOf(KnLp[1]));
            }
        }


        m= Pattern.compile("(?i)([0-9][0-9]\\.[0-9][0-9]\\.)[0-9]*").matcher(sms);

        if(m.find())
        {
            Log.d("Date entered: " , m.group(1));

            date=m.group(1);

            int year = Calendar.getInstance().get(Calendar.YEAR);

            date=date+year+".";
        }
        else{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy.");
            date = format1.format(cal.getTime());
        }

        m= Pattern.compile("(?i)([0-9][0-9]:[0-9][0-9])").matcher(sms);

        if(m.find())
        {
            Date parsedDate=null;
            Log.d("Time entered: " ,m.group(1));
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());
            String datetime=date+" "+m.group(1);
            try {
                parsedDate =  df.parse(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar cal = Calendar.getInstance();

            if(parsedDate.getTime()<cal.getTime().getTime()){
                Calendar c = Calendar.getInstance();
                c.setTime(parsedDate);
                c.add(Calendar.DATE, 1);
                parsedDate = c.getTime();
            }

            smsData.setDateTime(parsedDate);
        }

        m= Pattern.compile("(?i), (.*) \\(").matcher(sms);

        if(m.find())
        {
            Log.d("Zona entered: " , m.group(1));
            smsData.setZoneName(m.group(1));
        }

        return smsData;

    }

    @Override
    public boolean canParse(String sms) {
        return true;
    }
}
