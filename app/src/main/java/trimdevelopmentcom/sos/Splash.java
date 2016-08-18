package trimdevelopmentcom.sos;

import android.*;
import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.location.Address;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;


public class Splash extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 4;

    GlobalClass globalvariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        globalvariable = (GlobalClass) getApplicationContext();
        Data_coltrola.Embece_data.clear();
        Data_coltrola.Embece_data_filter.clear();
        buildGoogleApiClient();
        populateSpinner();


    }

    private void populateSpinner() {
        mGoogleApiClient.connect();

    }


    private void goto_next() {
        DatabaseHandler db = new DatabaseHandler(Splash.this);
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
//            if(!gps_enabled) {
            Intent intent = new Intent(Splash.this, Home_page.class);
            finish();
            startActivity(intent);

        } else {

            Intent intent = new Intent(Splash.this, SetingActivity.class);
            finish();
            startActivity(intent);

        }
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApiIfAvailable(LocationServices.API)
                .build();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                Toast.makeText(this, String.valueOf(mLastLocation.getLatitude()) + "Longitude: " +
                        String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();

                mLastLocation.getTime();

                globalvariable.setLatitude(String.valueOf(mLastLocation.getLatitude()));
                globalvariable.setLongitude(String.valueOf(mLastLocation.getLongitude()));

                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
                    get_adres(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                    Log.e("log","4");
                }else{

                    Log.e("log","5");
                    SharedPreferences prefs2 = getSharedPreferences("currency", MODE_WORLD_READABLE);

                    String  country_defolt2 = String.valueOf(prefs2.getString("cuntry_name", " "));
                    String  tp_cord_curent = String.valueOf(prefs2.getString("tp_cord_curent2", " "));
                    Log.e("country_defolt2",country_defolt2);

                    globalvariable.setDefolt_cuntry(country_defolt2);
                    globalvariable.setCounry_cord("0");
                    globalvariable.setAddres("not Addres");
                    goto_next();
                }


            } else {

                SharedPreferences prefs2 = getSharedPreferences("currency", MODE_WORLD_READABLE);

                String  country_defolt2 = String.valueOf(prefs2.getString("cuntry_name", " "));
                String  tp_cord_curent = String.valueOf(prefs2.getString("tp_cord_curent2", " "));
                Log.e("country_defolt2",country_defolt2);

                globalvariable.setDefolt_cuntry(country_defolt2);
                globalvariable.setCounry_cord("0");
                globalvariable.setAddres("not Addres");
                globalvariable.setLatitude("0");
                globalvariable.setLongitude("0");
                goto_next();

                Toast.makeText(this, "no_location_detected", Toast.LENGTH_LONG).show();
            }

            }


    private void get_adres(double latitude, double longitude) {

                    Log.e("addres", " "+latitude);

                    Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
                    StringBuilder builder = new StringBuilder();
                    Log.e("addres2", " "+latitude);

                    try {
                        List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
                        int maxLines = address.get(0).getMaxAddressLineIndex();

                        for (int i = 0; i < maxLines; i++) {
                            String addressStr = address.get(0).getAddressLine(i);
                        String Countr_cord2 = address.get(0).getCountryName();

                            builder.append(addressStr+" "+Countr_cord2);
                            builder.append(" ");
                        }
                        String Countr_cord = address.get(0).getCountryCode();

                        String fnialAddress = builder.toString(); //This is the complete address.

                        String countryName = address.get(0).getCountryName();
                        globalvariable.setDefolt_cuntry(countryName);

                        globalvariable.setCounry_cord(Countr_cord);
                        globalvariable.setAddres(fnialAddress);


                        goto_next();

                    } catch (IOException e) {
                        // Handle IOException
                    } catch (NullPointerException e) {
                        // Handle NullPointerException
                    }
        goto_next();

                }



    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    marshmallowPermissionsFlowOnConnected();
                    System.out.println();
                    System.out.println("masmalose"+"this application");

                }else {
                    getLocation();
                    System.out.println("masmalose"+"this not masmelose");
                }




    }

    @TargetApi(Build.VERSION_CODES.M)
    private void marshmallowPermissionsFlowOnConnected() {


        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explain to the user why we need to read the contacts
                Toast.makeText(this,"We need permission to ger location to be able to show\n" +
                        "        location.", Toast.LENGTH_LONG).show();
            }
//            getLocation();

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        marshmallowonRequestPermissionsResult(requestCode, grantResults);
    }

    private void marshmallowonRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    getLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "functionality that depends on this permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}