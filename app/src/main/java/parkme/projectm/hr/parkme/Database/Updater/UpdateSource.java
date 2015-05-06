package parkme.projectm.hr.parkme.Database.Updater;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingLot;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneWorkTime;
import parkme.projectm.hr.parkme.Database.Tables;

/**
 * Source for getting changed or added rows to tables.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
public interface UpdateSource {


    /**
     * Gets values for city table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<City> getNewCityRows(Date lastUpdate);
    /**
     * Gets values for parking zone table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<ParkingZone> getNewParkingZoneRows(Date lastUpdate);
    /**
     * Gets values for parking lot table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<ParkingLot> getNewParkingLotRows(Date lastUpdate);
    /**
     * Gets values for postcode table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<PostCode> getNewPostcodeRows(Date lastUpdate);
    /**
     * Gets values for payment mode table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<PaymentMode> getNewPaymentModeRows(Date lastUpdate);
    /**
     * Gets values for zone calendar table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<ZoneCalendar> getNewZoneCalendarRows(Date lastUpdate);
    /**
     * Gets values for zone work time table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<ZoneWorkTime> getNewZoneWorkTimeRows(Date lastUpdate);
    /**
     * Gets values for zone price table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<ZonePrice> getNewZonePriceRows(Date lastUpdate);

    /**
     * Gets values for max duration table changed after last update.
     * @param lastUpdate Date of last update.
     * @return List of changed of added rows.
     */
    public List<MaxDuration> getMaxDurationRows(Date lastUpdate);
}
