package edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.CountTasks;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.AuthenticatedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class CountTask extends AuthenticatedTask {

    public static final String COUNT_KEY = "count";
    public static final int COUNT_NUM = 20;

    /**
     * The user whose following count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    protected CountTask(Handler messageHandler, AuthToken authToken, User targetUser) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    @Override
    protected void processTask() {
        //empty for now?
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(SUCCESS_KEY, true);
        msgBundle.putInt(COUNT_KEY, COUNT_NUM);
    }
}
