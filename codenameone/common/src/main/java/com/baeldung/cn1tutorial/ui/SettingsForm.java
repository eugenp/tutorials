package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.model.AppearanceMode;
import com.baeldung.cn1tutorial.service.AppContext;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.spinner.Picker;

/**
 * Settings screen for language, appearance and app font scaling.
 */
public class SettingsForm extends BaseForm {
    private static final int[] FONT_STEPS = {80, 90, 100, 110, 120, 130};

    private final Picker languagePicker = new Picker();
    private final Picker appearancePicker = new Picker();
    private final Slider fontSlider = new Slider();
    private final Label sliderValueLabel = new Label();

    /**
     * @param context shared runtime context
     * @param previousForm previous form for back navigation
     */
    public SettingsForm(AppContext context, Form previousForm) {
        super(context, "settings.title", previousForm);
        Container content = new Container(BoxLayout.y());
        content.setScrollableY(true);

        configureLanguagePicker();
        content.add(fieldRow(context.text("settings.language"), languagePicker));

        configureAppearancePicker();
        content.add(fieldRow(context.text("settings.appearance"), appearancePicker));

        configureSlider();
        content.add(fieldRow(context.text("settings.font.size"), sliderSection()));

        Button crashTest = new Button(context.text("settings.crash.test"));
        crashTest.setName("settingsCrashTestButton");
        crashTest.addActionListener(evt -> {
            Object crash = null;
            crash.toString();
        });
        content.add(crashTest);

        Button apply = new Button(context.text("settings.apply"));
        apply.setName("settingsApplyButton");
        apply.setUIID("PrimaryActionButton");
        apply.addActionListener(evt -> applySettings());

        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, apply);
    }

    /**
     * Configures the language picker using the same visual and keyboard behavior as other CN1
     * pickers.
     */
    private void configureLanguagePicker() {
        languagePicker.setName("settingsLanguagePicker");
        languagePicker.setUseLightweightPopup(true);
        languagePicker.setStrings(context.text("language.english"), context.text("language.italian"));
        languagePicker.setSelectedStringIndex("it".equals(context.getSettings().languageCode()) ? 1 : 0);
    }

    /**
     * Configures the appearance picker using stable indexes mapped to {@link AppearanceMode}.
     */
    private void configureAppearancePicker() {
        appearancePicker.setName("settingsAppearancePicker");
        appearancePicker.setUseLightweightPopup(true);
        appearancePicker.setStrings(
                context.text("appearance.follow.device"),
                context.text("appearance.light"),
                context.text("appearance.dark")
        );
        appearancePicker.setSelectedStringIndex(indexOf(context.getSettings().appearanceMode()));
    }

    /**
     * Configures the slider and its CN1-specific rendering styles.
     */
    private void configureSlider() {
        fontSlider.setEditable(true);
        fontSlider.setName("settingsFontSlider");
        fontSlider.setUIID("FontScaleSlider");
        fontSlider.setMinValue(0);
        fontSlider.setMaxValue(FONT_STEPS.length - 1);
        fontSlider.setIncrements(FONT_STEPS.length - 1);
        fontSlider.setRenderValueOnTop(false);
        fontSlider.setRenderPercentageOnTop(false);
        fontSlider.setProgress(indexOf(context.getSettings().fontScalePercent()));
        fontSlider.addDataChangedListener((type, index) -> sliderValueLabel.setText(FONT_STEPS[fontSlider.getProgress()] + "%"));
        sliderValueLabel.setName("settingsFontValueLabel");
        sliderValueLabel.setText(FONT_STEPS[fontSlider.getProgress()] + "%");

        boolean darkMode = Boolean.TRUE.equals(CN.isDarkMode());
        int fullColor = darkMode ? 0x5eead4 : 0x0f766e;
        int emptyColor = darkMode ? 0x334155 : 0xcbd5e1;
        int thumbColor = darkMode ? 0x99f6e4 : 0x115e59;
        configureSliderTrackStyle(fontSlider.getSliderFullUnselectedStyle(), fullColor);
        configureSliderTrackStyle(fontSlider.getSliderFullSelectedStyle(), fullColor);
        configureSliderTrackStyle(fontSlider.getSliderEmptyUnselectedStyle(), emptyColor);
        configureSliderTrackStyle(fontSlider.getSliderEmptySelectedStyle(), emptyColor);
        Style thumbStyle = new Style();
        thumbStyle.setFgColor(thumbColor);
        thumbStyle.setBgTransparency(0);
        fontSlider.setThumbImage(FontImage.createMaterial(FontImage.MATERIAL_CIRCLE, thumbStyle, 4.8f));
        fontSlider.setPreferredH(CN.convertToPixels(7f));
    }

    /**
     * Removes skin-specific slider styling and replaces it with a consistent rounded track.
     *
     * @param style slider full/empty style to normalize
     * @param color track color
     */
    private void configureSliderTrackStyle(Style style, int color) {
        style.stripMarginAndPadding();
        style.setBgImage(null);
        style.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        style.setBgColor(color);
        style.setBgTransparency(255);
        style.setBorder(RoundRectBorder.create()
                .cornerRadius(1.5f)
                .stroke(0, false)
                .shadowOpacity(0));
    }

    /**
     * Builds the slider block made of slider, tick marks and current value label.
     *
     * @return slider section container
     */
    private Container sliderSection() {
        Container section = new Container(BoxLayout.y());
        section.add(fontSlider);
        section.add(new SliderScale(fontSlider, FONT_STEPS));
        section.add(sliderValueLabel);
        return section;
    }

    /**
     * Collects the current UI values and reapplies settings through the shared app context.
     */
    private void applySettings() {
        String languageCode = languagePicker.getSelectedStringIndex() == 1 ? "it" : "en";
        int fontScale = FONT_STEPS[fontSlider.getProgress()];
        AppearanceMode appearanceMode = appearanceModeForIndex(appearancePicker.getSelectedStringIndex());
        context.applySettings(new AppSettings(languageCode, fontScale, appearanceMode), false);
        context.showSettings(previousForm);
    }

    /**
     * Finds the slider index matching the stored font scale.
     *
     * @param fontScalePercent stored font percentage
     * @return matching slider index
     */
    private int indexOf(int fontScalePercent) {
        for (int i = 0; i < FONT_STEPS.length; i++) {
            if (FONT_STEPS[i] == fontScalePercent) {
                return i;
            }
        }
        return 2;
    }

    /**
     * Finds the picker index matching the stored appearance mode.
     *
     * @param appearanceMode stored appearance mode
     * @return picker index
     */
    private int indexOf(AppearanceMode appearanceMode) {
        switch (appearanceMode) {
            case LIGHT:
                return 1;
            case DARK:
                return 2;
            default:
                return 0;
        }
    }

    /**
     * Resolves the appearance mode represented by the picker index.
     *
     * @param index picker index
     * @return selected appearance mode
     */
    private AppearanceMode appearanceModeForIndex(int index) {
        switch (index) {
            case 1:
                return AppearanceMode.LIGHT;
            case 2:
                return AppearanceMode.DARK;
            default:
                return AppearanceMode.SYSTEM;
        }
    }

    /**
     * Creates one vertical label+field row.
     *
     * @param labelText localized label
     * @param field field component
     * @return configured row
     */
    private Container fieldRow(String labelText, com.codename1.ui.Component field) {
        Container row = new Container(BoxLayout.y());
        row.setUIID("SettingsRow");
        row.add(new Label(labelText));
        row.add(field);
        return row;
    }

    /**
     * Lightweight component that draws the tick marks aligned with the CN1 slider thumb positions.
     * <p>
     * Drawing the scale manually is more accurate than relying on a layout full of labels because
     * the thumb width changes the effective geometry of the slider track.
     */
    private static final class SliderScale extends Component {
        private final Slider slider;
        private final int[] steps;

        /**
         * @param slider slider whose geometry is mirrored
         * @param steps supported font-scale values
         */
        private SliderScale(Slider slider, int[] steps) {
            this.slider = slider;
            this.steps = steps;
            setUIID("TickLabel");
            setFocusable(false);
        }

        /**
         * Returns a small preferred height that mainly depends on the current font.
         */
        @Override
        protected Dimension calcPreferredSize() {
            int height = getStyle().getFont().getHeight() + CN.convertToPixels(3f);
            return new Dimension(Display.getInstance().getDisplayWidth() / 2, height);
        }

        /**
         * Paints one vertical marker per slider stop.
         *
         * @param g component graphics
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Style style = getStyle();
            g.setColor(style.getFgColor());
            g.setFont(style.getFont());

            int x = getX();
            int y = getY();
            int width = getWidth();
            int height = getHeight();
            int topPadding = style.getPaddingTop();
            int bottomPadding = style.getPaddingBottom();
            int contentTop = y + topPadding;
            int contentHeight = height - topPadding - bottomPadding;
            int markerHeight = Math.max(2, contentHeight - CN.convertToPixels(1f));
            int markerTop = contentTop;

            Image thumb = slider.getThumbImage();
            int thumbWidth = thumb == null ? 0 : thumb.getWidth();
            int minCenter = thumbWidth > 0 ? thumbWidth / 2 : 0;
            int maxCenter = thumbWidth > 0 ? Math.max(minCenter, width - thumbWidth / 2) : width;
            int tickWidth = Math.max(1, CN.convertToPixels(0.2f));

            for (int i = 0; i < steps.length; i++) {
                float ratio = steps.length == 1 ? 0f : (float) i / (float) (steps.length - 1);
                int centerX = Math.round(ratio * width);
                if (centerX < minCenter) {
                    centerX = minCenter;
                }
                if (centerX > maxCenter) {
                    centerX = maxCenter;
                }
                int markerX = Math.max(x, Math.min(x + width - tickWidth, x + centerX - tickWidth / 2));
                g.fillRect(markerX, markerTop, tickWidth, markerHeight);
            }
        }
    }
}
