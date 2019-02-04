package io.kraluk.transferapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import io.kraluk.transferapp.core.repository.RepositoryModule;
import io.kraluk.transferapp.core.web.LoggingHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.dropwizard.metrics.DropwizardMetricsModule;
import ratpack.guice.Guice;
import ratpack.handling.ResponseTimer;
import ratpack.server.RatpackServer;

/**
 * TransferApp main class
 *
 * @author lukasz
 */
public class TransferApp {
    private static final Logger log = LoggerFactory.getLogger(TransferApp.class);

    public static void main(final String... args) throws Exception {
        log.info("Starting TransferApp...");

        RatpackServer.start(server -> server
            .registry(Guice.registry(bindings -> bindings
                // Error handlers
                .bind(LoggingHandler.class)
                //.bind(DefaultDevelopmentErrorHandler.class)
                .bindInstance(ResponseTimer.decorator())

                // Jackson stuff
                .bindInstance(ObjectMapper.class, new ObjectMapper().registerModule(new Jdk8Module()))

                // Technical modules
                .module(RepositoryModule.class)
                .module(DropwizardMetricsModule.class)
            ))
            .handlers(chain -> chain
                .all(LoggingHandler.class)

                .get("", ctx -> ctx.render("Hello!"))
            )
        );
    }
}
