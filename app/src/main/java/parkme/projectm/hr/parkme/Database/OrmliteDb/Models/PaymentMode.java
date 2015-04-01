package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.util.Date;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimplePaymentMode;

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
    private Date duration;
    @DatabaseField
    private String smsPrefix;
    @DatabaseField
    private String smsSufix;
    @DatabaseField
    private int idZone;

    public PaymentMode(){

    }
    public PaymentMode(SimplePaymentMode simplePaymentMode){
        this.id = simplePaymentMode.getId();
        try {
            this.duration = DatabaseManager.timeFormatter.parse(simplePaymentMode.getDuration());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.smsPrefix = simplePaymentMode.getSmsPrefix();
        this.smsSufix = simplePaymentMode.getSmsSufix();
        this.idZone = simplePaymentMode.getIdZone();

    }
    public PaymentMode(int id, Date duration, String sms_prefix, String sms_sufix, int id_zone) {
        this.id = id;
        this.duration = duration;
        this.smsPrefix = sms_prefix;
        this.smsSufix = sms_sufix;
        this.idZone = id_zone;
    }

    public PaymentMode(int id, String duration, String sms_prefix, String sms_sufix, int id_zone) {
        this.id = id;
        try {
            this.duration = DatabaseManager.timeFormatter.parse(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.smsPrefix = sms_prefix;
        this.smsSufix = sms_sufix;
        this.idZone = id_zone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getSmsPrefix() {
        return smsPrefix;
    }

    public void setSmsPrefix(String smsPrefix) {
        this.smsPrefix = smsPrefix;
    }

    public String getSmsSufix() {
        return smsSufix;
    }

    public void setSmsSufix(String smsSufix) {
        this.smsSufix = smsSufix;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }
}
