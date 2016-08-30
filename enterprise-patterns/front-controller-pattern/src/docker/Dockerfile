FROM jboss/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh -u admin -p admin -g admins -e -cw
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh"]
CMD ["-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
VOLUME /opt/jboss/wildfly/standalone/deployments
EXPOSE 8080 8443 9990
