package io.kraluk.transferapp.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.handling.Context;
import ratpack.handling.Handler;

/**
 * Simple trace-level request logging handler
 *
 * @author lukasz
 */
public final class LoggingHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(LoggingHandler.class);

    @Override
    public void handle(final Context context) {
        log.trace("Request with URI: '{}'", context.getRequest().getRawUri());

        context.next();
    }
}
