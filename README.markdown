##  Jumia Affiliate Data

This projects provide an API entry point to Jumia Product data.
This project was written in pure scala ( _SPRAY-AKKA_ ) with elasticsearch as the search engine and data store.

* _Jetty_, Scala 2.11 + Akka 2.3 + spray 1.3 (the `on_jetty_1.3_scala-2.11` branch)


Follow these steps to get started:

1. Git-clone this repository.

        $ git clone git://github.com/OElesin/jumiadataapi.git my-project

2. Change directory into your clone:

        $ cd my-project

3. Launch SBT:

        $ sbt

4. Compile everything and run all tests:

        > test

5. Start the application:

        > re-start

6. Browse to [http://localhost:8080](http://localhost:8080/)

7. Stop the application:

        > re-stop

9. Start hacking on `src/main/scala/com/example/MyService.scala`
