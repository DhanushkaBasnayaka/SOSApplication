package trimdevelopmentcom.sos;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import trimdevelopmentcom.sos.Event_Bus.BusProvider;
import trimdevelopmentcom.sos.Event_Bus.ErrorEvent;
import trimdevelopmentcom.sos.Event_Bus.ServerEvent;
import trimdevelopmentcom.sos.Sever_task.Communicator;

public class MainActivity extends Fragment implements View.OnClickListener {
     Camera.Parameters p;
    private Boolean isFabOpen = false;
    private boolean isLighOn = false;
    private boolean isLighOn_sos = false;
    Location location;
    String SOS_E_mail, SOS_E_mail2, sos_phone, email, sos_Phone2, Sos_countdown_masseg, pin, adres, sos_phone_coud, sos_phone_coud2, longitude, latitude;
    int play = 1;
    private View rootView;
    public static Bus bus;
    private Camera camera;
    SmsManager smsManager;
    //double longitude = -122.084095
    PackageManager pm;
    String jsonmenues_stored;
    File myInternalFile;
    File directory;
    int blink =0;
    Thread t;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 1;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT_SMS = 2;

    private static final int REQUEST_EXTERNAL_STORAGE_RESULT_CALL = 3;

    private static final String jsonFilePath = "F:\\nikos7\\Desktop\\filesForExamples\\jsonFile.json";

    TextView toobar_title;

    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;
    public  String ambulens, police_number, fire, country_defolt,country_defolt2;
    public MainActivity() {

    }
    @Override
    public void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
        }
//        mTeleportClient.disconnect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://trimdevelopmentcom.sos/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }


    private ImageView fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    TextView elam, compos, lhit, Ambulance, police, fir;

    MediaPlayer mPlayer;
    public static boolean isSignedIn = false;
    GlobalClass globalvariable;
    DatabaseHandler db;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    int click_count = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        globalvariable = (GlobalClass) getActivity().getApplicationContext();

        longitude=globalvariable.getLongitude();
        latitude= globalvariable.getLatitude();
        adres=globalvariable.getAddres();



        elam = (TextView)rootView.findViewById(R.id.elam);
        compos = (TextView)rootView. findViewById(R.id.compos);
        lhit = (TextView)rootView. findViewById(R.id.lhit);
        Ambulance = (TextView)rootView. findViewById(R.id.Ambulance);
        police = (TextView)rootView. findViewById(R.id.police);
        fir = (TextView) rootView.findViewById(R.id.fir);


        //puter manu

        final Button sos_buton = (Button) rootView.findViewById(R.id.textView8);
        TextView count = (TextView) rootView.findViewById(R.id.count);



        Drawable img = getActivity().getApplication().getResources().getDrawable(R.drawable.sos);
        sos_buton.setBackground(img);

        sos_buton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Drawable img = getActivity().getApplication().getResources().getDrawable(R.drawable.sos_click);
                sos_buton.setBackground(img);

                send_messeg();
//                Content_clases content_clases = new  Content_clases();
//                content_clases.send_email();

                return false;
            }
        });


        //sos.setCompoundDrawables(null, getResources().getDrawable(R.drawable.help_clic), null, null);
//        Drawable img2 = getApplication().getResources().getDrawable(R.drawable.help_clic);
//        sos.setCompoundDrawablesWithIntrinsicBounds(null, img2, null, null);
        Ambulance.setOnClickListener(this);
        police.setOnClickListener(this);
        fir.setOnClickListener(this);

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Count_down.class);

                startActivity(intent);
//                Count_down embece_activity = new Count_down();
////                Bundle trBundle = new Bundle();
////                trBundle.putSerializable(AppConstance.TRAVEL_LIST, travelLists);
////                travelListFragment.setArguments(trBundle);
//                FragmentManager fm_embece_activity =getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction_embece_activity = fm_embece_activity.beginTransaction();
//                transaction_embece_activity.replace(R.id.container, embece_activity);
//                transaction_embece_activity.commit();

            }
        });

        fab1 = (ImageView) rootView.findViewById(R.id.fab1);
        fab2 = (ImageView) rootView.findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
        lhit.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);


        compos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Count_down = new Intent(getActivity(), Compose.class);

                startActivity(Count_down);

            }
        });

        elam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//play == 1 now ply
                if (play == 1) {
                    Uri myUri1 = Uri.parse("android.resource://trimdevelopmentcom.sos/" + R.raw.hosannatamil);

                    mPlayer = new MediaPlayer();


                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

                    }

                    try {
                        Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
                        Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
                        Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
                        Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");

                        Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

                        Object subtitleInstance = constructor.newInstance(MainActivity.this, null, null);

                        Field f = cSubtitleController.getDeclaredField("mHandler");

                        f.setAccessible(true);
                        try {
                            f.set(subtitleInstance, new Handler());
                        } catch (IllegalAccessException e) {
                        } finally {
                            f.setAccessible(false);
                        }

                        Method setsubtitleanchor = mPlayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

                        setsubtitleanchor.invoke(mPlayer, subtitleInstance, null);
                        //Log.e("", "subtitle is setted :p");
                    } catch (Exception e) {
                    }

                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mPlayer.setDataSource(getActivity(), myUri1);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (SecurityException e) {
                        Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mPlayer.prepare();
                    } catch (IllegalStateException e) {
                        Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    }

                    mPlayer.start();
                    play = 2;
                } else {
                    play = 1;
                    mPlayer.stop();
                }
                // mucick player

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(getActivity()).addApi(AppIndex.API).build();
        return rootView;
    }

    public void send_messeg() {
        DatabaseHandler  db2 = new DatabaseHandler(getActivity());
        Cursor c = db2.getAllContacts();

        System.out.println("all data" + c.getCount());
        if (c.moveToFirst()) {
//            name=(c.getString(1));
            email=(c.getString(2));

            sos_phone = c.getString(4);
            sos_phone_coud = c.getString(7);
            sos_phone_coud2 = c.getString(8);
            SOS_E_mail = c.getString(9);
            SOS_E_mail2= c.getString(9);
            System.out.println("sos_phone" + sos_phone);
            sos_Phone2 = c.getString(5);
            Sos_countdown_masseg = c.getString(11);
            pin = c.getString(13);
            country_defolt = c.getString(14);


        }




        Log.e("email ",email);
        Log.e("sos_phone ",sos_phone);
        Log.e("sos_phone_coud ",sos_phone_coud);
        Log.e("sos_phone_coud2 ",sos_phone_coud2);
        Log.e("SOS_E_mail ",SOS_E_mail);
        Log.e("SOS_E_mail2 ",SOS_E_mail2);
        Log.e("sos_Phone2 ",sos_Phone2);
        Log.e("Sos_countdown_masseg ",Sos_countdown_masseg);
        Log.e("pin ",pin);
        Log.e("country_defolt ",country_defolt);
        db2.close();

        open_SMS();
    }


    private void openCamera() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(),
                            "External storage permission required to save images",
                            Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_EXTERNAL_STORAGE_RESULT);
            }
            System.out.println("permission.CAMERA");
        } else {
            callCameraApp();

            System.out.println(" callCameraApp();");
        }


    }




    private void open_SMS() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                Sms_permition();
            } else {

                if (shouldShowRequestPermissionRationale(android.Manifest.permission.SEND_SMS)) {
                    Toast.makeText(getActivity(),
                            "External storage permission required to  SEND_SMS",
                            Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.SEND_SMS},
                        REQUEST_EXTERNAL_STORAGE_RESULT_SMS);
            }
            System.out.println("permission.CAMERA");
        } else {
            Sms_permition();

            System.out.println(" callCameraApp();");
        }


    }

    private void open_CALL(String Number) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                call_now(Number);
                ;
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(getActivity(),
                            "External storage permission required to  CALL",
                            Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE},
                        REQUEST_EXTERNAL_STORAGE_RESULT_CALL);
            }
            System.out.println("permission.CAMERA");
        } else {
            call_now(Number);
            ;

            System.out.println(" callCameraApp();");
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                Toast.makeText(getActivity(),
                        "External write permission has not been granted, cannot Camera",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (requestCode == REQUEST_EXTERNAL_STORAGE_RESULT_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Sms_permition();
            } else {
                Toast.makeText(getActivity(),
                        "External write permission has not been granted, cannot Send SMS",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (requestCode == REQUEST_EXTERNAL_STORAGE_RESULT_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //call_now(Number);
            } else {
                Toast.makeText(getActivity(),
                        "External write permission has not been granted, cannot Send SMS",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }



    }






    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lhit:
                openCamera();
                animateFAB();
                break;
            case R.id.fab1:

                if (isLighOn) {

//                    Log.i("info", "Flashlight is turn off") ;
//                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                    camera.setParameters(p);
//                    camera.stopPreview();
//                    camera.autoFocus(new Camera.AutoFocusCallback() {
//                        public void onAutoFocus(boolean success, Camera camera) {
//                        }
//                    });
//                    isLighOn = false;
                    turnOffFlash();
                    break;
                } else {
//
//                    Log.i("info","torch is turn on");
//                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//                    camera.setParameters(p);
//                    camera.startPreview();
//
//                    isLighOn = true;
                    turnOnFlash();
                    break;
                }


            case R.id.fab2:

                if(blink==0){
                blink(50,1000);
                    blink=1;
                }else{

                    blink=0;
                }
//                camera.stopPreview();


                break;
            case R.id.police:
                //mssege

//                call_now();
                if (globalvariable.getPolice() != null && !globalvariable.getPolice().equalsIgnoreCase(" ")){
                    open_CALL( globalvariable.getPolice());
                    Log.e("globalvariable.getPolice()",globalvariable.getPolice());
                }

                break;
            case R.id.Ambulance:
//                call_now(ambulens);
                if (globalvariable.getAmbulance() != null && !globalvariable.getAmbulance().equalsIgnoreCase(" ")){
                    open_CALL(globalvariable.getAmbulance());
                }
                break;
            case R.id.fir:
//                call_now(fire);
                if (globalvariable.getFir() != null && !globalvariable.getFir().equalsIgnoreCase(" ")){
                    open_CALL(globalvariable.getFir());
                }

                break;



        }
    }





    private void call_now(final String number) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custem_callbo);
        dialog.setTitle("Call Now");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                try {

                    startActivity(callIntent);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void animateFAB() {

        if (isFabOpen) {
            // lhit.startAnimation(rotate_backward);

            Drawable img = getActivity().getApplication().getResources().getDrawable(R.drawable.lhite);
            lhit.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");


        } else {
            Drawable img = getActivity().getApplication().getResources().getDrawable(R.drawable.lhite_click);
            lhit.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
            // lhit.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;

            Log.d("Raj", "open");
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("Raj", ""+Data_coltrola.Embece_data.size());

        if(Data_coltrola.Embece_data.size()==0){
            Jso_filereder();
        }
    }



    public void Sms_permition() {


        String map_link = "https://www.google.lk/maps/@" + latitude + "," + longitude + ",16z?hl=en";
        String final_mseg = Sos_countdown_masseg + "  " + "Location" + adres + "  " + "Map Link" +" "+ map_link;

        String final_number = sos_phone_coud + "" + sos_phone;




        if (sos_Phone2 != null && !sos_Phone2.equalsIgnoreCase(" ")) {
            // String final_mseg2=Sos_countdown_masseg+"  "+"Location"+adres;
            String final_number2 = sos_phone_coud2 + "" + sos_Phone2;

            Log.e("final_number2",final_number2);
            Log.e("final_number2",final_mseg);
            Log.e("final_number2",final_number);
            Log.e("final_number2",adres);

            sendSMS(final_number2, final_mseg, final_number, adres);
            System.out.println("final_number2" + final_number2);
            send_email();

        } else {
            sendSMS("0", final_mseg, final_number, adres);
            send_email();

            Log.e("final_number2",final_mseg);
            Log.e("final_number2",final_number);
            Log.e("final_number2",adres);
        }


    }

    public void send_email() {

        LatLng location = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        String map_link = "https://www.google.lk/maps/@" + latitude + "," + longitude + ",16z?hl=en";
        String final_mseg = Sos_countdown_masseg + "  " + "Location" + adres + "  " + "Map Link" +" "+ map_link;

        String to_email = SOS_E_mail;
        String to_email2 = SOS_E_mail2;
        String for_email = email;


        if (to_email2 != null && !to_email2.equalsIgnoreCase(" ")) {
            // String final_mseg2=Sos_countdown_masseg+"  "+"Location"+adres;

            usePost(to_email2, for_email, final_mseg);
            usePost(to_email, for_email, final_mseg);

        } else {
            usePost(for_email, for_email, final_mseg);
        }


    }


    public void sendSMS(String phoneNo, String msg, String phoneNo2, String Addres) {

        LatLng location2 = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        String location = Double.valueOf(latitude) + "," + Double.valueOf(longitude);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22

        System.out.println("phoneNo2" + location);
        try {
            smsManager = SmsManager.getDefault();
            if (!phoneNo2.equalsIgnoreCase("0")) {

                smsManager.sendTextMessage(phoneNo2, null, msg, null, null);
                Toast.makeText(getActivity(), "Message Sent",
                        Toast.LENGTH_LONG).show();


                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Toast.makeText(getActivity(), "Message Sent",
                        Toast.LENGTH_LONG).show();


                globalvariable.setPush_addres_gold(Addres);
                globalvariable.setPush_location_gold(String.valueOf(location));
                globalvariable.setPush_messeg_one_gold(phoneNo);
                globalvariable.setPush_messeg_two_gold(phoneNo2);
                globalvariable.setMsg_stetes("Was Sent");
                globalvariable.setPush_date_gold(dateFormat.format(cal.getTime()));
                send_susses();

            } else {

                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Toast.makeText(getActivity(), "Message Sent",
                        Toast.LENGTH_LONG).show();
                // send_susses(phoneNo,msg);

                System.out.println("phoneNo" + phoneNo);


                globalvariable.setPush_addres_gold(Addres);
                globalvariable.setPush_location_gold(String.valueOf(location));
                globalvariable.setPush_messeg_one_gold(phoneNo);
                globalvariable.setPush_messeg_two_gold("0");
                globalvariable.setMsg_stetes("Was Sent");
                globalvariable.setPush_date_gold(dateFormat.format(cal.getTime()));
                send_susses();


            }


            // save messeg in android
        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();printStackTrace

            if (!phoneNo2.equalsIgnoreCase("0")) {

                smsManager.sendTextMessage(phoneNo2, null, msg, null, null);
                Toast.makeText(getActivity(), "Message Sent",
                        Toast.LENGTH_LONG).show();


                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Toast.makeText(getActivity(), "Message Sent",
                        Toast.LENGTH_LONG).show();
                System.out.println("phoneNo2" + phoneNo2);

                globalvariable.setPush_addres_gold(Addres);
                globalvariable.setPush_location_gold(String.valueOf(location));
                globalvariable.setPush_messeg_one_gold(phoneNo);
                globalvariable.setPush_messeg_two_gold(phoneNo2);
                globalvariable.setMsg_stetes("Wasn't Sent");
                globalvariable.setPush_date_gold(dateFormat.format(cal.getTime()));
                send_susses();

            } else {

                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Toast.makeText(getActivity(), "Message Sent",
                        Toast.LENGTH_LONG).show();
                // send_susses(phoneNo,msg);

                System.out.println("phoneNo" + phoneNo);


                globalvariable.setPush_addres_gold(Addres);
                globalvariable.setPush_location_gold(String.valueOf(location));
                globalvariable.setPush_messeg_one_gold(phoneNo);
                globalvariable.setPush_messeg_two_gold("0");
                globalvariable.setMsg_stetes("Wasn't Sent");
                globalvariable.setPush_date_gold(dateFormat.format(cal.getTime()));
                send_susses();


            }

        }
    }

    private void send_susses() {

        Obj_Messag item = new Obj_Messag();
        item.setPush_messeg_two_gold(globalvariable.getPush_messeg_two_gold());
        item.setPush_messeg_one_gold(globalvariable.getPush_messeg_one_gold());
        item.setPush_email_one_gold("Dhanushka@gmail.com");
        item.setPush_email_two_gold("Dhanushka@gmail.com");
        item.setPush_location_gold(globalvariable.getPush_location_gold());
        item.setPush_addres_gold(globalvariable.getPush_addres_gold());
        item.setPush_date_gold(globalvariable.getPush_date_gold());
        item.setMsg_stetes("Send!");
        DatabaseHandler dbh = new DatabaseHandler(getActivity());
        dbh.insertContact_messeg(item);
        Toast.makeText(getActivity().getBaseContext(), "Inserted",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();


//        checkPlayServices();
        BusProvider.getInstance().register(this);


    }




    private void usePost(String to_email, String for_email, String messeg){
        Communicator  communicator = new  Communicator();
        communicator.Email_Post(to_email,for_email,messeg);
        Log.e("to_email",to_email);
        Log.e("for_email",for_email);
    }



    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent){
        Toast.makeText(getActivity(), ""+serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        if(serverEvent.getServerResponse().getMessage() != null){
            Toast.makeText(getActivity(), "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent){
        Toast.makeText(getActivity(), "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }
    private void callCameraApp() {


         pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e("err", "Device has no camera!");
            return;
        }
        camera = Camera.open();
       p = camera.getParameters();



}

    private void Jso_filereder() {
        String filepath = "hasdata";
        Log.e("List", "jsonMainNode.toString()");

        ContextWrapper contextWrapper = new ContextWrapper(getActivity());
        directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, "embecy");

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
                Data_coltrola.Embece_data.clear();

                for (int i = 0; i < lengthJsonArr; i++) {

                    JSONObject menuObject = jsonMainNode.getJSONObject(i);
                    String country_code = menuObject.getString("country_code");
                    String country_name = menuObject.getString("country_name");
                    String country_flag = menuObject.getString("country_flag");
                    JSONArray embassy = menuObject.getJSONArray("embassy");
                    String Ambulance_j = menuObject.getString("ambulance_no");
                    String fir_j = menuObject.getString("fire_no");
                    String police_j = menuObject.getString("police_no");


                    if (country_code.toLowerCase().equalsIgnoreCase(globalvariable.getCounry_cord().toLowerCase()) || country_name.toLowerCase().equalsIgnoreCase(globalvariable.getDefolt_cuntry().toLowerCase())) {
                        String Ambulance = menuObject.getString("ambulance_no");
                        String fir = menuObject.getString("fire_no");
                        String police = menuObject.getString("police_no");
                        globalvariable.setAmbulance(Ambulance);
                        globalvariable.setFir(fir);
                        globalvariable.setPolice(police);


                    }
                    Object_Embicy Embicy = new Object_Embicy();
                    Embicy.setCountry_code(country_code);
                    Embicy.setEmb_name(country_name);
                    Embicy.setEmbassy(embassy);
                    Embicy.setEmb_image(country_flag);
                    Embicy.setAmbulance(Ambulance_j);
                    Embicy.setPolice(police_j);
                    Embicy.setFir(fir_j);
                    Data_coltrola.Embece_data.add(Embicy);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void blink(final int delay, final int times) {
         t = new Thread() {
            public void run() {
                try {

                    for (int i=0; i < times*2; i++) {
                        if(blink==1){
                            if (isLighOn) {
                                turnOffFlash();
                            } else {
                                turnOnFlash();
                            }
                            sleep(delay);

                        }else{
                            turnOffFlash();
                            break;

                        }

                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }

    private void turnOnFlash() {
        if (!isLighOn) {
            if (camera == null || p == null) {
                return;
            }

            p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            isLighOn = true;
        }

    }

    private void turnOffFlash() {
        if (isLighOn) {
            if (camera == null || p == null) {
                return;
            }
            p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
            isLighOn = false;
        }
    }




}