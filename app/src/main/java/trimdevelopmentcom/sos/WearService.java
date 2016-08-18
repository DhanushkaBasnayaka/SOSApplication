package trimdevelopmentcom.sos;

import android.content.Intent;

import com.mariux.teleport.lib.TeleportService;


public class WearService extends TeleportService {

    @Override
    public void onCreate() {
        super.onCreate();

        setOnGetMessageTask(new StartActivityTask());
        System.out.println("setOnGetMessageTask");

    }

    //Task that shows the path of a received message
    public class StartActivityTask extends OnGetMessageTask {

        @Override
        protected void onPostExecute(String  path) {

       if (path.equals("startActivity")){


            Intent startIntent = new Intent(getBaseContext(), Splash.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);

//           MainActivity get_sms = new MainActivity();
//           get_sms.send_messeg();
         }

            //let's reset the task (otherwise it will be executed only once)
            setOnGetMessageTask(new StartActivityTask());
        }
    }


}
