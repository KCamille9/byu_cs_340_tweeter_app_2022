package edu.byu.cs.tweeter.client.view.main.presenter.Paged;


import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedPresenter;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User> {

    private final User user;
    private final AuthToken authToken;


    public FollowersPresenter(BaseView view) {
        super(view);
        this.user = new User();
        this.authToken = new AuthToken();
    }

    public FollowService getFollowerService() {
        return new FollowService();
    }

    @Override
    protected void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        getFollowerService().getFollowers(Cache.getInstance().getCurrUserAuthToken(),
                        user, pageSize, lastItem, new GetFollowersObserver());
    }



    public class GetFollowersObserver extends GetItemsObserver
    {
        @Override
        public String getDescription() {
            return "followers";
        }
    }

}
