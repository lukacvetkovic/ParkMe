package parkme.projectm.hr.parkme.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import parkme.projectm.hr.parkme.Activities.FragmentMenuActivity;
import parkme.projectm.hr.parkme.R;

public class ActiveParkingService extends Service {

    private final String TAG = "ActiveParkingService";
    private final IBinder binder = new LocalBinder();
    private static final int SERVICE_ID = 1980; // PAC-MAN ! :D

    public static String SERVICE_ACTION_START = "service_action_start";
    private static String SERVICE_ACTION_EXTEND_PARKING = "service_action_extend_parking";
    private static String SERVICE_ACTION_MUTE_NOTIFICATION = "service_action_mute_notification";
    public static String SERVICE_ACTION_STOP = "service_action_stop";

    private boolean didTicketAspire;
    // TODO dodat jos koji action ako se sjetis

    private boolean isRunning = false;
    private long remainingParkingMinutes = 42;

    private CountDownTimer remainigTimeCounter;

    /**
     * @return - Remaining parking time in minutes
     */
    public long getRemainingTicketTime(){
        return remainingParkingMinutes;
    }

    /**
     *
     * @param time - parking time available in minutes;
     */
    public void setRemainingTimeCounter(long time){
        remainingParkingMinutes = time;
        long parkingMiliseconds = time * 60 * 1000;
        didTicketAspire = false;
        /*remainigTimeCounter = new CountDownTimer(parkingMiliseconds, 60 * 1000) {      // TODO - ova je da svaku minutu otkuca i radi kak se spada
            @Override
            public void onTick(long l) {
                remainingParkingMinutes -= 1;
                // todo provjera dal je ostalo jos 5-10 min da se baci notifikacija neka ;)
            }

            @Override
            public void onFinish() {
                // TODO napravit alarm da je istekla parkirna karta ako nije muteana notifikacija :D
            }
        }.start();*/

        remainigTimeCounter = new CountDownTimer(time * 1000, 1000) {        // todo - debug da otkucava svaku sekundu i postavlja sekunde a ne minute
            @Override
            public void onTick(long l) {
                remainingParkingMinutes = remainingParkingMinutes - 1;
                Log.i(TAG, "TICK");
                if(remainingParkingMinutes == 0){
                    remainigTimeCounter.onFinish();
                }
                else if(remainingParkingMinutes == 5){
                    buildNotification(false);
                }
            }

            @Override
            public void onFinish() {
                didTicketAspire = true;
                buildNotification(true);
                stopForegroundService();
                // TODO napravit alarm da je istekla parkirna karta ako nije muteana notifikacija :D
            }
        }.start();
    }

    private void buildNotification(boolean didTicketExpire){
        Notification notification;

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent notificationIntent = new Intent(ActiveParkingService.this, FragmentMenuActivity.class);
        notificationIntent.putExtra(FragmentMenuActivity.INTENT_FROM_SERVICE_NOTIFICATION, true);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        if(didTicketExpire == false) {
            notification = builder.setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.parky_plus_icon).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("Your parking ticket is about to expire !")
                    .setContentText("Do you want to extend your parking ticket ?")
                    .build();
        }
        else{
            notification = builder.setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.parky_plus_icon).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("Your parking ticket EXPIRED !")
                    //.setContentText("Do you want to extend your parking ticket ?")
                    .build();
        }
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(0, notification);
    }

    private void stopForegroundService(){
        Log.i(TAG, "Stopping service");
        this.isRunning = false;
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        if(intent != null) {
            if (!isRunning && SERVICE_ACTION_START.equals(intent.getAction())){
                Intent notificationIntent = new Intent(this, FragmentMenuActivity.class);   // Klikom na notification idemo na Fragmente
                notificationIntent.putExtra(FragmentMenuActivity.INTENT_FROM_SERVICE_NOTIFICATION, true);
                notificationIntent.setAction(SERVICE_ACTION_START);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                        notificationIntent, 0);

                Intent extendParkingIntent = new Intent(this, ActiveParkingService.class);
                extendParkingIntent.setAction(SERVICE_ACTION_EXTEND_PARKING);
                PendingIntent pExtendParkingIntent = PendingIntent.getService(this, 0,
                        extendParkingIntent, 0);

                Intent muteNotificationIntent = new Intent(this, ActiveParkingService.class);
                muteNotificationIntent.setAction(SERVICE_ACTION_MUTE_NOTIFICATION);
                PendingIntent pMuteNotificationIntent = PendingIntent.getService(this, 0,
                        muteNotificationIntent, 0);

                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.parky_novac_icon);

                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("Active parking ticket")        // todo - odhardkodat ovo tu
                        .setTicker("Parking ticket started")
                        .setContentText("You have active parking ticket")
                        .setSmallIcon(R.drawable.parky_novac_icon)
                        .setLargeIcon(
                                Bitmap.createScaledBitmap(icon, 128, 128, false))
                        .setContentIntent(pendingIntent)
                        .setOngoing(true)
                        .addAction(android.R.drawable.ic_media_next,
                                "Extend parking", pExtendParkingIntent)
                        .addAction(android.R.drawable.ic_menu_info_details, "Mute notification",
                                pMuteNotificationIntent)
                        .build();
                startForeground(SERVICE_ID, notification);
                this.isRunning = true;
            }
            else if (SERVICE_ACTION_EXTEND_PARKING.equals(intent.getAction())){
                Log.i(TAG, "Extending parking ticket");
                // todo extend parking here
            }
            else if (SERVICE_ACTION_MUTE_NOTIFICATION.equals(intent.getAction())){
                Log.i(TAG, "Muting - Unmuting notification sound");
                // todo mute - unmute notification
            }
            else if (SERVICE_ACTION_STOP.equals(intent.getAction())) {
                Log.i(TAG, "Stopping service if it is running");
                if(this.isRunning) {
                    Log.i(TAG, "Stopping service");
                    this.isRunning = false;
                    stopForeground(true);
                    stopSelf();
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public ActiveParkingService getService() {
            return ActiveParkingService.this;
        }
    }
}
