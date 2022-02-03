package edu.byu.cs.tweeter.client.view.main.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter {

    private View view;

    public interface View {
        void displayErrorMessage(String message);
        void handleSuccessIntent(User user);
        void addItems(List<Status> newStatus, boolean hasMorePages);
        void setLoadingFooter(boolean value);
    }


    private StatusService statusService;

    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;

    private static final int PAGE_SIZE = 10;


    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }



    public StoryPresenter(View view) {
        this.view = view;
        statusService = new StatusService();
    }

    public void loadMoreItems(User user) {

        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);

            statusService.getStory(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new GetStoryObserver() );
        }
    }


    public void executeUserTask(String userAlias) {
        statusService.GetUserTask(Cache.getInstance().getCurrUserAuthToken(), userAlias, new GetUserObserver());
    }



    public class GetUserObserver implements StatusService.GetUserObserver
    {

        @Override
        public void handleSuccess(User user) {
            view.handleSuccessIntent(user);
        }

        @Override
        public void handleFailure(String message) {

            view.displayErrorMessage("Failed to get user's profile: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayErrorMessage("Failed to get user's profile because of exception: " + exception.getMessage());

        }
    }


    public class GetStoryObserver implements StatusService.GetStoryObserver
    {

        @Override
        public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
            setHasMorePages(hasMorePages);
            isLoading = false;
            view.setLoadingFooter(false);

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addItems(statuses, hasMorePages);
        }

        @Override
        public void handleFailure(String message) {
            hasMorePages = false;
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayErrorMessage("Failed to get story: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            hasMorePages = false;
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayErrorMessage("Failed to get story because of exception: " + exception.getMessage());
        }
    }



}
