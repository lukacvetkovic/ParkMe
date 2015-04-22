package parkme.projectm.hr.parkme.PomocPOST;

/**
 * Created by Luka on 22.4.2015..
 */
public class marker {
    private int id;
    private double lat;
    private double lng;
    private int id_zone;

    public marker(int id, double lat, double lng, int id_zone) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.id_zone = id_zone;
    }
}
