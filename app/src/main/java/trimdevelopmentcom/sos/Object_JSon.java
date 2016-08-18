package trimdevelopmentcom.sos;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Macbook on 7/14/16.
 */
public class Object_JSon {


    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
    @SerializedName("contacts")
    @Expose
    private String contacts;


}
