package parkme.projectm.hr.parkme.Database.OrmliteDb;


/**
 * Created by Adriano Bacac on 29.03.15..
 */

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import parkme.projectm.hr.parkme.Database.SMSParkingApi;
import parkme.projectm.hr.parkme.Database.Updater.Updater;

/**
 * Interface to database using OrmLite.
 */
public class DatabaseManager implements SMSParkingApi, Updater{

    static private DatabaseManager instance;
    private  OrmLiteDatabaseHelper helper;
    private Map<Integer, Integer> dayTransform;
    public static SimpleDateFormat timeFormatter;
    public static SimpleDateFormat dateFormatter;

    static {
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        timeFormatter = new SimpleDateFormat("H:m:s");
    }

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
    public List<City> getAllCities(boolean sort) {
        if(sort){
            List<City> sortedCities = helper.getRuntimeCityDao().queryForAll();
            Collections.sort(sortedCities, new Comparator<City>() {
                @Override
                public int compare(City city1, City city2) {
                    return city1.getName().compareTo(city2.getName());
                }
            });
            return sortedCities;
        }
        return helper.getRuntimeCityDao().queryForAll();
    }

    @Override
    public String getCityNameFromPostCode(String postCode) {
        try {
            int cityId = helper.getRuntimePostcodeDao().queryBuilder()
                    .where().eq("post_code", postCode).queryForFirst().getIdCity();
            return helper.getRuntimeCityDao().queryForId(cityId).getName();
        }
        catch (Exception e){
            return null;
        }
    }

    public List<ParkingLot> getAllParkingLots(){
        return helper.getRuntimeParkingLotDao().queryForAll();
    }

    public City getCityFromPostCode(String postCode) {
        try {
            int cityId = helper.getRuntimePostcodeDao().queryBuilder()
                    .where().eq("post_code", postCode).queryForFirst().getIdCity();
            return helper.getRuntimeCityDao().queryForId(cityId);
        }
        catch (Exception e){
            return null;
        }
    }

    public String getCityNameFromId(int cityId){
        try{
            return helper.getRuntimeCityDao().queryForId(cityId).getName();
        }
        catch (Exception e){
            return "Grad";
        }
    }

    // ## Favoritepayment methods
    public List<FavoritePayment> getAllFavoritePayments(){
        return helper.getRuntimeFavouritePaymentDao().queryForAll();
    }

    public void addFavoritePayment(FavoritePayment favoritePayment) {
        helper.getRuntimeFavouritePaymentDao().createIfNotExists(favoritePayment);
    }

    public FavoritePayment getFavoritePaymentFromId(int id) {
        return helper.getRuntimeFavouritePaymentDao().queryForId(id);
    }

    public void removeFavoritePayment(FavoritePayment favoritePayment) {
        helper.getRuntimeFavouritePaymentDao().delete(favoritePayment);
    }


    // ## PastparkingPayments metods
    public List<PastParkingPayment> getAllPastparkingPayments(){

        List<PastParkingPayment> allPayments=helper.getRuntimePastPaymentDao().queryForAll();

        Collections.sort(allPayments, new Comparator<PastParkingPayment>() {

            @Override
            public int compare(PastParkingPayment lhs, PastParkingPayment rhs) {
                if (lhs.getPastParkingPaymentId() < rhs.getPastParkingPaymentId()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return allPayments;
    }

    public PastParkingPayment getActiveParkingPayment(){
        List<PastParkingPayment> payments = getAllPastparkingPayments();
        if(payments != null && payments.size()>0){
            return payments.get(0);
        }
        return null;
    }

    public void addPastparkingPayment(PastParkingPayment pastParkingPayment) {
        helper.getRuntimePastPaymentDao().createIfNotExists(pastParkingPayment);
    }

    public PastParkingPayment getPastParkingPaymentFromId(int id){
        return helper.getRuntimePastPaymentDao().queryForId(id);
    }

    public void removePastParkingPayment(PastParkingPayment pastParkingPayment){
        helper.getRuntimePastPaymentDao().delete(pastParkingPayment);
    }


    // ## FavoriteCar methods

    public List<FavouriteCar> getAllFavouriteCars(){
        return helper.getRuntimeFavouriteCarDao().queryForAll();
    }

    public FavouriteCar getFavoriteCarFromPlates(String plates){
        try {
            return helper.getRuntimeFavouriteCarDao().queryBuilder()
                    .where().eq(FavouriteCar.column_car_registration, plates).queryForFirst();
        }
        catch (Exception e){
            Log.e("DatebaseManager", "No car for queried plates -> " + plates);
            return null;
        }
    }

    public void addFavouriteCar(FavouriteCar car) {
        helper.getRuntimeFavouriteCarDao().createIfNotExists(car);
    }

    public void removeFavouriteCar(FavouriteCar car) {
        helper.getRuntimeFavouriteCarDao().delete(car);
    }

    @Override
    public ParkingZone getParkingZoneFromId(int idParkingZone) {
        return helper.getRuntimeParkingZoneDao().queryForId(idParkingZone);
    }

    public String getZoneNameFromId(int idParkingZone){
        try{
            return helper.getRuntimeParkingZoneDao().queryForId(idParkingZone).getName();
        }
        catch (Exception e){
            return "Zona";
        }
    }

    @Override
    public PaymentMode getPaymentModeFromId(int idPaymentMode) {
        return helper.getRuntimePaymentModeDao().queryForId(idPaymentMode);
    }


    @Override
    public List<ParkingZone> getAllParkingZonesFromCity(int idCity) {
        try {
            List<ParkingZone> parkingZones = helper.getRuntimeParkingZoneDao().queryBuilder()
                    .where().eq("id_city", idCity).query();

            Collections.sort(parkingZones, new Comparator<ParkingZone>() {
                @Override
                public int compare(ParkingZone parkingZone, ParkingZone anotherparkingZone) {
                    return parkingZone.getName().compareTo(anotherparkingZone.getName());
                }
            });

            return parkingZones;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<PaymentMode> getAllPaymentModesFromParkingZone(int idParkingZone) {
        try {
            return helper.getRuntimePaymentModeDao().queryBuilder()
                    .where().eq("id_zone", idParkingZone).query();
        } catch (SQLException e) {
            return null;
        }
    }
    private MaxDuration maxDurationWithMinInterval(List<MaxDuration> maxDurations){
        MaxDuration min = maxDurations.get(0);
        if(maxDurations.size() > 1) {

            for(int i = 1; i<maxDurations.size();++i){
                MaxDuration query = maxDurations.get(i);
                long queryInterval = dateInterval(
                        query.getDateFrom()
                        , query.getDateTo()
                ).getTime();

                long minInterval = dateInterval(
                        min.getDateFrom()
                        , min.getDateTo()
                ).getTime();

                if(queryInterval < minInterval){
                    min = query;
                }
            }
        }
        return min;
    }

    private ZoneCalendar calendarWithMinInterval(List<ZoneCalendar> zoneCalendars){
        ZoneCalendar minCalendar = zoneCalendars.get(0);
        if(zoneCalendars.size() > 1) {

            for(int i = 1; i<zoneCalendars.size();++i){
                ZoneCalendar queryCalendar = zoneCalendars.get(i);
                long queryInterval = dateInterval(
                        queryCalendar.getDateFrom()
                        , queryCalendar.getDateTo()
                ).getTime();

                long minInterval = dateInterval(
                        minCalendar.getDateFrom()
                        , minCalendar.getDateTo()
                ).getTime();

                if(queryInterval < minInterval){
                    minCalendar = queryCalendar;
                }
            }
        }
        return minCalendar;
    }
    private Date dateInterval(Date date1, Date date2){
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        Date res = new Date();
        res.setTime(Math.abs(time2 - time1));
        return res;
    }
    private Date stripYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.YEAR , 1900);
        return cal.getTime();
    }
    private ZoneCalendar getCalendarWithMinInterval(Date date, int idZone) throws SQLException {

        Date strippedDate = stripYear(date);
        List<ZoneCalendar> dateIntervals;
        dateIntervals = helper.getRuntimeZoneCalendarDao().queryBuilder()
                .where()
                .eq("id_zone", idZone)
                .and()
                .lt("date_from", strippedDate)
                .and()
                .gt("date_to", strippedDate).query();

        if(dateIntervals.isEmpty()){
            throw new SQLException("No records found in calendar for zone id: " + idZone + ".");
        }
        return  calendarWithMinInterval(dateIntervals);
    }

    private ZoneWorkTime getWorkTime(Date date, int idCalendar) throws SQLException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = dayTransform.get(cal.get(Calendar.DAY_OF_WEEK));
        Date time = new Date();

        time.setTime(date.getTime() % (24*60*60*1000L));

        return helper.getRuntimeZoneWorkTimeDao().queryBuilder()
                .where()
                    .eq("id_zone_calendar", idCalendar)
                    .and()
                    .lt("time_from", time)
                    .and()
                    .gt("time_to", time)
                    .and()
                    .eq("day_name", day)
                .queryForFirst();

    }
    public ZonePrice getPriceObj(int idZoneWorkTime, int idPaymentMode){
        try {
            ZonePrice price= helper.getRuntimeZonePriceDao().queryBuilder()
                    .where()
                    .eq("id_zone_work_time", idZoneWorkTime)
                    .and()
                    .eq("id_payment_mode", idPaymentMode)
                    .queryForFirst();
            return price;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ZonePrice getPrice(Date date, int idPaymentMode) {
        PaymentMode mode = helper.getRuntimePaymentModeDao().queryForId(idPaymentMode);
        ParkingZone zone = helper.getRuntimeParkingZoneDao().queryForId(mode.getIdZone());

        ZoneCalendar calendar;

        try {
            calendar = getCalendarWithMinInterval(date, zone.getId());
        } catch (SQLException e) {
            return null;
        }

        ZoneWorkTime workTime;
        try {
            workTime = getWorkTime(date, calendar.getId());
        } catch (SQLException e) {
            return null;
        }
        return getPriceObj(workTime.getId(), idPaymentMode);
    }

    @Override
    public MaxDuration getMaxDuration(Date date, int idParkingZone) {
        Date strippedDate = stripYear(date);

        List<MaxDuration> maxDurations = new ArrayList<>();

        try {
            maxDurations = helper.getRuntimeMaxDurationDao().queryBuilder()
                    .where()
                    .eq("id_zone", idParkingZone)
                    .and()
                    .lt("date_from", strippedDate)
                    .and()
                    .gt("date_to", strippedDate).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxDurationWithMinInterval(maxDurations);
    }

    @Override
    public void updateCity(List<City> cities) {
        for (City city: cities) {
            helper.getRuntimeCityDao().createOrUpdate(city);
        }
    }

    @Override
    public void updateParkingLot(List<ParkingLot> parkingLots) {
        for(ParkingLot parkingLot: parkingLots)
        helper.getRuntimeParkingLotDao().createOrUpdate(parkingLot);
    }

    @Override
    public void updateParkingZone(List<ParkingZone> parkingZones) {
        for (ParkingZone parkingZone: parkingZones) {
            helper.getRuntimeParkingZoneDao().createOrUpdate(parkingZone);
        }
    }

    @Override
    public void updatePostcode(List<PostCode> postcodes) {
        for (PostCode postcode: postcodes) {
            helper.getRuntimePostcodeDao().createOrUpdate(postcode);
        }
    }

    @Override
    public void updatePaymentMode(List<PaymentMode> paymentModes) {
        for (PaymentMode paymentMode: paymentModes) {
            helper.getRuntimePaymentModeDao().createOrUpdate(paymentMode);
        }
    }

    @Override
    public void updateZoneCalendar(List<ZoneCalendar> zoneCalendars) {
        for (ZoneCalendar zoneCalendar: zoneCalendars) {
            helper.getRuntimeZoneCalendarDao().createOrUpdate(zoneCalendar);
        }
    }

    @Override
    public void updateZoneWorkTime(List<ZoneWorkTime> zoneWorkTimes) {
        for (ZoneWorkTime zoneWorkTime: zoneWorkTimes) {
            helper.getRuntimeZoneWorkTimeDao().createOrUpdate(zoneWorkTime);
        }
    }

    @Override
    public void updateZonePrice(List<ZonePrice> zonePrices) {
        for (ZonePrice zonePrice: zonePrices) {
            helper.getRuntimeZonePriceDao().createOrUpdate(zonePrice);
        }
    }

    @Override
    public void updateMaxDuration(List<MaxDuration> maxDurations) {
        for (MaxDuration zonePrice: maxDurations) {
            helper.getRuntimeMaxDurationDao().createOrUpdate(zonePrice);
        }
    }
}