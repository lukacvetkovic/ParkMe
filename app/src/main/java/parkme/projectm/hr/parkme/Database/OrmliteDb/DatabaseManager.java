package parkme.projectm.hr.parkme.Database.OrmliteDb;


/**
 * Created by Adriano Bacac on 29.03.15..
 */

import android.content.Context;

public class DatabaseManager {

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


}