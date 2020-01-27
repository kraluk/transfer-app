package io.kraluk.transferapp;

import io.kraluk.transferapp.core.CoreModule;
import io.kraluk.transferapp.core.repository.RepositoryModule;
import io.kraluk.transferapp.core.web.RequestLoggingHandler;
import io.kraluk.transferapp.transaction.TransactionsHandler;
import io.kraluk.transferapp.transaction.TransactionsIdHandler;
import io.kraluk.transferapp.transaction.TransactionsIdStatusHandler;
import io.kraluk.transferapp.transaction.domain.TransactionModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.dropwizard.metrics.DropwizardMetricsModule;
import ratpack.error.internal.DefaultDevelopmentErrorHandler;
import ratpack.exec.Promise;
import ratpack.guice.Guice;
import ratpack.handling.ResponseTimer;
import ratpack.server.RatpackServer;

/**
 * TransferApp main class
 *
 * @author lukasz
 */
public final class TransferApp {
    private static final Logger log = LoggerFactory.getLogger(TransferApp.class);

    public static void main(final String... args) throws Exception {
        log.info("Starting TransferApp...");

        RatpackServer.start(server -> server
            .registry(Guice.registry(bindings -> bindings
                // Error handlers
                .bind(RequestLoggingHandler.class)
                .bind(DefaultDevelopmentErrorHandler.class)
                .bindInstance(ResponseTimer.decorator())

                .bind(TransactionsHandler.class)
                .bind(TransactionsIdHandler.class)
                .bind(TransactionsIdStatusHandler.class)

                // Technical modules
                .module(CoreModule.class)
                .module(RepositoryModule.class)
                .module(DropwizardMetricsModule.class)

                // Business modules
                .module(TransactionModule.class)
            ))
            .handlers(chain -> chain
                .all(RequestLoggingHandler.class)

                .get("", ctx -> ctx.byContent(m -> m
                    .json(() -> Promise.value("TransferApp").then(ctx.getResponse()::send))))

                .path("transactions", TransactionsHandler.class)
                .path("transactions/:id", TransactionsIdHandler.class)
                .path("transactions/:id/status", TransactionsIdStatusHandler.class)
            )
        );
    }
}
