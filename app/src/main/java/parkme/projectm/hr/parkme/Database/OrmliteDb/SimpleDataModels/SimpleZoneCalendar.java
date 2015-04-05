package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
public class SimpleZoneCalendar {
    private int id;
    private int id_zone;
    private String date_from;
    private String date_to;

    public SimpleZoneCalendar(){

    }

    public SimpleZoneCalendar(int id, int idZone, String dateFrom, String dateTo) {
        this.id = id;
        this.id_zone = idZone;
        this.date_from = dateFrom;
        this.date_to = dateTo;
    }

    public int getIdZone() {
        return id_zone;
    }

    public void setIdZone(int idZone) {
        this.id_zone = idZone;
    }

    public String getDateFrom() {
        return date_from;
    }

    public void setDateFrom(String dateFrom) {
        this.date_from = dateFrom;
    }

    public String getDateTo() {
        return date_to;
    }

    public void setDateTo(String dateTo) {
        this.date_to = dateTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
