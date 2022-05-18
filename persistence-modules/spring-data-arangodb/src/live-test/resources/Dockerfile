FROM arangodb:3.8.0

COPY init-session.js /docker-entrypoint-initdb.d/

COPY arangodb-setup.js /setup/

EXPOSE 8529

ENV ARANGO_ROOT_PASSWORD=password

RUN chmod a+x /setup/arangodb-setup.js