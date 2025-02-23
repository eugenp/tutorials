FROM prom/prometheus:v2.48.1

COPY config/prometheus-*.yml /etc/prometheus/
COPY rules/ /etc/prometheus/rules/

EXPOSE 9090:9090
