package trimdevelopmentcom.sos;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.StringTokenizer;


public class Embec_ditilse extends AppCompatActivity implements View.OnClickListener {
    TextView addres_emb, phone_local, phone_inter, tv_fireVal,tv_ambulanceVal, tv_ambulanceTitl,fax_local, fax_inter, Email,tv_policeVal, map, embce, sos, notifi, add, Embass,tv_policeTitl;
    private Context ctx;
    public Resources res;
    String Embassy_contact_international,Embassy_address,Embassy_contact_local,Of_name,Embassy_email,Embassy_fax_international,
            Embassy_fax_local,Embassy_flag,Embassy_name,Embecy_of_image;
    Object_Embicy tempValues=null;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT_CALL = 3;
    Typeface typeFace, typeFace1, typeFace2;
    ImageView  img_policeCall,img_ambulanceCall,img_fireCall;
    ImageView embassyFlag;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    String Ambulance;
    String fir;
    String police;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String strtext=getArguments().getString("message");
        Bundle bundle = getIntent().getExtras();
        String strtext = bundle.getString("message");

        for(int i=0;i<Data_coltrola.Embece_data_filter.size();i++){

            Object_in_Embicy itam = Data_coltrola.Embece_data_filter.get(i);

            if(strtext.toLowerCase().equalsIgnoreCase( itam.getIdemb().toLowerCase())){
                        Embassy_contact_international= itam.getEmbassy_contact_international();
                        Embassy_address=itam.getEmbassy_address();
                        Embassy_contact_local=itam.getEmbassy_contact_local();
                        Of_name=itam.getOf_name();
                        Embassy_email=itam.getEmbassy_email();
                        Embassy_fax_international=itam.getEmbassy_fax_international();
                        Embassy_fax_local= itam.getEmbassy_fax_local();
                        Embassy_flag=itam.getEmbassy_flag();
                        Embassy_name=itam.getEmbassy_name();;
                        Embecy_of_image=itam.getEmbecy_of_image();
                        Ambulance=itam.getAmbulance();
                        fir=itam.getFir();
                        police=itam.getPolice();
                break;



            }


        }
        setContentView(R.layout.activity_embec_ditilse);

        Toolbar    toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView   toobar_title = (TextView) toolbar.findViewById(R.id.name_toll2);
        toobar_title.setText("Embassy Information");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        GlobalClass globalvariable = (GlobalClass) getApplicationContext();
      Log.e("message",strtext);


        img_policeCall = (ImageView) findViewById(R.id.img_policeCall);
        img_ambulanceCall = (ImageView) findViewById(R.id.img_ambulanceCall);
        img_fireCall = (ImageView) findViewById(R.id.img_fireCall);

        img_policeCall.setOnClickListener(this);
        img_ambulanceCall.setOnClickListener(this);
        img_fireCall.setOnClickListener(this);

        addres_emb = (TextView) findViewById(R.id.addres_emb);
        Embass = (TextView) findViewById(R.id.Embass);
        phone_local = (TextView)findViewById(R.id.phone_local);
        phone_inter = (TextView) findViewById(R.id.phone_inter);
        fax_inter = (TextView) findViewById(R.id.fax_inter);
        fax_local = (TextView) findViewById(R.id.fax_local);
        ImageView img = (ImageView) findViewById(R.id.img_embassyFlag);
         embassyFlag = (ImageView) findViewById(R.id.embassyFlag);




        // fax_inter = (TextView) findViewById(R.id.fax_inter);
        Email = (TextView) findViewById(R.id.Email);

        tv_policeTitl = (TextView)findViewById(R.id.tv_policeTitl);
        tv_policeVal = (TextView)findViewById(R.id.tv_policeVal);
        tv_ambulanceVal = (TextView)findViewById(R.id.tv_ambulanceVal);
        tv_fireVal = (TextView)findViewById(R.id.tv_fireVal);
        TextView  Numbrs = (TextView)findViewById(R.id.Numbrs);
        Numbrs.setText(Embassy_name+" "+"Emergency Numbrs");

        tv_ambulanceTitl = (TextView)findViewById(R.id.tv_ambulanceTitl);

        Embass.setText(Embassy_name+" "+"Embassy in"+" "+Of_name);
        tv_policeVal.setText(police);
        tv_ambulanceVal.setText(Ambulance);
        tv_fireVal.setText(fir);
        addres_emb.setText(Embassy_address);
        phone_local.setText("Local:"+" "+Embassy_contact_local);
        phone_inter.setText("International:"+" "+Embassy_contact_international);

        fax_local.setText("Local:"+" "+Embassy_fax_local);
        fax_inter.setText("International:"+" "+Embassy_fax_international);
        Email.setText("Local:"+" "+Embassy_email);


        String result2 = Embassy_flag.substring(Embassy_flag.lastIndexOf("/") + 1);
        StringTokenizer tokens2 = new StringTokenizer(result2, ".");
        final String image2 = tokens2.nextToken();// this will contain "Fruit"
        embassyFlag.setImageResource(Embec_ditilse.this.getResources().getIdentifier("drawable/"+image2.toLowerCase(), null,getPackageName()));


        String result = Embecy_of_image.substring(Embecy_of_image.lastIndexOf("/") + 1);

        StringTokenizer tokens = new StringTokenizer(result, ".");
        final String image = tokens.nextToken();//

        String uri = "@drawable/"+image;
        int imageResource = getResources().getIdentifier(uri, null,getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        img.setImageDrawable(res);

        client = new GoogleApiClient.Builder(Embec_ditilse.this).addApi(AppIndex.API).build();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_policeCall:
                Call(tv_policeVal.getText().toString());

                break;
            case R.id.img_ambulanceCall:

                Call(tv_ambulanceVal.getText().toString());

                break;
            case R.id.img_fireCall:
                Call(tv_fireVal.getText().toString());

                break;


        }
    }

    private void Call(String Number) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Embec_ditilse.this,
                    android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                call_now(Number);
                ;
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(Embec_ditilse.this,
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
            if (requestCode == REQUEST_EXTERNAL_STORAGE_RESULT_CALL) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    call_now();
                } else {
                    Toast.makeText(Embec_ditilse.this,
                            "External write permission has not been granted, cannot Camera",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
    }

    private void call_now(final String number) {

        final Dialog dialog = new Dialog(Embec_ditilse.this);
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
                    Toast.makeText(Embec_ditilse.this, "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public Bitmap blurBitmap(Bitmap bitmap) {
        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(Embec_ditilse.this);
        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        //Create the in/out Allocations with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //Set the radius of the blur
        blurScript.setRadius(10.f);
        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);
        //recycle the original bitmap
        bitmap.recycle();
        //After finishing everything, we destroy the Renderscript.
        rs.destroy();
        return outBitmap;

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
