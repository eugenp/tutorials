package org.hibernate.caveatemptor.tutorial3.auction.persistence;

import org.hibernate.usertype.*;
import auction.model.MonetaryAmount;
import org.hibernate.*;
import org.hibernate.engine.SessionImplementor;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

/**
 * This type adds parameterizable currency conversion to the normal composite user type,
 * that is, you can set the target currency for the conversion (for the value saved and
 * loaded from the database) to any ISO code in your mapping, depending on the
 * situation/property to map. It maps a two-attribute component to two database columns.
 *
 * @see MonetaryAmountSimpleUserType
 * @author Christian Bauer
 */public class MonetaryAmountType extends MonetaryAmountCompositeUserType implements ParameterizedType {

	// The amount
	private Currency convertTo;

	public void setParameterValues(Properties parameters) {
		this.convertTo = Currency.getInstance(parameters.getProperty("convertTo"));
	}

	public Object nullSafeGet(ResultSet resultSet,
							  String[] names,
							  SessionImplementor session,
							  Object owner)
			throws HibernateException, SQLException {

		if (resultSet.wasNull()) return null;
		BigDecimal value = resultSet.getBigDecimal( names[0] );
        // When loading, take the currency from the database
		Currency currency = Currency.getInstance(resultSet.getString( names[1] ) );
        return new MonetaryAmount(value, currency);
	}

	public void nullSafeSet(PreparedStatement statement,
							Object value,
							int index,
							SessionImplementor session)
			throws HibernateException, SQLException {

		if (value==null) {
		    statement.setNull(index, Types.NUMERIC);
		} else {
		    MonetaryAmount amount = (MonetaryAmount) value;
            // When saving, convert to target currency
		    MonetaryAmount dbAmount = MonetaryAmount.convert(amount, convertTo);
		    statement.setBigDecimal( index, dbAmount.getValue() );
            statement.setString( index+1, convertTo.getCurrencyCode());
		}
	}
	
}