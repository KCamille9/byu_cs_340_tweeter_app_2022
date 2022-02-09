package edu.byu.cs.tweeter.client.Handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.Observers.BoolNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.IsFollowerTask;

public class BoolNotificationHandler extends BackgroundTaskHandler<BoolNotificationObserver>{

    public BoolNotificationHandler(BoolNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, BoolNotificationObserver observer) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        observer.handleSuccess(isFollower);
    }
}
