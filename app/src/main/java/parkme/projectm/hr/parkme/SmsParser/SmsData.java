package parkme.projectm.hr.parkme.SmsParser;

import java.util.Date;

/**
 * Created by Luka on 18.4.2015..
 */
public class SmsData {

    private String zoneName;
    private int cijenaKn;
    private int cijenaLp;
    private Date dateTime;


    public SmsData(String zoneName, int cijenaKn, int cijenaLp, Date dateTime) {
        this.zoneName = zoneName;
        this.cijenaKn = cijenaKn;
        this.cijenaLp = cijenaLp;
        this.dateTime = dateTime;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getCijenaKn() {
        return cijenaKn;
    }

    public void setCijenaKn(int cijenaKn) {
        this.cijenaKn = cijenaKn;
    }

    public int getCijenaLp() {
        return cijenaLp;
    }

    public void setCijenaLp(int cijenaLp) {
        this.cijenaLp = cijenaLp;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
