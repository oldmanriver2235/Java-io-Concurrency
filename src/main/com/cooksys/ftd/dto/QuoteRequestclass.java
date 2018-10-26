package com.cooksys.ftd.ticker.dto;

import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class QuoteRequest
{
  @XmlElementWrapper
  @XmlElement(name="field")
  private Set<QuoteField> fields;
  @XmlElementWrapper
  @XmlElement(name="symbol")
  private Set<String> symbols;
  @XmlElement(name="interval")
  private int interval;
  
  public QuoteRequest() {}
  
  public QuoteRequest(Set<QuoteField> fields, Set<String> symbols, int interval)
  {
    this.symbols = symbols;
    this.fields = fields;
  }
  
  public Set<String> getSymbols()
  {
    return this.symbols;
  }
  
  public void setSymbols(Set<String> symbols)
  {
    this.symbols = symbols;
  }
  
  public Set<QuoteField> getFields()
  {
    return this.fields;
  }
  
  public void setFields(Set<QuoteField> fields)
  {
    this.fields = fields;
  }
  
  public int getInterval()
  {
    return this.interval;
  }
  
  public void setIntervals(int interval)
  {
    this.interval = interval;
  }
}