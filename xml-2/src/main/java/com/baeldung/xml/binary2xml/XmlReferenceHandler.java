import java.util.HashMap;
import java.util.Map;

public class XmlReferenceHandler {
    private Map<String, String> externalDataMap;

    public XmlReferenceHandler() {
        // Initialize or load external data
        externalDataMap = new HashMap<>();
        externalDataMap.put("external-data.xml", "Binary data for external file");
    }

    public String processXml(String xmlContent) {
        // Logic to handle XML references
        // Replace references with actual data
        for (Map.Entry<String, String> entry : externalDataMap.entrySet()) {
            String reference = "<reference>" + entry.getKey() + "</reference>";
            xmlContent = xmlContent.replace(reference, entry.getValue());
        }

        return xmlContent;
    }
}
