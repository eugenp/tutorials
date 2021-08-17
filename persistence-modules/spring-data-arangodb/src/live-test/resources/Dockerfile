FROM arangodb:3.8.0

COPY init-session.js /docker-entrypoint-initdb.d/

EXPOSE 8529

ENV ARANGO_ROOT_PASSWORD=password
