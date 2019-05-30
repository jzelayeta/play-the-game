# play-the-game

![](https://media.giphy.com/media/65R80T3r72EGTzlNad/giphy.gif)


# Run Local

> docker-compose -f docker/docker-compose.yml up


# Load tests

This microservice use Gatling for load tests.
* Make sure you have an instance running
* For adding a new Gatling simulation make sure it is under `/simulation` folder in `test`
* For executing simulations just use same args for sbt testing, i.e `sbt gatling:test` will execute all simuations,
  or use `testOnly`
  
