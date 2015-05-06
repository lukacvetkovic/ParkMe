package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleParkingLot;

/**
 * Created by Adriano Bacac on 06.05.15..
 */

@DatabaseTable(tableName = "parking_lot")
public class ParkingLot {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "description")
    private String description;
    @DatabaseField(columnName = "address")
    private String adress;
    @DatabaseField(columnName = "google_place_id")
    private String googlePlaceId;
    @DatabaseField(columnName = "relativity")
    private int relativity;
    @DatabaseField(columnName = "lat")
    private double lat;
    @DatabaseField(columnName = "lng")
    private double lng;

    public ParkingLot(){

    }

    public ParkingLot(SimpleParkingLot p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.adress = p.getAdress();
        this.googlePlaceId = p.getGooglePlaceId();
        this.relativity = p.getRelativity();
        this.lat = p.getLat();
        this.lng = p.getLng();
    }
    public ParkingLot(int id, String name, String description, String adress, String googlePlaceId, int relativity, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adress = adress;
        this.googlePlaceId = googlePlaceId;
        this.relativity = relativity;
        this.lat = lat;
        this.lng = lng;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public void setGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }

    public int getRelativity() {
        return relativity;
    }

    public void setRelativity(int relativity) {
        this.relativity = relativity;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
