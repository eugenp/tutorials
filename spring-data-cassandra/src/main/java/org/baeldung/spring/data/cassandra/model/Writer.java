package org.baeldung.spring.data.cassandra.model;

import java.util.UUID;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.DataType;

@Table
public class Writer extends AbstractModel {

    public enum WriterType {
        UNKNOWN, POET, DETECTIVE, JOUNALIST
    }

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "last_name")
    private String lastName;

    @Column(value = "writer_type")
    private Integer writerType;

    public Writer() {
        // default constructor
    }

    public Writer(final UUID id, final String firstName, final String lastName, final WriterType writerType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.writerType = writerType.ordinal();
    }

    public Writer(final UUID id, final String firstName, final String lastName, final Integer writerTypeNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.writerType = writerTypeNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("id=").append(getId());
        sb.append(",firstName=").append(getFirstName());
        sb.append(",lastName=").append(getLastName());
        sb.append(",type=").append(getWriterType());
        sb.append("}");
        return sb.toString();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public WriterType getWriterType() {
        if (writerType >= 0 && writerType < WriterType.values().length) {
            return WriterType.values()[writerType];
        }
        return WriterType.UNKNOWN;
    }

    public void setWriterType(WriterType writerType) {
        this.writerType = writerType.ordinal();
    }

}
