package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "zone_work_time")
public class ZoneWorkTime {
    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int idCalendar;
    @DatabaseField
    private Date day;
    @DatabaseField
    private Date timeFrom;
    @DatabaseField
    private Date timeTo;

    public ZoneWorkTime() {

    }

    public ZoneWorkTime(int id, int idCalendar, Date day, Date timeFrom, Date timeTo) {
        this.id = id;
        this.idCalendar = idCalendar;
        this.day = day;
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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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