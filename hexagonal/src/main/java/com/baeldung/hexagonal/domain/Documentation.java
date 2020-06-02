package com.baeldung.hexagonal.domain;

import java.util.Set;

public class Documentation {
        private String name;
        private String description;
        private Set<ComponentMethod> componentClass;

        public Documentation() {
        }

        public Documentation(String name, String description) {
                this.name = name;
                this.description = description;
        }

        public String getName() {
                return name;
        }

        public void addMethodToDocumentation(String name, String description) {
                componentClass.add(new ComponentMethod(name, description));
        }

        public boolean removeMethodFromDocumentation(String name) {
                if (!hasMethod(name)) {
                        return false;
                }
                componentClass.removeIf(componentMethod -> componentMethod.getName().equals(name));
                return true;
        }

        public String getDescription() {
                return description;
        }

        private boolean hasMethod(String name) {
                return componentClass.stream().anyMatch(componentMethod -> componentMethod.getName().equals(name));
        }
}
