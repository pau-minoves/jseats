package org.jseats.unit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.jseats.SeatAllocatorLauncher;
import org.jseats.model.SeatAllocationException;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

public class CommandLineTest {

	@Test
	public void inputConfigWithTieBreaker() throws Exception {
		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] {
					"--input-config", "target/test-classes/config.xml",
					"--tie-breaker", "minority-tie-breaker", "-v" });
		} catch (Exception e) {
			fail("No exception should be launched");
		}

		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] {
					"--input-config", "target/test-classes/config.xml",
					"--interactive-tie-breaker", "-v" });
		} catch (Exception e) {
			fail("No exception should be launched");
		}
	}

	@Test
	public void helpUsage() throws Exception {
		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] { "--help" });
		} catch (Exception e) {
			fail("No exception should be launched");
		}
	}

	@Test
	public void nonExistingParameter() throws Exception {
		try {
			SeatAllocatorLauncher
					.mainWithThrow(new String[] { "--non-existing-parameter" });
		} catch (ParameterException e) {
			assertTrue("ParameterException should be launched", true);
			return;
		}

		fail("No exception launched");
	}

	@Test
	public void anotherException() throws Exception {
		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] {
					"--input-config", "does/not/exists.xml" });
		} catch (FileNotFoundException e) {
			assertTrue("FileNotFoundException should be launched", true);
			return;
		}

		fail("No exception launched");
	}

	@Test
	public void listingFiltersDecoratorsAndMethods() throws Exception {
		try {
			SeatAllocatorLauncher
					.mainWithThrow(new String[] { "--list-filters" });
		} catch (Exception e) {
			fail("No exception should be launched for --list-filters");
		}
		try {
			SeatAllocatorLauncher
					.mainWithThrow(new String[] { "--list-decorators" });
		} catch (Exception e) {
			fail("No exception should be launched for --list-decorators");
		}
		try {
			SeatAllocatorLauncher
					.mainWithThrow(new String[] { "--list-methods" });
		} catch (Exception e) {
			fail("No exception should be launched --list-methods");
		}

	}

	@Test
	public void noMethodDefined() throws Exception {
		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] { "--candidate",
					"A:200", "--candidate", "B:200" });
		} catch (SeatAllocationException e) {
			assertTrue("SeatAllocationException should be launched", true);
			return;
		}

		fail("No exception launched");
	}

	@Test
	public void properties() throws Exception {
		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] { "--method",
					"SimpleMajority", "-d", "hi=bye" });
		} catch (Exception e) {
			fail("No exception should be launched --list-methods");
		}

		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] { "--method",
					"SimpleMajority", "-d", "hi" });
		} catch (Exception e) {
			fail("No exception should be launched --list-methods");
		}

		try {
			SeatAllocatorLauncher.mainWithThrow(new String[] { "--method",
					"SimpleMajority", "-d", "=bye" });
		} catch (SeatAllocationException e) {
			assertTrue("SeatAllocationException should be launched", true);
			return;
		}
		fail("No exception launched");
	}
}
