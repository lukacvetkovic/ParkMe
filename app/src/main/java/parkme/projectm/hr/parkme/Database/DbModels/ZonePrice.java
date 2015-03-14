package parkme.projectm.hr.parkme.Database.DbModels;

import java.util.Date;

/**
 * Created by Mihael on 14.3.2015..
 */
public class ZonePrice {

    private int id;
    private int id_payment_mode;
    private int id_zone_work_time;
    private int price_trimmed;
    private int price_decimal;
    private String currency;
    private Date modified_on;
    private Date created_on;

    public ZonePrice(){
    }

    public ZonePrice(int id, int id_payment_mode, int id_zone_work_time, int price_trimmed, int price_decimal, String currency, Date modified_on, Date created_on) {
        this.id = id;
        this.id_payment_mode = id_payment_mode;
        this.id_zone_work_time = id_zone_work_time;
        this.price_trimmed = price_trimmed;
        this.price_decimal = price_decimal;
        this.currency = currency;
        this.modified_on = modified_on;
        this.created_on = created_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_payment_mode() {
        return id_payment_mode;
    }

    public void setId_payment_mode(int id_payment_mode) {
        this.id_payment_mode = id_payment_mode;
    }

    public int getId_zone_work_time() {
        return id_zone_work_time;
    }

    public void setId_zone_work_time(int id_zone_work_time) {
        this.id_zone_work_time = id_zone_work_time;
    }

    public int getPrice_trimmed() {
        return price_trimmed;
    }

    public void setPrice_trimmed(int price_trimmed) {
        this.price_trimmed = price_trimmed;
    }

    public int getPrice_decimal() {
        return price_decimal;
    }

    public void setPrice_decimal(int price_decimal) {
        this.price_decimal = price_decimal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getModified_on() {
        return modified_on;
    }

    public void setModified_on(Date modified_on) {
        this.modified_on = modified_on;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }
}
