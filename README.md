Fig Maven Plugin
=======================================

fig(http://www.fig.sh/) is a fast, isolated development environments management tool using Docker


### Usage
Please add following code into pom.xml

     <plugin>
          <groupId>org.mvnsearch.docker</groupId>
          <artifactId>fig-maven-plugin</artifactId>
          <version>0.0.1-SNAPSHOT</version>
     </plugin>
Then execute mvn fig:start test fig:stop


### goals

* fig:start - use fig:up to start all the containers
* fig:stop  - stop and rm all the containers