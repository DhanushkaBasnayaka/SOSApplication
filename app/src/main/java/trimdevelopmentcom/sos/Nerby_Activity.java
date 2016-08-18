package trimdevelopmentcom.sos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class Nerby_Activity extends Fragment implements OnMapReadyCallback , View.OnClickListener {

    private GoogleMap mMap;

            ImageView plice,hospitel,bank;
    String longitude,latitude;
    GlobalClass globalvariable;
    DatabaseHandler db;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        globalvariable = (GlobalClass) getActivity().getApplicationContext();
        longitude =    globalvariable.getLongitude();
        latitude = globalvariable.getLatitude();

        rootView = inflater.inflate(R.layout.activity_nerby, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map2);

        mapFragment.getMapAsync(this);


        db = new DatabaseHandler(getActivity());


        plice=(ImageView) rootView.findViewById(R.id.plice);
        hospitel=(ImageView)rootView. findViewById(R.id.hospitel);
        bank=(ImageView) rootView.findViewById(R.id.bank);

        plice.setOnClickListener(this);
        hospitel.setOnClickListener(this);
        bank.setOnClickListener(this);




if (!latitude.equalsIgnoreCase("0")||!latitude.equalsIgnoreCase(null)){

    ConnectivityManager connectivityManager
            = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();



   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

   }else{
       plice.setVisibility(View.GONE);
       hospitel.setVisibility(View.GONE);
       bank.setVisibility(View.GONE);



   }

}else{
    System.out.println("location_dhanuska = "+ latitude +longitude);


}


        return rootView;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType( GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setTrafficEnabled( true );
//        setUpMap();


        LatLng sydney = new LatLng(Double.valueOf(latitude),Double.valueOf(longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        setUpMap();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {



            case R.id.plice:
               // near_place("police", "52.3742225", "-1.8853101");

                Intent Data_list = new Intent(getActivity(), Data_list.class);

                Data_list.putExtra("latitude", latitude);
                Data_list.putExtra("longitude", longitude);
                Data_list.putExtra("type", "police");
                startActivity(Data_list);
                break;
            case R.id.hospitel:
                //near_place("hospitel", "52.3742225", "-1.8853101");
                Intent hospitel = new Intent(getActivity(), Data_list.class);
                hospitel.putExtra("latitude", latitude);
                hospitel.putExtra("longitude", longitude);
                hospitel.putExtra("type", "hospital");
                startActivity(hospitel);
                break;
            case R.id.bank:
               // near_place("All", "52.3742225", "-1.8853101");
                Intent bank = new Intent(getActivity(), Data_list.class);
                bank.putExtra("latitude", latitude);
                bank.putExtra("longitude", longitude);
                bank.putExtra("type", "bank");
                startActivity(bank);
                break;

        }



    }




    private void setUpMap() {

        for(int i=0; Data_coltrola.places_data.size()>i;i++){

            final View marker = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_friend, null);
            ImageView uer_imag = (ImageView) marker.findViewById(R.id.num_txt);
            Picasso.with(getActivity()).load(Data_coltrola.places_data.get(i).getIcon()).into(uer_imag);
            double lat_get=Data_coltrola.places_data.get(i).getLat();
            double lon_get=Data_coltrola.places_data.get(i).getLon();
            String name=Data_coltrola.places_data.get(i).getPlace_name();
            final double lat = lat_get;// this will contain "Fruit"
            final double lon = lon_get;

            System.out.println("click get one ");
            marker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("click get one ");


                }
            });
            final LatLng markerLatLng = new LatLng(lat,lon);
            Marker customMarker = mMap.addMarker(new MarkerOptions()
                    .position(markerLatLng)
                    .title(name)
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    System.out.println("onMarkerClick");

                    //showFilterPopup(lat, lon);
                    return false;
                }


            });


            final View mapView = getChildFragmentManager().findFragmentById(R.id.map2).getView();
            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    // We check which build version we are using.
                    @Override
                    public void onGlobalLayout() {
                        LatLngBounds bounds = new LatLngBounds.Builder().include(markerLatLng).build();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            // mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            /// mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        // mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(markerLatLng, 20);
//                    map.animateCamera(cameraUpdate);
                    }
                });
            }}


    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }





}
