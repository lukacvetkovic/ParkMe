package parkme.projectm.hr.parkme.Models;

import android.text.format.Time;

/**
 * Created by Cveki on 11.2.2015..
 */
public class Reservation {
    private int id;
    private Time duration;
    private double price;
    private Time max_duration;
    private String sms_prefix;
    private String sms_sufix;
    private int id_zone;

    public Reservation(int id, Time duration, double price, Time max_duration, String sms_prefix, String sms_sufix, int id_zone) {
        this.id = id;
        this.duration = duration;
        this.price = price;
        this.max_duration = max_duration;
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

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Time getMax_duration() {
        return max_duration;
    }

    public void setMax_duration(Time max_duration) {
        this.max_duration = max_duration;
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
