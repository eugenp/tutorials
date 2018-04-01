package org.hibernate.caveatemptor.tutorial4.auction.persistence;

import org.hibernate.*;
import org.hibernate.usertype.UserType;
import auction.model.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Currency;
import java.io.Serializable;

/**
 * This is a simple Hibernate custom mapping type for MonetaryAmount value types.
 * <p>
 * Note that this mapping type is for legacy databases that only have a
 * single numeric column to hold monetary amounts. Every <tt>MonetaryAmount</tt>
 * will be converted to USD (using some conversion magic of the class itself)
 * and saved to the database.
 * <p>
 * Do not ask me how this currency conversion works. It doesn't work, it's not implemented.
 * The whole point of this class is not to show how you can convert money, but how
 * you can write a custom UserType.
 * 
 * @author Christian Bauer
 */
public class MonetaryAmountSimpleUserType
		implements UserType {

	public int[] sqlTypes() {
        return new int[]{ Hibernate.BIG_DECIMAL.sqlType() };
    }

	public Class returnedClass() { return MonetaryAmount.class; }

	public boolean isMutable() { return false; }

	public Object deepCopy(Object value) {
		return value;
	}

    public Serializable disassemble(Object value) {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) {
        return original;
    }

    public boolean equals(Object x, Object y) {
        if (x == y) return true;
        if (x == null || y == null) return false;
        return x.equals(y);
    }

    public int hashCode(Object x) {
        return x.hashCode();
    }

	public Object nullSafeGet(ResultSet resultSet,
							  String[] names,
							  Object owner)
			throws HibernateException, SQLException {

		BigDecimal valueInUSD = resultSet.getBigDecimal(names[0]);
        if (resultSet.wasNull()) return null; // Has to be done after first read
		Currency storedCurrency = Currency.getInstance("USD");
		return new MonetaryAmount(valueInUSD, storedCurrency);
	}

	public void nullSafeSet(PreparedStatement statement,
							Object value,
							int index)
			throws HibernateException, SQLException {

		if (value == null) {
			statement.setNull(index, Hibernate.BIG_DECIMAL.sqlType());
		} else {
			MonetaryAmount anyCurrency = (MonetaryAmount)value;
			MonetaryAmount amountInUSD =
			  MonetaryAmount.convert( anyCurrency,
									  Currency.getInstance("USD") );
			statement.setBigDecimal(index, amountInUSD.getValue());
		}
	}
}
