# HttpToHttp

### How to import the project in toolkit
1. In toolkit, goto Window -> open perspective -> Git. If you dont see Git Repositories view, then goto Window -> Show view -> Git Repositories

2. In your git repositories view select -> **clone a git repository** -> Enter github URL and credentials -> After import is finished, right click on the repository and **import projects** -> import as exisitng projects -> select all projects.

3. Do the above step for the commons parent repository as well. 
 
4. you may have to run maven clean package install on **commons parent** project (right click pom.xml -> run as maven build-> enter goal as `clean package install`)

5. Right click and refresh the projects. If dependencies in Java project are not resolved, goto Project -> clean. Deploy the application to default node.


### Before using the Framework
You need to create a one time setup of your toolkit so that all the IIB dependecies are resolved locally for your local development. If you are using a preconfigured VM, this is already done for you.

**Register IIB plugins into maven repository**
```
yum install maven

cd /root/IIB/iib-10.0.0.4/tools/plugins/com.ibm.etools.mft.jcn_10.0.400.v20160310-1307

mvn install:install-file -Dfile=jplugin2.jar -DgroupId=com.ibm.iib -DartifactId=jplugin2 -Dversion=1.0.0 -Dpackaging=jar

mvn install:install-file -Dfile=javacompute.jar -DgroupId=com.ibm.iib -DartifactId=javacompute -Dversion=1.0.0 -Dpackaging=jar

cd /root/IIB/iib-10.0.0.4/common/classes

mvn install:install-file -Dfile=IntegrationAPI.jar -DgroupId=com.ibm.iib -DartifactId=IntegrationAPI -Dversion=1.0.0 -Dpackaging=jar
```
### How to execute the API

**POST Mehtod**

POST  **http://localhost:7800/apiJson**

Request Header: **Content-Type: application/json**
Request Header: **Transaction-Id: <some string value>**

Request Body: JSON Input of the API such as  {  "left": 5,  "right": 6 }

Note that User defined variables are by default set set to REST_URL=http://api-springboot.mybluemix.net/operate/addJSON, HTTP_METHOD=POST. If you like to change them, refer to https://github.com/sanketsw/HttpRequestRestAPI.
Sample screenshot below: (Response will differ)

![21603bfe7d199f47-1](https://cloud.githubusercontent.com/assets/14492591/14194959/a000b03e-f7ff-11e5-8758-6ab483dc3f1b.jpg)


