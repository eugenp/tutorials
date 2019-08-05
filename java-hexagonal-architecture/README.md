This module contains practical example of Hexagonal Architecture.

According to {articleLink} we present here concepts of Ports and Adapters.

Port is defined in:

    ContentTypeMapper

Adapters are:

    CSVMapper
    ListMapper
    
Example usage of `Adapters` is presented in test class `HexagonalArchitectureTests`

You will find there how easily these two adapters can be autowired in Spring context just by type specification.