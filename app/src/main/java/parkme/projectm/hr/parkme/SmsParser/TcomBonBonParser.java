package parkme.projectm.hr.parkme.SmsParser;

/**
 * Created by Luka on 19.4.2015..
 */
public class TcomBonBonParser implements SmsParser {

    @Override
    public SmsData parse(String sms) {
        return null;
    }

    @Override
    public boolean canParse(String sms) {
        return sms.matches("3*(.* KN).*! HT.*");
    }
}
