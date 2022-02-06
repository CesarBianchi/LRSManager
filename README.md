# LRSManager

LRSManager is a part of LRSBackup Solution, responsible to persist data in a MySql DBase and offer a lot of services with capabilities to manager the main functions about LRSBackup, like "protected directories", "queue files to upload in public cloud", parameters data, like "users and keys" and others.

## Getting Started (for developers)
All code about LRSManager are available in **/src** directory and all libraries dependency are available in pom.xml file.
You can find a main class to run this program in **/src/br/com/lrsbackup/LRSManager/LRSManagerApplication.java**

Important Notice: This project is under development, almost done. So, there are some bugs yet   I'm working over it, so please, be patience!
All codes were write using Eclipse IDE using a "Maven Project"

PS: Note all details above, about "Environment Variables". You will need configure the same variables in your "IDE Run Configurations"

## Getting Started (for end-users)
First, you will need a empty database inside an MySQL Instance (you can use any kind of MySQL Instance, like MySQL PaaS Services in AWS or Azure, for example, or OnPremisses also.)

Then, you will need configure some "Environment Variables" in your host that will run LRSManager (docker containers requires the same variables).

The environment variables are:

Name: LRSManager_DBIP   - Value: <The IP address about your MySQL Instance, or DNS endpoint path>.

Name: LRSManager_DBPORT - Value: <The port where your MySql Instance is running>

Name: LRSManager_DBNAME - Value: <The name of your MySQL Database, dedicated to LRSManager> 

Name: LRSManager_DBUSER - Value: <The username of your MySQL Database instance, with grants to access the LRSDatabase>

Name: LRSManager_DBPSW  - Value: <The Password of your MySQL Database instance> 

To run LRSManager, look at the binary file (executable file) about LRSManager, called LRSManager.jar 
After download it, plese run the following commands in your command line application (Terminal to MacOs/Linux users or CMD to Windows Users)
```
java -jar -Dserver.port=6001 LRSManager.jar
```
We strongly recommend that you use a Docker container to run LRSManager. In this case, all docker containers related to LRSBackup environment needs have the same file mount point as a "Protected Files Source"
By the way, the LRSManager runs over 6001 HTTP Port, but we strongly recommend to you add any API Gateway between LRSManager and all others parts of LRSBackup Application, like "KrakenD API Gateway"

To run LRSManager through Docker Engine/Container, please use the example command (The LRSManager.Dockerfile are inside the git repo):
```
$ docker build -tlrsmanager lrsmanager -f LRSManager.Dockerfile 
```
```
$ docker run -d -p 6001:6001 -v /<your_local_source_files>/:/your_container_source_files/ --env LRSManager_DBIP=<your_mysql_IP> --env LRSManager_DBPORT=<your_mysql_port> --env LRSManager_DBNAME=<your_mysql_db_name> --env LRSManager_DBUSER=<your_mysql_db_user> --env LRSManager_DBPSW=<your_mysql_db_password> --restart unless-stopped lrsmanager
```

## Authors
- [Cesar Bianchi](https://www.linkedin.com/in/cesar-bianchi-9b90571b/), since 2021.

## License
 This software is licensed under [GPL-3.0 License](https://www.gnu.org/licenses/gpl-3.0.pt-br.html)   

## More Info
 Please visit http://www.lrsbackup.com (under construction) or write to me: cesar_bianchi@hotmail.com
 
 
