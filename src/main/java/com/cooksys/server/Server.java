package com.cooksys.server;

import pl.zankowski.iextrading4j.api.stocks.BatchStocks;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    public static void main(String[] args) {

        try {
            JAXBContext context = JAXBContext.newInstance(QuoteField.class, QuoteRequest.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ServerSocket server = new ServerSocket(3000);
            Socket client = server.accept();
            System.out.println("Got request from client");

            QuoteRequest request = (QuoteRequest) unmarshaller.unmarshal(client.getInputStream());
            System.out.println("Unmarshalled request");

            IEXTradingClient iexTradingClient = IEXTradingClient.create();
            Set<Quote> quotes = new HashSet<>();

            for (String symbol : request.getSymbols()) {
                quotes.add(iexTradingClient.executeRequest(new QuoteRequestBuilder()
                        .withSymbol(symbol)
                        .build()));
            }

            StringBuilder response = new StringBuilder();
            for (Quote quote : quotes) {
                response.append("\n").append(quote.getSymbol()).append(" -");
                for (QuoteField field : request.getFields()) {
                    switch (field) {
                        case HIGH:
                            response.append(" High: ").append(quote.getHigh());
                            break;
                        case LOW:
                            response.append(" Low: ").append(quote.getLow());
                            break;
                        case CLOSE:
                            response.append(" Close: ").append(quote.getClose());
                            break;
                        case OPEN:
                            response.append(" Open: ").append(quote.getOpen());
                            break;
                        case CHANGE_PERCENT:
                            response.append(" Change Percent: ").append(quote.getChangePercent());
                            break;
                        case CHANGE:
                            response.append(" Change: ").append(quote.getChange());
                            break;
                        case LATEST_PRICE:
                            response.append(" Latest Price: ").append(quote.getLatestPrice());
                            break;
                    }
                }
            }

            System.out.println(response);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            out.write(response.toString());
            out.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }

}
