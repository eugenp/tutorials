DROP KEYSPACE chgrequestdatabase;

CREATE KEYSPACE IF NOT exists chgrequestdatabase
WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS chgrequestdatabase.chgrequestentity(
   id uuid,
   app text,
   build text,
   jira text,
   environment text,
   releasenotes text,
   status text,
   createddatetime timestamp,
   updateddatetime timestamp,
   primary key(id)
);
