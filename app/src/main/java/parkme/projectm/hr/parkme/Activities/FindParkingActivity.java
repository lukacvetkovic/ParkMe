package parkme.projectm.hr.parkme.Activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingLot;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Cveki on 8.3.2015..
 */
public class FindParkingActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private final String TAG = "MyAwesomeApp";

    private TextView mLocationView;

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    private PrefsHelper prefsHelper;
    private double myCarLat;
    private double myCarLng;

    private ImageButton showMyCarLocationButton;
    private LatLng myCarPosition = null;

    private List<ParkingLot> parkingLots;

    private Map<Marker, Integer> mapParkingLotId;

    DatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        DatabaseManager.init(getBaseContext());
        databaseManager = DatabaseManager.getInstance();

        showMyCarLocationButton = (ImageButton) findViewById(R.id.btnShowMeMyCar);
        showMyCarLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myCarPosition != null){
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(myCarPosition)
                            .zoom(15)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });

        mapParkingLotId = new HashMap<>();

        /*mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {

                MarkerOptions newMarker = new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude)).title("Novi");
                mMap.addMarker(newMarker);
            }
        });*/

        prefsHelper = new PrefsHelper(this);
        String carLatString = prefsHelper.getString(PrefsHelper.carLat, null);
        String carLngString = prefsHelper.getString(PrefsHelper.carLng, null);

        try {
            if (carLatString != null && carLngString != null) {
                myCarLat = Double.valueOf(carLatString);
                myCarLng = Double.valueOf(carLngString);

                myCarPosition = new LatLng(myCarLat, myCarLng);

                MarkerOptions newMarker = new MarkerOptions()
                        .position(myCarPosition)
                        .title("Auto")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_location_icon));
                mMap.addMarker(newMarker);
            }
        }
        catch (Exception e){
            Log.w(TAG, "E nema auta");
        }

        getAllMarkers();

        putMarkersToMap(parkingLots);
    }

    private void putMarkersToMap(final List<ParkingLot> parkingLots) {

        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.parking_maps_icon);

        for (ParkingLot parkingLot : parkingLots) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(parkingLot.getLat(), parkingLot.getLng()))
                    .title(parkingLot.getName())
                    .snippet(parkingLot.getAdress())
                    .icon(markerIcon));

            mapParkingLotId.put(marker, parkingLot.getId());

        }

    }

    private void getAllMarkers() {
        Log.d("Pokupio sam", "parkiralista");
        parkingLots = databaseManager.getAllParkingLots();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();

            }
        }


    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            Log.d("DOSO SAM", "DOSO");
            LatLng myLocation = new LatLng(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

        }

        Log.d("DOSO SAM KAN", "DOSO");
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d("FAIL", "SUSFAIL");
    }

    @Override
    public void onDisconnected() {
        Log.d("FAIL", "DISFAIL");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("FAIL", "CONFAIL");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

}
