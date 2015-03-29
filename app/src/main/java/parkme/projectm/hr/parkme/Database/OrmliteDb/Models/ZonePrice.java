package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Adriano Bacac on 29.03.15..
 */

@DatabaseTable(tableName = "zone_price")
public class ZonePrice {
    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int id_payment_mode;
    @DatabaseField
    private int id_zone_work_time;
    @DatabaseField
    private int price_trimmed;
    @DatabaseField
    private int price_decimal;
    @DatabaseField
    private String currency;

    public ZonePrice() {
    }

    public ZonePrice(int id, int idPaymentMode, int idZoneWorkTime, float price) {
        this(id, idPaymentMode, idZoneWorkTime, price, "HRK");
    }

    public ZonePrice(int id, int idPaymentMode, int idZoneWorkTime, float price, String currency) {
        this(id, idPaymentMode, idZoneWorkTime, (int)price, ((int)(price * 100))%100, currency);
    }

    public ZonePrice(int id, int idPaymentMode, int idZoneWorkTime, int priceTrimmed, int priceDecimal) {
        this(id, idPaymentMode, idZoneWorkTime, priceTrimmed, priceDecimal, "HRK");
    }

    public ZonePrice(int id, int idPaymentMode, int idZoneWorkTime, int priceTrimmed, int priceDecimal, String currency) {
        this.id = id;
        this.id_payment_mode = idPaymentMode;
        this.id_zone_work_time = idZoneWorkTime;
        this.price_trimmed = priceTrimmed;
        this.price_decimal = priceDecimal;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaymentMode() {
        return id_payment_mode;
    }

    public void setIdPaymentMode(int idPaymentMode) {
        this.id_payment_mode = idPaymentMode;
    }

    public int getIdZoneWorkTime() {
        return id_zone_work_time;
    }

    public void setIdZoneWorkTime(int idZoneWorkTime) {
        this.id_zone_work_time = idZoneWorkTime;
    }

    public int getPrice_trimmed() {
        return price_trimmed;
    }

    public void setPrice_trimmed(int price_trimmed) {
        this.price_trimmed = price_trimmed;
    }

    public int getPriceDecimal() {
        return price_decimal;
    }

    public void setPriceDecimal(int priceDecimal) {
        this.price_decimal = priceDecimal;
    }

    public float getPriceFloat(){
        return price_trimmed + ((float) price_decimal) / String.valueOf(price_decimal).length();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
