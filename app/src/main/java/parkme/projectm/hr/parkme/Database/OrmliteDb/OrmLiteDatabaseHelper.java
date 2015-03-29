package parkme.projectm.hr.parkme.Database.OrmliteDb;


import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.R;
/**
 * Database helper class used to manage the creation and upgrading of your database.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "parky.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    public OrmLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(OrmLiteDatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, City.class);
            TableUtils.createTable(connectionSource, ParkingZone.class);
            TableUtils.createTable(connectionSource, PaymentMode.class);
            TableUtils.createTable(connectionSource, PostCode.class);
        } catch (SQLException e) {
            Log.e(OrmLiteDatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(OrmLiteDatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, City.class, true);
            TableUtils.dropTable(connectionSource, ParkingZone.class, true);
            TableUtils.dropTable(connectionSource, PaymentMode.class, true);
            TableUtils.dropTable(connectionSource, PostCode.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(OrmLiteDatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    // the DAO object we use to access the payment_mode table
    private Dao<City, Integer> cityDao = null;
    private RuntimeExceptionDao<City, Integer> cityRuntimeDao = null;

    // the DAO object we use to access the parking_zone table
    private Dao<ParkingZone, Integer> parkingZoneDao = null;
    private RuntimeExceptionDao<ParkingZone, Integer> parkingZoneRuntimeDao = null;

    // the DAO object we use to access the payment_mode table
    private Dao<PaymentMode, Integer> paymentModeDao = null;
    private RuntimeExceptionDao<PaymentMode, Integer> paymentModeRuntimeDao = null;


    /**
     * Returns the Database Access Object (DAO) for our City class. It will create it or just give the cached
     * value.
     */
    public Dao<City, Integer> getCityDao() throws SQLException {
        if (cityDao == null) {
            cityDao = getDao(City.class);
        }
        return cityDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our City class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<City, Integer> getRuntimeCityDao() {
        if (cityRuntimeDao == null) {
            cityRuntimeDao = getRuntimeExceptionDao(City.class);
        }
        return cityRuntimeDao;
    }


    /**
     * Returns the Database Access Object (DAO) for our ParkingZone class. It will create it or just give the cached
     * value.
     */
    public Dao<ParkingZone, Integer> getParkingZoneDao() throws SQLException {
        if (parkingZoneDao == null) {
            parkingZoneDao = getDao(ParkingZone.class);
        }
        return parkingZoneDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ParkingZone class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<ParkingZone, Integer> getRuntimeParkingZoneDao() {
        if (parkingZoneRuntimeDao == null) {
            parkingZoneRuntimeDao = getRuntimeExceptionDao(ParkingZone.class);
        }
        return parkingZoneRuntimeDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our PaymentMode class. It will create it or just give the cached
     * value.
     */
    public Dao<PaymentMode, Integer> getPaymentModeDao() throws SQLException {
        if (paymentModeDao == null) {
            paymentModeDao = getDao(PaymentMode.class);
        }
        return paymentModeDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our PaymentMode class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<PaymentMode, Integer> getRuntimePaymentModeDao() {
        if (paymentModeRuntimeDao == null) {
            paymentModeRuntimeDao = getRuntimeExceptionDao(PaymentMode.class);
        }
        return paymentModeRuntimeDao;
    }
    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }
}