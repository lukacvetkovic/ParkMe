package parkme.projectm.hr.parkme.Database.OrmliteDb;


/**
 * Created by Adriano Bacac on 29.03.15..
 */

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneWorkTime;
import parkme.projectm.hr.parkme.Database.SMSParkingApi;

/**
 * Interface to database using OrmLite.
 */
public class DatabaseManager implements SMSParkingApi{

    static private DatabaseManager instance;
    private  OrmLiteDatabaseHelper helper;
    private  SimpleDateFormat dateFormater;
    private Map<Integer, Integer> dayTransform;
    private SimpleDateFormat timeFormatter;


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
        dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        timeFormatter = new SimpleDateFormat("H:m:s");

        dayTransform = new HashMap<>();
        dayTransform.put(Calendar.MONDAY, 0);
        dayTransform.put(Calendar.TUESDAY, 1);
        dayTransform.put(Calendar.WEDNESDAY, 2);
        dayTransform.put(Calendar.THURSDAY, 3);
        dayTransform.put(Calendar.FRIDAY, 4);
        dayTransform.put(Calendar.SATURDAY, 5);
        dayTransform.put(Calendar.SUNDAY, 6);
    }

    @Override
    public List<City> getAllCities() {
        return helper.getRuntimeCityDao().queryForAll();
    }

    @Override
    public List<ParkingZone> getAllParkingZonesFromCity(int idCity) {
        try {
            return helper.getRuntimeParkingZoneDao().queryBuilder()
                    .where().eq("id_city", new Integer(idCity)).query();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<PaymentMode> getAllPaymentModesFromParkingZone(int idParkingZone) {
        try {
            return helper.getRuntimePaymentModeDao().queryBuilder()
                    .where().eq("id_zone", new Integer(idParkingZone)).query();
        } catch (SQLException e) {
            return null;
        }
    }

    private Date dateInterval(Date date1, Date date2){
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        Date res = new Date();
        res.setTime(Math.abs(time2 - time1));
        return res;
    }

    private ZoneCalendar getCalendarWithMinInterval(Date date, int idZone) throws SQLException {
        List<ZoneCalendar> dateIntervals;
        dateIntervals = helper.getRuntimeZoneCalendarDao().queryBuilder()
                .where()
                .eq("id_zone", new Integer(idZone))
                .and()
                .gt("date_from", date)
                .and()
                .lt("date_to", date).query();

        if(dateIntervals.isEmpty()){
            throw new SQLException("No records found in calendar for zone id: " + idZone + ".");
        }
        ZoneCalendar minCalendar = dateIntervals.get(0);
        if(dateIntervals.size() > 1) {

            for(int i = 1; i<dateIntervals.size();++i){
                ZoneCalendar queryCalendar = dateIntervals.get(i);
                long queryInterval = dateInterval(queryCalendar.getDateFrom()
                        , queryCalendar.getDateTo()).getTime();

                long minInterval = dateInterval(minCalendar.getDateFrom()
                        , minCalendar.getDateTo()).getTime();

                if(queryInterval < minInterval){
                    minCalendar = queryCalendar;
                }
            }
        }
        return minCalendar;
    }
    private ZoneWorkTime getWorkTime(Date date, int idCalendar) throws SQLException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = dayTransform.get(cal.get(Calendar.DAY_OF_WEEK));

        return helper.getRuntimeZoneWorkTimeDao().queryBuilder()
                .where()
                .eq("id_zone", new Integer(idCalendar))
                .and()
                .gt("time_from", timeFormatter.format(date))
                .and()
                .lt("date_to", timeFormatter.format(date))
                .and()
                .eq("day_name", day).queryForFirst();

    }
    public float getPrice(int idZoneWorkTime, int idPaymentMode){
        try {
            ZonePrice price= helper.getRuntimeZonePriceDao().queryBuilder()
                    .where()
                    .eq("id_zone_work_time", new Integer(idZoneWorkTime))
                    .and()
                    .eq("id_payment_mode", new Integer(idPaymentMode))
                    .queryForFirst();
            return price.getPriceFloat();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public float getPrice(Date date, int idPaymentMode) {
        PaymentMode mode = helper.getRuntimePaymentModeDao().queryForId(idPaymentMode);
        ParkingZone zone = helper.getRuntimeParkingZoneDao().queryForId(mode.getIdZone());

        ZoneCalendar calendar;

        try {
            calendar = getCalendarWithMinInterval(date, zone.getId());
        } catch (SQLException e) {
            return 0;
        }

        ZoneWorkTime workTime;
        try {
            workTime = getWorkTime(date, calendar.getId());
        } catch (SQLException e) {
            return 0;
        }
        return getPrice(workTime.getId(), idPaymentMode);
    }

    @Override
    public Date getMaxDuration(Date date, int idParkingZone) {
        return null;
    }
}