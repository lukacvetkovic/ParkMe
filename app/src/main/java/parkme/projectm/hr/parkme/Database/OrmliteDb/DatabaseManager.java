package parkme.projectm.hr.parkme.Database.OrmliteDb;


/**
 * Created by Adriano Bacac on 29.03.15..
 */

import android.content.Context;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.SMSParkingApi;

/**
 * Interface to database using OrmLite.
 */
public class DatabaseManager implements SMSParkingApi{

    static private DatabaseManager instance;
    private static OrmLiteDatabaseHelper helper;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager(Context ctx) {
        helper = new OrmLiteDatabaseHelper(ctx);
    }

    public static OrmLiteDatabaseHelper getHelper() {
        return helper;
    }


    @Override
    public List<City> getAllCities() {
        return helper.getRuntimeCityDao().queryForAll();
    }

    @Override
    public List<ParkingZone> getAllParkingZonesFromCity(int idCity) {
        try {
            return helper.getRuntimeParkingZoneDao().queryBuilder().where().eq("id_city", new Integer(idCity)).query();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<PaymentMode> getAllPaymentModesFromParkingZone(int idParkingZone) {
        try {
            return helper.getRuntimePaymentModeDao().queryBuilder().where().eq("id_zone", new Integer(idParkingZone)).query();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public float getPrice(Date date, int idPaymentMode) {
        return 0;
    }

    @Override
    public Date getMaxDuration(Date date, int idParkingZone) {
        return null;
    }
}