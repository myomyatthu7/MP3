package app.mmt.test;

import android.content.Context;
import android.widget.Toast;

public class ToastMsg {
    public static void toastMsgShort(Context context,String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void toastMsgLong(Context context,String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
