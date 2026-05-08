package com.peak.omnia.impl;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Omnia implements ModInitializer {
	public static final String MOD_ID = "omnia";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
		LOGGER.info("Omnia has initialized internally!");
	}
}