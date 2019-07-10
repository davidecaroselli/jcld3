FROM ubuntu:18.04

RUN apt-get update && apt-get purge gcc && apt-get install -y git openjdk-8-jdk maven cmake gcc-4.8 g++-4.8 wget
RUN rm -f /usr/bin/gcc && ln /usr/bin/gcc-4.8 /usr/bin/gcc && ln /usr/bin/g++-4.8 /usr/bin/g++
RUN wget https://github.com/protocolbuffers/protobuf/releases/download/v2.5.0/protobuf-2.5.0.tar.gz && \
    tar -xvzf protobuf-2.5.0.tar.gz && \
    cd protobuf-2.5.0 && \
    ./configure --disable-shared CFLAGS="-fPIC" CXXFLAGS="-fPIC" && make -j8 && make check && make install && ldconfig

RUN git clone https://github.com/davidecaroselli/jcld3.git /jcld3

WORKDIR /jcld3

RUN git submodule init && git submodule update
RUN mvn clean deploy