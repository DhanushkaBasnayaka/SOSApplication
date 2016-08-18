package trimdevelopmentcom.sos;

import android.*;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import trimdevelopmentcom.sos.Sever_task.Communicator;

public class Home_page extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient client;
    TeleportClient.OnSyncDataItemTask mOnSyncDataItemTask;
    TeleportClient.OnGetMessageTask mMessageTask;
    AppLocationService appLocationService;
    TeleportClient mTeleportClient;
    GlobalClass globalvariable;
    DatabaseHandler db;
    Communicator communicator;
    TextView toobar_title;

    String jsonmenues_stored;
    File myInternalFile;
    File directory;
    Toolbar  toolbar;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT_LOCATION = 3;
    TextView map, embce, sos, notifi, add;
    String SOS_E_mail, SOS_E_mail2,country_defolt2, sos_phone,country_defolt, email, sos_Phone2, Sos_countdown_masseg, pin, sos_phone_coud, sos_phone_coud2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        globalvariable = (GlobalClass) getApplicationContext();

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toobar_title = (TextView) toolbar.findViewById(R.id.name_toll);
        TextView setting = (TextView) toolbar.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Count_down = new Intent(Home_page.this, SetingActivity.class);

                startActivity(Count_down);

            }
        });



        toobar_title.setText(globalvariable.getDefolt_cuntry());



        communicator = new Communicator();
        db = new DatabaseHandler(Home_page.this);
        Cursor c = db.getAllContacts();

        if (c.moveToFirst()) {

            email = c.getString(3);
            sos_phone = c.getString(4);
            sos_phone_coud = c.getString(7);
            sos_phone_coud2 = c.getString(8);
            SOS_E_mail = c.getString(9);
            SOS_E_mail2 = c.getString(10);
            sos_Phone2 = c.getString(5);
            Sos_countdown_masseg = c.getString(11);
            pin = c.getString(13);
            country_defolt = c.getString(14);

        }

        db.close();

          // Googole wach application
        mTeleportClient = new TeleportClient(Home_page.this);

        mOnSyncDataItemTask = new ShowToastOnSyncDataItemTask();
        mMessageTask = new ShowToastFromOnGetMessageTask();
        mTeleportClient.setOnGetMessageTask(mMessageTask);

//        globalvariable.setDefolt_cuntry(country);

        map = (TextView) findViewById(R.id.map);
        embce = (TextView) findViewById(R.id.embce);
        sos = (TextView) findViewById(R.id.sos);
        notifi = (TextView) findViewById(R.id.notifi);
        add = (TextView) findViewById(R.id.add);
        map.setOnClickListener(this);
        sos.setOnClickListener(this);
        embce.setOnClickListener(this);
        notifi.setOnClickListener(this);
        add.setOnClickListener(this);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        if (db.getCount_notification() > 0) {
            int number = db.getCount_notification();
            frameLayout.setVisibility(View.VISIBLE);
            TextView frame = (TextView) findViewById(R.id.total_wishitems);
            frame.setText("" + number);

        } else {
            frameLayout.setVisibility(View.GONE);
        }



        //sos.setCompoundDrawables(null, getResources().getDrawable(R.drawable.help_clic), null, null);
        Drawable img2 = getApplication().getResources().getDrawable(R.drawable.help_clic);
        sos.setCompoundDrawablesWithIntrinsicBounds(null, img2, null, null);

        MainActivity travelListFragment = new MainActivity();
        Bundle trBundle = new Bundle();
//                trBundle.putSerializable(AppConstance.TRAVEL_LIST, travelLists);
//                travelListFragment.setArguments(trBundle);
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, travelListFragment);
        transaction.commit();




    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        location();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.map:
                Nerby_Activity near = new Nerby_Activity();

                FragmentManager fm_embece_activity_near =getSupportFragmentManager();
                FragmentTransaction transaction_near = fm_embece_activity_near.beginTransaction();
//                transaction_near.setCustomAnimations( R.anim.slide_in_left, 0,0, R.anim.slide_in_left);
                transaction_near.replace(R.id.container, near);
                transaction_near.commit();

                Drawable img2 = getApplication().getResources().getDrawable(R.drawable.map_clic);
                map.setCompoundDrawablesWithIntrinsicBounds(null, img2, null, null);
                Drawable building = getApplication().getResources().getDrawable(R.drawable.building);
                embce.setCompoundDrawablesWithIntrinsicBounds(null, building, null, null);
                Drawable help = getApplication().getResources().getDrawable(R.drawable.help);
                sos.setCompoundDrawablesWithIntrinsicBounds(null, help, null, null);
                Drawable paper = getApplication().getResources().getDrawable(R.drawable.paper);
                notifi.setCompoundDrawablesWithIntrinsicBounds(null, paper, null, null);
                Drawable madical = getApplication().getResources().getDrawable(R.drawable.madical);
                add.setCompoundDrawablesWithIntrinsicBounds(null, madical, null, null);


                toolbar.setVisibility(View.GONE);


                break;
            case R.id.embce:

                toolbar.setVisibility(View.VISIBLE);
                Embece_activity embece_activity = new Embece_activity();
//                Bundle trBundle = new Bundle();
//                trBundle.putSerializable(AppConstance.TRAVEL_LIST, travelLists);
//                travelListFragment.setArguments(trBundle);
                FragmentManager fm_embece_activity =getSupportFragmentManager();
                FragmentTransaction transaction_embece_activity = fm_embece_activity.beginTransaction();
                transaction_embece_activity.replace(R.id.container, embece_activity);
                transaction_embece_activity.commit();

                Drawable img3 = getApplication().getResources().getDrawable(R.drawable.building_clic);
                embce.setCompoundDrawablesWithIntrinsicBounds(null, img3, null, null);
                Drawable map_2 = getApplication().getResources().getDrawable(R.drawable.map);
                map.setCompoundDrawablesWithIntrinsicBounds(null, map_2, null, null);
                Drawable help_e = getApplication().getResources().getDrawable(R.drawable.help);
                sos.setCompoundDrawablesWithIntrinsicBounds(null, help_e, null, null);
                Drawable paper_e = getApplication().getResources().getDrawable(R.drawable.paper);
                notifi.setCompoundDrawablesWithIntrinsicBounds(null, paper_e, null, null);
                Drawable madical_e = getApplication().getResources().getDrawable(R.drawable.madical);
                add.setCompoundDrawablesWithIntrinsicBounds(null, madical_e, null, null);

                break;
            case R.id.sos:
                toolbar.setVisibility(View.VISIBLE);

                Drawable img4 = getApplication().getResources().getDrawable(R.drawable.help_clic);
                sos.setCompoundDrawablesWithIntrinsicBounds(null, img4, null, null);
                Drawable map_s = getApplication().getResources().getDrawable(R.drawable.map);
                map.setCompoundDrawablesWithIntrinsicBounds(null, map_s, null, null);
                Drawable building_s = getApplication().getResources().getDrawable(R.drawable.building);
                embce.setCompoundDrawablesWithIntrinsicBounds(null, building_s, null, null);
                Drawable paper_s = getApplication().getResources().getDrawable(R.drawable.paper);
                notifi.setCompoundDrawablesWithIntrinsicBounds(null, paper_s, null, null);
                Drawable madical_s = getApplication().getResources().getDrawable(R.drawable.madical);
                add.setCompoundDrawablesWithIntrinsicBounds(null, madical_s, null, null);

                MainActivity travelListFragment = new MainActivity();
                Bundle trBundle = new Bundle();
//                trBundle.putSerializable(AppConstance.TRAVEL_LIST, travelLists);
//                travelListFragment.setArguments(trBundle);
                FragmentManager fm =getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                transaction.replace(R.id.container, travelListFragment);
                transaction.commit();

                break;
            case R.id.notifi:
                toolbar.setVisibility(View.VISIBLE);

                Drawable img5 = getApplication().getResources().getDrawable(R.drawable.paper_clic);
                notifi.setCompoundDrawablesWithIntrinsicBounds(null, img5, null, null);
                Drawable map_n = getApplication().getResources().getDrawable(R.drawable.map);
                map.setCompoundDrawablesWithIntrinsicBounds(null, map_n, null, null);
                Drawable building_n = getApplication().getResources().getDrawable(R.drawable.building);
                embce.setCompoundDrawablesWithIntrinsicBounds(null, building_n, null, null);
                Drawable help_n = getApplication().getResources().getDrawable(R.drawable.help);
                sos.setCompoundDrawablesWithIntrinsicBounds(null, help_n, null, null);
                Drawable madical_n = getApplication().getResources().getDrawable(R.drawable.madical);
                add.setCompoundDrawablesWithIntrinsicBounds(null, madical_n, null, null);

                Notification travelListFragment_n = new Notification();

                FragmentManager fm_n =getSupportFragmentManager();
                FragmentTransaction transaction_n = fm_n.beginTransaction();
//                transaction_n.setCustomAnimations( R.anim.slide_in_left, 0, 0, R.anim.slide_out_right);
                transaction_n.replace(R.id.container, travelListFragment_n);
                transaction_n.commit();


                break;
            case R.id.add:
                toolbar.setVisibility(View.VISIBLE);
                Drawable img6 = getApplication().getResources().getDrawable(R.drawable.madical_clic);
                add.setCompoundDrawablesWithIntrinsicBounds(null, img6, null, null);
                Drawable map_a = getApplication().getResources().getDrawable(R.drawable.map);
                map.setCompoundDrawablesWithIntrinsicBounds(null, map_a, null, null);

                Drawable building_a = getApplication().getResources().getDrawable(R.drawable.building);
                embce.setCompoundDrawablesWithIntrinsicBounds(null, building_a, null, null);
                Drawable help_a = getApplication().getResources().getDrawable(R.drawable.help);
                sos.setCompoundDrawablesWithIntrinsicBounds(null, help_a, null, null);
                Drawable paper_a = getApplication().getResources().getDrawable(R.drawable.paper);
                notifi.setCompoundDrawablesWithIntrinsicBounds(null, paper_a, null, null);


                Frectio_advice travelListFragment_f = new Frectio_advice();
                FragmentManager fm_f =getSupportFragmentManager();
                FragmentTransaction transaction_f = fm_f.beginTransaction();
//                transaction_f.setCustomAnimations( R.anim.slide_in_left, 0, 0, R.anim.slide_out_right);
                transaction_f.replace(R.id.container, travelListFragment_f);
                transaction_f.commit();

//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations( R.anim.slide_in_left, 0, 0, R.anim.slide_out_right)
//
//                        .commit();

                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Task to show the String from DataMap with key "string" when a DataItem is synced
    public class ShowToastOnSyncDataItemTask extends TeleportClient.OnSyncDataItemTask {

        protected void onPostExecute(DataMap dataMap) {

            String s = dataMap.getString("string");

            Toast.makeText(getApplicationContext(), "DataItem - " + s, Toast.LENGTH_SHORT).show();

            mTeleportClient.setOnSyncDataItemTask(new ShowToastOnSyncDataItemTask());
        }
    }

    //Task that shows the path of a received message
    public class ShowToastFromOnGetMessageTask extends TeleportClient.OnGetMessageTask {

        @Override
        protected void onPostExecute(String path) {
//            open_SMS();
            Toast.makeText(getApplicationContext(), "Message - " + path, Toast.LENGTH_SHORT).show();

            //let's reset the task (otherwise it will be executed only once)
            mTeleportClient.setOnGetMessageTask(new ShowToastFromOnGetMessageTask());
        }
    }

    private void usePost(String to_email, String for_email, String messeg){
//        communicator.Email_Post(to_email,for_email,messeg);
    }

    @Override
    public void onStart() {
        super.onStart();
        mTeleportClient.connect();

        if(Data_coltrola.Embece_helth.size()==0){
            Jso_filereder();
        }
        if(globalvariable.getLatitude() != null && !globalvariable.getLatitude().equalsIgnoreCase("0")){
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                near_place("All", globalvariable.getLatitude(), globalvariable.getLongitude());
            }
        }










    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://trimdevelopmentcom.sos/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);

//        viewActionif (camera != null) {
//            camera.release();
//        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.disconnect();
    }

    private void Jso_filereder() {
        String filepath = "hasdata";
        Log.e("List","jsonMainNode.toString()");

        ContextWrapper contextWrapper = new ContextWrapper(Home_page.this);
        directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, "firstaid");

        //read

        if (myInternalFile.exists()) {

            System.out.println("no file");

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(myInternalFile);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(in));
                jsonmenues_stored = br.readLine();
                System.out.println("jsonmenues_stored" + jsonmenues_stored);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            JSONObject jObject = null;
            try {
                jObject = new JSONObject(jsonmenues_stored);
                JSONArray jsonMainNode = jObject.optJSONArray("contacts");
                int lengthJsonArr = jsonMainNode.length();
                Data_coltrola.Embece_helth.clear();

                for (int i = 0; i < lengthJsonArr; i++) {

                    JSONObject menuObject = jsonMainNode.getJSONObject(i);
                    String firstaid_title = menuObject.getString("firstaid_title");
                    String firstaid_image = menuObject.getString("firstaid_image");

                    JSONArray firstaid_details = menuObject.getJSONArray("firstaid_details");



                    Obj_Helth  Embicy = new   Obj_Helth();
                    Embicy.setFirstaid_details(firstaid_details);
                    Embicy.setFirstaid_image(firstaid_image);
                    Embicy.setFirstaid_title(firstaid_title);

                    Data_coltrola.Embece_helth.add(Embicy);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }}



    //ner by places
    private void near_place(String type,String mLatitude,String mLongitude) {

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location="+mLatitude+","+mLongitude);
        sb.append("&radius=10000");
        sb.append("&types="+type);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyBh0ZmzCH9VdRKcNbyjPqfXMUrXks3lBYk");

        // Creating a new non-ui thread task to download json data
        PlacesTask placesTask = new PlacesTask();

        // Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());


    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

    /** A class, to download Google Places */
    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }

    }


    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a List construct */
                places = placeJsonParser.parse(jObject);
                System.out.println("places"+places.toString());

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){
            Data_coltrola.places_data.clear();

            for(int i=0;i<list.size();i++){
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = list.get(i);

                double lat = Double.parseDouble(hmPlace.get("lat"));
                double lng = Double.parseDouble(hmPlace.get("lng"));
                String name = hmPlace.get("place_name");
                String icon = hmPlace.get("icon");
                String vicinity = hmPlace.get("vicinity");
//
                String refrence = hmPlace.get("refrence");
                System.out.println("refrence" + refrence);

                System.out.println("address" + vicinity + "loc" + icon);

                Object_ner data =new Object_ner();
                data.setLat(lat);
                data.setPlace_name(name);
                data.setLon(lng);
                data.setIcon(icon);
                data.setVicinity(vicinity);
                Data_coltrola.places_data.add(data);


            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {}

}
