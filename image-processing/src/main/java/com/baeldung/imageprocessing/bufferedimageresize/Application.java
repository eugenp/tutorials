package com.baeldung.image;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class Application {

    public static void main(String[] args) throws Exception {
        BufferedImage srcImg = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        float scaleW = 2.0f, scaleH = 2.0f;
        int w = srcImg.getWidth() * (int) scaleW;
        int h = srcImg.getHeight() * (int) scaleH;
        BufferedImage dstImg = new BufferedImage(w, h, srcImg.getType());
        AffineTransform scalingTransform = new AffineTransform();
        scalingTransform.scale(scaleW, scaleH);
        AffineTransformOp scaleOp = new AffineTransformOp(scalingTransform, AffineTransformOp.TYPE_BILINEAR);
        dstImg = scaleOp.filter(srcImg, dstImg);
        ImageIO.write(dstImg, "jpg", new File("src/main/resources/images/resized.jpg"));
    }

}
