package deutche.test.store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import deutche.test.trade.TradeDetails;

/**
 * This class is performing basic validation prior to storing records.
 * @author Rachit
 *
 */
public class StoreOperations {
	
	private List<TradeDetails> tradeDetails = new ArrayList<>();
	
	public StoreOperations(List<TradeDetails> tradeDetails) {
		this.tradeDetails = tradeDetails;
	}

	public List<TradeDetails> checkTradeRecords(TradeDetails trade) {
		
		Iterator<TradeDetails> itr = tradeDetails.iterator();
		boolean append = false;
		int counter = 0;
		while (itr.hasNext()) {
			TradeDetails storedDetails = itr.next();
			if (storedDetails.getTradeId().equals(trade.getTradeId())) {
				append = false;
				if (storedDetails.getVersion() == trade.getVersion()) {
					System.out.println("Same version Identified");
					TradeDetails details = new TradeDetails(trade.getTradeId(), trade.getVersion(),
							trade.getCounterPartyId(), trade.getBookId(), trade.getMaturityDate(),
							trade.getCreatedDate(), trade.getExpired());
					tradeDetails.set(counter, details);
					append = false;
					break;
				} else if (storedDetails.getVersion() < trade.getVersion()) {
					append = true;
				} else if (storedDetails.getVersion() > trade.getVersion()) {
					append = false;
					throw new RuntimeException("Can not store lower version of same Trade!");
				}
			} else if (!storedDetails.getTradeId().equals(trade.getTradeId())) {				
				append = true;

			}
			counter++;
		}
		if(append) {
			tradeDetails.add(trade);
		}
		
		return tradeDetails;
	}
	
}
