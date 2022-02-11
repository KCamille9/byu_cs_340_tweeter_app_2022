package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingCountPresenter extends FollowCountPresenter {


    private FollowService followServiceCount;

    public GetFollowingCountPresenter(BaseView baseView) {
        super(baseView);
        followServiceCount = new FollowService();
    }

    @Override
    public void proceedTask(User targetUser) {
        followServiceCount.GetGetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                targetUser, new GetFolloweesCountObserver());
    }

    public  class GetFolloweesCountObserver extends FollowCountObserver {

        @Override
        public String getDescription() {
            return "followees count";
        }
    }
}
