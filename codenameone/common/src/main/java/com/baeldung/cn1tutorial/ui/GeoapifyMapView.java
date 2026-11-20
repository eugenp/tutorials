package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.model.PlaceInfo;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.util.AppConfig;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.EncodedImage;
import com.codename1.io.Log;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.CN;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 * Reusable map preview component for activity details and place selection.
 * <p>
 * It prefers an embedded {@link BrowserComponent} with Leaflet, but falls back to a static image
 * when the current port cannot host an embedded browser reliably.
 */
public class GeoapifyMapView extends Container {
    private final AppContext context;
    private final SpanLabel placeholder = new SpanLabel();
    private final SpanLabel noteLabel = new SpanLabel();
    private final ScaleImageLabel staticMapLabel = new ScaleImageLabel();
    private final Container staticPreview = new Container(BoxLayout.y());
    private BrowserComponent browser;

    /**
     * @param context shared runtime context
     */
    public GeoapifyMapView(AppContext context) {
        super(new BorderLayout());
        this.context = context;
        setUIID("MapCard");
        setPreferredH(CN.convertToPixels(38f));
        placeholder.setUIID("MapPlaceholder");
        placeholder.setName("mapPlaceholder");
        noteLabel.setName("mapNoteLabel");
        staticMapLabel.setName("mapStaticImage");
        staticMapLabel.setUIID("MapStaticImage");
        staticPreview.add(noteLabel);
        staticPreview.add(staticMapLabel);
        placeholder.setText(context.text("map.placeholder"));
        add(BorderLayout.CENTER, placeholder);
    }

    /**
     * Rebuilds the preview for the provided place.
     *
     * @param place place to display, or {@code null} for the empty state
     */
    public void showPlace(PlaceInfo place) {
        releaseBrowser();
        removeAll();
        if (place == null) {
            placeholder.setText(context.text("map.placeholder"));
            add(BorderLayout.CENTER, placeholder);
        } else if (!AppConfig.isGeoapifyConfigured()) {
            placeholder.setText(context.text("geoapify.missing.key"));
            add(BorderLayout.CENTER, placeholder);
        } else {
            boolean browserShown = false;
            if (BrowserComponent.isNativeBrowserSupported()) {
                Log.p("BrowserComponent native support detected. simulator=" + CN.isSimulator());
                try {
                    // We need to create a new BrowserComponent each time we use it to avoid issues with the JS Port.
                    browser = new BrowserComponent();
                    browser.putClientProperty("HTML5Peer.removeOnDeinitialize", Boolean.TRUE);
                    browser.setPinchToZoomEnabled(true);
                    browser.setNativeScrollingEnabled(true);
                    browser.setPage(buildMapHtml(place), null);
                    add(BorderLayout.CENTER, browser);
                    browserShown = true;
                } catch (RuntimeException ex) {
                    Log.e(ex);
                    Log.p("BrowserComponent initialization failed; falling back to static preview.");
                    browser = null;
                }
            }
            if (!browserShown) {
                showStaticPreview(
                        place,
                        CN.isSimulator()
                                ? context.text("map.simulator.static")
                                : context.text("map.browser.static")
                );
            }
        }
        revalidate();
    }

    /**
     * Releases the embedded browser before this component is reused or its form is replaced.
     * <p>
     * This is especially important in the JavaScript port, where {@link BrowserComponent} is backed
     * by an iframe. Destroying it before navigating away avoids stale iframe callbacks trying to
     * access a deinitialized CN1 peer.
     */
    public void releaseBrowser() {
        if (browser == null) {
            return;
        }
        BrowserComponent released = browser;
        browser = null;
        try {
            released.stop();
            released.destroy();
        } catch (RuntimeException ex) {
            Log.e(ex);
        }
        if (released.getParent() != null) {
            released.remove();
        }
    }

    /**
     * Shows the static preview and note label.
     *
     * @param place place to preview
     * @param note explanatory note shown above the image
     */
    private void showStaticPreview(PlaceInfo place, String note) {
        noteLabel.setText(note);
        staticMapLabel.setIcon(buildStaticMapImage(place));
        add(BorderLayout.CENTER, staticPreview);
    }

    /**
     * Builds the HTML page loaded into the embedded browser.
     * <p>
     * The page is intentionally inline to keep the tutorial self-contained and make it easy for
     * students to see how CN1 hands off to standard web technologies inside a browser component.
     *
     * @param place place to display
     * @return HTML document
     */
    private String buildMapHtml(PlaceInfo place) {
        String escapedName = escape(place.name());
        String escapedAddress = escape(place.address());
        String tileUrl = "https://maps.geoapify.com/v1/tile/" + AppConfig.GEOAPIFY_TILE_STYLE
                + "/{z}/{x}/{y}.png?apiKey=" + AppConfig.geoapifyApiKey();
        return "<!doctype html><html><head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\"/>"
                + "<style>html,body,#map{height:100%;margin:0;padding:0;}body{font-family:sans-serif;}</style>"
                + "</head><body><div id=\"map\"></div>"
                + "<script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>"
                + "<script>"
                + "var map = L.map('map',{zoomControl:true}).setView([" + place.latitude() + "," + place.longitude() + "]," + AppConfig.MAP_ZOOM + ");"
                + "L.tileLayer('" + tileUrl + "',{maxZoom:20,attribution:'&copy; OpenStreetMap contributors &copy; Geoapify'}).addTo(map);"
                + "L.marker([" + place.latitude() + "," + place.longitude() + "]).addTo(map)"
                + ".bindPopup('<b>" + escapedName + "</b><br/>" + escapedAddress + "').openPopup();"
                + "</script></body></html>";
    }

    /**
     * Creates the static map image used as a fallback.
     *
     * @param place place to preview
     * @return cached image
     */
    private Image buildStaticMapImage(PlaceInfo place) {
        int width = CN.convertToPixels(90f);
        int height = CN.convertToPixels(38f);
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(width, height, 0xf3f4f6), false);
        String cacheKey = "geoapify-static-" + cacheKeyFragment(place) + "-" + width + "x" + height;
        return URLImage.createToStorage(
                placeholderImage,
                cacheKey,
                buildStaticMapUrl(place, width, height),
                URLImage.RESIZE_SCALE
        );
    }

    /**
     * Builds the Geoapify static-map URL.
     *
     * @param place place to preview
     * @param width requested image width
     * @param height requested image height
     * @return URL used by {@link URLImage}
     */
    private String buildStaticMapUrl(PlaceInfo place, int width, int height) {
        StringBuilder url = new StringBuilder("https://maps.geoapify.com/v1/staticmap?");
        url.append("style=").append(AppConfig.GEOAPIFY_STATIC_STYLE);
        url.append("&width=").append(width);
        url.append("&height=").append(height);
        url.append("&center=lonlat:").append(place.longitude()).append(',').append(place.latitude());
        url.append("&zoom=").append(AppConfig.MAP_ZOOM);
        url.append("&marker=lonlat:")
                .append(place.longitude()).append(',').append(place.latitude())
                .append(";type:material;color:%230f766e;size:small;icon:place");
        url.append("&apiKey=").append(AppConfig.geoapifyApiKey());
        return url.toString();
    }

    /**
     * Builds a cache-key fragment from the place identity.
     *
     * @param place place to identify
     * @return cache-key fragment
     */
    private String cacheKeyFragment(PlaceInfo place) {
        if (place.placeId() != null && place.placeId().trim().length() > 0) {
            return sanitize(place.placeId());
        }
        return sanitize(place.name()) + "-" + place.latitude() + "-" + place.longitude();
    }

    /**
     * Sanitizes strings for cache-key use.
     *
     * @param text raw cache fragment
     * @return ASCII-safe fragment
     */
    private String sanitize(String text) {
        if (text == null || text.length() == 0) {
            return "unknown";
        }
        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if ((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9')
                    || ch == '-') {
                out.append(ch);
            } else {
                out.append('-');
            }
        }
        return out.toString();
    }

    /**
     * Escapes text before injecting it into inline HTML.
     *
     * @param text raw text
     * @return escaped HTML text
     */
    private String escape(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder escaped = new StringBuilder(text.length() + 16);
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            switch (ch) {
                case '&':
                    escaped.append("&amp;");
                    break;
                case '<':
                    escaped.append("&lt;");
                    break;
                case '>':
                    escaped.append("&gt;");
                    break;
                case '"':
                    escaped.append("&quot;");
                    break;
                case '\'':
                    escaped.append("&#39;");
                    break;
                default:
                    escaped.append(ch);
                    break;
            }
        }
        return escaped.toString();
    }
}
