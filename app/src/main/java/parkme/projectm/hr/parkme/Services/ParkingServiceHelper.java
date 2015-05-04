package parkme.projectm.hr.parkme.Services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Mihael on 4.5.2015..
 */
public class ParkingServiceHelper {

    private static final String TAG = "ParkingServiceHelper";
    private static final int REQUEST_GET_STATS = 1;
    private static final int REQUEST_SET_PARKING_TIME = 2;
    private static final int REQUEST_NONE = -1;
    private static int serviceRequestStatus = 0;

    public static int SERVICE_IS_NOT_RUNNING = -6;

    private static Context staticContext;

    private static ActiveParkingService parkingService;

    private boolean isRunning;

    private static long timeToSet;


    public interface ParkingServiceListener {
        void serviceStatus(ServiceStatus status);
    }

    private static ParkingServiceListener serviceListener;

    public ParkingServiceListener getServiceListener() {
        return serviceListener;
    }

    public void setServiceListener(ParkingServiceListener serviceListener) {
        this.serviceListener = serviceListener;
    }

    /**
     * Returns ServiceStatus object via ParkingServiceListeners serviceStatus method
     */
    public void getServiceStatus(Context context) {
        staticContext = context;
        serviceRequestStatus = REQUEST_GET_STATS;
        if(isRunning){
            Log.i(TAG, "Binding service");
            Intent i1 = new Intent(staticContext, ActiveParkingService.class);
            context.bindService(i1, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        else{
            if(serviceListener != null){
                serviceListener.serviceStatus(new ServiceStatus(SERVICE_IS_NOT_RUNNING));
            }
        }
    }

    public void setActiveParkingTime(long time, Context context){
        staticContext = context;
        serviceRequestStatus = REQUEST_SET_PARKING_TIME;
        if(isRunning){
            Log.i(TAG, "Binding service");
            timeToSet = time;
            Intent i1 = new Intent(staticContext, ActiveParkingService.class);
            context.bindService(i1, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void startService(Context context){
        staticContext = context;    // TODO
        Intent i = new Intent(staticContext, ActiveParkingService.class);
        i.setAction(ActiveParkingService.SERVICE_ACTION_START);
        isRunning = true;
        staticContext.startService(i);
    }

    public void stopService(Context context){
        staticContext = context;
        Intent i = new Intent(staticContext, ActiveParkingService.class);
        i.setAction(ActiveParkingService.SERVICE_ACTION_STOP);
        isRunning = false;
        staticContext.startService(i);
    }

    private static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i(TAG, "Connecting service");
            parkingService = ((ActiveParkingService.LocalBinder)service).getService();
            if(parkingService != null){
                switch(serviceRequestStatus){
                    case REQUEST_GET_STATS:
                        Log.i(TAG, "Service request - GET_STATS");
                        serviceRequestStatus = REQUEST_NONE;
                        long remainingTime = parkingService.getRemainingTicketTime();
                        Log.i(TAG, "unbinding service");
                        try {
                            staticContext.unbindService(serviceConnection);
                        }
                        catch (Exception e){
                            Log.w(TAG, "Unbinding service failed !");
                        }
                        if(serviceListener != null){
                            serviceListener.serviceStatus(new ServiceStatus(remainingTime));
                        }
                        break;
                    case REQUEST_SET_PARKING_TIME:
                        Log.i(TAG, "Service request - SET_PARKING_TIME");
                        serviceRequestStatus = REQUEST_NONE;
                        parkingService.setRemainingTimeCounter(timeToSet);
                        Log.i(TAG, "unbinding service");
                        try {
                            staticContext.unbindService(serviceConnection);
                        }
                        catch (Exception e){
                            Log.w(TAG, "Unbinding service failed !");
                        }
                        break;
                    default:
                        Log.w(TAG, "Unsupported service request -> " + serviceRequestStatus);
                        Log.i(TAG, "unbinding service");
                        try {
                            staticContext.unbindService(serviceConnection);
                        }
                        catch (Exception e){
                            Log.w(TAG, "Unbinding service failed !");
                        }
                        break;
                }
            }
            else{
                Log.e(TAG, "Service is null -> in onServiceConnected");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "Disconnected service");
            parkingService = null;
        }
    };

    public static class ServiceStatus {
        private long activeParkingRemainingTime;

        public ServiceStatus(long activeParkingRemainingTime) {
            this.activeParkingRemainingTime = activeParkingRemainingTime;
        }

        public long getActiveParkingRemainingTime() {
            return activeParkingRemainingTime;
        }

        public void setActiveParkingRemainingTime(long activeParkingRemainingTime) {
            this.activeParkingRemainingTime = activeParkingRemainingTime;
        }
    }

}
