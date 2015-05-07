package parkme.projectm.hr.parkme.Database.OrmliteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static int DataBaseVersion = 4;

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "parky.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = DataBaseVersion;

    private static final String DATABASE_PATH = "/data/data/hr.projectm.parkme/databases/";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_PATH+DATABASE_NAME, null, DATABASE_VERSION);

        boolean dbexist = checkdatabase();
        if (!dbexist) {

            // If database did not exist, try copying existing database from assets folder.
            try {
                File dir = new File(DATABASE_PATH);
                dir.mkdirs();
                InputStream myinput = context.getAssets().open(DATABASE_NAME);
                String outfilename = DATABASE_PATH + DATABASE_NAME;
                Log.i(DatabaseHelper.class.getName(), "DB Path : " + outfilename);
                OutputStream myoutput = new FileOutputStream(outfilename);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myinput.read(buffer)) > 0) {
                    myoutput.write(buffer, 0, length);
                }
                myoutput.flush();
                myoutput.close();
                myinput.close();
                Log.d("Premjestio sam","bazu");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * Check whether or not database exist
    */
    private boolean checkdatabase() {
        boolean checkdb = false;

        String myPath = DATABASE_PATH + DATABASE_NAME;
        File dbfile = new File(myPath);
        checkdb = dbfile.exists();

        Log.i(DatabaseHelper.class.getName(), "DB Exist : " + checkdb);

        return checkdb;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
