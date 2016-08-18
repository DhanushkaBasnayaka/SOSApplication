package trimdevelopmentcom.sos.Sever_task;

import android.content.Intent;
import android.util.Log;

import com.squareup.otto.Produce;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import trimdevelopmentcom.sos.Event_Bus.BusProvider;
import trimdevelopmentcom.sos.Event_Bus.ErrorEvent;
import trimdevelopmentcom.sos.Event_Bus.ServerEvent;
import trimdevelopmentcom.sos.Interface;
import trimdevelopmentcom.sos.MainActivity;
import trimdevelopmentcom.sos.models.Models_email;
import trimdevelopmentcom.sos.models.Object_register;

/**
 * Created by Dori on 3/10/2016.
 */
public class Communicator {
    private static  final String TAG = "Communicator";
    private static final String SERVER_URL = "http://projects.yogeemedia.com/preview/embassy/Sos_application";


    public void Email_Post(String to_email, String For_email, String Messeg){
        Log.e("to_email ",to_email);
        Log.e("For_email ",For_email);
        Log.e("Messeg ",Messeg);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<Models_email> callback = new Callback<Models_email>() {


            @Override
            public void success(Models_email models_email, Response response) {

                if(models_email.getResponseCode() == 0){
                    BusProvider.getInstance().post(produceServerEvent(models_email));


                }else{

     BusProvider.getInstance().post(produceErrorEvent(models_email.getResponseCode(), models_email.getMessage()));

                }

            }

            @Override
            public void failure(RetrofitError error) {
                if(error != null ){
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200,error.getMessage()));
            }
        };
        communicatorInterface.postData(to_email, For_email,Messeg,callback);
    }


    // user rggistetion


//    public void loginGet(String username, String password){
//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setEndpoint(SERVER_URL)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .build();
//        Interface communicatorInterface = restAdapter.create(Interface.class);
//        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
//            @Override
//            public void success(ServerResponse serverResponse, Response response2) {
//                if(serverResponse.getResponseCode() == 0){
//                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
//                }else{
//                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                if(error != null ){
//                    Log.e(TAG, error.getMessage());
//                    error.printStackTrace();
//                }
//                BusProvider.getInstance().post(produceErrorEvent(-200,error.getMessage()));
//            }
//        };
//        communicatorInterface.getData("login", username, password, callback);
//    }

    @Produce
    public ServerEvent produceServerEvent(Models_email serverResponse) {
        return new ServerEvent(serverResponse);
    }
    @Produce
    public ServerEvent produceServerEvent_registetion (Object_register serverResponse) {

        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}
