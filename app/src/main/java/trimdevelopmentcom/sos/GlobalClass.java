package trimdevelopmentcom.sos;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Macbook on 5/4/16.
 */
public class GlobalClass extends Application {
    private String name;
    private String E_mail;
    private String Phone;
    private String Addres;
    private String Latitude;


    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getAmbulance() {
        return Ambulance;
    }

    public void setAmbulance(String ambulance) {
        Ambulance = ambulance;
    }

    public String getFir() {
        return fir;
    }

    public void setFir(String fir) {
        this.fir = fir;
    }

    private String police;
    private String Ambulance;
    private String fir;

    public String getCounry_cord() {
        return counry_cord;
    }

    public void setCounry_cord(String counry_cord) {
        this.counry_cord = counry_cord;
    }

    private String counry_cord;

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    private String Longitude;
    private String country;

    private String sos_phone;
    private String defolt_cuntry;
    private String sos_Phone2;
    private String SOS_E_mail;
    private String SOS_E_mail_sos2;
    private String Sos_masseg;
    private String Sos_countdown_masseg;
    private String couflag_2;
    private String couflag_add;
    private String pin;
    private String couflag;

    public String getAddres() {
        return Addres;
    }

    public void setAddres(String addres) {
        Addres = addres;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    public String getMsg_stetes() {
        return msg_stetes;
    }

    public void setMsg_stetes(String msg_stetes) {
        this.msg_stetes = msg_stetes;
    }

    private  String msg_stetes;
    private String push_email_one_gold,push_email_two_gold,push_messeg_one_gold,push_messeg_two_gold,push_location_gold,push_addres_gold,push_date_gold;


    public String getPush_email_one_gold() {
        return push_email_one_gold;
    }

    public void setPush_email_one_gold(String push_email_one_gold) {
        this.push_email_one_gold = push_email_one_gold;
    }

    public String getPush_email_two_gold() {
        return push_email_two_gold;
    }

    public void setPush_email_two_gold(String push_email_two_gold) {
        this.push_email_two_gold = push_email_two_gold;
    }

    public String getPush_messeg_one_gold() {
        return push_messeg_one_gold;
    }

    public void setPush_messeg_one_gold(String push_messeg_one_gold) {
        this.push_messeg_one_gold = push_messeg_one_gold;
    }

    public String getPush_messeg_two_gold() {
        return push_messeg_two_gold;
    }

    public void setPush_messeg_two_gold(String push_messeg_two_gold) {
        this.push_messeg_two_gold = push_messeg_two_gold;
    }

    public String getPush_location_gold() {
        return push_location_gold;
    }

    public void setPush_location_gold(String push_location_gold) {
        this.push_location_gold = push_location_gold;
    }

    public String getPush_addres_gold() {
        return push_addres_gold;
    }

    public void setPush_addres_gold(String push_addres_gold) {
        this.push_addres_gold = push_addres_gold;
    }

    public String getPush_date_gold() {
        return push_date_gold;
    }

    public void setPush_date_gold(String push_date_gold) {
        this.push_date_gold = push_date_gold;
    }


    public String getDefolt_cuntry() {
        return defolt_cuntry;
    }

    public void setDefolt_cuntry(String defolt_cuntry) {
        this.defolt_cuntry = defolt_cuntry;
    }


    public String getSOS_E_mail_sos2() {
        return SOS_E_mail_sos2;
    }

    public void setSOS_E_mail_sos2(String SOS_E_mail_sos2) {
        this.SOS_E_mail_sos2 = SOS_E_mail_sos2;
    }


    public String getCouflag_add() {
        return couflag_add;
    }

    public void setCouflag_add(String couflag_add) {
        this.couflag_add = couflag_add;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getE_mail() {
        return E_mail;
    }

    public void setE_mail(String e_mail) {
        E_mail = e_mail;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSos_phone() {
        return sos_phone;
    }

    public void setSos_phone(String sos_phone) {
        this.sos_phone = sos_phone;
    }

    public String getSos_Phone2() {
        return sos_Phone2;
    }

    public void setSos_Phone2(String sos_Phone2) {
        this.sos_Phone2 = sos_Phone2;
    }

    public String getSOS_E_mail() {
        return SOS_E_mail;
    }

    public void setSOS_E_mail(String SOS_E_mail) {
        this.SOS_E_mail = SOS_E_mail;
    }

    public String getSos_masseg() {
        return Sos_masseg;
    }

    public void setSos_masseg(String sos_masseg) {
        Sos_masseg = sos_masseg;
    }

    public String getSos_countdown_masseg() {
        return Sos_countdown_masseg;
    }

    public void setSos_countdown_masseg(String sos_countdown_masseg) {
        Sos_countdown_masseg = sos_countdown_masseg;
    }

    public String getCouflag_2() {
        return couflag_2;
    }

    public void setCouflag_2(String couflag_2) {
        this.couflag_2 = couflag_2;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCouflag() {
        return couflag;
    }

    public void setCouflag(String couflag) {
        this.couflag = couflag;
    }





    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
