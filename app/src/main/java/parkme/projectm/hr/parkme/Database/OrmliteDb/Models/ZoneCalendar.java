package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "zone_calendar")
public class ZoneCalendar {
    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int id_zone;
    @DatabaseField
    private Date date_from;
    @DatabaseField
    private Date date_to;

    public ZoneCalendar(){

    }

    public ZoneCalendar(int id, int idZone, Date dateFrom, Date dateTo) {
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

    public Date getDateFrom() {
        return date_from;
    }

    public void setDateFrom(Date dateFrom) {
        this.date_from = dateFrom;
    }

    public Date getDateTo() {
        return date_to;
    }

    public void setDateTo(Date dateTo) {
        this.date_to = dateTo;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
