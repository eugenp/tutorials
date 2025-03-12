FROM prom/prometheus:v2.48.1

COPY config/prometheus-docker.yml /etc/prometheus/prometheus.yml
COPY rules/ /etc/prometheus/rules/

EXPOSE 9090:9090
