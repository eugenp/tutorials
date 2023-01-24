FROM alpine:latest

ARG name
ENV env_name $name

COPY greetings.sh .

RUN chmod +x /greetings.sh

CMD ["/greetings.sh"]