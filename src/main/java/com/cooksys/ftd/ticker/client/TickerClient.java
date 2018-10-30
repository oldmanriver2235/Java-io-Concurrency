package com.cooksys.ftd.ticker.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.ticker.dto.QuoteField;
import com.cooksys.ftd.ticker.dto.QuoteRequest;
import com.cooksys.ftd.ticker.dto.Quotes;
import com.cooksys.ftd.ticker.dto.QuoteResponseField;

public class TickerClient {
	public static void main(String[] args) {
		Set<QuoteField> fields = new HashSet<>(Collections.singletonList(QuoteField.LATEST_PRICE));

		Set<String> symbols = new HashSet<>(Arrays.asList("NIO", "TWTR", "AMZN", "DVMT", "NKE"));

		// int interval = 1;

		// QuoteRequest request = new QuoteRequest(fields, symbols, interval);
		try (Socket socket = new Socket("localhost", 4000);
				ObjectOutputStream objectOut = new ObjectOutputStream(
						new BufferedOutputStream(socket.getOutputStream()));) {

			JAXBContext context = JAXBContext.newInstance(QuoteField.class, QuoteRequest.class, Quotes.class,
					Integer.class, QuoteResponseField.class);
			Marshaller marshaller = context.createMarshaller();
			Unmarshaller unmarsh = context.createUnmarshaller();

			QuoteRequest request;

			File xmlIn = new File("InputQuoteRequest.xml");
			if (xmlIn.exists()) {
				request = (QuoteRequest) unmarsh.unmarshal(xmlIn);
			} else {
				request = new QuoteRequest(fields, symbols, 1);
			}

			objectOut.writeObject(request);
			objectOut.flush();

			ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

			Quotes quoteResponse;

			while (socket.isConnected()) {

				quoteResponse = (Quotes) objectIn.readObject();

				System.out.println(quoteResponse.toString());

				OutputStream fileOut = new FileOutputStream("LatestStocks.xml");

				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				marshaller.marshal(quoteResponse, fileOut);

			}
			socket.close();

		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
