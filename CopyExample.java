class Address { 
    String city; 
    public Address(String city) { 
        this.city = city; 
        }
         @Override 
         public String toString() { 
            return city; 
            } 
            } 
            class Person implements Cloneable { 
                String name; 
                Address address; 
                public Person(String name, Address address) { 
                    this.name = name; 
                    this.address = address; 
                    } 
                    // Shallow copy 
                    @Override 
                    protected Object clone() throws CloneNotSupportedException { 
                        return super.clone(); 
                        } 
                        // Deep copy 
                        protected Person deepClone() { 
                            return new Person(this.name, new Address(this.address.city)); 
                            } 
                            @Override 
                            public String toString() { 
                                return name + " lives in " + address; } 
                                }
                                 public class CopyExample { 
                                    public static void main(String[] args) throws CloneNotSupportedException { 
                                        Address address = new Address("New York"); 
                                        Person person1 = new Person("John", address); 
                                        // Shallow Copy 
                                        Person person2 = (Person) person1.clone(); 
                                        person2.name = "Jane"; 
                                        person2.address.city = "Los Angeles"; 
                                        // Deep Copy 
                                        Person person3 = person1.deepClone(); 
                                        person3.name = "Mike"; 
                                        person3.address.city = "San Francisco"; 
                                        System.out.println("Original: " + person1); 
                                        System.out.println("Shallow Copy: " + person2); 
                                        System.out.println("Deep Copy: " + person3);
                                         } 
                                         }