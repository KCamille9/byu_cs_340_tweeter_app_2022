package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState.FollowStatePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState.FollowStateView;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowPresenter extends FollowStatePresenter {

    private FollowService followService;

    public FollowPresenter(FollowStateView baseView) {
        super(baseView);
        followService = new FollowService();
    }

    @Override
    public void executeTask(User targetUser) {
        followService.GetFollowTask(Cache.getInstance().getCurrUserAuthToken(), targetUser,new FollowObserver());
    }

    public  class FollowObserver extends FollowStateObserver {

        @Override
        public String getDescription() {
            return "follow";
        }
    }
}