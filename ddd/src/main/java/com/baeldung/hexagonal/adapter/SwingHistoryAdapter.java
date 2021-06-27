package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.model.WeightInTime;
import com.baeldung.hexagonal.core.port.WeightTrackerViewerService;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SwingHistoryAdapter implements UIAdapter {
    private final WeightTrackerViewerService service;

    public SwingHistoryAdapter(WeightTrackerViewerService service) {
        this.service = service;
    }

    @Override
    public void display() {
        JFrame jFrame = new JFrame("WeightTracker - Swing Adapter (Just viewer)");
        jFrame.setSize(500,300);

        List<WeightInTime> weightHistory = service.getWeightHistory();
        if (weightHistory.isEmpty()) {
            JLabel jLabel = new JLabel();
            jLabel.setText("<html><p><center><h1>No data Found</h1></center></p><p>Please use the Console version to add items.</p></html>");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jFrame.add(jLabel);
        } else {
            AtomicInteger index = new AtomicInteger();
            Object[][] data = new Object[weightHistory.size()][4];
            weightHistory.stream().map(w -> {
                return new Object[]{w.getId(), w.getWeight(), w.getWhen()};
            }).forEach(w -> {
                data[index.getAndIncrement()] = w;
            });

            String[] columnNames = {"id", "weight", "when"};
            JTable jTable = new JTable(data, columnNames);
            jFrame.add(new JScrollPane(jTable));
        }

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
