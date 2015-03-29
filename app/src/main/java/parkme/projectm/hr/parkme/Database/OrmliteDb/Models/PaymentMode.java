package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

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
    private double price;
    @DatabaseField
    private String sms_prefix;
    @DatabaseField
    private String sms_sufix;
    @DatabaseField
    private int id_zone;

    public PaymentMode(){

    }
    public PaymentMode(int id, Date duration, double price, String sms_prefix, String sms_sufix, int id_zone) {
        this.id = id;
        this.duration = duration;
        this.price = price;
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

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
