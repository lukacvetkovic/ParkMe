package parkme.projectm.hr.parkme.Models;

/**
 * Created by Cveki on 11.2.2015..
 */
public class Marker {
    private int id;
    private double lat;
    private double lng;
    private int id_zone;

    public Marker(int id, double lat, double lng, int id_zone) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.id_zone = id_zone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId_zone() {
        return id_zone;
    }

    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }
}
