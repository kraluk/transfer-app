package io.kraluk.transferapp.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.Request;

/**
 * Simple trace-level request logging handler
 *
 * @author lukasz
 */
public final class RequestLoggingHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingHandler.class);

    @Override
    public void handle(final Context context) {
        final Request request = context.getRequest();

        if (log.isTraceEnabled()) {
            final StringBuilder message = new StringBuilder("Request")
                .append(System.lineSeparator())
                .append("URI: ").append(request.getRawUri())
                .append(System.lineSeparator())
                .append("Query Params: ").append(request.getQueryParams().toString())
                .append(System.lineSeparator())
                .append("Headers: ").append(request.getHeaders().asMultiValueMap().toString())
                .append(System.lineSeparator());

            log.trace("{}", message);

        }

        context.next();
    }
}
