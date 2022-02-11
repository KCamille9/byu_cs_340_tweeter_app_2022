package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState.FollowStatePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState.FollowStateView;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class UnFollowPresenter extends FollowStatePresenter {

        private FollowService followService;

        public UnFollowPresenter(FollowStateView baseView) {
            super(baseView);
            followService = new FollowService();
        }

    @Override
        public void executeTask(User targetUser) {
            followService.GetUnFollowTask(Cache.getInstance().getCurrUserAuthToken(), targetUser,
                    new UnFollowObserver());
        }

        public  class UnFollowObserver extends FollowStateObserver {

            @Override
            public String getDescription() {
                return "unfollow";
            }
        }

}
