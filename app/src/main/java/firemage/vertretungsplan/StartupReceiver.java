package firemage.vertretungsplan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by firemage on 12.09.17.
 */

public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent("firemage.vertretungsplan.NetworkingService");
        i.setClass(context, getClass());
        context.startService(i);
    }
}
