package trimdevelopmentcom.sos;

import org.json.JSONArray;

/**
 * Created by Macbook on 5/8/16.
 */
public class Obj_Helth {
    String firstaid_title;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String note;

    public String getFirstaid_image() {
        return firstaid_image;
    }

    public void setFirstaid_image(String firstaid_image) {
        this.firstaid_image = firstaid_image;
    }

    public String getFirstaid_title() {
        return firstaid_title;
    }

    public void setFirstaid_title(String firstaid_title) {
        this.firstaid_title = firstaid_title;
    }

    public JSONArray getFirstaid_details() {
        return firstaid_details;
    }

    public void setFirstaid_details(JSONArray firstaid_details) {
        this.firstaid_details = firstaid_details;
    }

    String firstaid_image;
    JSONArray firstaid_details;




}
