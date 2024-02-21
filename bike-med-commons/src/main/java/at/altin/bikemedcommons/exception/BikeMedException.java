package at.altin.bikemedcommons.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom exception for BikeMed application
 *
 * @author Altin
 */
@Slf4j
public class BikeMedException extends RuntimeException {

    public BikeMedException() {
        super();
        log.debug("An error occurred");
    }

    public BikeMedException(String message) {
        super(message);
        log.debug(message);
    }

    public BikeMedException(String message, Throwable cause) {
        super(message, cause);
        log.debug(message, cause);
    }

    public BikeMedException(Throwable cause) {
        super(cause);
        log.debug(cause.getMessage(), cause);
    }
}
