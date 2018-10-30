package com.cooksys.ftd.ticker.dto;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class QuoteResponseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5347149712479984432L;

	@XmlElement
	private String symbol;

	@XmlElement
	private QuoteResponseField fields;

	public QuoteResponseObject() {
		super();
	}

	public QuoteResponseObject(String symbol, QuoteResponseField fields) {
		super();
		this.symbol = symbol;
		this.fields = fields;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public QuoteResponseField getFields() {
		return fields;
	}

	public void setFields(QuoteResponseField fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		String retStr = "";

		if (fields.getChange() != null) {
			retStr += symbol + " change = " + fields.getChange() + "\n";
		}

		if (fields.getChangePercent() != null) {
			retStr += symbol + " change percent = " + fields.getChangePercent() + "\n";
		}
		
		if (fields.getClose() != null) {
			retStr += symbol + " close = " + fields.getClose() + "\n";
		}

		if (fields.getHigh() != null) {
			retStr += symbol + " high = " + fields.getHigh() + "\n";
		}
		
		if (fields.getLatestPrice() != null) {
			retStr += symbol + " latest price = " + fields.getLatestPrice() + "\n";
		}

		if (fields.getLow() != null) {
			retStr += symbol + " low = " + fields.getLow() + "\n";
		}
		
		if (fields.getOpen() != null) {
			retStr += symbol + " open = " + fields.getOpen() + "\n";
		}

		return retStr;
	}

}
