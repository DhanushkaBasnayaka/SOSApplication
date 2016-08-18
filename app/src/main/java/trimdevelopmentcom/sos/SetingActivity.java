package trimdevelopmentcom.sos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.google.gson.JsonArray;
import com.squareup.otto.Produce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import trimdevelopmentcom.sos.Event_Bus.ErrorEvent;
import trimdevelopmentcom.sos.Event_Bus.ServerEvent;
import trimdevelopmentcom.sos.Sever_task.Communicator;
import trimdevelopmentcom.sos.models.Object_register;


public class SetingActivity extends AppCompatActivity {
    List<String> country = new ArrayList<String>();
    EditText name,E_mail,Phone,sos_phone,sos_Phone2,SOS_E_mail,Sos_masseg,Sos_countdown_masseg,pin,SOS_E_mail_sos2;
    String couflag_call,couflag_2_call,pngName_sos_add_call,cuntry_name;
    ImageView couflag;
    ImageView couflag_2;
    ImageView couflag_add;
    ImageView cancel;
    ImageView cancel_email;
    private static  final String TAG = "Communicator";
    private static final String SERVER_URL = "http://projects.yogeemedia.com/preview/embassy/Sos_application";
    String jsonmenues_stored;
    File myInternalFile;
    File directory;

    String token;
    TextView add,history,add_email;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Button Save;
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";
     DatabaseHandler db;
    GlobalClass globalvariable;
    AlertDialog alertDialog;
    ProgressDialog prgDialog;
    private String filepath = "hasdata";

    GoogleCloudMessaging gcmObj;
//    Context applicationContext;
    String regId = "";
    Communicator  communicator;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    AsyncTask<Void, Void, String> createRegIdTask;

    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        ContextWrapper contextWrapper = new ContextWrapper(SetingActivity.this);
        directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, "embecy");

        //read

        if (!myInternalFile.exists()) {
            getBooks();
            get_fastaid();

        }





        communicator = new Communicator();

        globalvariable = (GlobalClass) getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHandler(SetingActivity.this);

        Cursor c2 = db.getAllContacts();
        if (!c2.moveToFirst()) {
        RegisterUser();

        }
        //GetCountryZipCode();   Histori_messeg
       // Spinner spinner = (Spinner) findViewById(R.id.spinner);
        name =(EditText)findViewById(R.id.name);
        final RelativeLayout input_layout_phone_2 =(RelativeLayout)findViewById(R.id.input_layout_phone_2);
        final RelativeLayout input_emsil =(RelativeLayout)findViewById(R.id.input_emsil);


        E_mail =(EditText)findViewById(R.id.E_mail);
        Phone =(EditText)findViewById(R.id.Phone);
        sos_phone =(EditText)findViewById(R.id.sos_phone);
        sos_Phone2 =(EditText)findViewById(R.id.sos_Phone2);
        SOS_E_mail =(EditText)findViewById(R.id.SOS_E_mail);
        SOS_E_mail_sos2 =(EditText)findViewById(R.id.SOS_E_mail_sos2);


        Sos_masseg =(EditText)findViewById(R.id.Sos_masseg);
        add_email =(TextView)findViewById(R.id.add_email);


        Sos_countdown_masseg =(EditText)findViewById(R.id.Sos_countdown_masseg);
        pin =(EditText)findViewById(R.id.pin);
        history =(TextView)findViewById(R.id.history);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(db.getCount()>0) {
                    Intent emb = new Intent(SetingActivity.this, Histori_messeg.class);

                    finish();
                    startActivity(emb);
                }else{

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            SetingActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("You Haven't history message");

                    // set dialog message
                    alertDialogBuilder

                            .setCancelable(false)
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.cancel();
                                }
                            });
//

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
                }

        });
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        SharedPreferences prefs2 = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String registrationId = prefs2.getString(REG_ID, "");

        if (!TextUtils.isEmpty(registrationId)) {
//            Intent i = new Intent(SetingActivity.this, GreetingActivity.class);
//            i.putExtra("regId", registrationId);
//            startActivity(i);
//            finish();
        }
//        RegisterUser();



        couflag =(ImageView)findViewById(R.id.couflag);
        couflag_2 =(ImageView)findViewById(R.id.couflag_2);
        couflag_add =(ImageView)findViewById(R.id.couflag_add);
        add =(TextView)findViewById(R.id.add);

        cancel_email=(ImageView)findViewById(R.id.cancel_email);
        cancel=(ImageView)findViewById(R.id. cancel);

        SharedPreferences prefs = getSharedPreferences("currency", MODE_WORLD_READABLE);
       final String couflag_get = prefs.getString("pngName_curent", "gb");
        final String couflag_2_get = prefs.getString("pngName_sos", "gb");
        final String pngName_sos_add_get = prefs.getString("pngName_sos_add", "gb");
     cuntry_name = prefs.getString("cuntry_name", "United States");

       // cuntry_name = prefs.getString("cuntry_name", "United States");
         couflag_call = prefs.getString("tp_cord_curent", "350");
         couflag_2_call = prefs.getString("tp_cord_sos", "350");
         pngName_sos_add_call = prefs.getString("tp_cord_sos_add", "350");

        System.out.println("couflag_get"+couflag_get+" "+couflag_2_get);

        couflag.setImageResource(SetingActivity.this.getResources().getIdentifier("drawable/flag_" + couflag_get, null, SetingActivity.this.getPackageName()));
        couflag_2.setImageResource(SetingActivity.this.getResources().getIdentifier("drawable/flag_" + couflag_2_get, null, SetingActivity.this.getPackageName()));
        couflag_add.setImageResource(SetingActivity.this.getResources().getIdentifier("drawable/flag_" + pngName_sos_add_get, null, SetingActivity.this.getPackageName()));

        Save=(Button)findViewById(R.id.Save);
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            name.setText(c.getString(1));
            E_mail.setText(c.getString(2));
            Phone.setText(c.getString(3));
            sos_phone.setText(c.getString(4));
            sos_Phone2.setText(c.getString(5));

            if (c.getString(5) != null && !c.getString(5).equalsIgnoreCase(" ")){

                //add.setVisibility(View.GONE);
                input_layout_phone_2.setVisibility(View.VISIBLE);

            }

//            Phone.setText(c.getString(6));
//            sos_phone.setText(c.getString(7));
//            sos_Phone2.setText(c.getString(8));

            SOS_E_mail.setText(c.getString(9));
            Sos_masseg.setText(c.getString(11));
            Sos_countdown_masseg.setText(c.getString(12));
            pin.setText(c.getString(13));


        }else {
            if (globalvariable.getName() != null && !globalvariable.getName().equalsIgnoreCase(" ")){
                name.setText(globalvariable.getName());

            }
            if (globalvariable.getE_mail() != null && !globalvariable.getE_mail().equalsIgnoreCase(" ")){
                E_mail.setText(globalvariable.getE_mail());
            }
            if ( globalvariable.getPhone() != null && !globalvariable.getPhone().equalsIgnoreCase(" ")){
                Phone.setText(globalvariable.getPhone());


            }if (globalvariable.getSos_phone() != null && !globalvariable.getSos_phone().equalsIgnoreCase(" ")){
                sos_phone.setText(globalvariable.getSos_phone());

            }
            if (globalvariable.getSos_Phone2() != null && !globalvariable.getSos_Phone2().equalsIgnoreCase(" ")){
                add.setVisibility(View.GONE);
                input_layout_phone_2.setVisibility(View.VISIBLE);
                sos_Phone2.setText(globalvariable.getSos_Phone2());
            }
            if ( globalvariable.getSOS_E_mail() != null && !globalvariable.getSOS_E_mail().equalsIgnoreCase(" ")){
                SOS_E_mail.setText(globalvariable.getSOS_E_mail());
            }


            if (globalvariable.getSos_masseg()!= null && !globalvariable.getSos_masseg().equalsIgnoreCase(" ")){
                Sos_masseg.setText(globalvariable.getSos_masseg());

            }
            if (globalvariable.getSos_countdown_masseg() != null && !globalvariable.getSos_countdown_masseg().equalsIgnoreCase(" ")){
                Sos_countdown_masseg.setText(globalvariable.getSos_countdown_masseg());
            }
            if ( globalvariable.getPin() != null && !globalvariable.getPin().equalsIgnoreCase(" ")){
                pin.setText(globalvariable.getPin());
            }
            if ( globalvariable.getSOS_E_mail_sos2() != null && !globalvariable.getSOS_E_mail_sos2().equalsIgnoreCase(" ")){
                SOS_E_mail_sos2.setText(globalvariable.getSOS_E_mail_sos2());
            }
        }

        db.close();






        couflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalvariable.setName(name.getText().toString());
                globalvariable.setE_mail(E_mail.getText().toString());
                globalvariable.setPhone(Phone.getText().toString());
                globalvariable.setSos_phone(sos_phone.getText().toString());
                globalvariable.setSos_Phone2(sos_Phone2.getText().toString());
                globalvariable.setSOS_E_mail(SOS_E_mail.getText().toString());
                globalvariable.setSos_countdown_masseg(Sos_countdown_masseg.getText().toString());
                globalvariable.setSos_masseg(Sos_masseg.getText().toString());
                globalvariable.setPin(pin.getText().toString());
                globalvariable.setCouflag(couflag_get);
                globalvariable.setCouflag_2(couflag_2_get);
                globalvariable.setCouflag_add(pngName_sos_add_get);
                globalvariable.setSOS_E_mail_sos2(SOS_E_mail_sos2.getText().toString());


                Intent emb = new Intent(SetingActivity.this, Select_cuntry.class);
                emb.putExtra("flag", "defolt");
                finish();
                startActivity(emb);

            }
        });
        couflag_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalvariable.setName(name.getText().toString());
                globalvariable.setE_mail(E_mail.getText().toString());
                globalvariable.setPhone(Phone.getText().toString());
                globalvariable.setSos_phone( sos_phone.getText().toString());
                globalvariable.setSos_Phone2( sos_Phone2.getText().toString());
                globalvariable.setSOS_E_mail(SOS_E_mail.getText().toString());
                globalvariable.setSos_countdown_masseg(Sos_countdown_masseg.getText().toString());
                globalvariable.setSos_masseg(Sos_masseg.getText().toString());
                globalvariable.setPin(pin.getText().toString());
                globalvariable.setCouflag(couflag_get);
                globalvariable.setCouflag_2(couflag_2_get);
                globalvariable.setCouflag_add(pngName_sos_add_get);
                globalvariable.setSOS_E_mail_sos2(SOS_E_mail_sos2.getText().toString());

                Intent emb = new Intent(SetingActivity.this, Select_cuntry.class);
                emb.putExtra("flag", "sos");
                finish();
                startActivity(emb);

            }
        });
        couflag_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalvariable.setName(name.getText().toString());
                globalvariable.setE_mail(E_mail.getText().toString());
                globalvariable.setPhone(Phone.getText().toString());
                globalvariable.setSos_phone( sos_phone.getText().toString());
                globalvariable.setSos_Phone2( sos_Phone2.getText().toString());
                globalvariable.setSOS_E_mail(SOS_E_mail.getText().toString());
                globalvariable.setSos_countdown_masseg(Sos_countdown_masseg.getText().toString());
                globalvariable.setSos_masseg(Sos_masseg.getText().toString());
                globalvariable.setPin(pin.getText().toString());
                globalvariable.setCouflag(couflag_get);
                globalvariable.setCouflag_2(couflag_2_get);
                globalvariable.setCouflag_add(pngName_sos_add_get);
                globalvariable.setSOS_E_mail_sos2(SOS_E_mail_sos2.getText().toString());

                Intent emb = new Intent(SetingActivity.this, Select_cuntry.class);
                emb.putExtra("flag", "sos_add");
                finish();
                startActivity(emb);

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add.setVisibility(View.GONE);
                input_layout_phone_2.setVisibility(View.VISIBLE);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add.setVisibility(View.VISIBLE);
                input_layout_phone_2.setVisibility(View.GONE);

            }
        });


        add_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add_email.setVisibility(View.GONE);
                input_emsil.setVisibility(View.VISIBLE);


            }
        });

        cancel_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // add.setVisibility(View.VISIBLE);
                input_emsil.setVisibility(View.GONE);

            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validetion
                Cursor c = db.getAllContacts();
                if (c.moveToFirst())
                {
                Update_seting();
            }else{
                    validetion();

                }
            }
        });



    }

    private void Update_seting() {


        if (db.updateinsertContact
                (Integer.parseInt("1"),
                        name.getText().toString(),
                        E_mail.getText().toString(),Phone.getText().toString(),sos_phone.getText().toString(),
                        sos_Phone2.getText().toString(),couflag_call,couflag_2_call,pngName_sos_add_call,SOS_E_mail.getText().toString(),SOS_E_mail_sos2.getText().toString(),
                        Sos_masseg.getText().toString(),Sos_countdown_masseg.getText().toString(),pin.getText().toString(),cuntry_name))
            System.out.println("country_defolt_2"+cuntry_name);

        db.close();
//        usePost( E_mail.getText().toString(),name.getText().toString(),sos_phone.getText().toString(),"tokent");
        System.out.println("Update");
        Toast.makeText(getBaseContext(), "Update",
                Toast.LENGTH_SHORT).show();

//        usePost( E_mail.getText().toString(),name.getText().toString(),sos_phone.getText().toString(),"hfs");
        Intent itent = new Intent(SetingActivity.this, Splash.class);
        startActivity(itent);
        finish();


    }

    private void validetion() {

                if(name.getText().toString().isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your Nmae")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if(E_mail.getText().toString().isEmpty()){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your Email")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else if(Phone.getText().toString().isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your Phone number")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                     alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if(sos_phone.getText().toString().isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your Phone number")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else if(pin.getText().toString().isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your Pinn")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else if(SOS_E_mail.getText().toString().isEmpty()){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your Email")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else if(Sos_masseg.getText().toString().isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your Sos Masseg")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                 }else if(Sos_countdown_masseg.getText().toString().isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetingActivity.this);
                    alertDialogBuilder.setTitle("SOS Application");
                    alertDialogBuilder
                            .setMessage("Please Enter Your countdown_masseg")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

               }

                else{
                   //save the db  SOS_E_mail_sos2

                  insert_db();


                }






    }

    private void insert_db() {


        db.addinsertContact(name.getText().toString(),
                E_mail.getText().toString(),
                Phone.getText().toString(),
                sos_phone.getText().toString(),
                  sos_Phone2.getText().toString(),
                couflag_call,
                couflag_2_call,
                pngName_sos_add_call,
                SOS_E_mail.getText().toString(),
                SOS_E_mail_sos2.getText().toString(),
                Sos_masseg.getText().toString(),
                Sos_countdown_masseg.getText().toString(),
                pin.getText().toString(), cuntry_name);
        db.close();

//        email,name,tp,regId
        usePost( E_mail.getText().toString(),name.getText().toString(),sos_phone.getText().toString(),token);
        Toast.makeText(getBaseContext(), "Inserted",
                Toast.LENGTH_SHORT).show();

//        finish();


    }

    // When Register Me button is clicked
    public void RegisterUser() {
        {
            //Initializing our broadcast receiver
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {

                //When the broadcast received
                //We are sending the broadcast from GCMRegistrationIntentService

                @Override
                public void onReceive(Context context, Intent intent) {
                    //If the broadcast has received with success
                    //that means device is registered successfully
                    if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                        //Getting the registration token from the intent
                         token = intent.getStringExtra("token");
                        //Displaying the token as toast
//                        Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();
//                        Intent emb = new Intent(SetingActivity.this, MainActivity.class);
//sout
                        System.out.println( "Registration token:" + token);
                        //if the intent is not with success then displaying error messages
                    } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                        Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                    }
                }
            };

            //Checking play service is available or not
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

            //if play service is not available
            if(ConnectionResult.SUCCESS != resultCode) {
                //If play service is supported but not installed
                if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    //Displaying message that play service is not installed
                    Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                    GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                    //If play service is not supported
                    //Displaying an error message
                } else {
                    Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
                }

                //If play service is available
            } else {
                //Starting intent to register device
                Intent itent = new Intent(this, GCMRegistrationIntentService.class);
                startService(itent);
            }
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
        {
            int id = item.getItemId();
            DatabaseHandler db = new DatabaseHandler(SetingActivity.this);
            Cursor c = db.getAllContacts();
            if (c.moveToFirst()) {
                finish();
            }else{
            }
            db.close();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        DatabaseHandler db = new DatabaseHandler(SetingActivity.this);
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            finish();
        }else{
        }
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        BusProvider.getInstance().register(this);
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }
    private void usePost(String email, String name, String tp,String regId){
        User_registetion(email,name,tp,regId);

    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
//        BusProvider.getInstance().unregister(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);


    }


    public void User_registetion(String email, String name, String tp, String rg_togent){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<Object_register> callback = new Callback<Object_register>() {


            @Override
            public void success(Object_register models, Response response) {

                if(models.getResponseCode() == 0){

                    Intent itent = new Intent(SetingActivity.this, Splash.class);
                    finish();
                    startActivity(itent);

                }else{
                    Intent itent = new Intent(SetingActivity.this, Splash.class);
                    finish();
                    startActivity(itent);

//                    BusProvider.getInstance().post(produceErrorEvent(models.getResponseCode(), models.getMessage()));

                }

            }

            @Override
            public void failure(RetrofitError error) {
                if(error != null ){
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
//                BusProvider.getInstance().post(produceErrorEvent(-200,error.getMessage()));
            }
        };
        communicatorInterface.post_registetion(email,name,tp,rg_togent,callback);
    }







    @Produce
    public ServerEvent produceServerEvent_registetion (Object_register serverResponse) {

        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }



    private void getBooks(){
        //While the app fetched data we are displaying a progress dialog
       String ROOT_URL ="http://projects.yogeemedia.com/preview/embassy";

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        Interface api = adapter.create(Interface.class);

        //Defining the method
        api.getBooks( new Callback<Response>() {
                @Override
                public void success(Response detailsResponse, Response response2) {

                    String detailsString = getStringFromRetrofitResponse(detailsResponse);

                    try {
                        JSONObject object = new JSONObject(detailsString);

                        Log.e("List", String.valueOf(object.getJSONArray("contacts")));

                        writeToFile(object);

                        //In here you can check if the "details" key returns a JSONArray or a String

                    } catch (JSONException e) {

                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("List",error.toString());

                }});}



    private void get_fastaid(){
        //While the app fetched data we are displaying a progress dialog
        String ROOT_URL ="http://projects.yogeemedia.com/preview/embassy";

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        Interface api = adapter.create(Interface.class);

        //Defining the method
        api.getfasttaid( new Callback<Response>() {
            @Override
            public void success(Response detailsResponse, Response response2) {

                String detailsString = getStringFromRetrofitResponse(detailsResponse);

                try {
                    JSONObject object = new JSONObject(detailsString);

                    Log.e("List", String.valueOf(object.getJSONArray("contacts")));

                    writeToFile_fest(object);

                    //In here you can check if the "details" key returns a JSONArray or a String

                } catch (JSONException e) {

                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("List",error.toString());

            }});}

        public static String getStringFromRetrofitResponse(Response response) {
            //Try to get response body
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {

                reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                String line;

                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();

        }



            private void writeToFile(JSONObject list) {

                ContextWrapper contextWrapper = new ContextWrapper(SetingActivity.this);
                directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
                myInternalFile = new File(directory, "embecy");

                Log.e("contacts",list.toString());
                if (jsonmenues_stored == null) {
                    try {
                        FileOutputStream fos = new FileOutputStream(myInternalFile);
                        fos.write(list.toString().getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


    private void writeToFile_fest(JSONObject list) {

        ContextWrapper contextWrapper = new ContextWrapper(SetingActivity.this);
        directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, "firstaid");

        Log.e("firstaid",list.toString());
        if (jsonmenues_stored == null) {
            try {
                FileOutputStream fos = new FileOutputStream(myInternalFile);
                fos.write(list.toString().getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
