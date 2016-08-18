package trimdevelopmentcom.sos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class Embece_activity extends Fragment implements OnMapReadyCallback, View.OnClickListener{
    /**************  Intialize Variables *************/
//    public  ArrayList<Object_Embicy> CustomListViewValuesArr = new ArrayList<Object_Embicy>();
    TextView output = null;
    CustomAdapter adapter;
    CustomAdapter_of adapter_of;
//    Embece_activity activity = null;
    String  country,emb_of_image,emb_in_id,Company;

    TextView map2,embce,sos,notifi,add;
    GlobalClass globalvariable;
    ImageView plice,hospitel,bank;
    View rootView;



    public Embece_activity() {

    }

//    public static Embece_activity newInstance(@AnimationStyle int direction) {
//        Embece_activity f = new Embece_activity();
//        f.setArguments(new Bundle());
//        f.getArguments().putInt("direction", direction);
//        return f;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_embece_activity, container, false);
        globalvariable = (GlobalClass) getActivity().getApplicationContext();




        TextView serch = (TextView)rootView.findViewById(R.id.serch);

        final Spinner  SpinnerExample = (Spinner)rootView.findViewById(R.id.spinner_in);
        final Spinner  spinner_of = (Spinner)rootView.findViewById(R.id.spinner_of);

        final Resources res = getResources();

//


        adapter = new CustomAdapter(getActivity(), R.layout.spinner_rows, Data_coltrola.Embece_data,res);


        // Set adapter to spinner

        spinner_of.setAdapter(adapter);

        spinner_of.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here

                 Company = ((TextView) v.findViewById(R.id.company)).getText().toString().toLowerCase();

            JSONArray Emb_in= Data_coltrola.Embece_data.get(position).getEmbassy();
                String imafe_of= Data_coltrola.Embece_data.get(position).getEmb_image();
                String police= Data_coltrola.Embece_data.get(position).getPolice();
                String ambulens= Data_coltrola.Embece_data.get(position).getAmbulance();
                String fiear= Data_coltrola.Embece_data.get(position).getFir();

                Data_coltrola.Embece_data_filter.clear();
                int lengthJsonArr = Emb_in.length();

                for (int i=0;i<lengthJsonArr;i++){

                    JSONObject menuObject = null;
                    try {
                        menuObject = Emb_in.getJSONObject(i);
                        String embassy_flag = menuObject.getString("embassy_flag");
                        String idemb = menuObject.getString("id");
                        String embassy_name = menuObject.getString("embassy_name");
                        String embassy_address = menuObject.getString("embassy_address");
                        String embassy_contact_international = menuObject.getString("embassy_contact_international");
                        String embassy_contact_local = menuObject.getString("embassy_contact_local");
                        String embassy_email = menuObject.getString("embassy_email");
                        String embassy_fax_international = menuObject.getString("embassy_fax_international");
                        String embassy_fax_local = menuObject.getString("embassy_fax_local");

                        Object_in_Embicy fit =new Object_in_Embicy();

                        fit.setEmbassy_contact_international(embassy_contact_international);
                        fit.setEmbassy_address(embassy_address);
                        fit.setEmbassy_contact_local(embassy_contact_local);
                        fit.setOf_name(Company);
                        fit.setEmbassy_email(embassy_email);
                        fit.setEmbassy_fax_international(embassy_fax_international);
                        fit.setEmbassy_fax_local(embassy_fax_local);
                        fit.setEmbassy_flag(embassy_flag);
                        fit.setEmbassy_name(embassy_name);
                        fit.setIdemb(idemb);
                        fit.setEmbecy_of_image(imafe_of);
                        fit.setAmbulance(ambulens);
                        fit.setPolice(police);
                        fit.setFir(fiear);
                        Data_coltrola.Embece_data_filter.add(fit);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//
                }




                adapter_of = new CustomAdapter_of(getActivity(), R.layout.spinner_rows, Data_coltrola.Embece_data_filter,res);
                SpinnerExample.setAdapter(adapter_of);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // Listener called when spinner item selected

        SpinnerExample.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here
                // Get selected row data to show on screen
                String cuntryname = ((ImageView) v.findViewById(R.id.image)).toString();
//                emb_in_id= Data_coltrola.Embece_data_filter.get(position).getId();
                System.out.println("cuntryname_image"+cuntryname);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Company = ((TextView) SpinnerExample.findViewById(R.id.company)).getText().toString();

                if( Data_coltrola.Embece_data_filter.size()>0){
                    for(int i=0;Data_coltrola.Embece_data_filter.size()>i;i++){
                     if(Company.equalsIgnoreCase(Data_coltrola.Embece_data_filter.get(i).getEmbassy_name())){
                         Intent Count_down = new Intent(getActivity(), Embec_ditilse.class);

                         Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                         bundle.putString("message", Data_coltrola.Embece_data_filter.get(i).getIdemb());
//Add the bundle to the intent
                         Count_down.putExtras(bundle);
                         startActivity(Count_down);

//                         Embec_ditilse embece_activity = new Embec_ditilse();
//                         Bundle bundle=new Bundle();
//                         bundle.putString("message", Data_coltrola.Embece_data_filter.get(i).getIdemb());
////                trBundle.putSerializable("id",  Data_coltrola.Embece_data_filter.get(i).getIdemb());
//
//         FragmentManager fm_embece_activity =getActivity().getSupportFragmentManager();
//         FragmentTransaction transaction_embece_activity = fm_embece_activity.beginTransaction();
//         transaction_embece_activity.replace(R.id.container, embece_activity);
//         transaction_embece_activity.commit();
//                         embece_activity.setArguments(bundle);
    break;

}

                    }
                }



            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
//            case R.id.map:
//
//                Intent intent = new Intent(getActivity(), Nerby_Activity.class);
//
//                startActivity(intent);
//                getActivity().finish();
//
//                break;


        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    /***** Adapter class extends with ArrayAdapter ******/
    public class CustomAdapter extends ArrayAdapter<String>{

        private FragmentActivity activity;
        private ArrayList data;
        public Resources res;
        Object_Embicy tempValues=null;
        LayoutInflater inflater;
        public CustomAdapter(
                FragmentActivity activitySpinner,
                int textViewResourceId,
                ArrayList objects,
                Resources resLocal
        )
        {
            super(activitySpinner, textViewResourceId,objects);


            /********** Take passed values **********/
            activity = activitySpinner;
            data     = objects;
            res      = resLocal;

            /***********  Layout inflator to call external xml layout () **********************/
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // This funtion called for each row ( Called data.size() times )
        public View getCustomView(int position, View convertView, ViewGroup parent) {

            /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
            View row = inflater.inflate(R.layout.spinner_rows, parent, false);

            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (Object_Embicy) data.get(position);

            TextView label        = (TextView)row.findViewById(R.id.company);

            ImageView companyLogo = (ImageView)row.findViewById(R.id.image);

            label.setText(tempValues.getEmb_name());

            String result = tempValues.getEmb_image().substring(tempValues.getEmb_image().lastIndexOf("/") + 1);

            StringTokenizer tokens = new StringTokenizer(result, ".");
            final String image = tokens.nextToken();// this will contain "Fruit"

//
            System.out.println("Image name2 " + image);
                companyLogo.setImageResource(res.getIdentifier
                        ("trimdevelopmentcom.sos:drawable/"
                                +image, null, null));
                System.out.println("tempValues.getEmb_image()"+image);

//            if(position==0){
//
//                // Default selected Spinner item
//                label.setText(country);
//
//
//            }
//            else
//            {
//                // Set values for spinner each row
//                label.setText(tempValues.getEmb_name());
//
//                companyLogo.setImageResource(res.getIdentifier
//                        ("trimdevelopmentcom.sos.sosapplication:drawable/"
//                                + tempValues.getEmb_image(), null, null));
//                System.out.println("tempValues.getEmb_image()"+tempValues.getEmb_image());
//
//            }

            return row;
        }
    }




    /***** Adapter class extends with ArrayAdapter ******/
    public class CustomAdapter_of extends ArrayAdapter<String>{

        private Activity activity;
        private ArrayList data;
        public Resources res;
        Object_in_Embicy tempValues=null;
        LayoutInflater inflater;
        public CustomAdapter_of(
                FragmentActivity activitySpinner,
                int textViewResourceId,
                ArrayList objects,
                Resources resLocal
        )
        {
            super(activitySpinner, textViewResourceId, objects);

            /********** Take passed values **********/
            activity = activitySpinner;
            data     = objects;
            res      = resLocal;

            /***********  Layout inflator to call external xml layout () **********************/
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //adapter_of.notifyDataSetChanged();

        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // This funtion called for each row ( Called data.size() times )
        public View getCustomView(int position, View convertView, ViewGroup parent) {

            /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
            View row = inflater.inflate(R.layout.spinner_rows, parent, false);

            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (Object_in_Embicy) data.get(position);

            TextView label        = (TextView)row.findViewById(R.id.company);

            ImageView companyLogo = (ImageView)row.findViewById(R.id.image);

            label.setText(tempValues.getEmbassy_name());

//            StringTokenizer tokens = new StringTokenizer(tempValues.getEmbassy_flag(), "_.");
//            final String image8 = tokens.nextToken();
//            final String image6 = tokens.nextToken();
//
//            System.out.println("Image name1 " + image6);
//
//            StringTokenizer tokens_result = new StringTokenizer(image6, ".");
//            final String image = tokens_result.nextToken();
//            System.out.println("Image name2 " + image);
            String result = tempValues.getEmbassy_flag().substring(tempValues.getEmbassy_flag().lastIndexOf("/") + 1);
            System.out.println("Image name3" + result);

            StringTokenizer tokens = new StringTokenizer(result, ".");
            final String image8 = tokens.nextToken();
//
//            if(!result.isEmpty()||!result.equalsIgnoreCase("")){

                System.out.println("Image name2 " + image8);
//
            companyLogo.setImageResource(res.getIdentifier
                    ("trimdevelopmentcom.sos:drawable/"
                            +image8.toLowerCase(), null, null));
//
//
//



//            if(position==0){
//
//                // Default selected Spinner item
//                label.setText(country);
//
//
//            }
//            else
//            {
//                // Set values for spinner each row
//                label.setText(tempValues.getEmb_name());
//
//                companyLogo.setImageResource(res.getIdentifier
//                        ("trimdevelopmentcom.sos.sosapplication:drawable/"
//                                + tempValues.getEmb_image(), null, null));
//                System.out.println("tempValues.getEmb_image()"+tempValues.getEmb_image());
//
//            }

            return row;
        }
    }






}
