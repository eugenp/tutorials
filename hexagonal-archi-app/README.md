# A Quick And Practical Example to Understanding Hexagonal Architecture

This example is built with Java, Spring framework and it clearly shows the relationship between the Domain Layer(Library Core in this case) 
which is also the inner layer and Outer Layer(Library Adapter).

The interfaces serves as ports to facilitate communication between modules and 
the Adapters are the entry points in the application.

We're expected to have a database for the storage of data which is also a part of the infrastructure of the application.

The component Library is the application core and includes the domain entities and business rules. The component LibraryAdapter contains adapters


### Author
Joy Utosu

