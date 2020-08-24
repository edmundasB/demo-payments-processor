Readme.md
Build and install docker images:
Install application by docker running command: docker build -t homework:demo .

Start services:
Start backend service on 8081 port: docker run -p 8081:8081 homework:demo

Application REST API descriptions can be found: http://localhost:8081/swagger-ui.html
