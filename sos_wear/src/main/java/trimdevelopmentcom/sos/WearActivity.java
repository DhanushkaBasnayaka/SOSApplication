package trimdevelopmentcom.sos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;



public class WearActivity extends Activity {

    private TextView mTextView;
    TeleportClient mTeleportClient;
    TeleportClient.OnSyncDataItemTask mOnSyncDataItemTask;
    TeleportClient.OnGetMessageTask mMessageTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        mTeleportClient = new TeleportClient(this);

        mOnSyncDataItemTask = new ShowToastOnSyncDataItemTask();
        mMessageTask = new ShowToastFromOnGetMessageTask();

        mTeleportClient.setOnGetMessageTask(mMessageTask);

        System.out.println("sendStartActivityMessage");
        mTeleportClient.setOnGetMessageTask(new ShowToastFromOnGetMessageTask());
        mTeleportClient.sendMessage("startActivity", null);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();

    }



    public void sendStartActivityMessage(View v) {

        System.out.println("sendStartActivityMessage");
        mTeleportClient.setOnGetMessageTask(new ShowToastFromOnGetMessageTask());
        mTeleportClient.sendMessage("startActivity", null);
    }


    public class ShowToastOnSyncDataItemTask extends TeleportClient.OnSyncDataItemTask {

        @Override
        protected void onPostExecute(DataMap dataMap) {

            String s = dataMap.getString("string");


            Toast.makeText(getApplicationContext(),"DataItem - "+s,Toast.LENGTH_SHORT).show();

            //let's reset the task (otherwise it will be executed only once)
            mTeleportClient.setOnSyncDataItemTask(new ShowToastOnSyncDataItemTask());
        }
    }


    public class ShowToastFromOnGetMessageTask extends TeleportClient.OnGetMessageTask {

        @Override
        protected void onPostExecute(String path) {


            Toast.makeText(getApplicationContext(),"Message - "+path,Toast.LENGTH_SHORT).show();

            //let's reset the task (otherwise it will be executed only once)
            mTeleportClient.setOnGetMessageTask(new ShowToastFromOnGetMessageTask());
        }
    }




}
