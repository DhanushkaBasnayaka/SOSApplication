package trimdevelopmentcom.sos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Data_list extends AppCompatActivity {
    private LayoutInflater inflater;
    ListView listView;
    CustomListAdapter_Find find_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);



        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView=(ListView)findViewById(R.id.listView);
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        String type = intent.getStringExtra("type");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView setting = (TextView) toolbar.findViewById(R.id.setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Count_down = new Intent(Data_list.this, SetingActivity.class);

                startActivity(Count_down);

            }
        });
        TextView toobar_title = (TextView) toolbar.findViewById(R.id.name_toll2);
        toobar_title.setText("NEAR BY "+type);

        near_place(type, longitude, latitude);


    }

    private void near_place(String type,String mLatitude,String mLongitude) {

        System.out.println("mLatitude   =  " +mLatitude+","+mLongitude);

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location="+mLongitude+","+mLatitude);
        sb.append("&radius=10000");
        sb.append("&types="+ type);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyBh0ZmzCH9VdRKcNbyjPqfXMUrXks3lBYk");

        // Creating a new non-ui thread task to download json data
       // Data_coltrola.places_data.clear();
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
            System.out.println("list.size()"+list.size());
            for(int i=0;i<list.size();i++){
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = list.get(i);

                double lat = Double.parseDouble(hmPlace.get("lat"));
                double lng = Double.parseDouble(hmPlace.get("lng"));
                String name = hmPlace.get("place_name");
                String icon = hmPlace.get("icon");
                String vicinity = hmPlace.get("vicinity");
//                String rating = "";
//                if (!hmPlace.get("rate").equalsIgnoreCase("")) {
//                    rating = hmPlace.get("rate");
//                }
//                String icon = hmPlace.get("icon");
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
                 find_user = new CustomListAdapter_Find(Data_list.this,Data_coltrola.places_data);
                listView.setAdapter(find_user);


//                LatLng latLng = new LatLng(lat, lng);
//                markerOptions.position(latLng);
//                markerOptions.title(name + " : " + vicinity);
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
            }
            findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
        }

    }





    public class CustomListAdapter_Find extends BaseAdapter {
        private Activity activity;
        private List<Object_ner> movieItems_pro;


        public CustomListAdapter_Find(Activity activity, ArrayList<Object_ner> movieItems_pro) {
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

                convertView = inflater.inflate(R.layout.places_sell, null);
            find_user.notifyDataSetChanged();
//            LinearLayout tach = (LinearLayout) convertView.findViewById(R.id.tach);
            TextView name =(TextView)convertView.findViewById(R.id.name);
            TextView addres =(TextView)convertView.findViewById(R.id.addres);
            ImageView imag =(ImageView)convertView.findViewById(R.id.icon);
//            MyTextView selection =(MyTextView)convertView.findViewById(R.id.selection);
//            MyTextView action =(MyTextView)convertView.findViewById(R.id.action);



            final Object_ner m = movieItems_pro.get(position);
            name.setText(m.getPlace_name());
            addres.setText(m.getVicinity());
            Picasso.with(Data_list.this).load(Data_coltrola.places_data.get(position).getIcon()).into(imag);



            //System.out.println(order_number);
            return convertView;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_calander, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
