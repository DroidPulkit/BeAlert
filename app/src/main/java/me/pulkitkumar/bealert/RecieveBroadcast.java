package me.pulkitkumar.bealert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by pulkit on 23/3/17.
 */

public class RecieveBroadcast extends BroadcastReceiver{
    static int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
            Log.d("AHDEL", "volume has changed");
            count++;

            if (count == 4) {
                count = 0;
                Intent i = new Intent();
                i.setClass(context, smsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        }
    }
}
