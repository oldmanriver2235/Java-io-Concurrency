package com.cooksys.ftd.ticker.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pl.zankowski.iextrading4j.api.stocks.Quote;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class QuoteResponseField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6605551649795979909L;

	// make this the bottom level bean!!
	@XmlElement
	private BigDecimal high;
	@XmlElement
	private BigDecimal low;
	@XmlElement
	private BigDecimal close;
	@XmlElement
	private BigDecimal open;
	@XmlElement
	private BigDecimal changePercent;
	@XmlElement
	private BigDecimal change;
	@XmlElement
	private BigDecimal latestPrice;

	public QuoteResponseField() {
	}

	public QuoteResponseField(Quote quote, Set<QuoteField> quoteFields) {
		for (QuoteField qf : quoteFields) {
			switch (qf) {
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

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getChangePercent() {
		return changePercent;
	}

	public void setChangePercent(BigDecimal changePercent) {
		this.changePercent = changePercent;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public BigDecimal getLatestPrice() {
		return latestPrice;
	}

	public void setLatestPrice(BigDecimal latestPrice) {
		this.latestPrice = latestPrice;
	}
}
