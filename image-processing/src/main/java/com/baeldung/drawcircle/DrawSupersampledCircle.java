package com.baeldung.drawcircle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class DrawSupersampledCircle {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("SupersampledCircle");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new CirclePanel());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

    static class CirclePanel extends JPanel {
        private final BufferedImage hiResImage;
        private final int finalSize = 6;

        public CirclePanel() {
            int scale = 3;
            float stroke = 6f;

            hiResImage = makeSupersampledCircle(scale, stroke);
            setPreferredSize(new Dimension(finalSize + 32, finalSize + 32));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                int x = (getWidth() - finalSize) / 2;
                int y = (getHeight() - finalSize) / 2;

                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                g2.drawImage(hiResImage, x, y, finalSize, finalSize, null);
            } finally {
                g2.dispose();
            }
        }

        private BufferedImage makeSupersampledCircle(int scale, float stroke) {
            int hi = finalSize * scale;
            BufferedImage img = new BufferedImage(hi, hi, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                double d = hi - stroke;
                Shape circle = new Ellipse2D.Double(stroke / 2.0, stroke / 2.0, d, d);

                g2.setPaint(new Color(0xBBDEFB));
                g2.fill(circle);

                g2.setPaint(new Color(0x0D47A1));
                g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(circle);
            } finally {
                g2.dispose();
            }
            return img;
        }
    }
}
