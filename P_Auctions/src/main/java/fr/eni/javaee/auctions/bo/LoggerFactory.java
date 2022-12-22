package fr.eni.javaee.auctions.bo;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import fr.eni.javaee.auctions.testsunitaires.ServletTestManager;

public class LoggerFactory {
	private Logger logger = null;
	
	static public Logger getLogger(String className) throws SecurityException, IOException {
		
		Logger logger = Logger.getLogger(className);
		Handler textFileHandler = new FileHandler("/temp/logAppliEnchere.%g.log",10000,5,false);
		logger.addHandler(textFileHandler);
		SimpleFormatter simpleFormatter = new SimpleFormatter();
		textFileHandler.setFormatter(simpleFormatter);
		
		return logger;
	}
}

