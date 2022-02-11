package edu.byu.cs.tweeter.client.view.main.presenter.Paged;

import java.util.List;

import edu.byu.cs.tweeter.client.Observers.PagedNotificationObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.presenter.BasePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.client.view.main.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends BasePresenter {

    private final int PAGE_SIZE = 10;
    private User targetUser;
    private AuthToken authToken;
    private T lastItem;
    private boolean hasMorePages = true;
    private boolean isLoading = false;


    StatusService statusService;


    public PagedPresenter(BaseView baseView) {
        super(baseView);
    }

    protected abstract void getItems(AuthToken authToken, User targetUser, int pageSize, T lastItem);

//    protected abstract String getDescription();

    public void loadMoreItems(){
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            getPagedView().setLoading(true);

            getItems(authToken, targetUser, PAGE_SIZE, lastItem);

        }
    }


    public void getUser(String alias){
        statusService.GetUserTask(Cache.getInstance().getCurrUserAuthToken(), alias, new GetUserObserver());
    }




    public class GetUserObserver implements StatusService.GetUserObserver
            //extends BaseObserver
    {

        @Override
        public void handleSuccess(User user) {
            //navigate to user instead?
            getPagedView().navigateToUser(user);
        }

        @Override
        public void handleFailure(String message) {

            getPagedView().displayErrorMessage("Failed to get user's profile: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            getPagedView().displayErrorMessage("Failed to get user's profile because of exception: " + exception.getMessage());

        }

        @Override
        public String getDescription() {
            return null;
        }
    }

    public abstract class GetItemsObserver implements PagedNotificationObserver<T>
    {

        @Override
        public void handleSuccess(List<T> items, boolean hasMorePages) {
            setHasMorePages(hasMorePages);
            isLoading = false;
            getPagedView().setLoading(false);

            lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;

            getPagedView().addItems(items);
        }

        @Override
        public void handleFailure(String message) {

            handleError();
            getPagedView().displayErrorMessage("Failed to get " + getDescription()
                    + ": " + message);

        }

        @Override
        public void handleException(Exception exception) {

            handleError();
            getPagedView().displayErrorMessage("Failed to get "
                    + getDescription() + " because of exception: " + exception.getMessage());
        }
    }

    public void handleError()
    {
        hasMorePages = false;
        isLoading = false;
        getPagedView().setLoading(false);
    }




    public int getPAGE_SIZE() {
        return PAGE_SIZE;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public T getLastItem() {
        return lastItem;
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
