package pl.apala.ing;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.Headers;
import pl.apala.ing.atmservice.AtmserviceSolution;
import pl.apala.ing.onlinegame.OnlinegameSolution;
import pl.apala.ing.transactions.TransactionsSolution;

public class Routes {

private static final String CONTENT_TYPE_JSON = "application/json";

    public RoutingHandler handler() {
        return Handlers
                .routing()
                .post("/transactions/report", new BlockingHandler(transactions()))
                .post("/atms/calculateOrder", new BlockingHandler(atmservice()))
                .post("/onlinegame/calculate", new BlockingHandler(onlinegame()));
    }
    private HttpHandler transactions() {
        return exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, CONTENT_TYPE_JSON);
            TransactionsSolution.parseAndSolve(exchange.getInputStream(), exchange.getOutputStream());
        };
    }

    private HttpHandler atmservice() {
        return exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, CONTENT_TYPE_JSON);
            AtmserviceSolution.parseAndSolve(exchange.getInputStream(), exchange.getOutputStream());
        };
    }
    private HttpHandler onlinegame() {
        return exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, CONTENT_TYPE_JSON);
            OnlinegameSolution.parseAndSolve(exchange.getInputStream(), exchange.getOutputStream());
        };
    }

}
