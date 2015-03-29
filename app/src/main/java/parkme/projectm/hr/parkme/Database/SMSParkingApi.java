package parkme.projectm.hr.parkme.Database;

import android.text.format.Time;

import java.sql.Date;
import java.util.List;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;

/**
 * Created by Cveki on 29.3.2015..
 */
public interface SMSParkingApi {

    //Get all city list
    public List<City> getAllCities();
    //Get parking zone list for a city
    public List<ParkingZone>getAllParkingZonesFromCity(int IdCity);
    //Get list of options for parking zone
    public List<PaymentMode>getAllPaymentModesFromParkingZone(int IdParkingZone);
    //Get price for parking zone
    public float getPrice(Date date,int idParkingZone,int idOption);
    //Get maxduration for parkingzone--> TODO do not know if Time class is OK!
    public Time maxDuration(Date date,int IdParkingZone);


}
