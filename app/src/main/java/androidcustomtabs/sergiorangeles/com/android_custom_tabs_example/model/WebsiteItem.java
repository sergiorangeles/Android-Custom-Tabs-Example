package androidcustomtabs.sergiorangeles.com.android_custom_tabs_example.model;

/**
 * @author Sergio R. Angeles
 */
public class WebsiteItem {

    public String websiteName, websiteUrl;

    public WebsiteItem(String websiteName, String websiteUrl) {
        this.websiteName = websiteName;
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

}
