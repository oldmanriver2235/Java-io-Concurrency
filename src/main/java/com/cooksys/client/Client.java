package com.cooksys.client;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Client {

    public static void main(String[] args) {

        // List of fields that the client is requesting on a quote
        Set<QuoteField> fields = new HashSet<>(Collections.singletonList((QuoteField.LATEST_PRICE)));

        // Stock ticker symbols client is requesting
        Set<String> symbols = new HashSet<>(Arrays.asList("NIO", "TWTR"));

        // Encapsulating request object
        QuoteRequest request = new QuoteRequest(fields, symbols);

        try {
            Socket socket = new Socket("localhost", 3000);

            JAXBContext context = JAXBContext.newInstance(QuoteField.class, QuoteRequest.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Marshal to socket
            marshaller.marshal(request, System.out);
            marshaller.marshal(request, socket.getOutputStream());

            socket.getOutputStream().flush();

            // Get response
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while (socket.isConnected() && (message = in.readLine()) != null) {
                System.out.println(message);
            }

//            Thread t = new Thread(new QuoteReader(socket));
//            t.start();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

    }

}
