# TransferApp - load tests

The [Gatling][1] framework has been chosen for the load tests.

Frankly speaking, there is only one test written in Scala (em, just a simple Scala script) to present the abilities of the TransferApp and to give the opportunity to play with Ratpack's performance :)

## How to run the tests?

1. Run the app, for example with a following command:
   ```bash
   ./gradlew :transfer-app-core:run
   ```
2. Start the tests:
   ```bash
   ./gradlew :transfer-app-load-tests:gatlingRun
   ```
3. And wait for the results

[1]: https://gatling.io