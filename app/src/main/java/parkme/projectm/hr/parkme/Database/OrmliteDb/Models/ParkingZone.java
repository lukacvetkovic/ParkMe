package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleParkingZone;

/**
 * Model of parking zone.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "parking_zone")
public class ParkingZone {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String phoneNumber;
    @DatabaseField
    private int idCity;

    public ParkingZone(){

    }
    public ParkingZone(SimpleParkingZone simpleParkingZone){
        this.id = simpleParkingZone.getId();
        this.name = simpleParkingZone.getName();
        this.phoneNumber = simpleParkingZone.getPhoneNumber();
        this.idCity = simpleParkingZone.getIdCity();
    }
    public ParkingZone(int id, String name, String phone_number, int id_city) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phone_number;
        this.idCity = id_city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }
}
