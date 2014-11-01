package org.jseats.jbehave;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.jbehave.core.annotations.When;
import org.jseats.cli.SeatAllocatorLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLISteps {

	static Logger log = LoggerFactory.getLogger(CLISteps.class);

	@When("execute command with parameters at $file")
	public void runCommand(String paramsFile) throws Exception {

		File params = new File(getClass().getResource(paramsFile).getPath());

		assertTrue(
				"Parameters file does not exist: " + params.getAbsolutePath(),
				params.exists());

		log.info("Loading parameters file: " + params.getAbsolutePath());

		String[] args = { "@" + params.getAbsolutePath() };

		SeatAllocatorLauncher.mainWithThrow(args);
	}
}
