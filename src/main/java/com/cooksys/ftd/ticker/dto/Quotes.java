package com.cooksys.ftd.ticker.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Quotes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7865124996186360862L;
	
	@XmlElement(name = "quote")
	private Set<QuoteResponseObject> quotes = new HashSet<>();

	public Quotes() {
	}
	
	public Quotes(Set<QuoteResponseObject> quotes) {
		super();
		this.quotes = quotes;
	}

	public Set<QuoteResponseObject> getQuotes() {
		return quotes;
	}

	public void setQuotes(Set<QuoteResponseObject> quotes) {
		this.quotes = quotes;
	}

	@Override
	public String toString() {
		String retStr = "";
		
		for (QuoteResponseObject qrs : quotes) {
			retStr += qrs.toString() + "\n";
		}
		return retStr;
	}
}

