FROM openjdk:11

RUN useradd -m -d /work -s /bin/bash service \
  && mkdir -p /work/config \
  && mkdir -p /work/log \
  && mkdir -p /work/db \
  && chown -R service:service /work \
  && ln -sf /usr/share/zoneinfo/Europe/Moscow /etc/localtime && echo "Europe/Moscow" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata

COPY target/ /work/
COPY testing.sqlite /work/db

USER service

ENV HOME /work

WORKDIR /work

ENV JAVA_OPTS ""

CMD java $JAVA_OPTS -jar yandex-app.jar
