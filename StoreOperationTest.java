package deutche.test.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import deutche.test.run.Run;
import deutche.test.trade.TradeDetails;

/**
 * 
 * @author Rachit
 *
 */
public class StoreOperationTest {

	
	@Test(expected = RuntimeException.class)
	public void testStoreRecordsforLessVersion() {
		TradeDetails details = new TradeDetails("T1",1,"CP-1","B2","20-05-2023","04-09-2020","N");
		TradeDetails details1 = new TradeDetails("T2",2,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details2 = new TradeDetails("T3",3,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details3 = new TradeDetails("T4",4,"CP-3","B2","20-05-2014","04-09-2014","Y");
		TradeDetails details4 = new TradeDetails("T2",0,"CP-2","B2","20-05-2023","04-09-2020","N");
		
		List<TradeDetails> list = Arrays.asList(details,details1,details2,details3);
		StoreOperations operations = new StoreOperations(list);
		operations.checkTradeRecords(details4);
		
	}
		
	@Test
	public void testStoreRecordsforUpdatedVersion() {
		TradeDetails details = new TradeDetails("T1",1,"CP-1","B2","20-05-2023","04-09-2020","N");
		TradeDetails details1 = new TradeDetails("T2",2,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details2 = new TradeDetails("T3",3,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details3 = new TradeDetails("T4",4,"CP-3","B2","20-05-2014","04-09-2014","Y");
		TradeDetails details4 = new TradeDetails("T5",5,"CP-2","B8","20-05-2023","04-09-2020","N");
		
		List<TradeDetails> list = new ArrayList<> (Arrays.asList(details,details1,details2,details3));
		List<TradeDetails> updatedlist = new ArrayList<> (Arrays.asList(details,details1,details2,details3,details4));
		
		StoreOperations operations = new StoreOperations(list);
		assertEquals(updatedlist,operations.checkTradeRecords(details4));
		
	}
	
	@Test
	public void testStoreRecordsforOverride() {
		TradeDetails details = new TradeDetails("T1",1,"CP-1","B2","20-05-2023","04-09-2020","N");
		TradeDetails details1 = new TradeDetails("T2",2,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details2 = new TradeDetails("T3",3,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details3 = new TradeDetails("T4",4,"CP-3","B2","20-05-2014","04-09-2014","Y");
		TradeDetails details4 = new TradeDetails("T2",2,"CP-2","B4","20-05-2023","04-09-2020","N");
		
		List<TradeDetails> list = new ArrayList<> (Arrays.asList(details,details1,details2,details3));
		
		TradeDetails updaetddetails = new TradeDetails("T1",1,"CP-1","B2","20-05-2023","04-09-2020","N");
		TradeDetails updaetddetails1 = new TradeDetails("T2",2,"CP-2","B4","20-05-2023","04-09-2020","N");
		TradeDetails updaetddetails2 = new TradeDetails("T3",3,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails updaetddetails3 = new TradeDetails("T4",4,"CP-3","B2","20-05-2014","04-09-2014","Y");
		List<TradeDetails> updatedlist = new ArrayList<> (Arrays.asList(updaetddetails,updaetddetails1,updaetddetails2,updaetddetails3));
		
		StoreOperations operations = new StoreOperations(list);
		assertNotEquals(updatedlist,operations.checkTradeRecords(details4));
		
	}
	
	@Test
	public void testStoreNewRecords() {
		TradeDetails details = new TradeDetails("T1",1,"CP-1","B2","20-05-2023","04-09-2020","N");
		TradeDetails details1 = new TradeDetails("T2",2,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details2 = new TradeDetails("T3",3,"CP-2","B2","20-05-2023","04-09-2020","N");
		TradeDetails details3 = new TradeDetails("T4",4,"CP-3","B2","20-05-2014","04-09-2014","Y");
		TradeDetails details4 = new TradeDetails("T2",2,"CP-2","B4","20-05-2023","04-09-2020","N");
		
		List<TradeDetails> list = new ArrayList<> (Arrays.asList(details,details1,details2,details3));
		
		List<TradeDetails> updatedlist = new ArrayList<> (Arrays.asList(details,details1,details2,details3,details4));
		
		StoreOperations operations = new StoreOperations(list);
		assertNotEquals(updatedlist,operations.checkTradeRecords(details4));
		
	}
	
	@Test
	public void testStoreExpiredRecords() {
		TradeDetails details = new TradeDetails("T1",1,"CP-1","B2","20-05-2014","04-09-2020","N");
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate currentDate = LocalDate.now();
		LocalDate givenDate = LocalDate.parse(details.getMaturityDate(), format);
		
		assertNotEquals(false,Run.checkExpiry(givenDate,currentDate));
		
	}
	
}
