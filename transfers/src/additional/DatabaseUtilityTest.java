package additional;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseUtilityTest {

	@Test
	public final void testGetUrl() {
		DatabaseUtility du = new DatabaseUtility();
		assertEquals("Result:", "jdbc:mysql://83.212.119.122/teipir?characterEncoding=UTF-8",du.getUsername());
	}

	@Test
	public final void testGetUsername() {
		DatabaseUtility du = new DatabaseUtility();
		assertEquals("Result:", "teipir",du.getUsername());
	}

	@Test
	public final void testGetPass() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testLogin() {
		DatabaseUtility du = new DatabaseUtility();
		assertEquals("Result:", false,du.login("teipir", "1111", "Administrator"));
		assertEquals("Result:", true,du.login("phil", "1234", "Employee"));
	
		
	}

}
