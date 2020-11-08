package com.baeldung.yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
    private String name;
    private String environment;
    private boolean enabled;
    private List<String> servers = new ArrayList<String>();
    private List<String> external = new ArrayList<String>();
    private Map<String, String> map = new HashMap<String, String>();
    private Component component = new Component();

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<String> getExternal() {
        return external;
    }

    public void setExternal(List<String> external) {
        this.external = external;
    }

    public class Component {
        private Idm idm = new Idm();
        private Service service = new Service();
        
        public Idm getIdm() {
            return idm;
        }
        public void setIdm(Idm idm) {
            this.idm = idm;
        }
        
        public Service getService() {
            return service;
        }
        public void setService(Service service) {
            this.service = service;
        }
        
    }
    
    public class Idm {
        private String url;
        private String user;
        private String password;
        private String description;
        
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getUser() {
            return user;
        }
        public void setUser(String user) {
            this.user = user;
        }
        
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class Service {
        private String url;
        private String token;
        private String description;
        
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
        
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }

}
