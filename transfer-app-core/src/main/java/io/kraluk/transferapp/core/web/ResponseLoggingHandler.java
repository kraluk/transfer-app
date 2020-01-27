package io.kraluk.transferapp.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.Response;

/**
 * Simple trace-level request logging handler
 *
 * @author lukasz
 */
public final class ResponseLoggingHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(ResponseLoggingHandler.class);

    @Override
    public void handle(final Context context) {
        final Response response = context.getResponse();

        if (log.isTraceEnabled()) {
            final StringBuilder message = new StringBuilder("Response")
                .append(System.lineSeparator())
                .append("Status: ").append(response.getStatus())
                .append(System.lineSeparator())
                .append("Headers: ").append(response.getHeaders().asMultiValueMap().toString())
                .append(System.lineSeparator());

            log.trace("{}", message);

        }

        context.next();
    }
}
