package org.hibernate.caveatemptor.tutorial4.auction.model;

/**
 * This billing strategy can handle various credit cards.
 * <p>
 * The type of credit card is handled with a typesafe
 * enumeration, <tt>CreditCardType</tt>.
 *
 * @see CreditCardType
 * @author Christian Bauer
 */
public class CreditCard extends BillingDetails {

	private CreditCardType type;
	private String number;
	private String expMonth;
	private String expYear;

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public CreditCard() { super(); }

	/**
	 * Full constructor.
	 *
	 * @param owner
	 * @param user
	 * @param type
	 * @param expMonth
	 * @param expYear
	 */
	public CreditCard(String owner, User user, String number, CreditCardType type,
					  String expMonth, String expYear) {
		super(owner, user);
		this.type = type;
		this.number = number;
		this.expMonth = expMonth;
		this.expYear = expYear;
	}

	// ********************** Accessor Methods ********************** //

	public CreditCardType getType() { return type; }

	public String getNumber() { return number; }

	public String getExpMonth() { return expMonth; }

	public String getExpYear() { return expYear; }

	// ********************** Common Methods ********************** //

	public String toString() {
		return  "CreditCard ('" + getId() + "'), " +
				"Type: '" + getType() + "'";
	}

	// ********************** Business Methods ********************** //

	public boolean isValid() {
		// Use the type to validate the CreditCard details.
		return getType().isValid(this);
	}

}
