package com.cooksys.server.stock;

import com.cooksys.server.dto.QuoteRequest;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.util.Set;
import java.util.stream.Collectors;

public class StockApi {

    private static Quote fetchQuote(QuoteRequestBuilder quoteRequestBuilder) {
        IEXTradingClient iexTradingClient = IEXTradingClient.create();
        return iexTradingClient.executeRequest(quoteRequestBuilder.build());
    }

    public static Set<Quote> fetchQuotes(QuoteRequest request) {
        return request.getSymbols()
                .stream()
                .map(s -> fetchQuote(new QuoteRequestBuilder().withSymbol(s)))
                .collect(Collectors.toSet());
    }

}
