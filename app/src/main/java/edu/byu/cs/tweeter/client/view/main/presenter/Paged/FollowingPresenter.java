package edu.byu.cs.tweeter.client.view.main.presenter.Paged;

import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedPresenter;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User> {

    private static final String LOG_TAG = "FollowingPresenter";

    private final User user;
    private final AuthToken authToken;


    /**
     * Creates an instance.
     *
     * @param view      the view for which this class is the presenter.
     * @param user      the user that is currently logged in.
     * @param authToken the auth token for the current session.
     */
    public FollowingPresenter(BaseView view, User user, AuthToken authToken) {
        super(view);
        this.user = user;
        this.authToken = authToken;
    }


    /**
     * Returns an instance of {@link FollowService}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public FollowService getFollowingService() {
        return new FollowService();
    }


    /**
     * Requests the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned for a previous request. This is an asynchronous
     * operation.
     *
     */
    @Override
    protected void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        int limit = 20;
        getFollowingService().getFollowees(authToken, targetUser, limit, lastItem, new FollowingObserver());
    }


    public class FollowingObserver extends GetItemsObserver{

        @Override
        public String getDescription() {
            return "followees";
        }
    }
}
