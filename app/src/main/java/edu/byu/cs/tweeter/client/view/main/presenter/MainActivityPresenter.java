package edu.byu.cs.tweeter.client.view.main.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;

//import edu.byu.cs.tweeter.client.view.main.presenter.FollowCount.FollowCountPresenter;
//import edu.byu.cs.tweeter.client.view.main.presenter.FollowCount.FollowCountView;
//import edu.byu.cs.tweeter.client.view.main.presenter.FollowState.FollowStatePresenter;
//import edu.byu.cs.tweeter.client.view.main.presenter.FollowState.FollowStateView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount.FollowCountPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount.FollowCountView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState.FollowStatePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState.FollowStateView;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.client.view.main.service.StatusService;
import edu.byu.cs.tweeter.client.view.main.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter {

    private FollowService followService;
    private UserService userService;
    private StatusService statusService;


    private View view;




    public interface View {

        void displayErrorMessage(String message);

        void logoutUserPresenter();

        void updateFollowerCount(int count);

        void updateFolloweeCount(int count);

        void setIsFollowerCase();

        void setIsNotFollowerCase();

        void followObserverSuccess();

        void enableFollowButton();

        void unFollowObserverSuccess();

        void postStatusSuccess();
    }

    public MainActivityPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public StatusService getStatusService()
    {
        if(statusService == null)
        {
            statusService = new StatusService();
        }

        return statusService;
    }


    // Helper functions

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }


    // Execute Tasks

    public void executeIsFollowerTask(User user) {
        followService.GetIsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), user, new IsFollowerObserver());

    }

    public void executeLogOutTask() {
        userService.GetLogOutTask(Cache.getInstance().getCurrUserAuthToken(), new LogOutObserver());
    }

    public void executePostStatusTask(String post) throws ParseException {

        Status newStatus = new Status(post, Cache.getInstance().getCurrUser(),
                getFormattedDateTime(), parseURLs(post), parseMentions(post));

        getStatusService().GetPostStatusTask(Cache.getInstance().getCurrUserAuthToken(), newStatus,
                new PostStatusObserver());
    }


    // Observers

    private class IsFollowerObserver implements FollowService.IsFollowerObserver {

        @Override
        public void handleSuccess(boolean isFollower) {
            // If logged in user if a follower of the selected user, display the follow button as "following"
            if (isFollower) {
                view.setIsFollowerCase();
            } else {
                view.setIsNotFollowerCase();
            }
        }

        @Override
        public void handleFailure(String message) {
            view.displayErrorMessage("Failed to determine following relationship: "  + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayErrorMessage("Failed to determine following relationship because of exception: " + exception.getMessage());
        }

        @Override
        public String getDescription() {
            return "isfollower";
        }
    }

    private class LogOutObserver implements UserService.LogOutObserver {

        @Override
        public void handleSuccess() {
            view.logoutUserPresenter();
            Cache.getInstance().clearCache();
        }

        @Override
        public void handleFailure(String message) {
            view.displayErrorMessage("Failed to logout: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayErrorMessage("Failed to logout because of exception: " + exception.getMessage());
        }

        @Override
        public String getDescription() {
            return "logout";
        }
    }

    public class PostStatusObserver implements StatusService.PostStatusObserver {

        @Override
        public void handleSuccess() {
            view.postStatusSuccess();
        }

        @Override
        public void handleFailure(String message) {
            view.displayErrorMessage("Failed to post status: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayErrorMessage("Failed to post status because of exception: " + exception.getMessage());
        }

        @Override
        public String getDescription() {
            return "post";
        }
    }


}
