package com.baeldung.seda.apachecamel;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.builder.ExpressionBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;

public class WordCountRoute extends RouteBuilder {

    public static final String receiveTextUri = "seda:receiveText?concurrentConsumers=5";
    public static final String splitWordsUri = "seda:splitWords?concurrentConsumers=5";
    public static final String toLowerCaseUri = "seda:toLowerCase?concurrentConsumers=5";
    public static final String countWordsUri = "seda:countWords?concurrentConsumers=5";
    public static final String returnResponse = "mock:result";

    @Override
    public void configure() throws Exception {

        from(receiveTextUri).to(splitWordsUri);

        from(splitWordsUri).transform(ExpressionBuilder.bodyExpression(s -> s.toString()
            .split(" ")))
          .to(toLowerCaseUri);

        from(toLowerCaseUri).split(body(), new StringListAggregationStrategy())
          .transform(ExpressionBuilder.bodyExpression(body -> body.toString()
            .toLowerCase()))
          .end()
          .to(countWordsUri);

        from(countWordsUri).transform(ExpressionBuilder.bodyExpression(List.class, body -> body.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))))
          .to(returnResponse);
    }
}

class StringListAggregationStrategy extends AbstractListAggregationStrategy<String> {
    @Override
    public String getValue(Exchange exchange) {
        return exchange.getIn()
          .getBody(String.class);
    }

}
