# LRSManager

LRSManager is a part of LRSBackup Solution, responsible to persist data in a MySql DBase and offer a lot of services with capabilities to manager the main functions about LRSBackup, like "protected directories", "queue files to upload in public cloud", parameters data, like "users and keys" and others.

## Getting Started (for developers)
All code about LRSManager are available in **/src** directory and all libraries dependency are available in pom.xml file.
You can find a main class to run this program in **/src/br/com/lrsbackup/LRSManager/LRSManagerApplication.java**

Important Notice: This project is under development, almost done. So, there are some bugs yet, i'm working over it. All codes were write using Eclipse IDE using a "Maven Project"


## Getting Started (for end-users)
To run LRSManager, look at the binary file (executable file) about LRSManager. 
After download it, plese run the following commands in your command line application (Terminal to MacOs/Linux users or CMD to Windows Users)
```
java -jar -Dserver.port=6001 LRSManager.jar
```
We strongly recommend that you use a Docker container to run LRSManager. In this case, all docker containers related to LRSBackup environment needs have the same file mount point as a "Protected Files Source"
By the way, the LRSManager runs over 6001 HTTP Port.

## Authors
- [Cesar Bianchi](https://www.linkedin.com/in/cesar-bianchi-9b90571b/), since 2021.

## License
 This software is licensed under [GPL-3.0 License](https://www.gnu.org/licenses/gpl-3.0.pt-br.html)   

## More Info
 Please visit http://www.lrsbackup.com (under construction) or write to me: cesar_bianchi@hotmail.com
 
 
