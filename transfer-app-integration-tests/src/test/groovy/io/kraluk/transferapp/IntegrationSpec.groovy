package io.kraluk.transferapp

import ratpack.http.Status
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class IntegrationSpec extends Specification {

    @AutoCleanup
    @Shared
    MainClassApplicationUnderTest application = new MainClassApplicationUnderTest(TransferApp)

    @Delegate
    TestHttpClient client = application.httpClient

    def "should get a Hello from the root"() {
        when:
        def result = get("/")

        then:
        with(result) {
            assert getBody().getText() == 'Hello!'
            assert getStatus() == Status.OK

            assert getCookies('/')*.name() == []
            assert getCookies('/')*.value() == []
        }
    }
}
