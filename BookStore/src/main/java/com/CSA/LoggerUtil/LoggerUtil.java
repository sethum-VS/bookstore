package com.CSA.LoggerUtil;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class for centralized logging throughout the BookStore application.
 * Uses java.util.logging to log messages to both console and file.
 */
public class LoggerUtil {
    
    private static final Logger LOGGER = Logger.getLogger(LoggerUtil.class.getName());
    private static final String LOG_FILE = "bookstore.log";
    
    static {
        try {
            // Configure the logger to handle both console and file output
            LOGGER.setUseParentHandlers(false);
            LOGGER.setLevel(Level.ALL);
            
            // Create and configure the console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            LOGGER.addHandler(consoleHandler);
            
            // Create and configure the file handler
            FileHandler fileHandler = new FileHandler(LOG_FILE, true); // true for append mode
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            
            LOGGER.info("Logger initialized successfully");
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Logs an informational message
     * 
     * @param message The message to log
     */
    public static void logInfo(String message) {
        LOGGER.info(message);
    }
    
    /**
     * Logs a warning message
     * 
     * @param message The message to log
     */
    public static void logWarning(String message) {
        LOGGER.warning(message);
    }
    
    /**
     * Logs a severe error message
     * 
     * @param message The message to log
     */
    public static void logSevere(String message) {
        LOGGER.severe(message);
    }
}
