package com.baeldung.simplehexagonalex.repository.primary.quoteadapter;

import java.util.List;

public class ProviderQuoteEnvelope {

    private Success success;
    private Contents contents;
    private String baseurl;
    private Copyright copyright;

    public ProviderQuoteEnvelope() {
        
    }
    
    public ProviderQuoteEnvelope(Success success, Contents contents, String baseurl, Copyright copyright) {
        super();
        this.success = success;
        this.contents = contents;
        this.baseurl = baseurl;
        this.copyright = copyright;
    }

    public Success getSuccess() {
        return success;
    }

    public Contents getContents() {
        return contents;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public Copyright getCopyright() {
        return copyright;
    }
    
    public class Contents {
        private List<ProviderQuote> quotes;

         public List<ProviderQuote> getQuotes() {
            return quotes;
        }
    }

    public class Copyright {
        private int year;
        private String url;
 
        public int getYear() {
            return year;
        }

        public String getUrl() {
            return url;
        }
    }

    public class Success {
        private int total;

        public int getTotal() {
            return total;
        }
    }
}
