package org.baeldung.caching.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The Class CustomerDataService.
 */
@Component
@CacheConfig("addressDemo")
public class CustomerDataService {

	/** The cache manager. */
	@Autowired
	CacheManager cacheManager;

	/**
	 * Gets the address.
	 *
	 * @param customer the customer
	 * @return the address
	 */

   @Cacheable("addresses", “directory”)

   public String getAddress1(Customer customer) {

     return customer.getAddress();
   }

	/**
		* Gets the address.
		*
		* @param customer the customer
		* @return the address
		*/

	@CacheEvict(value="addresses", allEntries=true)

	public String getAddress2(Customer customer) {

		return customer.getAddress();
	}

	/**
		* Gets the address.
		*
		* @param customer the customer
		* @return the address
		*/

	@Caching(evict = { @CacheEvict("addresses"), @CacheEvict(value="directory", key="customer.name") })

	public String getAddress3(Customer customer) {

		return customer.getAddress();
	}

	/**
	* Gets the address.
	*
	* @param customer the customer
	* @return the address
	*/

	@Cacheable // parameter not required as we have declared it using @CacheConfig

	public String getAddress4(Customer customer) {

		return customer.getAddress();
	}

	/**
	* Gets the address.
	*
	* @param customer the customer
	* @return the address
	*/


	@CachePut(value="addresses", condition=”#customer.name=’Tom’”)
	@CachePut(value="addresses", unless=”#result.length>64”)

	public String getAddress5(Customer customer) {

		return customer.getAddress();
	}


}
