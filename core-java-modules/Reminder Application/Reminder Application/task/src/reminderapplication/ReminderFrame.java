package reminderapplication;

import static reminderapplication.ConstraintsBuilder.*;

import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ReminderFrame extends JFrame {

    private static Timer TIMER = new Timer();
    private final TimeReminderApplication reminderApplication;
    private final JLabel reminderTextLabel;
    private final JLabel repeatPeriodLabel;
    private final JLabel setDelayLabel;
    private final JComboBox<Integer> delay;
    private final JComboBox<Integer> period;
    private final JButton cancelButton;
    private final JButton okButton;
    private final JTextField textField;
    private final JLabel delaysLabel;
    private final JLabel periodLabel;

    public ReminderFrame(TimeReminderApplication reminderApp) throws HeadlessException {
        this.reminderApplication = reminderApp;
        textField = createTextField();
        delay = createDelayComboBox();
        period = createPeriodComboBox();
        cancelButton = createCancelButton();
        okButton = createOkButton();
        reminderTextLabel = createReminderTextLabel();
        repeatPeriodLabel = createRepeatPeriodLabel();
        setDelayLabel = createSetDelayLabel();
        delaysLabel = createDelaysLabel();
        periodLabel = createPeriodLabel();
        configureVisualRepresentation();
        configureActions();
    }

    private void configureActions() {
        createNewReminder();
    }

    private void configureVisualRepresentation() {
        configureFrame();
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        add(reminderTextLabel, constraint(0,0));
        add(repeatPeriodLabel, constraint(1,0));
        add(setDelayLabel, constraint(2,0));
        add(textField, constraint(0, 1));
        add(delay, constraint(1, 1));
        add(period, constraint(2, 1));
        add(delaysLabel, constraint(1,3));
        add(periodLabel, constraint(2,3));
        add(okButton, constraint(1, 4));
        add(cancelButton, constraint(2, 4));
        pack();
        setVisible(true);
    }

    private void configureFrame() {
        setTitle("Set Reminder");
        setName("Set Reminder");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private static JLabel createSetDelayLabel() {
        return createLabel("Set Delay", "Set Delay Label");
    }

    private static JLabel createRepeatPeriodLabel() {
        return createLabel("Set Period", "Set Repeat Period Label");
    }

    private static JLabel createReminderTextLabel() {
        return createLabel("Reminder Text", "Reminder Text Label");
    }

    private JLabel createPeriodLabel() {
        return createLabel("0", "Period label");
    }

    private JLabel createDelaysLabel() {
        return createLabel("30", "Delays Label");
    }

    private JComboBox<Integer> createPeriodComboBox() {
        final JComboBox<Integer> comboBox = new JComboBox<>(new DefaultComboBoxModel<>(new Integer[]{0, 5, 10, 20}));
        comboBox.setName("set Period");
        comboBox.addActionListener(e -> periodLabel.setText(comboBox.getSelectedItem().toString()));
        return comboBox;
    }

    private JComboBox<Integer> createDelayComboBox() {
        final JComboBox<Integer> comboBox = new JComboBox<>(new DefaultComboBoxModel<>(new Integer[]{30, 25, 15, 5}));
        comboBox.setName("set Delay");
        comboBox.addActionListener(e -> delaysLabel.setText(comboBox.getSelectedItem().toString()));
        return comboBox;
    }

    private JTextField createTextField() {
        final JTextField textField = new JTextField(20);
        textField.setName("Field");
        return textField;
    }

    private JButton createOkButton() {
        final JButton button = new JButton("ok");
        button.setName("OK");
        return button;
    }

    private void createNewReminder() {

        okButton.addActionListener(e -> this.dispose());
        okButton.addActionListener(e -> {
            final int periodInSeconds = getTimeInSeconds(period);
            final int delayInSeconds = getTimeInSeconds(delay);
            final Reminder reminder = new Reminder(textField.getText(), delayInSeconds, periodInSeconds);
            ((DefaultListModel<Reminder>) reminderApplication.getReminders()).addElement(reminder);
        });
        okButton.addActionListener(e -> scheduleReminder(textField, delay, period));
    }

    private void scheduleReminder(final JTextField textField, final JComboBox<Integer> delay, final JComboBox<Integer> period) {
        final int periodInSeconds = getTimeInSeconds(period);
        if (periodInSeconds == 0)
            scheduleNonRepeatedReminder(textField, delay);
        else
            scheduleRepeatedReminder(textField, delay, period);
    }

    private void scheduleRepeatedReminder(final JTextField textField, final JComboBox<Integer> delay, final JComboBox<Integer> period) {
        final int delayInSeconds = getTimeInSeconds(delay) + 200;
        final int periodInSeconds = getTimeInSeconds(period);
        final TimerTask timerTask = getTimerTask(textField.getText(), delayInSeconds, periodInSeconds);
        TIMER.schedule(timerTask, TimeUnit.SECONDS.toMillis(delayInSeconds), TimeUnit.SECONDS.toMillis(periodInSeconds));
    }

    private void scheduleNonRepeatedReminder(final JTextField textField, final JComboBox<Integer> delay) {
        final int delayInSeconds = getTimeInSeconds(delay);
        final int periodInSeconds = 0;
        final TimerTask timerTask = getTimerTask(textField.getText(), delayInSeconds, periodInSeconds);
        TIMER.schedule(timerTask, TimeUnit.SECONDS.toMillis(delayInSeconds));

    }

    private int getTimeInSeconds(final JComboBox<Integer> comboBox) {
        if (comboBox != null && comboBox.getSelectedItem() != null)
            return ((Integer) comboBox.getSelectedItem());
        else
            return 0;
    }

    private TimerTask getTimerTask(final String reminderText, final Integer delayInSeconds, final Integer periodInSeconds) {
        return new TimerTask() {
            @Override
            public void run() {
                new ReminderPopupFrame(reminderApplication, reminderText, delayInSeconds, periodInSeconds);
            }
        };
    }

    private JButton createCancelButton() {
        final JButton button = new JButton("cancel");
        button.setName("Cancel");
        button.addActionListener(e -> this.dispose());
        return button;
    }

    private static JLabel createLabel(final String text, final String name) {
        JLabel label = new JLabel(text);
        label.setName(name);
        return label;
    }
}
