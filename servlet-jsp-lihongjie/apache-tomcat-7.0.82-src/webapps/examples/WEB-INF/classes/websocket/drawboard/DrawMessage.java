/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package websocket.drawboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * A message that represents a drawing action.
 * Note that we use primitive types instead of Point, Color etc.
 * to reduce object allocation.<br><br>
 *
 * TODO: But a Color objects needs to be created anyway for drawing this
 * onto a Graphics2D object, so this probably does not save much.
 */
public final class DrawMessage {

    private int type;
    private byte colorR, colorG, colorB, colorA;
    private double thickness;
    private double x1, y1, x2, y2;
    private boolean lastInChain;

    /**
     * The type.<br>
     * 1: Brush<br>
     * 2: Line<br>
     * 3: Rectangle<br>
     * 4: Ellipse
     */
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public double getThickness() {
        return thickness;
    }
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public byte getColorR() {
        return colorR;
    }
    public void setColorR(byte colorR) {
        this.colorR = colorR;
    }
    public byte getColorG() {
        return colorG;
    }
    public void setColorG(byte colorG) {
        this.colorG = colorG;
    }
    public byte getColorB() {
        return colorB;
    }
    public void setColorB(byte colorB) {
        this.colorB = colorB;
    }
    public byte getColorA() {
        return colorA;
    }
    public void setColorA(byte colorA) {
        this.colorA = colorA;
    }

    public double getX1() {
        return x1;
    }
    public void setX1(double x1) {
        this.x1 = x1;
    }
    public double getX2() {
        return x2;
    }
    public void setX2(double x2) {
        this.x2 = x2;
    }
    public double getY1() {
        return y1;
    }
    public void setY1(double y1) {
        this.y1 = y1;
    }
    public double getY2() {
        return y2;
    }
    public void setY2(double y2) {
        this.y2 = y2;
    }

    /**
     * Specifies if this DrawMessage is the last one in a chain
     * (e.g. a chain of brush paths).<br>
     * Currently it is unused.
     */
    public boolean isLastInChain() {
        return lastInChain;
    }
    public void setLastInChain(boolean lastInChain) {
        this.lastInChain = lastInChain;
    }



    public DrawMessage(int type, byte colorR, byte colorG, byte colorB,
            byte colorA, double thickness, double x1, double x2, double y1,
            double y2, boolean lastInChain) {

        this.type = type;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.colorA = colorA;
        this.thickness = thickness;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.lastInChain = lastInChain;
    }


    /**
     * Draws this DrawMessage onto the given Graphics2D.
     * @param g
     */
    public void draw(Graphics2D g) {

        g.setStroke(new BasicStroke((float) thickness,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g.setColor(new Color(colorR & 0xFF, colorG & 0xFF, colorB & 0xFF,
                colorA & 0xFF));

        if (x1 == x2 && y1 == y2) {
            // Always draw as arc to meet the behavior in the HTML5 Canvas.
            Arc2D arc = new Arc2D.Double(x1, y1, 0, 0,
                    0d, 360d, Arc2D.OPEN);
            g.draw(arc);

        } else if (type == 1 || type == 2) {
            // Draw a line.
            Line2D line = new Line2D.Double(x1, y1, x2, y2);
            g.draw(line);

        } else if (type == 3 || type == 4) {
            double x1 = this.x1, x2 = this.x2,
                    y1 = this.y1, y2 = this.y2;
            if (x1 > x2) {
                x1 = this.x2;
                x2 = this.x1;
            }
            if (y1 > y2) {
                y1 = this.y2;
                y2 = this.y1;
            }

            // TODO: If (x1 == x2 || y1 == y2) draw as line.

            if (type == 3) {
                // Draw a rectangle.
                Rectangle2D rect = new Rectangle2D.Double(x1, y1,
                        x2 - x1, y2 - y1);
                g.draw(rect);

            } else if (type == 4) {
                // Draw an ellipse.
                Arc2D arc = new Arc2D.Double(x1, y1, x2 - x1, y2 - y1,
                        0d, 360d, Arc2D.OPEN);
                g.draw(arc);

            }
        }
    }

    /**
     * Converts this message into a String representation that
     * can be sent over WebSocket.<br>
     * Since a DrawMessage consists only of numbers,
     * we concatenate those numbers with a ",".
     */
    @Override
    public String toString() {

        return type + "," + (colorR & 0xFF) + "," + (colorG & 0xFF) + ","
                + (colorB & 0xFF) + "," + (colorA & 0xFF) + "," + thickness
                + "," + x1 + "," + y1 + "," + x2 + "," + y2 + ","
                + (lastInChain ? "1" : "0");
    }

    public static DrawMessage parseFromString(String str)
            throws ParseException {

        int type;
        byte[] colors = new byte[4];
        double thickness;
        double[] coords = new double[4];
        boolean last;

        try {
            String[] elements = str.split(",");

            type = Integer.parseInt(elements[0]);
            if (!(type >= 1 && type <= 4))
                throw new ParseException("Invalid type: " + type);

            for (int i = 0; i < colors.length; i++) {
                colors[i] = (byte) Integer.parseInt(elements[1 + i]);
            }

            thickness = Double.parseDouble(elements[5]);
            if (Double.isNaN(thickness) || thickness < 0 || thickness > 100)
                throw new ParseException("Invalid thickness: " + thickness);

            for (int i = 0; i < coords.length; i++) {
                coords[i] = Double.parseDouble(elements[6 + i]);
                if (Double.isNaN(coords[i]))
                    throw new ParseException("Invalid coordinate: "
                            + coords[i]);
            }

            last = !"0".equals(elements[10]);

        } catch (RuntimeException ex) {
            throw new ParseException(ex);
        }

        DrawMessage m = new DrawMessage(type, colors[0], colors[1],
                colors[2], colors[3], thickness, coords[0], coords[2],
                coords[1], coords[3], last);

        return m;
    }

    public static class ParseException extends Exception {
        private static final long serialVersionUID = -6651972769789842960L;

        public ParseException(Throwable root) {
            super(root);
        }

        public ParseException(String message) {
            super(message);
        }
    }



}
