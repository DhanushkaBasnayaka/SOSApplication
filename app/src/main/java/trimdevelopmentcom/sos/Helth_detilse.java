package trimdevelopmentcom.sos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class Helth_detilse extends Fragment implements View.OnClickListener  {

    ListView listView2;
    private LayoutInflater inflater;
    TextView map,embce,sos,notifi,add;
    private String[] Ride_stetest = {"Planed", "Previous", "current", "Previous"};
    String firstaid_details_name;
    CustomListAdapter_helth apter_Rides;
    JSONArray  firstaid_point_not;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String userProfileString=getArguments().getString("message");
        try {
            JSONArray jsonObject=new JSONArray(userProfileString);
            for(int i=0;i<jsonObject.length();i++){
                JSONObject menuObject = jsonObject.getJSONObject(i);
                firstaid_details_name =menuObject.getString("firstaid_details_name");
                firstaid_point_not =menuObject.getJSONArray("firstaid_point_not");


            }

            for(int i=0;i<firstaid_point_not.length();i++){
                JSONObject m = firstaid_point_not.getJSONObject(i);
                String firstaid_details_not =m.getString("firstaid_details_not"+i);
                Obj_Helth itam = new Obj_Helth();
                itam.setNote(firstaid_details_not);
                Data_coltrola.helth_note.add(itam);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        rootView = inflater.inflate(R.layout.activity_helth_detilse, container, false);



        listView2=(ListView)rootView.findViewById(R.id.listView2);
        TextView topic = (TextView)rootView.findViewById(R.id.topic);
        topic.setText(firstaid_details_name);







        apter_Rides  = new CustomListAdapter_helth(getActivity(), Data_coltrola.helth_note);
        listView2.setAdapter(apter_Rides);


        return rootView;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {



        }

    }

    public class CustomListAdapter_helth extends BaseAdapter {
        private Activity activity;
        private List<Obj_Helth> movieItems_pro;


        public CustomListAdapter_helth(Activity activity, ArrayList<Obj_Helth> movieItems_pro) {
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
            inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null)

                convertView = inflater.inflate(R.layout.helth_sell_data, null);

            TableRow tach = (TableRow) convertView.findViewById(R.id.tableRow2);
            TextView infromation = (TextView) convertView.findViewById(R.id.infromation);




            final Obj_Helth m = movieItems_pro.get(position);

            infromation.setText(m.getNote());
            return convertView;
        }
    }


}

