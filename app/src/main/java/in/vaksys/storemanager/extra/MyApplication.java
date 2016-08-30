package in.vaksys.storemanager.extra;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import in.vaksys.storemanager.R;

/**
 * Created by lenovoi3 on 8/15/2016.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static Handler handler = new Handler();
    private ProgressDialog pDialog;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }
    // common in volley singleton and analytics
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void createDialog(Activity activity, boolean cancelable) {
        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(cancelable);
    }

    public void DialogMessage(String message) {
        pDialog.setMessage(message);
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();


    }

    public void hideDialog() {
        //// TODO: 23-05-2016  errorr solve

        //show dialog
        if (pDialog.isShowing())
            pDialog.dismiss();

    }




}
