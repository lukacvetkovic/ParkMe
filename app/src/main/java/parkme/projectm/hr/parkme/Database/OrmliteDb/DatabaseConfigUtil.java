package parkme.projectm.hr.parkme.Database.OrmliteDb;


import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            City.class,
            ParkingZone.class,
            PaymentMode.class,
            PostCode.class
    };
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config", classes);
    }
}