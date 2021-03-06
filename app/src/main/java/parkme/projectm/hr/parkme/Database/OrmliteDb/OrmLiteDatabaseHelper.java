package parkme.projectm.hr.parkme.Database.OrmliteDb;


import java.sql.SQLException;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import parkme.projectm.hr.parkme.CustomViewModels.FavoriteCarsArrayAdapter;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingLot;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PastParkingPayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneWorkTime;
import parkme.projectm.hr.parkme.R;
/**
 * Database helper class used to manage the creation and upgrading of your database.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static int DataBaseVersion = 4;

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "parky.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = DataBaseVersion;

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
            TableUtils.createTable(connectionSource, ZoneCalendar.class);
            TableUtils.createTable(connectionSource, ZonePrice.class);
            TableUtils.createTable(connectionSource, ZoneWorkTime.class);
            TableUtils.createTable(connectionSource, MaxDuration.class);
            TableUtils.createTable(connectionSource, FavouriteCar.class);
            TableUtils.createTable(connectionSource, FavoritePayment.class);
            TableUtils.createTable(connectionSource, PastParkingPayment.class);
            TableUtils.createTable(connectionSource, ParkingLot.class);
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
            TableUtils.dropTable(connectionSource, ZoneCalendar.class, true);
            TableUtils.dropTable(connectionSource, ZonePrice.class, true);
            TableUtils.dropTable(connectionSource, ZoneWorkTime.class, true);
            TableUtils.dropTable(connectionSource, MaxDuration.class, true);
            TableUtils.dropTable(connectionSource, FavouriteCar.class, true);
            TableUtils.dropTable(connectionSource, FavoritePayment.class, true);
            TableUtils.dropTable(connectionSource, PastParkingPayment.class, true);
            TableUtils.dropTable(connectionSource, ParkingLot.class, true);
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

    // the DAO object we use to access the parking_zone table
    private Dao<ParkingZone, Integer> parkingZoneDao = null;
    private RuntimeExceptionDao<ParkingZone, Integer> parkingZoneRuntimeDao = null;

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

    // the DAO object we use to access the payment_mode table
    private Dao<ParkingLot, Integer> parkingLotDao = null;
    private RuntimeExceptionDao<ParkingLot, Integer> parkingLotRuntimeDao = null;

    /**
     * Returns the Database Access Object (DAO) for our ParkingLot class. It will create it or just give the cached
     * value.
     */
    public Dao<ParkingLot, Integer> getparkingLotDao() throws SQLException {
        if (parkingLotDao == null) {
            parkingLotDao = getDao(ParkingLot.class);
        }
        return parkingLotDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ParkingLot class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<ParkingLot, Integer> getRuntimeParkingLotDao() {
        if (parkingLotRuntimeDao == null) {
            parkingLotRuntimeDao = getRuntimeExceptionDao(ParkingLot.class);
        }
        return parkingLotRuntimeDao;
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


    // the DAO object we use to access the payment_mode table
    private Dao<PaymentMode, Integer> paymentModeDao = null;
    private RuntimeExceptionDao<PaymentMode, Integer> paymentModeRuntimeDao = null;


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

    // the DAO object we use to access the zone_calendar table
    private Dao<ZoneCalendar, Integer> zoneCalendarDao = null;
    private RuntimeExceptionDao<ZoneCalendar, Integer> zoneCalendarRuntimeDao = null;

    /**
     * Returns the Database Access Object (DAO) for our ZoneCalendar class. It will create it or just give the cached
     * value.
     */
    public Dao<ZoneCalendar, Integer> getZoneCalendarDao() throws SQLException {
        if (zoneCalendarDao == null) {
            zoneCalendarDao = getDao(ZoneCalendar.class);
        }
        return zoneCalendarDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ZoneCalendar class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<ZoneCalendar, Integer> getRuntimeZoneCalendarDao() {
        if (zoneCalendarRuntimeDao == null) {
            zoneCalendarRuntimeDao = getRuntimeExceptionDao(ZoneCalendar.class);
        }
        return zoneCalendarRuntimeDao;
    }

    // the DAO object we use to access the zone_work_time table
    private Dao<ZoneWorkTime, Integer> zoneWorkTimeDao = null;
    private RuntimeExceptionDao<ZoneWorkTime, Integer> zoneWorkTimeRuntimeDao = null;

    /**
     * Returns the Database Access Object (DAO) for our ZoneWorkTime class. It will create it or just give the cached
     * value.
     */
    public Dao<ZoneWorkTime, Integer> getZoneWorkTimeDao() throws SQLException {
        if (zoneWorkTimeDao == null) {
            zoneWorkTimeDao = getDao(ZoneWorkTime.class);
        }
        return zoneWorkTimeDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ZoneWorkTime class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<ZoneWorkTime, Integer> getRuntimeZoneWorkTimeDao() {
        if (zoneWorkTimeRuntimeDao == null) {
            zoneWorkTimeRuntimeDao = getRuntimeExceptionDao(ZoneWorkTime.class);
        }
        return zoneWorkTimeRuntimeDao;
    }

    // the DAO object we use to access the zone_price table
    private Dao<ZonePrice, Integer> zonePriceDao = null;
    private RuntimeExceptionDao<ZonePrice, Integer> zonePriceRuntimeDao = null;

    /**
     * Returns the Database Access Object (DAO) for our ZonePrice class. It will create it or just give the cached
     * value.
     */
    public Dao<ZonePrice, Integer> getZonePriceDao() throws SQLException {
        if (zonePriceDao == null) {
            zonePriceDao = getDao(ZonePrice.class);
        }
        return zonePriceDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ZonePrice class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<ZonePrice, Integer> getRuntimeZonePriceDao() {
        if (zonePriceRuntimeDao == null) {
            zonePriceRuntimeDao = getRuntimeExceptionDao(ZonePrice.class);
        }
        return zonePriceRuntimeDao;
    }

    // the DAO object we use to access the post_code table
    private Dao<PostCode, Integer> postcodeDao = null;
    private RuntimeExceptionDao<PostCode, Integer> postcodeRuntimeDao = null;

    /**
     * Returns the Database Access Object (DAO) for our PostCode class. It will create it or just give the cached
     * value.
     */
    public Dao<PostCode, Integer> getPostcodeDao() throws SQLException {
        if (postcodeDao == null) {
            postcodeDao = getDao(PostCode.class);
        }
        return postcodeDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our PostCode class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<PostCode, Integer> getRuntimePostcodeDao() {
        if (postcodeRuntimeDao == null) {
            postcodeRuntimeDao = getRuntimeExceptionDao(PostCode.class);
        }
        return postcodeRuntimeDao;
    }



    // the DAO object we use to access the max_duration table
    private Dao<MaxDuration, Integer> maxDurationDao = null;
    private RuntimeExceptionDao<MaxDuration, Integer> maxDurationRuntimeDao = null;

    /**
     * Returns the Database Access Object (DAO) for our MaxDuration class. It will create it or just give the cached
     * value.
     */
    public Dao<MaxDuration, Integer> getMaxDurationDao() throws SQLException {
        if (maxDurationDao == null) {
            maxDurationDao = getDao(MaxDuration.class);
        }
        return maxDurationDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our MaxDuration class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<MaxDuration, Integer> getRuntimeMaxDurationDao() {
        if (maxDurationRuntimeDao == null) {
            maxDurationRuntimeDao = getRuntimeExceptionDao(MaxDuration.class);
        }
        return maxDurationRuntimeDao;
    }


    // the DAO object we use to access the favourite_cars table
    private Dao<FavouriteCar, String> favouriteCarDao = null;
    private RuntimeExceptionDao<FavouriteCar, String> favouriteCarRuntimeDao = null;

    public Dao<FavouriteCar, String> getFavouriteCarDao() throws SQLException {
        if(favouriteCarDao == null) {
            favouriteCarDao = getDao(FavouriteCar.class);
        }
        return favouriteCarDao;
    }

    public RuntimeExceptionDao<FavouriteCar, String> getRuntimeFavouriteCarDao() {
        if(favouriteCarRuntimeDao == null) {
            favouriteCarRuntimeDao = getRuntimeExceptionDao(FavouriteCar.class);
        }
        return favouriteCarRuntimeDao;
    }

    // the DAO object we use to access the favorite_payments table
    private Dao<FavoritePayment, Integer> favoritePaymentDao = null;
    private RuntimeExceptionDao<FavoritePayment, Integer> favoritePaymentRuntimeDao = null;

    public Dao<FavoritePayment, Integer> getFavouritePaymentDao() throws SQLException {
        if(favoritePaymentDao == null) {
            favoritePaymentDao = getDao(FavoritePayment.class);
        }
        return favoritePaymentDao;
    }

    public RuntimeExceptionDao<FavoritePayment, Integer> getRuntimeFavouritePaymentDao() {
        if(favoritePaymentRuntimeDao == null) {
            favoritePaymentRuntimeDao = getRuntimeExceptionDao(FavoritePayment.class);
        }
        return favoritePaymentRuntimeDao;
    }


    // the DAO object we use to access the past_parking_payments table
    private Dao<PastParkingPayment, Integer> pastPaymentDao = null;
    private RuntimeExceptionDao<PastParkingPayment, Integer> pastPaymentRuntimeDao = null;

    public Dao<PastParkingPayment, Integer> getPastPaymentDao() throws SQLException {
        if(pastPaymentDao == null) {
            pastPaymentDao = getDao(PastParkingPayment.class);
        }
        return pastPaymentDao;
    }

    public RuntimeExceptionDao<PastParkingPayment, Integer> getRuntimePastPaymentDao() {
        if(pastPaymentRuntimeDao == null) {
            pastPaymentRuntimeDao = getRuntimeExceptionDao(PastParkingPayment.class);
        }
        return pastPaymentRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }
}