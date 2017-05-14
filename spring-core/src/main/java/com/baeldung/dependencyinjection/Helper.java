Package com.baeldung.dependencyinjection;

public class Helper {
    OutputGenerator outputGenerator;
 
    Helper(OutputGenerator outputGenerator) {
        this.outputGenerator = outputGenerator;
    }
    
    public void setOutputGenerator(OutputGenerator outputGenerator) {
        this.outputGenerator = outputGenerator;
    }
}
