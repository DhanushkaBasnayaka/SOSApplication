package trimdevelopmentcom.sos.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Macbook on 6/3/16.
 */
public class Models_email implements Serializable {

//        @SerializedName("returned_username")
        private String To_emial;
//        @SerializedName("returned_password")
        private String For_email;
        @SerializedName("message")
        private String message;

        private int responseCode;

        public Models_email(String To_emial, String For_email, String message, int responseCode){
        this.To_emial = To_emial;
        this.For_email = For_email;
        this.message = message;
       // this.responseCode = responseCode;
    }

        public String getTo_emial() {
            return To_emial;
        }

        public void setTo_emial(String to_emial) {
            To_emial = to_emial;
        }

        public String getFor_email() {
            return For_email;
        }

        public void setFor_email(String for_email) {
            For_email = for_email;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }
    }