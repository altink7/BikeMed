package at.altin.bikemedcommons.listener;

/**
 * CommonEventListener
 * This interface is used to define the common event listener.
 */
public interface CommonEventListener {

    /**
     * This method is used to handle the message.
     * @param message the message from Event to be handled
     */
    void handleMessage(String message);
}
