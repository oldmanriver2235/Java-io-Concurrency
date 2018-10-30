package com.cooksys.ftd.ticker.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

import com.cooksys.ftd.ticker.api.StockApi;
import com.cooksys.ftd.ticker.dto.QuoteRequest;
import com.cooksys.ftd.ticker.dto.QuoteResponseField;
import com.cooksys.ftd.ticker.dto.QuoteResponseObject;
import com.cooksys.ftd.ticker.dto.Quotes;

import pl.zankowski.iextrading4j.api.stocks.Quote;

public class ClientHandler implements Runnable {
	private Socket client;

	public ClientHandler(Socket client) {
		this.client = client;
	}

	public void run() {
		try (ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						new BufferedOutputStream(client.getOutputStream()));

		) {

			QuoteRequest quoteRequest = (QuoteRequest) objectIn.readObject();

			while (client.isConnected()) {
				Set<Quote> quotes = StockApi.fetchQuotes(quoteRequest);

				Quotes response = new Quotes();

				for (Quote q : quotes) {
					// create a QuoteResponseObject providing the symbol for the current quote &
					// the QuoteResponseFields.
					QuoteResponseObject qro = new QuoteResponseObject(q.getSymbol(),
							new QuoteResponseField(q, quoteRequest.getFields()));

					// Finally, add the QuoteResponseObject to the QuoteResponse
					response.getQuotes().add(qro);
				}

				// Send response to client
				objectOutputStream.writeObject(response);
				objectOutputStream.flush();

				Thread.sleep(1000 * quoteRequest.getInterval());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
