package org.baeldung.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class TechnologyForm {
 
    @NotEmpty
    private String inputName;
    
    private String[] technologies;

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String[] technologies) {
        this.technologies = technologies;
    }

}
