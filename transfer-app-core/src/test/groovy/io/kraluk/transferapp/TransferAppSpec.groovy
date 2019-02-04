package io.kraluk.transferapp

import spock.lang.Specification

class TransferAppSpec extends Specification {

    def "should start the application (not really a unit test)"() {
        when:
        TransferApp.main()

        then:
        noExceptionThrown()
    }
}
