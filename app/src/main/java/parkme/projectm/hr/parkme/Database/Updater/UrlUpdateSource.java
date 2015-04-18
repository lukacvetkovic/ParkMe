package parkme.projectm.hr.parkme.Database.Updater;

import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneWorkTime;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleCity;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleMaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimplePaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimplePostCode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleZoneWorkTime;
import parkme.projectm.hr.parkme.Helpers.Rest.GetRestService;
import parkme.projectm.hr.parkme.Helpers.JavaJsonHelper;

/**
 * Created by Adriano Bacac on 30.03.15..
 */
public class UrlUpdateSource implements UpdateSource{

    private  GetRestService rest;

    public UrlUpdateSource(GetRestService rest){
        this.rest = rest;
    }
    private <T> List<T> getNewTableRows(String url, Class<T> dotClass){
        rest.setUrl(url);
        String response;
        try {
            Log.d("Upit","REST");
            response = rest.execute();
            Log.d("Upit gotov","REST");
            return JavaJsonHelper.fromStringToList(response, dotClass);
        } catch (ExecutionException | InterruptedException | JSONException e) {
            return new ArrayList<T>();
        }
    }
    private String urlBuilder(String tableName, Date lastUpdate){
        return "https://lumipex.me/ParkMe/api/data/" + tableName
                + "/modified/after/" + DatabaseManager.dateFormatter.format(lastUpdate) + ".json";
    }
    @Override
    public List<City> getNewCityRows(Date lastUpdate){
        List<SimpleCity> simpleRows = getNewTableRows(urlBuilder("city", lastUpdate), SimpleCity.class);
        List<City> rows = new ArrayList<>();
        for (SimpleCity simpleRow : simpleRows){
            rows.add(new City(simpleRow));
        }
        return rows;
    }

    @Override
    public List<ParkingZone> getNewParkingZoneRows(Date lastUpdate) {
        List<SimpleParkingZone> simpleRows =  getNewTableRows(urlBuilder("parking_zone", lastUpdate), SimpleParkingZone.class);
        List<ParkingZone> rows = new ArrayList<>();
        for (SimpleParkingZone simpleRow : simpleRows){
            rows.add(new ParkingZone(simpleRow));
        }
        return rows;
    }

    @Override
    public List<PostCode> getNewPostcodeRows(Date lastUpdate) {
        List<SimplePostCode> simpleRows =  getNewTableRows(urlBuilder("post_code", lastUpdate), SimplePostCode.class);
        List<PostCode> rows = new ArrayList<>();
        for (SimplePostCode simpleRow : simpleRows){
            rows.add(new PostCode(simpleRow));
        }
        return rows;
    }

    @Override
    public List<PaymentMode> getNewPaymentModeRows(Date lastUpdate) {
        List<SimplePaymentMode> simpleRows =  getNewTableRows(urlBuilder("payment_mode", lastUpdate), SimplePaymentMode.class);
        List<PaymentMode> rows = new ArrayList<>();
        for (SimplePaymentMode simpleRow : simpleRows){
            rows.add(new PaymentMode(simpleRow));
        }
        return rows;
    }

    @Override
    public List<ZoneCalendar> getNewZoneCalendarRows(Date lastUpdate) {
        List<SimpleZoneCalendar> simpleRows =  getNewTableRows(urlBuilder("zone_calendar", lastUpdate), SimpleZoneCalendar.class);
        List<ZoneCalendar> rows = new ArrayList<>();
        for (SimpleZoneCalendar simpleRow : simpleRows){
            rows.add(new ZoneCalendar(simpleRow));
        }
        return rows;
    }

    @Override
    public List<ZoneWorkTime> getNewZoneWorkTimeRows(Date lastUpdate) {
        List<SimpleZoneWorkTime> simpleRows =  getNewTableRows(urlBuilder("zone_work_time", lastUpdate), SimpleZoneWorkTime.class);
        List<ZoneWorkTime> rows = new ArrayList<>();
        for (SimpleZoneWorkTime simpleRow : simpleRows){
            rows.add(new ZoneWorkTime(simpleRow));
        }
        return rows;
    }

    @Override
    public List<ZonePrice> getNewZonePriceRows(Date lastUpdate) {
        List<SimpleZonePrice> simpleRows =  getNewTableRows(urlBuilder("zone_price", lastUpdate), SimpleZonePrice.class);
        List<ZonePrice> rows = new ArrayList<>();
        for (SimpleZonePrice simpleRow : simpleRows){
            rows.add(new ZonePrice(simpleRow));
        }
        return rows;
    }

    @Override
    public List<MaxDuration> getMaxDurationRows(Date lastUpdate) {
        List<SimpleMaxDuration> simpleRows =  getNewTableRows(urlBuilder("max_duration", lastUpdate), SimpleMaxDuration.class);
        List<MaxDuration> rows = new ArrayList<>();
        for (SimpleMaxDuration simpleRow : simpleRows){
            rows.add(new MaxDuration(simpleRow));
        }
        return rows;
    }
}
