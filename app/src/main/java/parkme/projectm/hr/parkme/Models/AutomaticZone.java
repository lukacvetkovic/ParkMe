package parkme.projectm.hr.parkme.Models;

/**
 * Created by Luka on 5.5.2015..
 */
public class AutomaticZone {
    private Integer id;

    private Integer id_zone;

    private Double lng;

    private Double lat;

    private Double dist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_zone() {
        return id_zone;
    }

    public void setId_zone(Integer id_zone) {
        this.id_zone = id_zone;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", id_zone = " + id_zone + ", lng = " + lng + ", lat = " + lat + ", dist = " + dist + "]";
    }
}
