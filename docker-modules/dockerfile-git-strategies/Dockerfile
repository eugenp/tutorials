ADD git-strategies /project/
ADD /build/ /project/
ADD /output/project.jar /project/

ADD ssh-private-key /root/.ssh/id_rsa
RUN git clone git@github.com:eugenp/tutorials.git

ARG username=$GIT_USERNAME
ARG password=$GIT_PASSWORD
RUN git clone https://username:password@github.com:eugenp/tutorials.git

VOLUME /build/ /project/

