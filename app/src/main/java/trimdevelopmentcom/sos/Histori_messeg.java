package trimdevelopmentcom.sos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Histori_messeg extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private LayoutInflater inflater;
    DatabaseHandler db;
    GoogleMap map;
    String lon, lat;
    private LatLng markerLatLng;
    CustomListAdapter_Rides adapter_pro;
    private GoogleApiClient client;
    List<Obj_Messag> messg_hist;

    GlobalClass globalvariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_messeg);
        db = new DatabaseHandler(Histori_messeg.this);

        globalvariable = (GlobalClass) Histori_messeg.this.getApplicationContext();

        messg_hist = db.Read_messeg();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toobar_title = (TextView) toolbar.findViewById(R.id.name_toll2);
        //TextView name_toll2 = (TextView) findViewById(R.id.name_toll2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toobar_title.setText("Message History");

        TextView setting = (TextView) toolbar.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Count_down = new Intent(Histori_messeg.this, SetingActivity.class);

                startActivity(Count_down);

            }
        });
        TextView clear_history = (TextView) findViewById(R.id.textView14);

        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteContact();
//                for (Obj_Messag todo : messg_hist) {
//                    System.out.println("todo.getId()"+todo.getPush_date_gold());
//
//
//                    }
                Data_coltrola.Embece_mesag.clear();
                finish();

            }
        });

//        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
//                .findFragmentById(R.id.map2);
        FragmentManager myFM = getSupportFragmentManager();
        SupportMapFragment myMAPF = (SupportMapFragment) myFM
                .findFragmentById(R.id.map_hist);
        myMAPF.getMapAsync(this);


//        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_hist)).getMap();

        System.out.println("waitids" + messg_hist.size());
        if (messg_hist.size() > 0) {

            String prefix = "";
            for (Obj_Messag todo : messg_hist) {
                Obj_Messag itam = new Obj_Messag();
                itam.setPush_messeg_two_gold(todo.getPush_messeg_two_gold());
                itam.setPush_messeg_one_gold(todo.getPush_messeg_one_gold());
                itam.setPush_email_one_gold(todo.getPush_email_one_gold());
                itam.setPush_email_two_gold(todo.getPush_email_two_gold());
                itam.setPush_date_gold(todo.getPush_date_gold());
                itam.setPush_location_gold(todo.getPush_location_gold());
                itam.setPush_addres_gold(todo.getPush_addres_gold());
                itam.setMsg_stetes(todo.getMsg_stetes());
                Data_coltrola.Embece_mesag.add(itam);

                System.out.println("todo" + todo.getPush_messeg_one_gold());
            }

        } else {

            System.out.println("emty");
        }


        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListView list_messeg = (ListView) findViewById(R.id.listView3);
        TextView textView14 = (TextView) findViewById(R.id.textView14);


        list_messeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String location = Data_coltrola.Embece_mesag.get(position).getPush_location_gold();
                if (!location.equalsIgnoreCase("0.0,0.0")) {
                    if (!location.equalsIgnoreCase(null)) {
                        StringTokenizer tokens = new StringTokenizer(location, ",");
                        String lat = tokens.nextToken();// this will contain "Fruit"
                        String lon = tokens.nextToken();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)), 15);
                        map.animateCamera(cameraUpdate);

                    }
                }


            }
        });
//
        adapter_pro = new CustomListAdapter_Rides(Histori_messeg.this, Data_coltrola.Embece_mesag);
        list_messeg.setAdapter(adapter_pro);


        for (int i = 0; Data_coltrola.Embece_mesag.size() > i; i++) {
            String lat_get = Data_coltrola.Embece_mesag.get(i).getPush_location_gold();
            //  String name = Data_coltrola.Embece_mesag.get(1).getPush_email_one_gold();
            if (!lat_get.equalsIgnoreCase("0.0,0.0")) {
                StringTokenizer tokens = new StringTokenizer(lat_get, ",");

                lat = tokens.nextToken();// this will contain "Fruit"
                lon = tokens.nextToken();
                System.out.println("lat_get" + lat_get);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(lat), Double.valueOf(lon)), 15);
//                map.animateCamera(cameraUpdate);
                break;

            }


        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Histori_messeg Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://trimdevelopmentcom.sos/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Histori_messeg Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://trimdevelopmentcom.sos/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setUpMap();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        map.setMyLocationEnabled(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(  globalvariable.getLatitude()), Double.parseDouble(globalvariable.getLongitude())), 15);
        map.animateCamera(cameraUpdate);



    }


    public class CustomListAdapter_Rides extends BaseAdapter {
        private Activity activity;
        private List<Obj_Messag> movieItems_pro;


        public CustomListAdapter_Rides(Activity activity, ArrayList<Obj_Messag> movieItems_pro) {
            this.activity = activity;
            this.movieItems_pro = movieItems_pro;
        }

        @Override
        public int getCount() {
            return this.movieItems_pro.size();
        }

        @Override
        public Object getItem(int location) {
            return movieItems_pro.get(location);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null)

                convertView = inflater.inflate(R.layout.sell_messeg, null);
            adapter_pro.notifyDataSetChanged();
            TextView date_send = (TextView) convertView.findViewById(R.id.date_send);
            TextView send_email1 = (TextView) convertView.findViewById(R.id.send_email1);
            TextView send_email2 = (TextView) convertView.findViewById(R.id.send_email2);
            TextView send_tp_messeg = (TextView) convertView.findViewById(R.id.send_tp_messeg);
            TextView send_tp_messeg2 = (TextView) convertView.findViewById(R.id.send_tp_messeg2);
            TextView send_Addres = (TextView) convertView.findViewById(R.id.send_Addres);

//            send_tp_messeg2.setVisibility(View.VISIBLE);

            final Obj_Messag m = movieItems_pro.get(position);
            if (m.getPush_messeg_two_gold().length() > 7) {

                send_tp_messeg2.setVisibility(View.VISIBLE);
                date_send.setText(m.getPush_date_gold());
                send_email1.setText("Push to" + " " + m.getPush_email_one_gold() + " " + " Sent");
                send_email2.setText("Push to" + " " + m.getPush_email_two_gold() + " " + " Sent");
                send_tp_messeg.setText("SMS to" + " " + m.getPush_messeg_one_gold() + " " + " Sent");
                send_tp_messeg2.setText("SMS to" + " " + m.getPush_messeg_two_gold() + " " + " Sent");
                send_Addres.setText(m.getPush_addres_gold());
                System.out.println("m.getPush_addres_gold()"+m.getPush_addres_gold());
            } else {

                send_tp_messeg2.setVisibility(View.GONE);
                date_send.setText(m.getPush_date_gold());
                send_email1.setText("Push to" + " " + m.getPush_email_one_gold() + " " + " Sent");
                send_email2.setText("Push to" + " " + m.getPush_email_two_gold() + " " + " Sent");
                send_tp_messeg.setText("SMS to" + " " + m.getPush_messeg_one_gold() + " " + " Sent");
                //send_tp_messeg2.setText("SMS to"+" "+m.getPush_messeg_two_gold()+" "+m.getMsg_stetes());
                send_Addres.setText(m.getPush_addres_gold());
                System.out.println("m.getPush_addres_gold()"+m.getPush_addres_gold());
            }


//            shoping_moted_get = (m.getshipping_methods());

//
            return convertView;
        }

    }



    private void setUpMap() {
        for(int i=0; Data_coltrola.Embece_mesag.size()>i;i++){

            final View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sell_histori, null);

            String lat_get = Data_coltrola.Embece_mesag.get(i).getPush_location_gold();
            String name = Data_coltrola.Embece_mesag.get(i).getPush_email_one_gold();
            String tp = Data_coltrola.Embece_mesag.get(i).getPush_email_one_gold();

            if ( !lat_get.equalsIgnoreCase("0.0,0.0")){
                if (!lat_get.equalsIgnoreCase(null) ) {
                    StringTokenizer tokens = new StringTokenizer(lat_get, ",");
                    final String lat = tokens.nextToken();// this will contain "Fruit"
                    final String lon = tokens.nextToken();

                    System.out.println("click get one ");
                    marker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("click get one ");


                        }
                    });
                    markerLatLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                    Marker customMarker = map.addMarker(new MarkerOptions()
                            .position(markerLatLng)
                            .title(name+"<br/>"+tp)
                            .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(Histori_messeg.this, marker))));

                }
            }}


            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    System.out.println("onMarkerClick");

//                    showFilterPopup(lat, lon);
                    return false;
                }


            });


            final View mapView = getSupportFragmentManager().findFragmentById(R.id.map_hist).getView();
            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    // We check which build version we are using.
                    @Override
                    public void onGlobalLayout() {
                      //  LatLngBounds bounds = new LatLngBounds.Builder().include(markerLatLng).build();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_calander, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        {
            int id = item.getItemId();
            finish();


        }

        return super.onOptionsItemSelected(item);
    }


}