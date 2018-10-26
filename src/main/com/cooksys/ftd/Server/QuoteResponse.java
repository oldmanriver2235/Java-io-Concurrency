package com.cooksys.ftd.ticker.server;

public class QuoteResponse implements Serializable {

	private static final long

	@XmlElemetWrapper @XmlElement(name="stock")
	private Set<QuoteResponseStock> stocks = new HashSet<>();

	public QuoteResponse() {

	public QuoteResponse(Set<Quote> quotes, Set<QuoteField> quotefields) {
		super();
		
		QuoteResponseStock qrs;
		
		for (Quote q : quotes) {
			System.out.println(q.toString());
			System.out.println(quoteFields.toString());
			
			qrs = new QuoteResponseStock(q, quoteFields);
			
			System.out.println(qrs.toString());
			
			this.stocks.add(qrs);
		}
	}

	@Override
	public String toString() {
		String retstr = "";
		
		for (QuoteResponseStock qrs : stocks) {
			retstr += qrs.toString() + "\n";
		}
		return retStr;
	}
}

