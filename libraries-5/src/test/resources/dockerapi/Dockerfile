FROM alpine:3.6

RUN apk --update add git openssh && \
  rm -rf /var/lib/apt/lists/* && \
  rm /var/cache/apk/*

ENTRYPOINT ["git"]
CMD ["--help"]