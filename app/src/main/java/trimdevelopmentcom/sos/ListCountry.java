package trimdevelopmentcom.sos;

/**
 * Created by basnayaka on 12/3/2015.
 */
public class ListCountry
{

    public ListCountry()
    {
    }

    public String getsiombelCode()
    {
        return siombelCode;
    }

    public void setsiombelCode(String siombelCode)
    {
        this.siombelCode = siombelCode;
    }

    public String getdisplayName()
    {
        return displayName;
    }

    public void setdisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getcurrencyCode()
    {
        return currencyCode;
    }

    public void setcurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    private String displayName;
    private String currencyCode;
    private String siombelCode;
}
