FROM centos
WORKDIR /home/LRSManager
COPY . /home/LRSManager
RUN yum -y update
RUN yum -y remove java
RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel
	   
CMD ["java","-jar","-Dserver.port=6001","/home/LRSManager/LRSManager.jar"]