
package com.cooksys.ftd.ticker.api;

	import java.util.Iterator;
import java.util.Set;

import com.cooksys.ftd.ticker.dto.QuoteField;
import pl.zankowski.iextrading4j.api.stocks.Quote;

	public class StockUtils
	{
	  public static String quotesToString(Set<Quote> quotes, Set<QuoteField> fields)
	  {
	    StringBuilder quoteString = new StringBuilder();
	    Iterator localIterator2;
	    for (Iterator localIterator1 = quotes.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
	    {
	      Quote quote = (Quote)localIterator1.next();
	      quoteString.append("\n").append(quote.getSymbol()).append(" -");
	      localIterator2 = fields.iterator();
	      
	      QuoteField field = (QuoteField)localIterator2.next();
	      switch (field)
	      {
	      case CLOSE: 
	        quoteString.append(" High: ").append(quote.getHigh());
	        break;
	      case HIGH: 
	        quoteString.append(" Low: ").append(quote.getLow());
	        break;
	      case CHANGE_PERCENT: 
	        quoteString.append(" Close: ").append(quote.getClose());
	        break;
	      case CHANGE: 
	        quoteString.append(" Open: ").append(quote.getOpen());
	        break;
	      case OPEN: 
	        quoteString.append(" Change Percent: ").append(quote.getChangePercent());
	        break;
	      case LOW: 
	        quoteString.append(" Change: ").append(quote.getChange());
	        break;
	      case LATEST_PRICE: 
	        quoteString.append(" Latest Price: ").append(quote.getLatestPrice());
	      }
	    }
	    return quoteString.toString();
	  }
	}
