package edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.AuthenticatedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthenticatedTask {
    private static final String LOG_TAG = "LogoutTask";


    public LogoutTask(AuthToken authToken, Handler messageHandler) {
        super(messageHandler, authToken);
    }
    

    @Override
    protected void processTask() {
        //nothing here
    }


    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        Message msg = Message.obtain();
        msg.setData(msgBundle);
    }
}
