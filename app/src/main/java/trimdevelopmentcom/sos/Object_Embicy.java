package trimdevelopmentcom.sos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Macbook on 3/30/16.
 */
public class Object_Embicy {


    String id;
    String Ambulance;
    String fir;
    String police;

    public String getFir() {
        return fir;
    }

    public void setFir(String fir) {
        this.fir = fir;
    }

    public String getAmbulance() {
        return Ambulance;
    }

    public void setAmbulance(String ambulance) {
        Ambulance = ambulance;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }


    String Emb_name;
    String Emb_image;
    String Emb_addres;
    String phone_local;
    String phone_inter;
    String fax_loc;
    String fax_inter;
    String Emb_Email;
    String Emb_police;
    String Emb_Ambulens;
    String Emb_fire;
    String Emb_of;

    String country_code;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public JSONArray getEmbassy() {
        return embassy;
    }

    public void setEmbassy(JSONArray embassy) {
        this.embassy = embassy;
    }

    String country_name;
    JSONArray embassy;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getEmb_of() {
        return Emb_of;
    }

    public void setEmb_of(String emb_of) {
        this.Emb_of = emb_of;
    }

    public String getEmb_name() {
        return Emb_name;
    }

    public void setEmb_name(String emb_name) {
        Emb_name = emb_name;
    }

    public String getEmb_image() {
        return Emb_image;
    }

    public void setEmb_image(String emb_image) {
        Emb_image = emb_image;
    }

    public String getEmb_addres() {
        return Emb_addres;
    }

    public void setEmb_addres(String emb_addres) {
        Emb_addres = emb_addres;
    }

    public String getPhone_local() {
        return phone_local;
    }

    public void setPhone_local(String phone_local) {
        this.phone_local = phone_local;
    }

    public String getPhone_inter() {
        return phone_inter;
    }

    public void setPhone_inter(String phone_inter) {
        this.phone_inter = phone_inter;
    }

    public String getFax_loc() {
        return fax_loc;
    }

    public void setFax_loc(String fax_loc) {
        this.fax_loc = fax_loc;
    }

    public String getFax_inter() {
        return fax_inter;
    }

    public void setFax_inter(String fax_inter) {
        this.fax_inter = fax_inter;
    }

    public String getEmb_Email() {
        return Emb_Email;
    }

    public void setEmb_Email(String emb_Email) {
        Emb_Email = emb_Email;
    }

    public String getEmb_police() {
        return Emb_police;
    }

    public void setEmb_police(String emb_police) {
        Emb_police = emb_police;
    }

    public String getEmb_Ambulens() {
        return Emb_Ambulens;
    }

    public void setEmb_Ambulens(String emb_Ambulens) {
        Emb_Ambulens = emb_Ambulens;
    }

    public String getEmb_fire() {
        return Emb_fire;
    }

    public void setEmb_fire(String emb_fire) {
        Emb_fire = emb_fire;
    }


}
