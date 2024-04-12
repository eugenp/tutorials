FROM mongo:4.2.1

COPY init-session.js /docker-entrypoint-initdb.d/

EXPOSE 27017

HEALTHCHECK --interval=5s --timeout=3s --start-period=10s CMD mongo db.stats()
CMD ["mongod", "--replSet", "rs0"]
