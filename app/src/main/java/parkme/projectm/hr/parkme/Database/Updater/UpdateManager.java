package parkme.projectm.hr.parkme.Database.Updater;

import java.util.Date;
import java.util.List;

import parkme.projectm.hr.parkme.Database.Tables;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
public class UpdateManager {

    private  Updater updater;
    private UpdateSource source;

    public UpdateManager(Updater updater, UpdateSource source){
        this.updater = updater;
        this.source = source;
    }

    public void update(){
        for(Tables table :Tables.values()){
            updater.updateTable(table, source.getNewTableRows(table, new Date()));
        }
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }

    public void setSource(UpdateSource source) {
        this.source = source;
    }

    public Updater getUpdater() {
        return updater;
    }

    public UpdateSource getSource() {
        return source;
    }
}
