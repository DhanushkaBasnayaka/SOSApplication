package trimdevelopmentcom.sos;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import trimdevelopmentcom.sos.Event_Bus.BusProvider;
import trimdevelopmentcom.sos.Event_Bus.ErrorEvent;
import trimdevelopmentcom.sos.Event_Bus.ServerEvent;
import trimdevelopmentcom.sos.Sever_task.Communicator;


@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi")

public class Count_down extends AppCompatActivity implements View.OnClickListener {
    TextView coun_hr, coun_mint, coun_ss, save, active, cancel;
    CounterClass timer;
    int valu_pin = 0;
    TextView elam, compos, toobar_title, Ambulance, police, fir, map, embce, sos, notifi, add;
    EditText Sos_countdown_masseg_new_edit;
    String country_defolt;
    String SOS_E_mail2;
    TableRow Time_click;
    String email;
    DatabaseHandler db;
    Toolbar toolbar;

    String SOS_E_mail, sos_phone, sos_Phone2, Sos_countdown_masseg, pin, adres, sos_phone_coud, sos_phone_coud2;
    Communicator communicator;
    GlobalClass globalvariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
//        rootView = inflater.inflate(R.layout.activity_count_down, container, false);

        globalvariable = (GlobalClass) getApplicationContext();
        communicator = new Communicator();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toobar_title = (TextView) toolbar.findViewById(R.id.name_toll2);
        toobar_title.setText("Count Down");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adres = globalvariable.getAddres();


        db = new DatabaseHandler(Count_down.this);

        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            email = c.getString(3);
            sos_phone = c.getString(4);
            sos_phone_coud = c.getString(7);
            sos_phone_coud2 = c.getString(8);
            SOS_E_mail = c.getString(9);
            SOS_E_mail2 = c.getString(10);
            System.out.println("sos_phone" + sos_phone);
            sos_Phone2 = c.getString(5);
            Sos_countdown_masseg = c.getString(11);
            pin = c.getString(13);
            country_defolt = c.getString(14);

        }

        System.out.println("sos_phone" + pin);
        db.close();

        active = (TextView) findViewById(R.id.active);
        cancel = (TextView) findViewById(R.id.cancel);
        coun_hr = (TextView) findViewById(R.id.coun_hr);
        coun_mint = (TextView) findViewById(R.id.coun_mint);
        coun_ss = (TextView) findViewById(R.id.coun_ss);
        Time_click = (TableRow) findViewById(R.id.input_layout_phone);
        /// sos.setBackground(getResources().getDrawable(R.drawable.help_clic));
        // sos.setCompoundDrawables(null, getResources().getDrawable(R.drawable.help_clic), null, null);
        Sos_countdown_masseg_new_edit = (EditText) findViewById(R.id.Sos_countdown_masseg_new);
        Sos_countdown_masseg_new_edit.setText(Sos_countdown_masseg);

        Sos_countdown_masseg_new_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        coun_hr.setText("00");
        coun_mint.setText("01");  // X * 60000
        coun_ss.setText("00");  // X * 1000
        timer = new CounterClass(600000, 1000);

        active.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(valu_pin==0){
                    Enter_cord();
                }


            }
        });
        coun_hr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (valu_pin == 0) {
                    Select_count();
                } else {


                }


            }
        });
        coun_mint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (valu_pin == 0) {
                    Select_count();
                } else {


                }


            }
        });
        coun_ss.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (valu_pin == 0) {
                    Select_count();
                } else {


                }


            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valu_pin==1){
                    Enter_cord_calsel();
                }


            }
        });

    }

    private void Enter_cord() {
        final Dialog dialog1 = new Dialog(Count_down.this);
        dialog1.setContentView(R.layout.custom_alert);
        dialog1.setTitle("Enter Pass Cord");

        final EditText cord_e = (EditText) dialog1.findViewById(R.id.cord);
        Button ok = (Button) dialog1.findViewById(R.id.ok);
        final TextView not = (TextView) dialog1.findViewById(R.id.not);
        not.setVisibility(View.GONE);

        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pin.equalsIgnoreCase(cord_e.getText().toString())) {
                    timer.start();
                    valu_pin = 1;
                    dialog1.cancel();
                } else {
                    not.setVisibility(View.VISIBLE);
                    not.setText("YOU ENTER PIN INCORECT");
                }
            }
        });

        dialog1.show();


    }


    private void Enter_cord_calsel() {
        final Dialog dialog1 = new Dialog(Count_down.this);
        dialog1.setContentView(R.layout.custom_alert);
        dialog1.setTitle("Enter Pass Cord");


        final EditText cord_enter = (EditText) dialog1.findViewById(R.id.cord);
        Button ok = (Button) dialog1.findViewById(R.id.ok);
        final TextView not = (TextView) dialog1.findViewById(R.id.not);
        not.setVisibility(View.GONE);

        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                if (pin.equalsIgnoreCase(cord_enter.getText().toString())) {
                    timer.cancel();
                    ;
                    valu_pin = 0;
                    dialog1.cancel();
                } else {
                    not.setVisibility(View.VISIBLE);
                    not.setText("YOU ENTER PIN INCORECT");
                }

            }
        });

        dialog1.show();


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {


        }

    }


    private void Select_count() {
        final Dialog dialog1 = new Dialog(Count_down.this);
        dialog1.setContentView(R.layout.custom_alert_count);

        final int[] Selected_hr = new int[1];
        final int[] Selected_min = new int[1];
        final int[] Selected_sec = new int[1];

        final NumberPicker hr = (NumberPicker) dialog1.findViewById(R.id.hr);
        final NumberPicker min = (NumberPicker) dialog1.findViewById(R.id.min);
        final NumberPicker sec = (NumberPicker) dialog1.findViewById(R.id.sec);
        Button ok = (Button) dialog1.findViewById(R.id.ok);
        hr.setMinValue(01);
        hr.setMaxValue(12);
        min.setMinValue(01);
        min.setMaxValue(60);
        sec.setMinValue(01);
        sec.setMaxValue(60);
        hr.setWrapSelectorWheel(false);
        min.setWrapSelectorWheel(false);
        sec.setWrapSelectorWheel(false);

        hr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                Selected_hr[0] = newVal;
                System.out.println("Selected Value is : " + newVal);

            }
        });
        min.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Selected_min[0] = newVal;

                System.out.println("Selected Value is : " + newVal);
            }
        });
        sec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Selected_sec[0] = newVal;

                System.out.println("Selected Value is : " + newVal);
            }
        });

        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Selected_hr[0]" + Selected_hr[0] + Selected_min[0] + Selected_sec[0]);
                int fina_time = (Selected_hr[0] * 3600000) + (Selected_min[0] * 60000) + (Selected_sec[0] * 1000);
                System.out.println("fina_time" + fina_time);
                coun_hr.setText(" " + Selected_hr[0]);
                coun_mint.setText(" " + Selected_min[0]);  // X * 60000
                coun_ss.setText(" " + Selected_sec[0]);  // X * 1000

                timer = new CounterClass(fina_time, 1000);

                dialog1.cancel();

            }
        });

        dialog1.show();


    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onFinish() {
            String map_link = "https://www.google.lk/maps/@" + globalvariable.getLatitude() + "," + globalvariable.getLongitude() + ",16z?hl=en";
            String final_mseg = Sos_countdown_masseg + "  " + "Location" + adres + "  " + "Map Link" + " " + map_link;


            String final_number = sos_phone_coud + "" + sos_phone;

            sendSMS(final_number, final_mseg);
            if (sos_Phone2 != null && !sos_Phone2.equalsIgnoreCase(" ")) {
                String map_link2 = "https://www.google.lk/maps/@" + globalvariable.getLatitude() + "," + globalvariable.getLongitude() + ",16z?hl=en";
                String final_mseg2 = Sos_countdown_masseg + "  " + "Location" + adres + "  " + "Map Link" + " " + map_link2;

                String final_number2 = sos_phone_coud2 + "" + sos_Phone2;
                sendSMS(final_number2, final_mseg2);

            }

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

//            textViewTime.setText("Completed.");
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;

            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            StringTokenizer tokens = new StringTokenizer(hms, ":");
            String h = tokens.nextToken();// this will contain "Fruit"
            String m = tokens.nextToken();
            String s = tokens.nextToken();

            coun_hr.setText(h);
            coun_mint.setText(m);
            coun_ss.setText(s);
        }
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(Count_down.this, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(Count_down.this, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    private void usePost(String to_email, String for_email, String messeg) {
        communicator.Email_Post(to_email, for_email, messeg);
    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        Toast.makeText(Count_down.this, "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        if (serverEvent.getServerResponse().getMessage() != null) {
            Toast.makeText(Count_down.this, "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(Count_down.this, "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
