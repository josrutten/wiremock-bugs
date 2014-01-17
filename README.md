wiremock-bugs
=============

playground for investigating and reproducing wiremock bugs

steps to reproduce passing the Host header (see https://github.com/tomakehurst/wiremock/issues/76)

- start restservice
	+ goto restservice
	+ type mvn package exec:java
- start wiremock recorder in standalone mode in a new prompt
	+ goto wiremock-runner
	+ type mvn exec:java -DproxyUrl=http://localhost:8888
- in a browser open url http://localhost:8888/sample
- the console output of restservice will show that the "Host" header is : localhost:8888
- now via wiremock: in a browser open url http://localhost:9090/sample
- the console output of restservice will show that the "Host" header is : localhost:9090
- if you place the restservice component on another machine you will get an error back:
	+ 404 Not Found: Requested route ('localhost:9090') does not exist.