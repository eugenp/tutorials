package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.component.StringUtil;
import com.baeldung.architecture.hexagonal.dependencies.ConsoleLogger;
import com.baeldung.architecture.hexagonal.dependencies.StringTransformationLoggerToConsoleLoggerAdapter;
import com.baeldung.architecture.hexagonal.callers.StringArrayUppercaseConverter;
import com.baeldung.architecture.hexagonal.callers.StringArrayUppercaseConverterToStringUtilAdapter;

public class HexagonalApplication {
    public static void main(String[] args) {
        ConsoleLogger logger = new ConsoleLogger();
        StringTransformationLoggerToConsoleLoggerAdapter loggerAdapter = new StringTransformationLoggerToConsoleLoggerAdapter(logger);

        StringUtil util = StringUtil.create(loggerAdapter);

        StringArrayUppercaseConverterToStringUtilAdapter converterAdapter = new StringArrayUppercaseConverterToStringUtilAdapter(util);
        StringArrayUppercaseConverter converter = new StringArrayUppercaseConverter(converterAdapter);

        String[] result = converter.getUppercase(new String[] { "h", "e", "l", "l", "o" });

        logger.log(String.format("Results are: %s", String.join(", ", result)));
    }
}
