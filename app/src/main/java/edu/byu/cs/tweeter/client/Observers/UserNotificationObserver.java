package edu.byu.cs.tweeter.client.Observers;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserNotificationObserver extends ServiceObserver{
    void handleSuccess(User user);
}
