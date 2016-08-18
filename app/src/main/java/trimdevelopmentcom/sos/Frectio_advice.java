package trimdevelopmentcom.sos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class Frectio_advice extends Fragment implements View.OnClickListener  {

    ListView list;
    private LayoutInflater inflater;
    TextView map,embce,sos,notifi,add;


    CustomListAdapter_helth apter_Rides;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frectio_advice, container, false);


        list=(ListView)rootView.findViewById(R.id.list_helth);










        apter_Rides  = new CustomListAdapter_helth(getActivity(), Data_coltrola.Embece_helth);
        list.setAdapter(apter_Rides);


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

                convertView = inflater.inflate(R.layout.helth_sell, null);

            TableRow tach = (TableRow) convertView.findViewById(R.id.tableRow2);
            ImageView drop = (ImageView) convertView.findViewById(R.id.imge);
            TextView topic = (TextView) convertView.findViewById(R.id.topic);

            final Obj_Helth m = movieItems_pro.get(position);

tach.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Helth_detilse embece_activity = new Helth_detilse();


        Bundle bundle=new Bundle();
        bundle.putString("message", m.getFirstaid_details().toString());


//
        FragmentManager fm_embece_activity =getActivity().getSupportFragmentManager();
        FragmentTransaction transaction_embece_activity = fm_embece_activity.beginTransaction();
        transaction_embece_activity.replace(R.id.container, embece_activity);
        transaction_embece_activity.commit();
        embece_activity.setArguments(bundle);
//        Intent Count_down = new Intent(Frectio_advice.this, Helth_detilse.class);
//
//        startActivity(Count_down);
    }
});


            topic.setText(m.getFirstaid_title());
            return convertView;
        }
    }


}
