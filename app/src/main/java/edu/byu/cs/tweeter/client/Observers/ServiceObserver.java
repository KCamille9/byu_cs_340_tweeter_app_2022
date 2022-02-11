package edu.byu.cs.tweeter.client.Observers;

public interface ServiceObserver {
    void handleFailure(String message);
    void handleException(Exception exception);

    String getDescription();
}
