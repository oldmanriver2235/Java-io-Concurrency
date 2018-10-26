package com.cooksys.ftd.ticker.client;

import com.cooksys.ftd.ticker.dto.QuoteField;
import com.cooksys.ftd.ticker.dto.QuoteRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TickerClient
{
  public static void main(String[] args)
  {
    Set<QuoteField> fields = new HashSet(Collections.singletonList(QuoteField.LATEST_PRICE));
    
    Set<String> symbols = new HashSet(Arrays.asList(new String[] { "NIO", "TWTR", "AMZN", "DVMT", "NKE" }));
    
    int interval = 1;
    
    QuoteRequest request = new QuoteRequest(fields, symbols, interval);
    try
    {
      Socket socket = new Socket("localhost", 4000);
      
      JAXBContext context = JAXBContext.newInstance(new Class[] { QuoteField.class, QuoteRequest.class, Integer.class, QuoteResponseStock.class });
      Marshaller marshaller = context.createMarshaller();
      Unmarshaller unmarsh = context.createUnmarshaller();
      
      ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
      
      QuoteRequest request;
      
      File xmlIn = new File("InputQuoteRequest.xml");
      if (xmlIn.exists()) {
    	  request = (QuoteRequest) unmarsh.unmarshal(xmlIn);
      }else {
    	  request = new QuoteRequest(fields, symbols, 10000);
      }
      
      objectOut.writeObject(request);
      
      Quoteresponse qr;
      
      while (socket.isConnected()) {
    	  
    	  qr = (QuoteResponse) objectIn.readObject();
    	  
    	  System.out.println(qr.toString());
    	  
    	  OutputStream fileOut = new FileOutputStream("LatestStocks.xml");
    	  
    	  marshaller.marshal(qr, fileOut);
    	  
      }
      
      
      
      StringWriter stringWriter = new StringWriter();
      marshaller.marshal(request, stringWriter);
      
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      out.write(stringWriter.toString());
      out.newLine();
      out.flush();
      
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String message;
      do
      {
        String message;
        System.out.println(message);
        if (!socket.isConnected()) {
          break;
        }
      } while ((message = in.readLine()) != null);
    }
    catch (JAXBException|IOException e)
    {
      e.printStackTrace();
    }
  }
}

