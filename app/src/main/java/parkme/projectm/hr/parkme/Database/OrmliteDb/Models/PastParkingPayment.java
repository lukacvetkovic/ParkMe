package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 13.4.2015..
 */

@DatabaseTable(tableName = "past_parking_payments")
public class PastParkingPayment {

    public final static String pastParkingPaymentTableName = "past_parking_payments";

    public final static String columnGradId = "grad_id";
    public final static String columnZoneId = "zone_id";
    public final static String columnPaymentModeId = "payment_mode_id";
    public final static String columnStartOfPayment = "start_of_payment";
    public final static String columnEndOfPayment = "end_of_payment";
    public final static String columnCarTables = "car_tables";
    public final static String columnPaymentCarIcon = "payment_car_icon";

    @DatabaseField(generatedId = true)
    private int pastParkingPaymentId;

    @DatabaseField(columnName = columnGradId)
    private int gradId;

    @DatabaseField(columnName = columnZoneId)
    private int zoneID;

    @DatabaseField(columnName = columnPaymentModeId)
    private int paymentMethodId;

    @DatabaseField(columnName = columnStartOfPayment)
    private String startOfPayment;

    @DatabaseField(columnName = columnEndOfPayment)
    private String endOfPayment;

    @DatabaseField(columnName = columnCarTables)
    private String capPlates;

    @DatabaseField(columnName = columnPaymentCarIcon)
    private int carIcon;

    private Integer greenBackgroundCarIcon = null;

    public PastParkingPayment() {
    }

    public String getCapPlates() {
        return capPlates;
    }

    public PastParkingPayment(String capPlates, int carIcon, String endOfPayment, int gradId, int pastParkingPaymentId, int paymentMethodId, String startOfPayment, int zoneID) {
        this.capPlates = capPlates;
        this.carIcon = carIcon;
        this.endOfPayment = endOfPayment;
        this.gradId = gradId;
        this.pastParkingPaymentId = pastParkingPaymentId;
        this.paymentMethodId = paymentMethodId;
        this.startOfPayment = startOfPayment;
        this.zoneID = zoneID;
    }

    public void setCapPlates(String capPlates) {
        this.capPlates = capPlates;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public String getStartOfPayment() {
        return startOfPayment;
    }

    public void setStartOfPayment(String startOfPayment) {
        this.startOfPayment = startOfPayment;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getPastParkingPaymentId() {
        return pastParkingPaymentId;
    }

    public void setPastParkingPaymentId(int pastParkingPaymentId) {
        this.pastParkingPaymentId = pastParkingPaymentId;
    }

    public int getGradId() {
        return gradId;
    }

    public void setGradId(int gradId) {
        this.gradId = gradId;
    }

    public String getEndOfPayment() {
        return endOfPayment;
    }

    public void setEndOfPayment(String endOfPayment) {
        this.endOfPayment = endOfPayment;
    }

    public int getCarIcon() {
        return carIcon;
    }

    public void setCarIcon(int carIcon) {
        this.carIcon = carIcon;
    }

    public Integer getGreenBackgroundCarIcon() {
        if(greenBackgroundCarIcon == null){
            switch(carIcon){
                case R.drawable.car_icon_blue_s:
                    greenBackgroundCarIcon = R.drawable.car_icon_green_background_blue_s;
                    break;
                case R.drawable.car_icon_green_s:
                    greenBackgroundCarIcon = R.drawable.car_icon_green_background_green_s;
                    break;
                case R.drawable.car_icon_orange_s:
                    greenBackgroundCarIcon = R.drawable.car_icon_green_background_orange_s;
                    break;
                case R.drawable.car_icon_red_s:
                    greenBackgroundCarIcon = R.drawable.car_icon_green_background_red_s;
                    break;
            }
        }
        return greenBackgroundCarIcon;
    }

    public void setGreenBackgroundCarIcon(Integer greenBackgroundCarIcon) {
        this.greenBackgroundCarIcon = greenBackgroundCarIcon;
    }
}
