/**
 * 
 */
package com.mybatis3.mappers;

import org.apache.ibatis.annotations.Select;

import com.mybatis3.domain.Address;

/**
 * @author Siva
 *
 */
public interface AddressMapper 
{
	@Select("select addr_id as addrId, street, city, state, zip, country from addresses where addr_id=#{id}")
	Address selectAddressById(int id);
}
