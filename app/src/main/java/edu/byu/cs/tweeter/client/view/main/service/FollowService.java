package edu.byu.cs.tweeter.client.view.main.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.Handlers.BoolNotificationHandler;
import edu.byu.cs.tweeter.client.Handlers.CountNotificationHandler;
import edu.byu.cs.tweeter.client.Handlers.PagedNotificationHandler;
import edu.byu.cs.tweeter.client.Handlers.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.Observers.BoolNotificationObserver;
import edu.byu.cs.tweeter.client.Observers.CountNotificationObserver;
import edu.byu.cs.tweeter.client.Observers.PagedNotificationObserver;
import edu.byu.cs.tweeter.client.Observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.CountTasks.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.CountTasks.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.UnfollowTask;
//import edu.byu.cs.tweeter.client.view.main.presenter.FollowPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount.GetFollowersCountPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount.GetFollowingCountPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.FollowersPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.FollowingPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityPresenter;
//import edu.byu.cs.tweeter.client.view.main.presenter.UnFollowPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface GetFollowingObserver extends PagedNotificationObserver<User> {

    }

    public interface GetFollowersObserver extends PagedNotificationObserver<User> {

    }

//    public interface FollowObserver extends SimpleNotificationObserver {
//    }

    public interface UnFollowObserver extends SimpleNotificationObserver{

    }

    public interface IsFollowerObserver extends BoolNotificationObserver {

    }

//    public interface GetFollowersCountObserver extends CountNotificationObserver{
//
//    }

    public interface GetFollowingCountObserver extends CountNotificationObserver {

    }


    /**
     * Creates an instance.
     */
    public FollowService() {}

    /**
     * Requests the users that the user specified in the request is following.
     * Limits the number of followees returned and returns the next set of
     * followees after any that were returned in a previous request.
     * This is an asynchronous operation.
     *  @param authToken the session auth token.
     * @param targetUser the user for whom followees are being retrieved.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last followee returned in the previous request (can be null).
     * @param observer
     */
    public void getFollowees(AuthToken authToken, User targetUser, int limit, User lastFollowee, FollowingPresenter.FollowingObserver observer) {
        GetFollowingTask followingTask = getGetFollowingTask(authToken, targetUser, limit, lastFollowee, observer);
        BackgroundTaskUtils.runTask(followingTask);
    }

    /**
     * Returns an instance of {@link GetFollowingTask}. Allows mocking of the
     * GetFollowingTask class for testing purposes. All usages of GetFollowingTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    // This method is public so it can be accessed by test cases
    public GetFollowingTask getGetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee, FollowingPresenter.FollowingObserver observer) {
        return new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new PagedNotificationHandler<User>(observer));
    }



    public void getFollowers(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, FollowersPresenter.GetFollowersObserver getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new PagedNotificationHandler<User>(getFollowersObserver));

        BackgroundTaskUtils.runTask(getFollowersTask);
    }


    public void GetFollowTask(AuthToken currUserAuthToken, User user, MainActivityPresenter.FollowPresenter.FollowObserver followObserver) {
        FollowTask followTask = new FollowTask(currUserAuthToken,
                user, new SimpleNotificationHandler(followObserver));

        BackgroundTaskUtils.runTask(followTask);
    }

    public void GetUnFollowTask(AuthToken currUserAuthToken, User user, MainActivityPresenter.UnFollowPresenter.UnFollowObserver unFollowObserver) {
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                user, new SimpleNotificationHandler(unFollowObserver));

        BackgroundTaskUtils.runTask(unfollowTask);
    }

    public void GetIsFollowerTask(AuthToken currUserAuthToken, User currUser, User user, IsFollowerObserver isFollowerObserver) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken, currUser, user,
                new BoolNotificationHandler(isFollowerObserver));

        BackgroundTaskUtils.runTask(isFollowerTask);
    }

    public void GetGetFollowersCountTask(AuthToken currUserAuthToken, User user, GetFollowersCountPresenter.GetFollowersCountObserver getFollowersCountObserver) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(currUserAuthToken, user,
                new CountNotificationHandler(getFollowersCountObserver));
        executor.execute(followersCountTask);
    }


    public void GetGetFollowingCountTask(AuthToken currUserAuthToken, User user, GetFollowingCountPresenter.GetFolloweesCountObserver getFollowingCountObserver) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(currUserAuthToken,
                user, new CountNotificationHandler(getFollowingCountObserver));
        executor.execute(followingCountTask);

    }


}
