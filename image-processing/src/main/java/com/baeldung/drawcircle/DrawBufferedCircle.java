package com.baeldung.drawcircle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class DrawBufferedCircle {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Circle");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new CircleImagePanel(64, 3));   // final size: 64x64, supersample: 3x
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

    static class CircleImagePanel extends JPanel {
        private final int finalSize;
        private final BufferedImage circleImage;

        public CircleImagePanel(int finalSize, int scale) {
            this.finalSize = finalSize;
            this.circleImage = makeCircleImage(finalSize, scale);
            setPreferredSize(new Dimension(finalSize + 16, finalSize + 16));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                int x = (getWidth()  - finalSize) / 2;
                int y = (getHeight() - finalSize) / 2;

                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                g2.drawImage(circleImage, x, y, finalSize, finalSize, null);
            } finally {
                g2.dispose();
            }
        }

        private BufferedImage makeCircleImage(int finalSize, int scale) {
            int hi = finalSize * scale;
            BufferedImage img = new BufferedImage(hi, hi, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                        RenderingHints.VALUE_STROKE_PURE);

                g2.setComposite(AlphaComposite.Src);

                float stroke = 6f * scale / 3f;
                double diameter = hi - stroke - (4 * scale);
                double x = (hi - diameter) / 2.0;
                double y = (hi - diameter) / 2.0;

                Shape circle = new Ellipse2D.Double(x, y, diameter, diameter);

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
