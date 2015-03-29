package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Model of payment mode.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "payment_mode")
public class PaymentMode {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String duration;
    @DatabaseField
    private String sms_prefix;
    @DatabaseField
    private String sms_sufix;
    @DatabaseField
    private int id_zone;

    public PaymentMode(){

    }

    public PaymentMode(int id, String duration, String sms_prefix, String sms_sufix, int id_zone) {
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

    public String getSms_prefix() {
        return sms_prefix;
    }

    public void setSms_prefix(String sms_prefix) {
        this.sms_prefix = sms_prefix;
    }

    public String getSms_sufix() {
        return sms_sufix;
    }

    public void setSms_sufix(String sms_sufix) {
        this.sms_sufix = sms_sufix;
    }

    public int getId_zone() {
        return id_zone;
    }

    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }
}
