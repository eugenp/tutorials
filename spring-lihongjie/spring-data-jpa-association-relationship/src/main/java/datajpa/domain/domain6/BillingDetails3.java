package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * 每个类层次结构一张表
 * 必须添加一个特殊的列以便在持久化类之间进行区分：辨别标志（discriminator), 如果超类中没有指定列名，则默认列名是DTYPE，值为字符串。 Hibernate自动设置和获取辨别标志的值。
 * 子类的属性不允许 NOT NULL 约束
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "BILLING_DETAILS_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class BillingDetails3 {

    @Id
    @GeneratedValue
    @Column(name = "BILLING_DETAILS_ID")
    private Long id;

    @Column(name = "OWNER", nullable = false)
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
