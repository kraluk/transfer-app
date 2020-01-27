package io.kraluk.transferapp

import ratpack.http.Status
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

/**
 * Integration Specification for {@code /} endpoint
 *
 * @author lukasz
 */
class IndexIntegrationSpec extends Specification {

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
            assert getBody().getText() == "TransferApp"
            assert getStatus() == Status.OK

            assert getHeaders().contains("X-Response-Time")

            assert getCookies('/')*.name() == []
            assert getCookies('/')*.value() == []
        }
    }
}
