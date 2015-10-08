package androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.controller.home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.R;
import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.model.WebsiteItem;

/**
 * @author Sergio R. Angeles
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<WebsiteItemViewHolder> implements OnClickListener {
    private static final String TAG = HomeRecyclerViewAdapter.class.getSimpleName();

    private List<WebsiteItem> mWebsiteItemList;
    private WeakReference<OnWebsiteItemClickListener> mOnWebsiteItemClickListenerWeakRef;

    public HomeRecyclerViewAdapter(List<WebsiteItem> websiteItemList, OnWebsiteItemClickListener onWebsiteItemClickListener) {
        mWebsiteItemList = websiteItemList;
        mOnWebsiteItemClickListenerWeakRef = new WeakReference<OnWebsiteItemClickListener>(onWebsiteItemClickListener);
    }


    @Override
    public WebsiteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Get the layout inflater and inflate the view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View websiteItemView = layoutInflater.inflate(R.layout.website_item, parent, false);

        return new WebsiteItemViewHolder(websiteItemView);
    }

    @Override
    public void onBindViewHolder(WebsiteItemViewHolder websiteItemViewHolder, int position) {
        if (websiteItemViewHolder == null) {
            return;
        }

        WebsiteItem websiteItem = mWebsiteItemList.get(position);
        String websiteName = websiteItem.getWebsiteName();

        websiteItemViewHolder.websiteName.setText(websiteName);
        websiteItemViewHolder.websiteParentLayout.setOnClickListener(this);
        websiteItemViewHolder.websiteParentLayout.setTag(websiteItem);

    }

    @Override
    public int getItemCount() {

        if (mWebsiteItemList != null && !mWebsiteItemList.isEmpty()) {
            return mWebsiteItemList.size();
        }

        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick");

        WebsiteItem websiteItem = null;

        Object obj = view.getTag();
        if (obj != null && obj instanceof WebsiteItem) {
            websiteItem = (WebsiteItem) obj;
        }

        // Forward the click even to the click listener
        OnWebsiteItemClickListener onWebsiteItemClickListener = mOnWebsiteItemClickListenerWeakRef.get();
        if (websiteItem != null && onWebsiteItemClickListener != null) {
            onWebsiteItemClickListener.onWebsiteItemClick(websiteItem);
        }
    }
}
