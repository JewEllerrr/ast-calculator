# ast-calculator

#### Build instruction (Ubuntu 20.04.4 LTS)
1. Download and install the JDK (16 GA release) from [here](https://jdk.java.net/archive/) 
2. Open terminal, enter following commands
  ```
  export JAVA_HOME=/usr/lib/jvm/java-16-openjdk-amd64
  export PATH=$JAVA_HOME/bin:$PATH
  export PATH=/opt/apache-maven-3.8.6/bin/:$PATH
  cd ~/ast-calculator/
  mvn clean install
  ```
#### Run project 
```
java -jar target/ast-calculator-1.0-SNAPSHOT.jar
```
