package com.baeldung.micronaut.langchain4j;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface RelocationAdvisor {

    @SystemMessage("""  
	    You are a relocation advisor. Answer using official tone.        
	    Provide the numbers you will get from the resources.            
	    From the best of your knowledge answer the question below regarding    Possible relocation.            
	    Please get information only from the following sources:             
            - https://www.numbeo.com/cost-of-living/country_result.jsp?country=Spain             
            - https://www.numbeo.com/cost-of-living/country_result.jsp?country=Romania                                   And their subpages. Then, answer. If you don't have the information - answer exact text 'I don't have  
            information about {Country}'            
        """)
    String chat(@UserMessage String question);
}
