package androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.controller.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.R;

/**
 * @author Sergio R. Angeles
 */
public class WebsiteItemViewHolder extends RecyclerView.ViewHolder {

    public TextView websiteName;
    public ViewGroup websiteParentLayout;

    public WebsiteItemViewHolder(View itemView) {
        super(itemView);

        websiteName = (TextView)itemView.findViewById(R.id.website_item_website_name_text_view);
        websiteParentLayout = (ViewGroup) itemView.findViewById(R.id.website_item_linear_layout);

    }
}
