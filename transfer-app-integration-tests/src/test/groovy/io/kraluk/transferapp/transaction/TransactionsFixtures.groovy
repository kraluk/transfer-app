package io.kraluk.transferapp.transaction

import io.kraluk.transferapp.transaction.dto.TransactionDto

import java.time.LocalDateTime

/**
 * Provides some useful testing fixtures
 *
 * @author lukasz
 */
class TransactionsFixtures {

    public static final LocalDateTime REQUEST_DATE = LocalDateTime.of(1990, 1, 1, 1, 1)

    static def createTransaction() {
        TransactionDto.builder()
                .withBusinessId(null)
                .withName("New transaction")
                .withCreatedAt(REQUEST_DATE)
                .withModifiedAt(REQUEST_DATE)
                .build()
    }

    static def createTransaction(String name) {
        TransactionDto.builder()
                .withName(name)
                .build()
    }

    static def createTransactions() {
        [
                createTransaction("T1"),
                createTransaction("T2"),
                createTransaction("T3")
        ]
    }
}
