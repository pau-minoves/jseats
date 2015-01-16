package org.jseats.unit;

import org.jseats.SeatAllocatorLauncher;
import org.junit.Test;

public class CommandLineTest {

	@Test
	public void test() throws Exception {

		SeatAllocatorLauncher.mainWithThrow(new String[] { "--input-config",
				"target/test-classes/config.xml", "--tie-breaker",
				"minority-tie-breaker", "-v" });
	}
}
