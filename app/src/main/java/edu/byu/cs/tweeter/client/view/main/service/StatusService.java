package edu.byu.cs.tweeter.client.view.main.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.PagedTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PostStatusTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {



    public interface GetUserObserver {
        void handleSuccess(User user);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface GetStoryObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface PostStatusObserver {
        void handleSuccess();
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface GetFeedObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }



    public void GetUserTask(AuthToken currUserAuthToken, String userAlias, GetUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
                userAlias, new GetUserHandler(getUserObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }

    public void getStory(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, GetStoryObserver getStoryObserver) {
        GetStoryTask getStoryTask = new GetStoryTask(currUserAuthToken,
                user, pageSize, lastStatus, new GetStoryHandler(getStoryObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getStoryTask);
    }

    public void GetPostStatusTask(AuthToken currUserAuthToken, Status status, PostStatusObserver postStatusObserver) {
        PostStatusTask statusTask = new PostStatusTask(currUserAuthToken, status, new PostStatusHandler(postStatusObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }

    public void GetGetFeedTask(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, GetFeedObserver getFeedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(currUserAuthToken, user, pageSize, lastStatus, new GetFeedHandler(getFeedObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);

    }



    /**
     * Message handler (i.e., observer) for GetUserTask.
     */
    private class GetUserHandler extends Handler {

        private GetUserObserver observer;

        public GetUserHandler(GetUserObserver observer){
            this.observer = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
            if (success) {
                User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
                observer.handleSuccess(user);

            } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
                observer.handleFailure(message);

            } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
                observer.handleException(ex);

            }
        }
    }

    /**
     * Message handler (i.e., observer) for GetStoryTask.
     */
    private class GetStoryHandler extends Handler {

        private GetStoryObserver observer;

        public GetStoryHandler(GetStoryObserver observer)
        {
            this.observer = observer;
        }


        @Override
        public void handleMessage(@NonNull Message msg) {

            boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);

                observer.handleSuccess(statuses, hasMorePages);
            } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
                observer.handleFailure(message);
            } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
                observer.handleException(ex);
            }
        }
    }

    private class PostStatusHandler extends Handler {

        private PostStatusObserver observer;

        public PostStatusHandler(PostStatusObserver observer)
        {
            this.observer = observer;
        }


        @Override
        public void handleMessage(@NonNull Message msg) {

            boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
            if (success) {

                observer.handleSuccess();
            } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
                observer.handleFailure(message);
            } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
                observer.handleException(ex);
            }
        }
    }

    private class GetFeedHandler extends Handler {

        private GetFeedObserver observer;

        public GetFeedHandler(GetFeedObserver observer)
        {
            this.observer = observer;
        }


        @Override
        public void handleMessage(@NonNull Message msg) {

            boolean success = msg.getData().getBoolean(GetFeedTask.SUCCESS_KEY);
            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
                observer.handleSuccess(statuses, hasMorePages);
            } else if (msg.getData().containsKey(GetFeedTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
                observer.handleFailure(message);
            } else if (msg.getData().containsKey(GetFeedTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
                observer.handleException(ex);
            }
        }
    }



}
