package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersCountPresenter extends FollowCountPresenter {


    private FollowService followServiceCount;

    public GetFollowersCountPresenter(FollowCountView baseView) {
        super(baseView);
        followServiceCount = new FollowService();
    }

//    @Override
    public void proceedTask(User targetUser) {

        followServiceCount.GetGetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(), targetUser,
                new GetFollowersCountObserver());
    }

    public  class GetFollowersCountObserver extends FollowCountObserver {

        @Override
        public String getDescription() {
            return "followers count";
        }
    }
}