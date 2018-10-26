package com.cooksys.ftd.ticker.server;

import com.cooksys.ftd.ticker.dto.QuoteField;
import java.math.BigDecimal;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import pl.zankowski.iextrading4j.api.stocks.Quote;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class QuoteResponseStocks implements Serializable {

	//private final long
	
  @XmlElement
  private String symbol;
  @XmlElement
  private BigDecimal high = null;
  @XmlElement
  private BigDecimal low = null;
  @XmlElement
  private BigDecimal close = null;
  @XmlElement
  private BigDecimal open = null;
  @XmlElement
  private BigDecimal changePercent = null;
  @XmlElement
  private BigDecimal change = null;
  @XmlElement
  private BigDecimal latestPrice = null;
  
  public QuoteResponseStocks() {}
  
  public QuoteResponseStocks(Quote quote, Set<QuoteField> quoteFields)
  {
    this.symbol = quote.getSymbol();
    for (QuoteField qf : quoteFields) {
      switch (qf)
      {
      case CHANGE: 
        this.open = quote.getOpen();
      case CHANGE_PERCENT: 
        this.close = quote.getClose();
      case CLOSE: 
        this.high = quote.getHigh();
      case HIGH: 
        this.low = quote.getLow();
      case LATEST_PRICE: 
        this.latestPrice = quote.getLatestPrice();
      case LOW: 
        this.change = quote.getChange();
      case OPEN: 
        this.changePercent = quote.getChangePercent();
      }
    }
  }
}
