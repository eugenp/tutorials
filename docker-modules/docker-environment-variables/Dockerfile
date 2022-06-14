FROM alpine:latest

ARG name
ENV env_name $name

RUN echo "hello $name"

COPY greetings.sh .

RUN chmod +x /greetings.sh

CMD ["/greetings.sh"]