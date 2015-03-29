package parkme.projectm.hr.parkme.Models;

/**
 * Created by Cveki on 29.3.2015..
 */
public class Option {
    private String sms_sufix;
    private String sms_prefix;
    private String duration;
    private int id;
    private int id_zone;

    public Option(String sms_sufix, String sms_prefix, String duration, int id, int id_zone) {
        this.sms_sufix = sms_sufix;
        this.sms_prefix = sms_prefix;
        this.duration = duration;
        this.id = id;
        this.id_zone = id_zone;
    }

    public String getSms_sufix() {
        return sms_sufix;
    }

    public void setSms_sufix(String sms_sufix) {
        this.sms_sufix = sms_sufix;
    }

    public String getSms_prefix() {
        return sms_prefix;
    }

    public void setSms_prefix(String sms_prefix) {
        this.sms_prefix = sms_prefix;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_zone() {
        return id_zone;
    }

    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }
}
