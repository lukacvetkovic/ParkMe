package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

/**
 * Created by Adriano Bacac on 29.03.15..
 */

public class SimpleZonePrice {
    private int id;
    private int id_payment_mode;
    private int id_zone_work_time;
    private int price_trimmed;
    private int price_decimal;
    private String currency;

    public SimpleZonePrice(int id, int idPaymentMode, int idZoneWorkTime, int priceTrimmed, int priceDecimal, String currency) {
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

    public int getPriceTrimmed() {
        return price_trimmed;
    }

    public void setPriceTrimmed(int priceTrimmed) {
        this.price_trimmed = price_trimmed;
    }

    public int getPriceDecimal() {
        return price_decimal;
    }

    public void setPriceDecimal(int priceDecimal) {
        this.price_decimal = priceDecimal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
