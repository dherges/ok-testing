FROM java:8-jdk

ENV GRADLE_VERSION 2.6

WORKDIR /usr/bin
RUN curl -sLO https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-all.zip && \
    unzip gradle-${GRADLE_VERSION}-all.zip && \
    ln -s gradle-${GRADLE_VERSION} gradle && \
    rm gradle-${GRADLE_VERSION}-all.zip

ENV GRADLE_HOME /usr/bin/gradle
ENV PATH $PATH:$GRADLE_HOME/bin

EXPOSE 4567


RUN mkdir /app
WORKDIR /app
COPY build.gradle build.gradle
RUN ["gradle", "installDist"]

COPY src src
RUN ["gradle", "check"]
ENTRYPOINT ["gradle", "run"]
