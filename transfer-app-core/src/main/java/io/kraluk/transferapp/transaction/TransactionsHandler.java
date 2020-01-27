package io.kraluk.transferapp.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import io.kraluk.transferapp.transaction.domain.TransactionFacade;
import io.kraluk.transferapp.transaction.dto.TransactionDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.Request;
import ratpack.http.Status;
import ratpack.http.TypedData;

/**
 * REST handler for {@code /transactions} endpoint
 *
 * @author lukasz
 */
public class TransactionsHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(TransactionsHandler.class);

    private final TransactionFacade facade;
    private final ObjectMapper mapper;

    @Inject
    public TransactionsHandler(final TransactionFacade facade, final ObjectMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @Override
    public void handle(final Context context) throws Exception {
        context.byContent(c -> c
            .json(() -> context.byMethod(m -> m
                .get(this::find)
                .post(this::create)
            ))
            .noMatch(() -> context.getResponse().status(Status.BAD_REQUEST).send())
            .unspecified(() -> context.getResponse().status(Status.BAD_REQUEST).send())
        );
    }

    private void create(final Context context) {
        context.getRequest().getBody()
            .flatMap(t -> {
                final TransactionDto dto = toDto(t);

                return Blocking.get(() -> facade.save(dto));
            })
            .onError(context::error)
            .map(this::toJson)
            .then(context.getResponse()::send);
    }

    private void find(final Context context) {
        final Request request = context.getRequest();

        final Map<String, String> params = request.getQueryParams();
        log.info("Params '{}'", params);

        Blocking.get(facade::find)
            .onError(context::error)
            .map(this::toJson)
            .then(context.getResponse()::send);
    }

    private TransactionDto toDto(final TypedData data) throws IOException {
        return mapper.readValue(data.getText(), TransactionDto.class);
    }

    private String toJson(final Object dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }
}
