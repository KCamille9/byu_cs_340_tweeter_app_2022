package edu.byu.cs.tweeter.client.backgroundTask.AuthenticatePkg;

import android.os.Handler;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatePkg.AuthenticateTask;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {

    private static final String LOG_TAG = "LoginTask";


    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password);

    }

}
