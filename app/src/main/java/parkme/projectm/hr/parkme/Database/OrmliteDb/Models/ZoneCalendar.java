package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.util.Date;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleZoneCalendar;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "zone_calendar")
public class ZoneCalendar {
    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int idZone;
    @DatabaseField
    private Date dateFrom;
    @DatabaseField
    private Date dateTo;

    public ZoneCalendar(){

    }
    public ZoneCalendar(SimpleZoneCalendar simpleZoneCalendar){
        this.id = simpleZoneCalendar.getId();
        this.idZone = simpleZoneCalendar.getIdZone();
        try {
            this.dateFrom = DatabaseManager.dateFormatter.parse(simpleZoneCalendar.getDateFrom());
            this.dateTo = DatabaseManager.dateFormatter.parse(simpleZoneCalendar.getDateTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public ZoneCalendar(int id, int idZone, Date dateFrom, Date dateTo) {
        this.id = id;
        this.idZone = idZone;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
