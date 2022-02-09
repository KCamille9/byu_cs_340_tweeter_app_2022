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

//    @Override
//    protected void processTask() {
//        Pair<User, AuthToken> loginResult = doLogin();
//
//        loggedInUser = loginResult.getFirst();
//        authToken = loginResult.getSecond();
//    }


//    private Pair<User, AuthToken> doLogin() {
//        loggedInUser = getFakeData().getFirstUser();
//        authToken = getFakeData().getAuthToken();
//        return new Pair<>(loggedInUser, authToken);
//    }
//
//
//    @Override
//    protected void loadSuccessBundle(Bundle msgBundle) {
//        msgBundle.putSerializable(USER_KEY, loggedInUser);
//        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
//    }
}
