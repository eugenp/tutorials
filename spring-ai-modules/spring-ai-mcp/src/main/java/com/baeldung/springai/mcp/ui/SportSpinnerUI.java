package com.baeldung.springai.mcp.ui;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.ai.mcp.annotation.McpResource;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.context.MetaProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
class SportSpinnerUI {

    @Value("classpath:/static/sport-spinner.html")
    private Resource sportSpinnerResource;

    @McpTool(
        title = "Spin Sport Wheel",
        name = "spin-sport-wheel",
        description = "Opens a fortune wheel that spins and randomly picks today's sport from 12 slices: swim, bike, run, bouldering, tennis, and a special triathlon (each appearing twice).",
        metaProvider = SportSpinnerToolMetaProvider.class)
    public String spinSportWheel() {
        return "Opening the sport spinner wheel.";
    }

    @McpResource(
        name = "Sport Spinner App Resource",
        uri = "ui://sport/sport-spinner.html",
        mimeType = "text/html;profile=mcp-app",
        metaProvider = CspMetaProvider.class)
    public String getSportSpinnerResource() throws IOException {
        return sportSpinnerResource.getContentAsString(StandardCharsets.UTF_8);
    }

    public static final class SportSpinnerToolMetaProvider implements MetaProvider {
        @Override
        public Map<String, Object> getMeta() {
            return Map.of("ui",
                Map.of("resourceUri", "ui://sport/sport-spinner.html"));
        }
    }

	public static final class CspMetaProvider implements MetaProvider {
		@Override
		public Map<String, Object> getMeta() {
			return Map.of("ui",
				Map.of("csp",
					Map.of("resourceDomains",
						List.of("https://unpkg.com"))));
		}
	}

}
