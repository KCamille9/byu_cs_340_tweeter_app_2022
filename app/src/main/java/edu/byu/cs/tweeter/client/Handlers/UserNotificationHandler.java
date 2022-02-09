package edu.byu.cs.tweeter.client.Handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.Observers.UserNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

public class UserNotificationHandler extends BackgroundTaskHandler<UserNotificationObserver>{

    public UserNotificationHandler(UserNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, UserNotificationObserver observer) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);
        observer.handleSuccess(user);
    }
}
