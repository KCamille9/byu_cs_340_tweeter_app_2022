package edu.byu.cs.tweeter.client.Handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.Observers.SimpleNotificationObserver;

public class SimpleNotificationHandler extends BackgroundTaskHandler<SimpleNotificationObserver> {

    public SimpleNotificationHandler(SimpleNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleNotificationObserver observer) {
        observer.handleSuccess();
    }
}
