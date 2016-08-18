package trimdevelopmentcom.sos.Event_Bus;

import trimdevelopmentcom.sos.models.Models_email;
import trimdevelopmentcom.sos.models.Object_register;

/**
 * Created by Dori on 3/10/2016.
 */
public class ServerEvent {

    private Models_email serverResponse;
    private Object_register serverResponse_gegister;

    public ServerEvent(Models_email serverResponse) {
        this.serverResponse = serverResponse;
    }

    public Models_email getServerResponse() {
        return serverResponse;
    }


    public void setServerResponse(Models_email serverResponse) {
        this.serverResponse = serverResponse;
    }


    // user Registetion
    public ServerEvent(Object_register serverResponse) {
        this.serverResponse_gegister = serverResponse;
    }

    public Object_register getServerResponse_registetion() {
        return serverResponse_gegister;
    }


    public void setServerResponse_registetion(Object_register serverResponse) {
        this.serverResponse_gegister = serverResponse;
    }


}
