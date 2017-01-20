# Availabook

COMS W4156 Advanced Software Engineering Project

Availabook is a social application which can facilitate interaction between users who have free time. With Availabook, people can view whether your friends are available, based on which you can evaluate the chance of asking your friends out in a specific time slot. 

## Development Overview
1. Use [Spark Framework](http://sparkjava.com/) to create the Java web application.
2. Use [SQLite](https://sqlite.org/) database to store all the backend data.
3. Use [Jade template language](https://www.npmjs.com/package/jade) to write HTML.

##How to execute the maven project
1. Open your terminal and git clone this repository `git clone https://github.com/StarBugs-ASE/Availabook.git`
2. Change the current working directory to your local project.
3. Execute `mvn package` and `mvn compile`, this will install the package into the local repository and compile it.
4. Execute `mvn exec:java` will run our project on your local machine and create a sqlite database.
5. Execute `mvn -Dtest=TestCircle test` will automatically run all the test cases.
5. Execute `mvn clean` will remove all files generated by the previous build, but the database will remain.

## Current Development
All done.

### Team StarBugs
Xun Xue(xx2241)
Xiaofei Chen(xc2364)
Maolin Zuo(mz2584)
Jing Zhong(jz2748)
