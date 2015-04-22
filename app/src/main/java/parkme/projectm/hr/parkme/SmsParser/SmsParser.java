package parkme.projectm.hr.parkme.SmsParser;

/**
 * Created by Luka on 18.4.2015..
 */
public interface SmsParser {

    public  SmsData parse(String sms);

    public  boolean canParse(String sms);
}
