To build and run metar-project:<br/>
1- First install and run docker and docker composer.<br/>
2- After that run following commands:<br/>
$ cd /[path to metar-project]/<br/>
$ mvn clean package<br/>docker
$ docker image build . -t metar-project<br/>
$ docker-compose up<br/>
3- root address (locally) of program to browse in web browsers or postman <br/>
   is http://localhost:8080 and follow by RestControllers mapping pathes (include in source files).<br/>
4- to run automated task:<br/>
   4.1- install jq and crontab<br/>
   4.2- run following commands:<br/>
        $ cd /[path to metar-project]/auto<br/>
        $ crontab ./auto.job<br/>
---------------------------------------
Search subscriptions examples:<br/>
    - http://localhost:8080/subscriptions?icaoCode=LZ <br/>
      find all subscriptions that icaoCode has LZ. <br/>
    - http://localhost:8080/subscriptions?icaoCode=LZ&active=1 <br/>
      find all subscriptions that icaoCode has LZ and active equal to 1. <br/>
