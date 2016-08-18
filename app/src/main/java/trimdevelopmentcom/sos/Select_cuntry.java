package trimdevelopmentcom.sos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Select_cuntry extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListCountry mCountryModel;
    String name_cc, pngName,section;
    SearchView   mSearchView;
    String[] recourseList;
    private ArrayList<String> filterArrayList2;
    private ArrayList<String> filterArrayList_flag;

     ListView listview;
    private LayoutInflater inflater;
    CountriesListAdapter countryAdapter;
    private ArrayList<String> movieItems_catagery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cuntry);
        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Intent intent = getIntent();
        section = intent.getStringExtra("flag");


        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Set<Currency> avail = Currency.getAvailableCurrencies();
        Select_cuntry cs = new Select_cuntry();
        Menu_items.cuntry_menu.clear();
        Map<String, String> currencies = cs.getAvailableCurrencies();
        for (String country : currencies.keySet()) {
            String currencyCode = currencies.get(country);
            String currencyCode_id = currencies.get(currencyCode);
            String currencyCode_id2 = currencies.get(currencyCode);
            //System.out.println(country + " => " + currencyCode);
            mCountryModel = new ListCountry();
            mCountryModel.setdisplayName(country);
            mCountryModel.setcurrencyCode(currencyCode);
            mCountryModel.setsiombelCode(currencyCode_id);
            Menu_items.cuntry_menu.add(mCountryModel);
            //System.out.println(country + " /N " + "displayName" + " " + currencyCode);
        }

        Menu_items.couintry_list.clear();
        Menu_items.couintry_list_flag.clear();
         recourseList=this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < recourseList.length; i++) {
            String filtername  = GetCountryZipCode(recourseList[i].split(",")[1]).trim().toLowerCase();
            //  final String curency  =
            String filternamepngName = recourseList[i].split(",")[1].trim().toLowerCase();

            Menu_items.couintry_list.add(filtername);
            Menu_items.couintry_list_flag.add(filternamepngName);
//            System.out.println("toArray"+ Menu_items.couintry_list.toArray().toString());
        }


         listview = (ListView) findViewById(R.id.listView);


        countryAdapter = (new CountriesListAdapter(this, recourseList));
        listview.setAdapter(countryAdapter);
        mSearchView = (SearchView)findViewById(R.id.search_view_cuntry);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.equalsIgnoreCase("")) {
            //System.out.println("emty");

            countryAdapter = new CountriesListAdapter(this, recourseList);
            countryAdapter.notifyDataSetChanged();
            listview.setAdapter(countryAdapter);

        } else {
            //System.out.println("not emty");
//            listView2.setVisibility(View.VISIBLE);
//            listview.setVisibility(View.GONE);


            filterArrayList2 = new ArrayList<String>();
            filterArrayList_flag = new ArrayList<String>();
            String[] searchCountryList;
            ArrayList<String> searchPositions = new ArrayList<String>();
            for (int i = 0; i <  Menu_items.couintry_list.size(); i++) {
                if (Menu_items.couintry_list.get(i).toString().toLowerCase().contains(newText.toLowerCase())) {
                    filterArrayList2.add(Menu_items.couintry_list.get(i));
                    searchPositions.add(""+i);
                }
            }
            //System.out.println("filterArrayList2" + filterArrayList2.toString());
            searchCountryList = new String[searchPositions.size()];
            for (int j = 0 ; j < searchPositions.size() ; j++) {
                //System.out.println("position "+Integer.parseInt(searchPositions.get(j)));
                searchCountryList[j] = recourseList[Integer.parseInt(searchPositions.get(j))];
            }


            countryAdapter = new CountriesListAdapter(this, searchCountryList);
            countryAdapter.notifyDataSetChanged();
            listview.setAdapter(countryAdapter);

//            CustomListAdapter_catgery adapter_catager = new CustomListAdapter_catgery(Select_cuntry.this, filterArrayList2);
//            listView2.setAdapter(adapter_catager);
//
//            if (filterArrayList2.size() == 0) {
//                System.out.println("arrylist emty");
//            } else {
//
//            }
        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }
//    public class CustomListAdapter_catgery extends BaseAdapter {
//        private Activity activity;
//        private LayoutInflater inflater;
//
//    public CustomListAdapter_catgery(Activity activity, ArrayList<String> movieItems) {
//        this.activity = activity;
//        movieItems_catagery = movieItems;
//    }
//
//    @Override
//    public int getCount() {
//        return movieItems_catagery.size();
//    }
//
//    @Override
//    public Object getItem(int location) {
//        return movieItems_catagery.get(location);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (inflater == null)
//            inflater = (LayoutInflater) activity
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (convertView == null)
//            convertView = inflater.inflate(R.layout.country_list_item, null);
//
//
//        TextView textView2 = (TextView) convertView.findViewById(R.id.txtViewCountryName);
//        ImageView imageView2 = (ImageView) convertView.findViewById(R.id.imgViewFlag);
//        LinearLayout LinearLayout = (LinearLayout) convertView.findViewById(R.id.cou);
//        String nem = filterArrayList2.get(position);
//       // String flag = filterArrayList_flag.get(position);
//        textView2.setText(nem);
//       /// imageView2.setImageResource(activity.getResources().getIdentifier("drawable/flag_" + flag, null, activity.getPackageName()));
//  //System.out.println(nem +" "+nem);
//        return convertView;
//
//    }
//
//
//}







    public class CountriesListAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public CountriesListAdapter(Context context, String[] values) {
            super(context, R.layout.country_list_item, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.country_list_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.txtViewCountryName);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imgViewFlag);
            RelativeLayout LinearLayout = (RelativeLayout) rowView.findViewById(R.id.cou);



//            final String[] g=;
           final String name  = GetCountryZipCode(values[position].split(",")[1].trim().toLowerCase());
            //  final String curency  =
            pngName = values[position].split(",")[1].trim().toLowerCase();

            //System.out.println("toString" + Menu_items.couintry_list.toString());

//
            textView.setText(name);

            imageView.setImageResource(context.getResources().getIdentifier("drawable/flag_" + pngName, null, context.getPackageName()));
            LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("name click" + name + " index "+position);

                    int catagaryu = Menu_items.cuntry_menu.size();
                    for (int i = 0; i < catagaryu; i++) {
                        ListCountry menu = Menu_items.cuntry_menu.get(i);
                        String title = menu.getdisplayName().toLowerCase();
                        String countr_cord =menu.getcurrencyCode().toLowerCase();

                     String   pngName_flag = values[position].split(",")[1].trim().toLowerCase();
                        String   number = values[position].split(",")[0].trim().toLowerCase();

//                        System.out.println("titlenew" + title);
//                        System.out.println("imgname"+pngName);

                        if (name.toLowerCase().equalsIgnoreCase(title)) {

                            String   curency = menu.getcurrencyCode();
                            System.out.println("name_titil" + name  );

                            if(section.equalsIgnoreCase("defolt")){
                                SharedPreferences prefs = getSharedPreferences("currency", MODE_WORLD_READABLE);
                                SharedPreferences.Editor editor = prefs.edit();

                                String image_flog= ("drawable/flag_" + pngName);
                                editor.putString("pngName_curent", pngName_flag);
                                editor.putString("tp_cord_curent", number);
                                editor.putString("pngName_curent", pngName_flag);
                                editor.putString("tp_cord_curent2", countr_cord);
                                editor.putString("cuntry_name", name);


                                editor.commit();
                                System.out.println("country_defolt_3"+name);
                                Intent emb = new Intent(Select_cuntry.this, SetingActivity.class);

                                finish();
                                startActivity(emb);
                            }else if(section.equalsIgnoreCase("sos")){
                                SharedPreferences prefs = getSharedPreferences("currency", MODE_WORLD_READABLE);
                                SharedPreferences.Editor editor = prefs.edit();

                                String image_flog= ("drawable/flag_" + pngName);
                                editor.putString("pngName_sos", pngName_flag);
                                editor.putString("tp_cord_sos", number);


                                editor.commit();
                                Intent emb = new Intent(Select_cuntry.this, SetingActivity.class);

                                finish();
                                startActivity(emb);

                            }else if(section.equalsIgnoreCase("sos_add")){
                                SharedPreferences prefs = getSharedPreferences("currency", MODE_WORLD_READABLE);
                                SharedPreferences.Editor editor = prefs.edit();

                                String image_flog= ("drawable/flag_" + pngName);
                                editor.putString("pngName_sos_add", pngName_flag);
                                editor.putString("tp_cord_sos_add", number);

                                editor.commit();
                                Intent emb = new Intent(Select_cuntry.this, SetingActivity.class);

                                finish();
                                startActivity(emb);

                            }

                         ///   System.out.println("curency"+curency);




                        }
                    }
                    // System.out.println("menu_titil"+menu_titil);

                }
            });

            return rowView;

        }


    }
    private String GetCountryZipCode(String ssid){
        Locale loc = new Locale("", ssid);

        return loc.getDisplayCountry().trim();
    }
    private Map<String, String> getAvailableCurrencies() {
        Locale[] locales = Locale.getAvailableLocales();

        //
        // We use TreeMap so that the order of the data in the map sorted
        // based on the country name.
        //
        Map<String, String> currencies = new TreeMap<String, String>();
        for (Locale locale : locales) {
            try {
                currencies.put(locale.getDisplayCountry(),
                        Currency.getInstance(locale).getCurrencyCode());
            } catch (Exception e) {
                // when the locale is not supported
            }
        }
        return currencies;
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


}
