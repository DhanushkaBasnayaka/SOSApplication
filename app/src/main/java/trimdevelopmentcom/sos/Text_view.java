package trimdevelopmentcom.sos;

/**
 * Created by Macbook on 6/14/16.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class Text_view extends TextView {

    public Text_view(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Text_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Text_view(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "font/helvetica_lt.ttf");
        int tf2= Typeface.BOLD;
        setTypeface(tf,tf2);


    }


}
