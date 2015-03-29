package parkme.projectm.hr.parkme.Database.SqlHardCore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import parkme.projectm.hr.parkme.Database.SqlHardCore.DbModels.ZonePrice;


public class DataBaseHelper extends SQLiteOpenHelper{

    private Context context;

    private static final String TAG = "DbHelper";

    // ## Database

    private static final String DATABASE_NAME = "park_me_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TYPE_TEXT = "TEXT";
    private static final String TYPE_INT = "INTEGER";
    private static final String TYPE_REAL = "REAL";
    private static final String TYPE_DATETIME = "DATETIME";


    // ## Table zone_price

    private static final String ZONE_PRICE_TABLE_NAME = "zone_price";

    private static final String ZONE_PRICE_COLLUMN_ID = "zone_price_id";
    private static final String ZONE_PRICE_COLLUMN_ID_PAYMENT_MODE = "zone_price_id_payment_mode";
    private static final String ZONE_PRICE_COLLUMN_ID_ZONE_WORK_TIME = "zone_price_id_zone_work_time";
    private static final String ZONE_PRICE_COLLUMN_PRICE_TRIMMED = "zone_price_price_trimmed";
    private static final String ZONE_PRICE_COLLUMN_PRICE_DECIMAL = "zone_price_price_decimal";
    private static final String ZONE_PRICE_COLLUMN_CURRENCY = "zone_price_currency";
    private static final String ZONE_PRICE_COLLUMN_MODIFIED_ON = "zone_price_modified_on";
    private static final String ZONE_PRICE_COLLUMN_CREATED_ON = "zone_price_created_on";

    private static final String CREATE_TABLE_ZONE_PRICE =
            "CREATE TABLE " + ZONE_PRICE_TABLE_NAME + "("
                    + ZONE_PRICE_COLLUMN_ID + " " + TYPE_INT + ","
                    + ZONE_PRICE_COLLUMN_ID_PAYMENT_MODE + " " + TYPE_INT + ","
                    + ZONE_PRICE_COLLUMN_ID_ZONE_WORK_TIME + " " + TYPE_INT + ","
                    + ZONE_PRICE_COLLUMN_PRICE_TRIMMED + " " + TYPE_INT + ","
                    + ZONE_PRICE_COLLUMN_PRICE_DECIMAL + " " + TYPE_INT + ","
                    + ZONE_PRICE_COLLUMN_CURRENCY + " " + TYPE_TEXT + ","
                    + ZONE_PRICE_COLLUMN_MODIFIED_ON + " " + TYPE_DATETIME + ","
                    + ZONE_PRICE_COLLUMN_CREATED_ON + " " + TYPE_DATETIME
                    + ")";

    public void insertZonePrice(ZonePrice zonePrice) {
        SQLiteDatabase db = this.openDatabaseWriteable();
        ContentValues val = new ContentValues();

        val.put(ZONE_PRICE_COLLUMN_ID, zonePrice.getId());
        val.put(ZONE_PRICE_COLLUMN_ID_PAYMENT_MODE, zonePrice.getId_payment_mode());
        val.put(ZONE_PRICE_COLLUMN_ID_ZONE_WORK_TIME, zonePrice.getId_zone_work_time());
        val.put(ZONE_PRICE_COLLUMN_PRICE_TRIMMED, zonePrice.getPrice_trimmed());
        val.put(ZONE_PRICE_COLLUMN_PRICE_DECIMAL, zonePrice.getPrice_decimal());
        val.put(ZONE_PRICE_COLLUMN_CURRENCY, zonePrice.getCurrency());
        val.put(ZONE_PRICE_COLLUMN_MODIFIED_ON, this.getDateTime(zonePrice.getModified_on()));
        val.put(ZONE_PRICE_COLLUMN_CREATED_ON, this.getDateTime(zonePrice.getCreated_on()));

        db.insert(ZONE_PRICE_TABLE_NAME, null, val);
    }

    //### DBHelper methods

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private SQLiteDatabase openDatabaseReadable() {
        SQLiteDatabase db = null;
        db = this.getReadableDatabase();        // TODO provjere bacit
        return db;
    }

    private SQLiteDatabase openDatabaseWriteable() {
        SQLiteDatabase db = null;
        db = this.getWritableDatabase();        // TODO provjere nabacit
        return db;
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public boolean doesDatabaseExist() {
        return doesTableExist(ZONE_PRICE_TABLE_NAME);           // todo nabacit jos provjera
    }

    private boolean doesTableExist(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
