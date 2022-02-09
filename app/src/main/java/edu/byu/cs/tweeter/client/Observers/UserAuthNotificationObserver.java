package edu.byu.cs.tweeter.client.Observers;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface UserAuthNotificationObserver extends ServiceObserver{
    void handleSuccess(User user, AuthToken authToken);
}
