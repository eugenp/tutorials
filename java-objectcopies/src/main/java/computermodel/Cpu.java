package computermodel;

public class Cpu {

    private String modelNumber;
    private String brandName;

    public Cpu(String modelNumber, String brandName) {
        this.modelNumber = modelNumber;
        this.brandName = brandName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
