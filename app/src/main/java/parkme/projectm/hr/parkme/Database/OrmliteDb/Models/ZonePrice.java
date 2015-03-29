package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Adriano Bacac on 29.03.15..
 */

@DatabaseTable(tableName = "zone_price")
public class ZonePrice {
    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int idPaymentMode;
    @DatabaseField
    private int idZoneWorkTime;
    @DatabaseField
    private int priceTrimmed;
    @DatabaseField
    private int priceDecimal;
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
        this.idPaymentMode = idPaymentMode;
        this.idZoneWorkTime = idZoneWorkTime;
        this.priceTrimmed = priceTrimmed;
        this.priceDecimal = priceDecimal;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaymentMode() {
        return idPaymentMode;
    }

    public void setIdPaymentMode(int idPaymentMode) {
        this.idPaymentMode = idPaymentMode;
    }

    public int getIdZoneWorkTime() {
        return idZoneWorkTime;
    }

    public void setIdZoneWorkTime(int idZoneWorkTime) {
        this.idZoneWorkTime = idZoneWorkTime;
    }

    public int getPriceTrimmed() {
        return priceTrimmed;
    }

    public void setPriceTrimmed(int priceTrimmed) {
        this.priceTrimmed = priceTrimmed;
    }

    public int getPriceDecimal() {
        return priceDecimal;
    }

    public void setPriceDecimal(int priceDecimal) {
        this.priceDecimal = priceDecimal;
    }

    public float getPriceFloat(){
        return priceTrimmed + ((float)priceDecimal) / String.valueOf(priceDecimal).length();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
