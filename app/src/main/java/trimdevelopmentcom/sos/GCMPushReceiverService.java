package trimdevelopmentcom.sos;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import trimdevelopmentcom.sos.MainActivity;
import trimdevelopmentcom.sos.R;
import trimdevelopmentcom.sos.models.Object_notification;


/**
 * Created by Belal on 4/15/2016.
 */

//Class is extending GcmListenerService
public class GCMPushReceiverService extends GcmListenerService {

    //This method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data) {
        //Getting the message from the bundle
        String id_not = data.getString("id_not");
        String message = data.getString("message");
        String topick = data.getString("titil_not");
        String date = data.getString("date");
        String web_linck = data.getString("web_link");


        Object_notification item = new Object_notification();
        item.setNot_id(id_not);
        item.setMeeseg(message);
        item.setTitil(topick);
        item.setTime(date);
        item.setWeb_link(web_linck);
        item.setStetest("0");

        DatabaseHandler dbh = new DatabaseHandler(this);
        dbh.insertContact_notification(item);
        //Displaying a notiffication with the message
        sendNotification(message,topick,date,web_linck);
//        sendNotification(message);

    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(String message, String topick, String date, String web_linck) {
//        private void sendNotification(String message) {
        Intent intent = new Intent(this, Notification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(topick)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }
}
