package parkme.projectm.hr.parkme.Database.OrmliteDb;


import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingLot;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PastParkingPayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneWorkTime;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            City.class,
            ParkingZone.class,
            PaymentMode.class,
            PostCode.class,
            ZoneCalendar.class,
            ZoneWorkTime.class,
            ZonePrice.class,
            MaxDuration.class,
            FavouriteCar.class,
            FavoritePayment.class,
            PastParkingPayment.class,
            ParkingLot.class
    };
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config", classes);
    }
}