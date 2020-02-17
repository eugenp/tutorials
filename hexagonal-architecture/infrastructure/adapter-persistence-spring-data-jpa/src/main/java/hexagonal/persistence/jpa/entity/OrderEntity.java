package hexagonal.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ORDER")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer orderId;

    @Column
    private String name;

}
