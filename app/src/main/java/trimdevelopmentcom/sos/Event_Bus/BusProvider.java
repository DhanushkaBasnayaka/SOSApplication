package trimdevelopmentcom.sos.Event_Bus;

import com.squareup.otto.Bus;

/**
 * Created by Dori on 3/10/2016.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider(){}
}
