package androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.controller.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.R;
import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.controller.customtabs.CustomTabActivity;
import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.model.WebsiteItem;

/**
 * @author Sergio R. Angeles
 */
public class MainActivity extends AppCompatActivity implements OnWebsiteItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    private LayoutManager mLayoutManager;

    private List<WebsiteItem> mWebsiteItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init the recycler view
        mRecyclerView = (RecyclerView)findViewById(R.id.activity_main_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // Init the layout manager needed for the recycler view
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create the website items for recycler view
        WebsiteItem captechWebsite = new WebsiteItem("Captech Consulting", "http://www.captechconsulting.com/");
        WebsiteItem engadgetWebsite = new WebsiteItem("Engadget", "http://www.engadget.com/");
        WebsiteItem techcrunchWebsite = new WebsiteItem("Techcrunch", "http://www.techcrunch.com/");
        WebsiteItem facebookWebsite = new WebsiteItem("Facebook", "http://www.facebook.com");

        // Init the data list and add the websites
        mWebsiteItemList = new ArrayList<>();
        mWebsiteItemList.add(captechWebsite);
        mWebsiteItemList.add(engadgetWebsite);
        mWebsiteItemList.add(techcrunchWebsite);
        mWebsiteItemList.add(facebookWebsite);

        // Create the website adapter and set it
        mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter(mWebsiteItemList, this);
        mRecyclerView.setAdapter(mHomeRecyclerViewAdapter);

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
    }

    @Override
    public void onWebsiteItemClick(WebsiteItem websiteItem) {
        Log.d(TAG, "onWebsiteItemClick");

        if (websiteItem != null) {
            Intent customTabActivityIntent = CustomTabActivity.newInstanceIntent(this, websiteItem);
            startActivity(customTabActivityIntent);
        }
    }

//    @Override
//    public void onClick(View view) {
//        Log.d(TAG, "onClick");
//
//        CustomTabsIntent.Builder intentBuilder = new Builder();
//        intentBuilder.setShowTitle(true);
//
//        int color = getResources().getColor(R.color.primary_red);
//
//        intentBuilder.setToolbarColor(color);
//        intentBuilder.setShowTitle(true);
//
//        CustomTabsIntent customTabsIntent = intentBuilder.build();
//
//        customTabsIntent.launchUrl(this, Uri.parse("http://www.captechconsulting.com/"));
//
//    }


}
