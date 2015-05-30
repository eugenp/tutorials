/**
 * The Class Customer.
 */
public class Customer {

  /** The id. */
  private int id;

  /** The name. */
  private String name;

  /** The address. */
  private String customerAddress;

  Customer(String name, String address){
    this.name = name;
    this.address = address;
  }


  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the address.
   *
   * @return the address
   */
  public String getCustomerAddress() {
    return this.customerAddress;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the address.
   *
   * @param name the new address
   */
  public void setCustomerAddress(String address) {
    this.customerAddress = this.name + "," + address;
  }
}
