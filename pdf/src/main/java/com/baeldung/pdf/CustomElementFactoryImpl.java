package com.baeldung.pdf;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class CustomElementFactoryImpl implements ReplacedElementFactory {
    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth,
                                                 int cssHeight) {
        Element e = box.getElement();
        String nodeName = e.getNodeName();
        if (nodeName.equals("img")) {
            String imagePath = e.getAttribute("src");
            try {
                FSImage fsImage = getImageInstance(imagePath);
                if (cssWidth != -1 || cssHeight != -1) {
                    fsImage.scale(cssWidth, cssHeight);
                } else {
                    fsImage.scale(2000, 1000);
                }
                return new ITextImageElement(fsImage);
            } catch (Exception e1) {
                // Log if any errors
            }
        }
        return null;
    }

    private FSImage getImageInstance(String imagePath) throws IOException, BadElementException {
		InputStream input = new FileInputStream(imagePath);
        final byte[] bytes = IOUtils.toByteArray(input);
        final Image image = Image.getInstance(bytes);
        FSImage fsImage = new ITextFSImage(image);
        return fsImage;
    }

    @Override
    public void reset() {
    }

    @Override
    public void remove(Element e) {
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
    }
}