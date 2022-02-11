package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState;

import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityView;

public interface FollowStateView extends MainActivityView {
    void unFollowObserverSuccess();
    void enableFollowButton();
}

