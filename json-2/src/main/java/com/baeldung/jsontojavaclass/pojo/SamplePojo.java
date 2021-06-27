
package com.baeldung.jsontojavaclass.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "area", "author", "id", "salary", "topics" })
@Generated("jsonschema2pojo")
public class SamplePojo {

    @JsonProperty("name")
    private String name;
    @JsonProperty("area")
    private String area;
    @JsonProperty("author")
    private String author;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("salary")
    private Integer salary;
    @JsonProperty("topics")
    private List<String> topics = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public SamplePojo withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("area")
    public String getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(String area) {
        this.area = area;
    }

    public SamplePojo withArea(String area) {
        this.area = area;
        return this;
    }

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    public SamplePojo withAuthor(String author) {
        this.author = author;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public SamplePojo withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("salary")
    public Integer getSalary() {
        return salary;
    }

    @JsonProperty("salary")
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public SamplePojo withSalary(Integer salary) {
        this.salary = salary;
        return this;
    }

    @JsonProperty("topics")
    public List<String> getTopics() {
        return topics;
    }

    @JsonProperty("topics")
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public SamplePojo withTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public SamplePojo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SamplePojo.class.getName())
            .append('@')
            .append(Integer.toHexString(System.identityHashCode(this)))
            .append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("area");
        sb.append('=');
        sb.append(((this.area == null) ? "<null>" : this.area));
        sb.append(',');
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null) ? "<null>" : this.author));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("salary");
        sb.append('=');
        sb.append(((this.salary == null) ? "<null>" : this.salary));
        sb.append(',');
        sb.append("topics");
        sb.append('=');
        sb.append(((this.topics == null) ? "<null>" : this.topics));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? "<null>" : this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.area == null) ? 0 : this.area.hashCode()));
        result = ((result * 31) + ((this.author == null) ? 0 : this.author.hashCode()));
        result = ((result * 31) + ((this.topics == null) ? 0 : this.topics.hashCode()));
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.salary == null) ? 0 : this.salary.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SamplePojo) == false) {
            return false;
        }
        SamplePojo rhs = ((SamplePojo) other);
        return ((((((((this.area == rhs.area) || ((this.area != null) && this.area.equals(rhs.area))) && ((this.author == rhs.author) || ((this.author != null) && this.author.equals(rhs.author))))
            && ((this.topics == rhs.topics) || ((this.topics != null) && this.topics.equals(rhs.topics)))) && ((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))))
            && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id)))) && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))))
            && ((this.salary == rhs.salary) || ((this.salary != null) && this.salary.equals(rhs.salary))));
    }

}
