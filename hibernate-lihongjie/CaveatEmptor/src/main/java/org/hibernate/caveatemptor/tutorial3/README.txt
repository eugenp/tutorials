CaveatEmptor JPA (20061211)
###########################################################################


Requirements
---------------------------------------------------------------------------

- JDK 5.0
- Ant 1.6


Building and running the tests
---------------------------------------------------------------------------

The following Ant targets are available:

    clean                   Clean the build directory

    startdb                 Run HSQL database server with clean DB
    dbmanager               Start HSQL DB manager
    schemaexport            Exports schema DDL to file in build directory

    test.integration        Run the integration tests

    instrument.lazyloading  Enhance bytecode for lazy loading interception

1. Open your command shell and change to the project directory

2. Use 'ant startdb' to start a fresh HSQL database, keep the database running

3. Open a second command shell and change to the project directory

4. Use 'ant' to run the basic integration tests against HSQL DB (a schema is
   exported automatically for each test run).

5. Use 'ant schemaexport' to export the DDL file build/caveatemptor-jpa-ddl.sql

6. Use 'ant dbmanager' to open the HSQL database browser, note that
   the database is empty after each test run! Set automatic schema export to
   "create" instead of "create-drop", to see the schema and data from the last
   test run.


Notes
---------------------------------------------------------------------------

You can always reset the database by restarting the database server.


Contact & Forum
---------------------------------------------------------------------------

http://caveatemptor.hibernate.org/
http://forum.hibernate.org/viewforum.php?f=7


License
---------------------------------------------------------------------------

This product includes software developed by
the Apache Software Foundation (http://www.apache.org/).

This software is distributed as open source.
The following disclaimer applies to all files
(not any included 3rd party libraries) distributed in
this package:

Copyright (c) 2005, Christian Bauer <christian@hibernate.org>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

- Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

- Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

Neither the name of the original author nor the names of contributors may be
used to endorse or promote products derived from this software without
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
THE POSSIBILITY OF SUCH DAMAGE.
