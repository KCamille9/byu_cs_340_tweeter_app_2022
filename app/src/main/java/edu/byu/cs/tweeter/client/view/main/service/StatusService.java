package edu.byu.cs.tweeter.client.view.main.service;

import edu.byu.cs.tweeter.client.Handlers.PagedNotificationHandler;
import edu.byu.cs.tweeter.client.Handlers.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.Handlers.UserNotificationHandler;
import edu.byu.cs.tweeter.client.Observers.PagedNotificationObserver;
import edu.byu.cs.tweeter.client.Observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.Observers.UserNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PostStatusTask;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {



    public interface GetUserObserver extends UserNotificationObserver {

    }

    public interface GetStoryObserver extends PagedNotificationObserver<Status> {
    }

    public interface PostStatusObserver extends SimpleNotificationObserver {

    }

    public interface GetFeedObserver extends PagedNotificationObserver<Status>{

    }



    public void GetUserTask(AuthToken currUserAuthToken, String userAlias, GetUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
                userAlias, new UserNotificationHandler(getUserObserver));

        BackgroundTaskUtils.runTask(getUserTask);
    }

    public void getStory(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus,
                         PagedPresenter.GetItemsObserver getStoryObserver) {
        GetStoryTask getStoryTask = new GetStoryTask(currUserAuthToken,
                user, pageSize, lastStatus, new PagedNotificationHandler<Status>(getStoryObserver));

        BackgroundTaskUtils.runTask(getStoryTask);
    }

    public void GetPostStatusTask(AuthToken currUserAuthToken, Status status, PostStatusObserver postStatusObserver) {
        PostStatusTask statusTask = new PostStatusTask(currUserAuthToken, status,
                new SimpleNotificationHandler(postStatusObserver));

        BackgroundTaskUtils.runTask(statusTask);
    }

    public void GetGetFeedTask(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, GetFeedObserver getFeedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(currUserAuthToken, user, pageSize, lastStatus,
                new PagedNotificationHandler<Status>(getFeedObserver));

        BackgroundTaskUtils.runTask(getFeedTask);
    }


}
