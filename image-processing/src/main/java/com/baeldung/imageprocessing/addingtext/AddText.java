package com.baeldung.imageprocessing.addingtext;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;

import javax.imageio.ImageIO;

public class AddText {
    public static void main(String[] args) throws IOException {
        String imagePath = AddText.class.getClassLoader().getResource("lena.jpg").getPath();
        
        ImagePlus resultPlus= signImageImageProcessor("www.baeldung.com", imagePath);
        resultPlus.show();
        
        ImagePlus resultGraphics = new ImagePlus("", signImageGraphics("www.baeldung.com", imagePath));
        resultGraphics.show();

        ImagePlus resultGraphicsWithIterator = new ImagePlus("", signImageGraphicsWithIterator("www.baeldung.com", imagePath));
        resultGraphicsWithIterator.show();

        ImagePlus resultGraphicsCentered = new ImagePlus("", signImageCenter("www.baeldung.com", imagePath));
        resultGraphicsCentered.show();

        ImagePlus resultGraphicsBottomRight = new ImagePlus("", signImageBottomRight("www.baeldung.com", imagePath));
        resultGraphicsBottomRight.show();

        ImagePlus resultGraphicsTopLeft= new ImagePlus("", signImageTopLeft("www.baeldung.com", imagePath));
        resultGraphicsTopLeft.show();

        ImagePlus resultGraphicsAdaptBasedOnImage= new ImagePlus("", signImageAdaptBasedOnImage("www.baeldung.com", imagePath));
        resultGraphicsAdaptBasedOnImage.show();
    }

    private static ImagePlus signImageImageProcessor(String text, String path) {
        ImagePlus image = IJ.openImage(path);
        Font font = new Font("Arial", Font.BOLD, 18);
        
        ImageProcessor ip = image.getProcessor();
        ip.setColor(Color.GREEN);
        ip.setFont(font);
        ip.drawString(text, 0, 20);
        
        return image;
    }

    private static BufferedImage signImageGraphics(String text, String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        Font font = new Font("Arial", Font.BOLD, 18);
        
        Graphics g = image.getGraphics();
        g.setFont(font);
        g.setColor(Color.GREEN);
        g.drawString(text, 0, 20);

        return image;
    }
    

    private static BufferedImage signImageGraphicsWithIterator(String text, String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        Font font = new Font("Arial", Font.BOLD, 18);
        
        AttributedString attributedText = new AttributedString(text);
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
        
        Graphics g = image.getGraphics();
        g.drawString(attributedText.getIterator(), 0, 20);

        return image;
    }
    
    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     * @throws IOException 
     */
    public static BufferedImage signImageCenter(String text, String path) throws IOException {
        
        BufferedImage image = ImageIO.read(new File(path));
        Font font = new Font("Arial", Font.BOLD, 18);
        
        AttributedString attributedText = new AttributedString(text);
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
        
        Graphics g = image.getGraphics();

        FontMetrics metrics = g.getFontMetrics(font);
        int positionX = (image.getWidth() - metrics.stringWidth(text)) / 2;
        int positionY = (image.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        
        g.drawString(attributedText.getIterator(), positionX, positionY);
                
        return image;
    } 
    
    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     * @throws IOException 
     */
    public static BufferedImage signImageBottomRight(String text, String path) throws IOException {
        
        BufferedImage image = ImageIO.read(new File(path));
        
        Font font = new Font("Arial", Font.BOLD, 18);
        
        AttributedString attributedText = new AttributedString(text);
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
        
        Graphics g = image.getGraphics();

        FontMetrics metrics = g.getFontMetrics(font);
        int positionX = (image.getWidth() - metrics.stringWidth(text));
        int positionY = (image.getHeight() - metrics.getHeight()) + metrics.getAscent();
        
        g.drawString(attributedText.getIterator(), positionX, positionY);
                
        return image;
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     * @throws IOException 
     */
    public static BufferedImage signImageTopLeft(String text, String path) throws IOException {
        
        BufferedImage image = ImageIO.read(new File(path));
        
        Font font = new Font("Arial", Font.BOLD, 18);
        
        AttributedString attributedText = new AttributedString(text);
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
        
        Graphics g = image.getGraphics();

        FontMetrics metrics = g.getFontMetrics(font);
        int positionX = 0;
        int positionY = metrics.getAscent();
        
        g.drawString(attributedText.getIterator(), positionX, positionY);
                
        return image;
    }
    
    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     * @throws IOException 
     */
    public static BufferedImage signImageAdaptBasedOnImage(String text, String path) throws IOException {
        
        BufferedImage image = ImageIO.read(new File(path));
        
        Font font = createFontToFit(new Font("Arial", Font.BOLD, 80), text, image);
        
        AttributedString attributedText = new AttributedString(text);
        attributedText.addAttribute(TextAttribute.FONT, font);
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
        
        Graphics g = image.getGraphics();
        
        FontMetrics metrics = g.getFontMetrics(font);
        int positionX = (image.getWidth() - metrics.stringWidth(text));
        int positionY = (image.getHeight() - metrics.getHeight()) + metrics.getAscent();
        
        g.drawString(attributedText.getIterator(), positionX, positionY);
                
        return image;
    }
    
    public static Font createFontToFit(Font baseFont, String text, BufferedImage image) throws IOException
    {              
        Font newFont = baseFont;
        
        FontMetrics ruler = image.getGraphics().getFontMetrics(baseFont);
        GlyphVector vector = baseFont.createGlyphVector(ruler.getFontRenderContext(), text);
        
        Shape outline = vector.getOutline(0, 0);
        
        double expectedWidth = outline.getBounds().getWidth();
        double expectedHeight = outline.getBounds().getHeight();
        
        boolean textFits = image.getWidth() >= expectedWidth && image.getHeight() >= expectedHeight;
        
        if(!textFits) {
            double widthBasedFontSize = (baseFont.getSize2D()*image.getWidth())/expectedWidth;
            double heightBasedFontSize = (baseFont.getSize2D()*image.getHeight())/expectedHeight;
            
            double newFontSize = widthBasedFontSize < heightBasedFontSize ? widthBasedFontSize : heightBasedFontSize;
            newFont = baseFont.deriveFont(baseFont.getStyle(), (float)newFontSize);
        }
        return newFont;
    }
}
