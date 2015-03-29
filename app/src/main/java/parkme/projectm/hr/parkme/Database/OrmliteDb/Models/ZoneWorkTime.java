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
    private int id_calendar;
    @DatabaseField
    private Date day_name;
    @DatabaseField
    private Date time_from;
    @DatabaseField
    private Date time_to;

    public ZoneWorkTime() {

    }

    public ZoneWorkTime(int id, int idCalendar, Date day, Date timeFrom, Date timeTo) {
        this.id = id;
        this.id_calendar = idCalendar;
        this.day_name = day;
        this.time_from = timeFrom;
        this.time_to = timeTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_calendar() {
        return id_calendar;
    }

    public void setId_calendar(int id_calendar) {
        this.id_calendar = id_calendar;
    }

    public Date getDay() {
        return day_name;
    }

    public void setDay(Date day) {
        this.day_name = day;
    }

    public Date getTime_from() {
        return time_from;
    }

    public void setTime_from(Date time_from) {
        this.time_from = time_from;
    }

    public Date getTimeTo() {
        return time_to;
    }

    public void setTimeTo(Date timeTo) {
        this.time_to = timeTo;
    }
}