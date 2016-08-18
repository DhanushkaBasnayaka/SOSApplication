package trimdevelopmentcom.sos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trimdevelopmentcom.sos.models.Object_notification;

public class Notification extends Fragment implements View.OnClickListener {
    ListView listView2;
    private LayoutInflater inflater;
    TextView map, embce, sos, notifi, add;
    CustomListAdapter_notification apter_Rides;
    List<Object_notification> messg_hist;
    DatabaseHandler db;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_notification, container, false);


        db = new DatabaseHandler(getActivity());


        messg_hist = db.Read_notification();
        Data_coltrola.Notification_mesag.clear();
        System.out.println("waitids" + messg_hist.size());
        if (messg_hist.size() > 0) {

            String prefix = "";

            for (Object_notification todo : messg_hist) {
                Object_notification itam = new Object_notification();
                itam.setTitil(todo.getTitil());
                itam.setMeeseg(todo.getMeeseg());
                itam.setWeb_link(todo.getWeb_link());
                itam.setTime(todo.getTime());
                itam.setNot_id(todo.getNot_id());
                itam.setStetest(todo.getStetest());

                Data_coltrola.Notification_mesag.add(itam);

                System.out.println("todo" + todo.getTitil());
            }

        } else {

            System.out.println("emty");
        }




        listView2 = (ListView) rootView.findViewById(R.id.notification);



         apter_Rides = new CustomListAdapter_notification(getActivity(), Data_coltrola.Notification_mesag);
        listView2.setAdapter(apter_Rides);


        return rootView;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {



        }

    }

    public class CustomListAdapter_notification extends BaseAdapter {
        private Activity activity;
        private List<Object_notification> movieItems_pro;


        public CustomListAdapter_notification(Activity activity, ArrayList<Object_notification> movieItems_pro) {
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

                convertView = inflater.inflate(R.layout.notification_list, null);


            TextView topick = (TextView) convertView.findViewById(R.id.topick);
            TextView infromation = (TextView) convertView.findViewById(R.id.infromation);

            TextView weblink = (TextView) convertView.findViewById(R.id.weblink);

            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView New = (TextView) convertView.findViewById(R.id.New);

            LinearLayout  row = (LinearLayout) convertView.findViewById(R.id.row);



            final Object_notification m = movieItems_pro.get(position);

            if(m.getStetest().equalsIgnoreCase("0")){

                New.setVisibility(View.VISIBLE);
                topick.setText(m.getTitil());
                infromation.setText(m.getMeeseg());
                weblink.setText(m.getWeb_link());
                date.setText("Date : "+m.getTime());
            }else{

                topick.setText(m.getTitil());
                infromation.setText(m.getMeeseg());
                weblink.setText(m.getWeb_link());
                date.setText("Date : "+m.getTime());
                New.setVisibility(View.GONE);

            }

//            String goToSettings = ("read more");
//            int start = m.getMeeseg().indexOf(goToSettings);
//            int end = start + goToSettings.length();
//
//            SpannableString spannableString = new SpannableString(m.getMeeseg());
//            spannableString.setSpan(new GoToSettingsSpan(), start, end, 0);
//            infromation.setText(spannableString);
//
//            infromation.setMovementMethod(new LinkMovementMethod());


            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(),
                            ""+m.getNot_id(), Toast.LENGTH_LONG)
                            .show();

                    db.updateContact(m.getNot_id(),1);
                    db.close();

                    Intent emb = new Intent(getActivity(), Notification_reder.class);
                    emb.putExtra("titil", m.getTitil());
                    emb.putExtra("msg", m.getMeeseg());
                    emb.putExtra("web", m.getWeb_link());
                    emb.putExtra("dater", m.getTime());

                    startActivity(emb);
                    getActivity().finish();
                }
            });
            return convertView;
        }
    }

    private static class GoToSettingsSpan extends ClickableSpan {
        public void onClick(View view) {
//            view.getContext().startActivity(
//                    new Intent(android.provider.Settings.ACTION_SETTINGS));
        }
    }

//    @Override
//    public void onBackPressed(){
//
//        Intent intent = new Intent(Notification.this, MainActivity.class);
//
//        startActivity(intent);
//        finish();
//
//
//
//    }
}