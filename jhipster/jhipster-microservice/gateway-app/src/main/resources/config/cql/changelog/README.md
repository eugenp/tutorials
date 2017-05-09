The changelog folder for cassandra/cql files is similar to Liquibase (for SQL databases), but with a minimal tooling.

- The script name should follow the pattern yyyyMMddHHmmss_{script-name}.cql
  - eg: 20150805124838_added_entity_BankAccount.cql
- The scripts will be applied sequentially in alphabetical order
- The scripts will be applied automatically only in two contexts:
  - Unit tests
  - Docker-compose for to start a [cassandra cluster for development](http://jhipster.github.io/docker-compose/#cassandra-in-development)

Unlike Liquibase, the scripts are not currently automatically applied to the database when deployed with a production profile
