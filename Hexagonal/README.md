This java code example illustrates how we build the Ports & Adapters in real world and what benefits this architectural design brings to us.

The component appcore is the application core and includes the domain entities and business rules. The component command-console-adapter and inmemory-repository-adapter are adapters . The former adapter drives application core and the latter one serves the application core.
