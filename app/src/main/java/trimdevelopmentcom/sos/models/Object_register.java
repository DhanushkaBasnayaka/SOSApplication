package trimdevelopmentcom.sos.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Macbook on 6/5/16.
 */
public class Object_register {

    @SerializedName("message")
    private String message;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("tp")
    private String tp;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @SerializedName("response_code")

    private int responseCode;

    @SerializedName("rgid")
    private String rgid;

    public Object_register(String email,String rgid, String message, String name, String tp,int responseCode){
        this.message = message;
        this.email = email;
        this.name = name;
        this.tp = tp;
        this.rgid = rgid;
        this.responseCode=responseCode;
        // this.responseCode = responseCode;
    }

    public String getRgid() {
        return rgid;
    }

    public void setRgid(String rgid) {
        this.rgid = rgid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }


}
