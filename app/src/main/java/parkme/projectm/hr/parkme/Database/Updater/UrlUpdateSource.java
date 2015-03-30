package parkme.projectm.hr.parkme.Database.Updater;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneWorkTime;
import parkme.projectm.hr.parkme.Helpers.GetRestService;
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
            response = rest.execute();
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
        return getNewTableRows(urlBuilder("city", lastUpdate), City.class);
    }

    @Override
    public List<ParkingZone> getNewParkingZoneRows(Date lastUpdate) {
        return getNewTableRows(urlBuilder("parking_zone", lastUpdate), ParkingZone.class);
    }

    @Override
    public List<PostCode> getNewPostcodeRows(Date lastUpdate) {
        return getNewTableRows(urlBuilder("post_code", lastUpdate), PostCode.class);
    }

    @Override
    public List<PaymentMode> getNewPaymentModeRows(Date lastUpdate) {
        return getNewTableRows(urlBuilder("payment_mode", lastUpdate), PaymentMode.class);
    }

    @Override
    public List<ZoneCalendar> getNewZoneCalendarRows(Date lastUpdate) {
        return getNewTableRows(urlBuilder("zone_calendar", lastUpdate), ZoneCalendar.class);
    }

    @Override
    public List<ZoneWorkTime> getNewZoneWorkTimeRows(Date lastUpdate) {
        return getNewTableRows(urlBuilder("zone_work_time", lastUpdate), ZoneWorkTime.class);
    }

    @Override
    public List<ZonePrice> getNewZonePriceRows(Date lastUpdate) {
        return getNewTableRows(urlBuilder("zone_price", lastUpdate), ZonePrice.class);
    }
}
