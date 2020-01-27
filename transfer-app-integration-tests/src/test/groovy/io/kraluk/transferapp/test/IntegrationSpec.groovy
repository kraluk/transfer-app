package io.kraluk.transferapp.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import groovy.json.JsonSlurper
import io.kraluk.transferapp.TransferApp
import ratpack.http.Status
import ratpack.http.client.ReceivedResponse
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

/**
 * Base class for any Integration Specification
 *
 * @author lukasz
 */
class IntegrationSpec extends Specification {

    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()

    @Shared
    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())

    @AutoCleanup
    @Shared
    MainClassApplicationUnderTest application = new MainClassApplicationUnderTest(TransferApp)

    @Delegate
    TestHttpClient client = application.httpClient

    static void assertOkResponse(final ReceivedResponse response) {
        assert response.getStatus() == Status.OK
        assert response.getHeaders().contains("X-Response-Time")
    }
}
