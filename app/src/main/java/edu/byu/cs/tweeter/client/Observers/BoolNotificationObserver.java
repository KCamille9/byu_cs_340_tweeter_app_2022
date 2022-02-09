package edu.byu.cs.tweeter.client.Observers;

public interface BoolNotificationObserver extends ServiceObserver{
    void handleSuccess(boolean value);
}
