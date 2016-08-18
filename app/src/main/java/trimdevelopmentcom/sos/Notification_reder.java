package trimdevelopmentcom.sos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Notification_reder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView setting = (TextView) toolbar.findViewById(R.id.setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Count_down = new Intent(Notification_reder.this, SetingActivity.class);

                startActivity(Count_down);

            }
        });
        TextView toobar_title = (TextView) toolbar.findViewById(R.id.name_toll2);
        toobar_title.setText("Notification");

        TextView topick = (TextView) findViewById(R.id.topick_red);
        TextView infromation = (TextView) findViewById(R.id.infromation_red);

        TextView weblink = (TextView) findViewById(R.id.weblink_red);

        TextView date = (TextView) findViewById(R.id.date_red);



        Intent intent = getIntent();

        String titil = intent.getStringExtra("titil");
        String messeg = intent.getStringExtra("msg");
        String web = intent.getStringExtra("web");
        String dat_mesg = intent.getStringExtra("dater");

        topick.setText(titil);
        infromation.setText(messeg);
        weblink.setText(web);
        date.setText(dat_mesg);

    }

    @Override
    public void onBackPressed(){
        finish();



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
