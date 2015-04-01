package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.util.Date;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleZoneWorkTime;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "zone_work_time")
public class ZoneWorkTime {


    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField(columnName = "id_zone_calendar")
    private int idCalendar;
    @DatabaseField(columnName = "day_name")
    private int dayCode;
    @DatabaseField(columnName = "time_from")
    private Date timeFrom;
    @DatabaseField(columnName = "time_to")
    private Date timeTo;

    public ZoneWorkTime() {

    }
    public ZoneWorkTime(SimpleZoneWorkTime simpleZoneWorkTime){
        this.id = simpleZoneWorkTime.getId();
        this.idCalendar = simpleZoneWorkTime.getIdCalendar();
        this.dayCode = simpleZoneWorkTime.getDay();

        try {
            this.timeFrom = DatabaseManager.timeFormatter.parse(simpleZoneWorkTime.getTimeFrom());
            this.timeTo = DatabaseManager.timeFormatter.parse(simpleZoneWorkTime.getTimeTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public ZoneWorkTime(int id, int idCalendar, int day, Date timeFrom, Date timeTo) {
        this.id = id;
        this.idCalendar = idCalendar;
        this.dayCode = day;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCalendar() {
        return idCalendar;
    }

    public void setIdCalendar(int idCalendar) {
        this.idCalendar = idCalendar;
    }

    public int getDayCode() {
        return dayCode;
    }

    public void setDayCode(int dayCode) {
        this.dayCode = dayCode;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }
}