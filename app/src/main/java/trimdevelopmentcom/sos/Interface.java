package trimdevelopmentcom.sos;


import com.android.volley.Response;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import trimdevelopmentcom.sos.models.*;

/**
 * Created by Macbook on 6/3/16.
 */
public interface Interface {

    @FormUrlEncoded
    @POST("/email.php")
    void postData(@Field("TO_email") String TO_email,
                  @Field("For_email") String For_email,
                  @Field("messeg") String password,
                  Callback<Models_email> serverResponseCallback);

    @FormUrlEncoded
    @POST("/db_handler.php")
    void post_registetion(@Field("email") String email,
                  @Field("name") String name,
                  @Field("tp") String tp,
                   @Field("rgid") String rgid,
                  Callback<Object_register> serverResponseCallback);


//    @FormUrlEncoded
//    @POST("/fr/embassy-json/")http://projects.yogeemedia.com/preview/embassy/fr/firstaid-json/
//    void getBooks(@Body Object_JSon contacts, Callback<Object_JSon> cb);
//    http://projects.yogeemedia.com/preview/embassy/fr/embassy-json/
@GET("/fr/embassy-json/")      //here is the other url part for the API
    void getBooks(Callback<retrofit.client.Response> callback);

    @GET("/fr/firstaid-json/")      //here is the other url part for the API
    void getfasttaid(Callback<retrofit.client.Response> callback);
}
