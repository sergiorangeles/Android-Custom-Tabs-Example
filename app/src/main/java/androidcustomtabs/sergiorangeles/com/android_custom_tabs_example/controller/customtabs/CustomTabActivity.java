package androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.controller.customtabs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsIntent.Builder;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.R;
import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.model.WebsiteItem;

public class CustomTabActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = CustomTabActivity.class.getSimpleName();

    private static final String KEY_ARG_WEBSITE_URL = "KEY_ARG_WEBSITE_URL";

    private static final String PKG_NAME_CHROME = "com.android.chrome";

    private CustomTabsSession mCustomTabsSession;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;

    private String mWebsiteUrl;
    private CheckBox mDisplayTitleCheckbox, mColorToolbarCheckbox, mCustomMenuCheckbox,
            mDisplayBackButtonCheckbox, mCustomAnimCheckbox, mActionButtonCheckbox;
    private Button testButton;

    private Bitmap mActionCallIcon, mActionCallLightIcon, mActionBackIcon;

    private CustomTabServiceController mCustomTabServiceController;


    public static Intent newInstanceIntent(Context context, WebsiteItem websiteItem) {

        Intent intent = new Intent(context, CustomTabActivity.class);

        Bundle args = new Bundle();

        // Pull out the website URL that we need for custom tabs
        String websiteUrl = websiteItem != null ? websiteItem.getWebsiteUrl() : null;

        if (websiteUrl != null) {
            args.putString(KEY_ARG_WEBSITE_URL, websiteUrl);
        }

        intent.putExtras(args);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tab);

        // Default parameters
        Bundle args = getIntent().getExtras();
        if (args == null) {
            mWebsiteUrl = null;
        }
        // Otherwise set incoming parameters
        else {
            mWebsiteUrl = args.containsKey(KEY_ARG_WEBSITE_URL) ? args.getString(KEY_ARG_WEBSITE_URL) : null;
        }

        // If the website url is null and/or empty, custom tabs will not function, so close it
        if (TextUtils.isEmpty(mWebsiteUrl)) {
            finish();
            return;
        }

        // Init checkboxes
        mDisplayTitleCheckbox = (CheckBox)findViewById(R.id.activity_custom_tab_display_title_checkbox);
        mColorToolbarCheckbox = (CheckBox)findViewById(R.id.activity_custom_tab_color_toolbar_checkbox);
        mDisplayBackButtonCheckbox = (CheckBox)findViewById(R.id.activity_custom_tab_custom_back_button_checkbox);
        mCustomAnimCheckbox = (CheckBox)findViewById(R.id.activity_custom_tab_custom_animations_checkbox);
        mActionButtonCheckbox = (CheckBox)findViewById(R.id.activity_custom_tab_action_button_checkbox);
        mCustomMenuCheckbox = (CheckBox)findViewById(R.id.activity_custom_tab_custom_menu_checkbox);

        testButton = (Button)findViewById(R.id.activity_custom_tab_button);
        testButton.setOnClickListener(this);

        // These icons need to be this color, or they don't show up on the chrome custom tab
        // the color is HOLO-LIGHT
        Resources resources = getResources();
        mActionCallIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_action_call);
        mActionCallLightIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_action_call_light);
        mActionBackIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_action_back);

        // Init the custom tabs service connection
        mCustomTabServiceController = new CustomTabServiceController(this, mWebsiteUrl);
        mCustomTabServiceController.bindCustomTabService();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        // Unbind the custom tabs service
        mCustomTabServiceController.unbindCustomTabService();
    }

    @Override
    public void onClick(View view) {

        // Build the custom tab intent and launch the url
        CustomTabsIntent customTabsIntent = buildCustomTabsIntent();
        customTabsIntent.launchUrl(this, Uri.parse(mWebsiteUrl));
    }

    /**
     * Method to build a custom {@link CustomTabsIntent} using the {@link CustomTabsIntent.Builder}.
     * Uses checkboxes to determine what fields are set.
     *
     *
     * @return
     *      {@link CustomTabsIntent}
     */
    private CustomTabsIntent buildCustomTabsIntent() {
        CustomTabsIntent.Builder intentBuilder = new Builder();

        // Show the title
        intentBuilder.setShowTitle(mDisplayTitleCheckbox.isChecked());

        // Set the color
        if (mColorToolbarCheckbox.isChecked()) {
            intentBuilder.setToolbarColor(Color.BLUE);
        }

        // Display custom back button
        //TODO: why isn't this working???
        if (mDisplayBackButtonCheckbox.isChecked()) {
            intentBuilder.setCloseButtonIcon(mActionBackIcon);
            Log.d(TAG, "display back button complete");
        }

        // Add custom entrance/exit animations
        if (mCustomAnimCheckbox.isChecked()) {
            intentBuilder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
            intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        // Add action button
        if (mActionCallIcon != null && mActionButtonCheckbox.isChecked()) {
            Intent actionIntent = new Intent(Intent.ACTION_DIAL);
            actionIntent.setData(Uri.parse("tel:18001234567"));

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, actionIntent, 0);
            intentBuilder.setActionButton( mColorToolbarCheckbox.isChecked() ? mActionCallLightIcon :mActionCallIcon, "Call", pendingIntent);
        }

        // Add custom menu items
        if (mCustomMenuCheckbox.isChecked()) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this website: " + mWebsiteUrl);
            PendingIntent menuIntent = PendingIntent.getActivity(this, 0, shareIntent, 0);
            intentBuilder.addMenuItem(getString(R.string.activity_custom_tab_share_website), menuIntent);
        }

        return intentBuilder.build();
    }

}
