package edu.byu.cs.tweeter.client.Handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.Observers.CountNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.CountTasks.CountTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.CountTasks.GetFollowingCountTask;

public class CountNotificationHandler extends BackgroundTaskHandler<CountNotificationObserver>{

    public CountNotificationHandler(CountNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, CountNotificationObserver observer) {
        int count = data.getInt(CountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }
}
