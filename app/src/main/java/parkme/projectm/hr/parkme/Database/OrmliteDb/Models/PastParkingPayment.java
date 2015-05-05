package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    public PastParkingPayment() {
    }

    public PastParkingPayment(String capPlates, String endOfPayment, int gradId, int pastParkingPaymentId, int paymentMethodId, String startOfPayment, int zoneID) {
        this.capPlates = capPlates;
        this.endOfPayment = endOfPayment;
        this.gradId = gradId;
        this.pastParkingPaymentId = pastParkingPaymentId;
        this.paymentMethodId = paymentMethodId;
        this.startOfPayment = startOfPayment;
        this.zoneID = zoneID;
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

    public String getCapPlates() {
        return capPlates;
    }

    public void setCapPlates(String capPlates) {
        this.capPlates = capPlates;
    }
}
