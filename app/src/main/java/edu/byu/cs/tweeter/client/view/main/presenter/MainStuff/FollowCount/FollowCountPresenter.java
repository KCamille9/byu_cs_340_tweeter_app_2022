package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount;

import edu.byu.cs.tweeter.client.Observers.CountNotificationObserver;
import edu.byu.cs.tweeter.client.Observers.UserAuthNotificationObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.presenter.BasePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class FollowCountPresenter extends BasePresenter {

    public FollowCountPresenter(BaseView baseView) {
        super(baseView);
    }

    protected abstract void proceedTask(User targetUser);

    public abstract class FollowCountObserver implements CountNotificationObserver
    {

        @Override
        public void handleSuccess(int count) {
            getFollowCountView().updateFollowerCount(count);
        }

        @Override
        public void handleFailure(String message) {
            getAuthenticateView().displayErrorMessage("Failed to get " + getDescription()
                    + " count: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            getAuthenticateView().displayErrorMessage("Failed to get " + getDescription()
                    + " because of exception: " + exception.getMessage());
        }


    }
}
