package androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.controller.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author Sergio R. Angeles
 */
public class CustomTabServiceController extends CustomTabsServiceConnection {

    private static final String TAG = CustomTabServiceController.class.getSimpleName();

    private static final String PKG_NAME_CHROME = "com.android.chrome";

    private WeakReference<Context> mContextWeakRef;
    private String mWebsiteUrl;
    private CustomTabsSession mCustomTabsSession;

    public CustomTabServiceController(Context context, String websiteUrl) {
        mContextWeakRef = new WeakReference<Context>(context);
        mWebsiteUrl = websiteUrl;
    }

    @Override
    public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
        Log.d(TAG, "onCustomTabsServiceConnected");

        if (customTabsClient != null) {
            customTabsClient.warmup(0L);

            // Create a new session if needed, or else use a previous session
            if (mCustomTabsSession == null) {
                // Can pass in a call back here
                Log.d(TAG, "creating new session");
                mCustomTabsSession = customTabsClient.newSession(null);
            }

            // Let the session know that it may launch a URL soon
            if (!TextUtils.isEmpty(mWebsiteUrl)) {
                Uri uri = Uri.parse(mWebsiteUrl);
                if (uri != null && mCustomTabsSession != null) {

                    // If this returns true, custom tabs will work,
                    // otherwise, you need another alternative if you don't want the user
                    // to be launched out of the app by default
                    mCustomTabsSession.mayLaunchUrl(uri, null, null);
                }
            }
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(TAG, "onServiceDisconnected");
        mCustomTabsSession = null;

    }

    public void bindCustomTabService() {

        Context ctx = mContextWeakRef.get();
        if (ctx != null) {
            CustomTabsClient.bindCustomTabsService(ctx, PKG_NAME_CHROME, this);
        }
    }

    public void unbindCustomTabService() {

        Context ctx = mContextWeakRef.get();
        if (ctx != null) {
            ctx.unbindService(this);
        }
    }
}
