package io.kraluk.transferapp;

import io.kraluk.transferapp.core.CoreModule;
import io.kraluk.transferapp.core.repository.RepositoryModule;
import io.kraluk.transferapp.core.web.LoggingHandler;

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
                    .bind(LoggingHandler.class)
                    .bind(DefaultDevelopmentErrorHandler.class)
                    .bindInstance(ResponseTimer.decorator())

                    // Technical modules
                    .module(CoreModule.class)
                    .module(RepositoryModule.class)
                    .module(DropwizardMetricsModule.class)

                // Business modules
                //.module(TransactionModule.class)
                //.module(AccountModule.class)
                //.module(CustomerModule.class)
            ))
            .handlers(chain -> chain
                .all(LoggingHandler.class)

                .get("", ctx -> Promise.value("Hello!").then(ctx::render))
            )
        );
    }
}
