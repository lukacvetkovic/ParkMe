package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
public class SimpleZoneWorkTime {


    private int id;
    private int id_zone_calendar;
    private int day_name;
    private String time_from;
    private String time_to;


    public SimpleZoneWorkTime(int id, int idCalendar, int day, String timeFrom, String timeTo) {
        this.id = id;
        this.id_zone_calendar = idCalendar;
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

    public int getIdCalendar() {
        return id_zone_calendar;
    }

    public void setIdCalendar(int id_calendar) {
        this.id_zone_calendar = id_calendar;
    }

    public int getDay() {
        return day_name;
    }

    public void setDay(int day) {
        this.day_name = day;
    }

    public String getTimeFrom() {
        return time_from;
    }

    public void setTimeFrom(String timeFrom) {
        this.time_from = timeFrom;
    }

    public String getTimeTo() {
        return time_to;
    }

    public void setTimeTo(String timeTo) {
        this.time_to = timeTo;
    }
}