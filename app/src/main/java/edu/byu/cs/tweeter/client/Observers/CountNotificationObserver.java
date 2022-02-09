package edu.byu.cs.tweeter.client.Observers;

public interface CountNotificationObserver extends ServiceObserver {
    void handleSuccess(int count);
}
