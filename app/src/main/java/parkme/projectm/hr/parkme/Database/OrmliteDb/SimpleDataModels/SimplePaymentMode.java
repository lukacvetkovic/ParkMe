package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model of payment mode.
 *
 * Created by Adriano Bacac on 29.03.15..
 */

public class SimplePaymentMode {
    private int id;
    private String duration;
    private String sms_prefix;
    private String sms_sufix;
    private int id_zone;


    public SimplePaymentMode(int id, String duration, String sms_prefix, String sms_sufix, int id_zone) {
        this.id = id;
        this.duration = duration;
        this.sms_prefix = sms_prefix;
        this.sms_sufix = sms_sufix;
        this.id_zone = id_zone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSmsPrefix() {
        return sms_prefix;
    }

    public void setSmsPrefix(String smsPrefix) {
        this.sms_prefix = smsPrefix;
    }

    public String getSmsSufix() {
        return sms_sufix;
    }

    public void setSmsSufix(String smsSufix) {
        this.sms_sufix = smsSufix;
    }

    public int getIdZone() {
        return id_zone;
    }

    public void setIdZone(int idZone) {
        this.id_zone = idZone;
    }
}
