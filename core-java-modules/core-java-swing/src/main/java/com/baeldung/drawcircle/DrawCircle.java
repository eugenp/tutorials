package com.baeldung.drawcircle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class DrawCircle extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            float stroke = 2f;
            double diameter = Math.min(getWidth(), getHeight()) - 12; // padding
            double x = (getWidth()  - diameter) / 2.0;
            double y = (getHeight() - diameter) / 2.0;

            Ellipse2D circle = new Ellipse2D.Double(x, y, diameter, diameter);

            g2.setColor(new Color(0xBBDEFB));
            g2.fill(circle);

            g2.setColor(new Color(0x0D47A1));
            g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(circle);
        } finally {
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Circle");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new DrawCircle());
            f.setSize(320, 240);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
