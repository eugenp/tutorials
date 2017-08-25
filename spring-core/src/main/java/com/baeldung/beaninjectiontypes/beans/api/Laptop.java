package com.baeldung.beaninjectiontypes.beans.api;

/**
 * @author mushtaq
 * @since 22/08/17.
 */
public abstract class Laptop {

    private VideoGpu primaryGpu;
    private VideoGpu secondaryGpu;
    private Processor processor;

    public Processor getProcessor() {
        return processor;
    }

    public VideoGpu getPrimaryGpu() {
        return primaryGpu;
    }

    public VideoGpu getSecondaryGpu() {
        return secondaryGpu;
    }

    public void setPrimaryGpu(VideoGpu primaryGpu) {
        this.primaryGpu = primaryGpu;
    }

    public void setSecondaryGpu(VideoGpu secondaryGpu) {
        this.secondaryGpu = secondaryGpu;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public String toString() {
        return " Laptop\n--------------------- " + "\n\tprocessor = " + getProcessor() + "\n\tprimaryGpu = " + ((getPrimaryGpu() != null) ? getPrimaryGpu().toString() : "N/A") + "\n\tsecondaryGpu = " + ((getSecondaryGpu() != null)
          ? getSecondaryGpu().toString()
          : "N/A") + "\n";
    }
}
