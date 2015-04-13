package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mihael on 13.4.2015..
 */
@DatabaseTable(tableName = "favorite_payments")
public class FavoritePayment {

    public final static String favoritePaymentTableName = "favorite_payments";

    public final static String columnGradId = "grad_id";
    public final static String columnZoneId = "zone_id";
    public final static String columnPaymentMethodId = "payment_method_id";

    @DatabaseField(id = true, generatedId = true)
    private int favoritePaymentId;

    @DatabaseField(columnName = columnGradId)
    private int gradId;

    @DatabaseField(columnName = columnZoneId)
    private int zoneID;

    @DatabaseField(columnName = columnPaymentMethodId)
    private int paymentMethodId;

    public FavoritePayment() {
    }

    public FavoritePayment(int favoritePaymentId, int gradId, int zoneID, int paymentMethodId) {
        this.favoritePaymentId = favoritePaymentId;
        this.gradId = gradId;
        this.zoneID = zoneID;
        this.paymentMethodId = paymentMethodId;
    }

    public int getFavoritePaymentId() {
        return favoritePaymentId;
    }

    public void setFavoritePaymentId(int favoritePaymentId) {
        this.favoritePaymentId = favoritePaymentId;
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

}
