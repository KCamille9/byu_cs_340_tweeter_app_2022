package edu.byu.cs.tweeter.client.Handlers;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.Observers.UserAuthNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatePkg.AuthenticateTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatePkg.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserAuthNotificationHandler extends BackgroundTaskHandler<UserAuthNotificationObserver>{

    public UserAuthNotificationHandler(UserAuthNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, UserAuthNotificationObserver observer) {
        User user = (User) data.getSerializable(AuthenticateTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);
        observer.handleSuccess(user, authToken);
    }
}
