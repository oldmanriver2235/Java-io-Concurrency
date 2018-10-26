 package com.cooksys.ftd.ticker.server;

import com.cooksys.ftd.ticker.api.StockApi;
import com.cooksys.ftd.ticker.api.StockUtils;
import com.cooksys.ftd.ticker.dto.QuoteField;
import com.cooksys.ftd.ticker.dto.QuoteRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import pl.zankowski.iextrading4j.api.stocks.Quote;

public class WorkInProgress
  implements Runnable
{
  private Socket client;
  
  public WorkInProgress(Socket client)
  {
    this.client = client;
  }
  
  public void run()
  {
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { QuoteField.class, QuoteRequest.class });
      Unmarshaller unmarshaller = context.createUnmarshaller();
      
      Socket client = this.client;
      
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      StringReader stringReader = new StringReader(bufferedReader.readLine());
      
      QuoteRequest quoteRequest = (QuoteRequest)unmarshaller.unmarshal(stringReader);
      for (;;)
      {
        Set<Quote> quotes = StockApi.fetchQuotes(quoteRequest);
        
        String stringQuote = StockUtils.quotesToString(quotes, quoteRequest.getFields());
        
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        out.write(stringQuote);
        out.newLine();
        out.flush();
        
        Thread.sleep(1000 * quoteRequest.getInterval());
      }
    }
    catch (IOException|JAXBException e)
    {
      e.printStackTrace();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
