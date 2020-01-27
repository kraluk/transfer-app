package io.kraluk.transferapp.transaction

import io.kraluk.transferapp.test.IntegrationSpec
import io.kraluk.transferapp.transaction.dto.TransactionDto
import ratpack.http.HttpMethod
import ratpack.http.Status

import static io.kraluk.transferapp.transaction.TransactionsFixtures.*

/**
 * Integration Specification for {@code /transactions} endpoint
 *
 * @author lukasz
 */
class TransactionsIntegrationSpec extends IntegrationSpec {

    def "Should find several transactions just after saving couple of them"() {
        given: "there are 3 transactions to be saved"
        def transactions = createTransactions()

        and: "all transactions are already saved in the system"
        saveTransactions(transactions)

        when: "the GET request is sent"
        def result = request("transactions", {
            r ->
                r.method(HttpMethod.GET)
                r.getHeaders().add("Accept", "application/json")
        })

        then: "response is OK"
        assertOkResponse(result)

        and: "received response contains all already saved transactions"
        def json = jsonSlurper.parse(result.body.getBytes())
        assertAcquiredTransactions(json)
    }

    private void assertAcquiredTransactions(json) {
        assert json.size == 3
        assert json[0].businessId != null
        assert json[0].name == "T1"
        assert json[1].businessId != null
        assert json[1].name == "T2"
        assert json[2].businessId != null
        assert json[2].name == "T3"
    }

    private def saveTransactions(List<TransactionDto> transactions) {
        transactions.forEach { it ->
            request("transactions", {
                r ->
                    r.method(HttpMethod.POST)
                    r.getHeaders().add("Accept", "application/json")
                    r.getBody().text(mapper.writeValueAsString(it))
            }).status == Status.OK
        }
    }

    def "Should save a transaction and return it with business id"() {
        given: "there is a transaction to be saved"
        def transaction = createTransaction()

        when: "the given transaction is send to the proper endpoint"
        def result = request("transactions", {
            it ->
                it.method(HttpMethod.POST)
                it.getHeaders().add("Accept", "application/json")
                it.getBody().text(mapper.writeValueAsString(transaction))
        })

        then:
        def json = jsonSlurper.parse(result.body.getBytes())
        assertSavedTransaction(json)
    }

    private void assertSavedTransaction(json) {
        assert json.businessId != null
        assert json.createdAt != REQUEST_DATE
        assert json.modifiedAt != REQUEST_DATE
    }
}
