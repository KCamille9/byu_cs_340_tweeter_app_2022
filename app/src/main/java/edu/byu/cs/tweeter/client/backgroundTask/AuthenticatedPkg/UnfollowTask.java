package edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.AuthenticatedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AuthenticatedTask {
    private static final String LOG_TAG = "UnfollowTask";

    /**
     * The user that is being followed.
     */
    private User followee;


    public UnfollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(messageHandler, authToken);
        this.followee = followee;
    }


    @Override
    protected void processTask() {
        //nothing here?
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        Message msg = Message.obtain();
        msg.setData(msgBundle);
    }
}
