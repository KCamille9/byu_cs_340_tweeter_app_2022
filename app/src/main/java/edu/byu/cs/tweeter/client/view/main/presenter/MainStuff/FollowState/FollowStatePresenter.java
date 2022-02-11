package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState;

import edu.byu.cs.tweeter.client.Observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.view.main.presenter.BasePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class FollowStatePresenter extends BasePresenter {


    public FollowStatePresenter(BaseView baseView) {
        super(baseView);
    }

    protected abstract void executeTask(User targetUser);

    public abstract  class FollowStateObserver implements SimpleNotificationObserver
    {

        @Override
        public void handleSuccess() {
            getFollowStateView().unFollowObserverSuccess();
            getFollowStateView().enableFollowButton();
        }

        @Override
        public void handleFailure(String message) {
            getFollowStateView().displayErrorMessage("Failed to " + getDescription() + ":"+ message);
            getFollowStateView().enableFollowButton();
        }

        @Override
        public void handleException(Exception exception) {
            getFollowStateView().displayErrorMessage("Failed to " + getDescription() +
                    " because of exception: " + exception.getMessage());
            getFollowStateView().enableFollowButton();
        }



    }
}
