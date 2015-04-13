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
    public final static String columnPaymentMethodId = "payment_method_id";
    public final static String columnDateOfPayment = "date_of_payment";


    @DatabaseField(generatedId = true)
    private int pastParkingPaymentId;

    @DatabaseField(columnName = columnGradId)
    private int gradId;

    @DatabaseField(columnName = columnZoneId)
    private int zoneID;

    @DatabaseField(columnName = columnPaymentMethodId)
    private int paymentMethodId;

    @DatabaseField(columnName = columnDateOfPayment)
    private String dateOfPayment;

    public PastParkingPayment() {
    }

    public PastParkingPayment(int pastParkingPaymentId, int gradId, int zoneID, int paymentMethodId, String dateOfPayment) {
        this.pastParkingPaymentId = pastParkingPaymentId;
        this.gradId = gradId;
        this.zoneID = zoneID;
        this.paymentMethodId = paymentMethodId;
        this.dateOfPayment = dateOfPayment;
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

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

}
