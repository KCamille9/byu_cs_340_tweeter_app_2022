package edu.byu.cs.tweeter.client.view.main.presenter.Authenticate;

import edu.byu.cs.tweeter.client.Observers.UserAuthNotificationObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.presenter.BasePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticatePresenter extends BasePresenter {


    public AuthenticatePresenter(BaseView baseView) {
        super(baseView);
    }


    public abstract class AuthenticateObserver implements UserAuthNotificationObserver
    {

        @Override
        public void handleSuccess(User user, AuthToken authToken) {
            Cache.getInstance().setCurrUser(user);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            getAuthenticateView().authenticateSuccessful(user);
        }

        @Override
        public void handleFailure(String message) {
            getAuthenticateView().displayErrorMessage("Failed to " + getDescription()
                    + ": " + message);
        }

        @Override
        public void handleException(Exception exception) {
            getAuthenticateView().displayErrorMessage("Failed to " + getDescription()
                    + " because of exception: " + exception.getMessage());
        }
    }
}
