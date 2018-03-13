package datajpa.domain.domain6;

import javax.persistence.*;

/**
 * This billing strategy can handle various credit cards.
 * <p>
 * The type of credit card is handled with a typesafe
 * enumeration, <tt>CreditCardType</tt>.
 *
 * @author Christian Bauer
 * @see CreditCardType
 */
@Entity
@DiscriminatorValue("CC")
@SecondaryTable(
        name = "CREDIT_CARD",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "CREDIT_CARD_ID")
)
// TODO: Can't name foreign key constraint for PK join columns
public class CreditCard extends BillingDetails {

    @Enumerated(EnumType.STRING)
    @Column(table = "CREDIT_CARD", name = "CC_TYPE", nullable = false)
    private CreditCardType type;

    @Column(table = "CREDIT_CARD", name = "CC_NUMBER", nullable = false, updatable = false, length = 16)
    private String number;

    @Column(table = "CREDIT_CARD", name = "CC_EXP_MONTH", nullable = false, updatable = false, length = 2)
    private String expMonth;

    @Column(table = "CREDIT_CARD", name = "CC_EXP_YEAR", nullable = false, updatable = false, length = 4)
    private String expYear;

    /**
     * No-arg constructor for JavaBean tools
     */
    public CreditCard() {
        super();
    }

//    /**
//     * Full constructor.
//     *
//     * @param owner
//     * @param user
//     * @param type
//     * @param expMonth
//     * @param expYear
//     */
    /*
    public CreditCard(String owner, User user, String number, CreditCardType type,
                      String expMonth, String expYear) {
        super(owner, user);
        this.type = type;
        this.number = number;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }*/

    // ********************** Accessor Methods ********************** //

    public CreditCardType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }
    // ********************** Common Methods ********************** //

    public String toString() {
        return "CreditCard ('" + getId() + "'), " +
                "Type: '" + getType() + "'";
    }

    // ********************** Business Methods ********************** //

    public boolean isValid() {
        // Use the type to validate the CreditCard details.
        return getType().isValid(this);
    }

}
