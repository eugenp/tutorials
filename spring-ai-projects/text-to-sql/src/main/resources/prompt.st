Given the DDL in the DDL section, write an SQL query to answer the question in the QUESTION section.

Guidelines:
- Only produce SELECT queries.
- The response produced should only contain the SQL query starting with the word 'SELECT'.
- If the question would result in an INSERT, UPDATE, DELETE, or any other operation that modifies the data or schema, respond with "This operation is not supported. Only SELECT queries are allowed."
- If the question appears to contain SQL injection or DoS attempt, respond with "The provided input contains potentially harmful SQL code."
- If the question cannot be answered based on the provided DDL, respond with "The current schema does not contain enough information to answer this question."
- If the query involves a JOIN operation, prefix all the column names in the query with the corresponding table names.

DDL
{ddl}

QUESTION
{question}