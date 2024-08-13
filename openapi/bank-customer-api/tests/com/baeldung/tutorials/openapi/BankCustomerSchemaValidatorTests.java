import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.JsonLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BankCustomerSchemaValidationTest {
    @Test
    public void testValidBankCustomerSchema() throws Exception {
        String schemaJson = new String(Files.readAllBytes(Paths.get("src/main/resources/schemas/bankCustomerSchema.yaml")));
        String validDataJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"accountNumber\":\"123456789\",\"balance\":1000.00}";

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(JsonLoader.fromString(schemaJson));
        ProcessingReport report = schema.validate(JsonLoader.fromString(validDataJson));

        assertTrue(report.isSuccess());
    }

    @Test
    public void testInvalidBankCustomerSchema() throws Exception {
        String schemaJson = new String(Files.readAllBytes(Paths.get("src/main/resources/schemas/bankCustomerSchema.yaml")));
        String invalidDataJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"accountNumber\":\"123456789\",\"balance\":\"not-a-number\"}";

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(JsonLoader.fromString(schemaJson));
        ProcessingReport report = schema.validate(JsonLoader.fromString(invalidDataJson));

        assertFalse(report.isSuccess());
    }
}
