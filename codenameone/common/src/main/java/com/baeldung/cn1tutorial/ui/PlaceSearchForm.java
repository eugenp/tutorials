package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.model.PlaceInfo;
import com.baeldung.cn1tutorial.model.PlaceSuggestion;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.PlaceSearchService;
import com.baeldung.cn1tutorial.util.AppConfig;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.util.SuccessCallback;
import java.util.List;

/**
 * Form used to search, preview and confirm a place before attaching it to an activity.
 */
public class PlaceSearchForm extends BaseForm {
    private final SuccessCallback<PlaceInfo> placeSelectionCallback;
    private final TextField queryField = new TextField();
    private final Container resultsContainer = new Container(BoxLayout.y());
    private final SpanLabel selectionLabel = new SpanLabel();
    private final GeoapifyMapView mapView;
    private final Button usePlaceButton;
    private PlaceSearchService.SearchHandle activeSearch;
    private PlaceSuggestion selectedSuggestion;

    /**
     * @param context shared runtime context
     * @param previousForm previous form for back navigation
     * @param placeSelectionCallback callback invoked when the user confirms a place
     */
    public PlaceSearchForm(AppContext context, Form previousForm, SuccessCallback<PlaceInfo> placeSelectionCallback) {
        super(context, "place.search.title", previousForm);
        this.placeSelectionCallback = placeSelectionCallback;
        this.mapView = new GeoapifyMapView(context);
        this.usePlaceButton = new Button(context.text("place.use.selected"));
        queryField.setName("placeQueryField");
        resultsContainer.setName("placeResultsContainer");
        selectionLabel.setName("placeSelectionLabel");
        usePlaceButton.setName("usePlaceButton");
        usePlaceButton.setUIID("PrimaryActionButton");
        usePlaceButton.setEnabled(false);
        usePlaceButton.addActionListener(evt -> useSelectedPlace());

        queryField.setHint(context.text("place.search.hint"));
        Button searchButton = new Button(context.text("search"));
        searchButton.setName("placeSearchButton");
        searchButton.addActionListener(evt -> executeSearch());

        Container searchRow = new Container(BoxLayout.y());
        searchRow.add(queryField);
        searchRow.add(searchButton);

        resultsContainer.setScrollableY(true);
        selectionLabel.setText(context.text("place.no.selection"));

        Container north = new Container(BoxLayout.y());
        north.add(searchRow);
        north.add(selectionLabel);

        Container south = new Container(BoxLayout.y());
        south.add(mapView);
        south.add(usePlaceButton);

        add(BorderLayout.NORTH, north);
        add(BorderLayout.CENTER, resultsContainer);
        add(BorderLayout.SOUTH, south);
    }

    /**
     * Validates the query and starts the asynchronous place lookup.
     */
    private void executeSearch() {
        String query = queryField.getText() == null ? "" : queryField.getText().trim();
        if (query.length() < 3) {
            Dialog.show(context.text("validation.title"), context.text("place.validation.query"), context.text("ok"), null);
            return;
        }
        if (!AppConfig.isGeoapifyConfigured()) {
            Dialog.show(context.text("geoapify.title"), context.text("geoapify.missing.key"), context.text("ok"), null);
            return;
        }
        if (activeSearch != null) {
            activeSearch.cancel();
        }
        selectionLabel.setText(context.text("place.search.loading"));
        resultsContainer.removeAll();
        resultsContainer.revalidate();
        activeSearch = context.getPlaceSearchService().search(
                context,
                query,
                this::showSuggestions,
                (source, err, errorCode, message) -> selectionLabel.setText(context.text("place.search.failed"))
        );
    }

    /**
     * Rebuilds the result list from the returned suggestions.
     *
     * @param suggestions suggestions returned by the service
     */
    private void showSuggestions(List<PlaceSuggestion> suggestions) {
        resultsContainer.removeAll();
        selectedSuggestion = null;
        usePlaceButton.setEnabled(false);
        mapView.showPlace(null);
        if (suggestions.isEmpty()) {
            selectionLabel.setText(context.text("place.search.empty"));
            resultsContainer.add(new SpanLabel(context.text("place.search.empty")));
        } else {
            selectionLabel.setText(context.format("place.search.results", Integer.valueOf(suggestions.size())));
            for (PlaceSuggestion suggestion : suggestions) {
                resultsContainer.add(createSuggestionCard(suggestion));
            }
        }
        resultsContainer.revalidate();
        revalidateLater();
        applyAutoShrinkLater();
    }

    /**
     * Builds one result card plus an invisible overlay button for unified click handling.
     *
     * @param suggestion suggestion to display
     * @return configured card
     */
    private Container createSuggestionCard(PlaceSuggestion suggestion) {
        Container result = new Container(new LayeredLayout());
        result.setUIID("ActivityCard");
        Container content = new Container(new BorderLayout());

        Label iconLabel = new Label();
        iconLabel.setUIID("SectionTitle");
        FontImage.setMaterialIcon(iconLabel, FontImage.MATERIAL_PLACE, 4f);

        SpanLabel titleLabel = new SpanLabel(suggestion.primaryText());
        titleLabel.setUIID("SectionTitle");

        SpanLabel subtitleLabel = new SpanLabel(suggestion.secondaryText());
        subtitleLabel.setUIID("SectionText");

        Container textColumn = new Container(BoxLayout.y());
        textColumn.add(titleLabel);
        textColumn.add(subtitleLabel);

        Button overlay = createCardOverlayButton("placeSuggestion-" + sanitize(suggestion.placeId()), () -> selectSuggestion(suggestion));
        installCardPressFeedback(result, overlay);

        content.add(BorderLayout.WEST, iconLabel);
        content.add(BorderLayout.CENTER, textColumn);

        LayeredLayout layout = (LayeredLayout) result.getLayout();
        result.add(content);
        result.add(overlay);
        layout.setInsets(content, "0 0 0 0");
        layout.setInsets(overlay, "0 0 0 0");
        return result;
    }

    /**
     * Marks a suggestion as selected and refreshes the preview widgets.
     *
     * @param suggestion selected suggestion
     */
    private void selectSuggestion(PlaceSuggestion suggestion) {
        selectedSuggestion = suggestion;
        selectionLabel.setText(suggestion.primaryText() + "\n" + suggestion.secondaryText());
        mapView.showPlace(suggestion.toPlaceInfo());
        usePlaceButton.setEnabled(true);
    }

    /**
     * Confirms the selected place and returns to the previous form.
     */
    private void useSelectedPlace() {
        if (selectedSuggestion == null) {
            return;
        }
        PlaceInfo place = selectedSuggestion.toPlaceInfo();
        releaseResources();
        if (placeSelectionCallback != null) {
            CN.callSerially(() -> placeSelectionCallback.onSucess(place));
        }
    }

    /**
     * Cancels asynchronous work and releases browser-backed peers before leaving this form.
     */
    private void releaseResources() {
        if (activeSearch != null) {
            activeSearch.cancel();
            activeSearch = null;
        }
        mapView.releaseBrowser();
    }

    /**
     * Produces a stable ASCII-only fragment for component names and cache keys.
     *
     * @param value raw identifier
     * @return sanitized identifier
     */
    private String sanitize(String value) {
        if (value == null || value.length() == 0) {
            return "unknown";
        }
        StringBuilder out = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
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
     * Creates a transparent overlay button for the whole suggestion card.
     *
     * @param name component name used by tests
     * @param action action to run when tapped
     * @return configured overlay button
     */
    private Button createCardOverlayButton(String name, Runnable action) {
        Button overlay = new Button();
        overlay.setName(name);
        overlay.setText("");
        overlay.addActionListener(evt -> action.run());
        overlay.setFocusable(false);
        overlay.setRippleEffect(false);
        clearOverlayButtonStyle(overlay.getAllStyles());
        clearOverlayButtonStyle(overlay.getPressedStyle());
        clearOverlayButtonStyle(overlay.getSelectedStyle());
        clearOverlayButtonStyle(overlay.getDisabledStyle());
        return overlay;
    }

    /**
     * Changes the suggestion card UIID while the overlay is pressed.
     *
     * @param card card to restyle
     * @param overlay overlay button handling the pointer events
     */
    private void installCardPressFeedback(Container card, Button overlay) {
        overlay.addPointerPressedListener(evt -> {
            card.setUIID("ActivityCardPressed");
            card.repaint();
        });
        overlay.addPointerReleasedListener(evt -> {
            card.setUIID("ActivityCard");
            card.repaint();
        });
    }

    /**
     * Removes the default button visuals from the card overlay.
     *
     * @param style one overlay button style
     */
    private void clearOverlayButtonStyle(Style style) {
        style.setBgTransparency(0);
        style.setBgColor(0);
        style.setBorder(Border.createEmpty());
        style.setMargin(0, 0, 0, 0);
        style.setPadding(0, 0, 0, 0);
    }
}
