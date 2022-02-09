package edu.byu.cs.tweeter.client.backgroundTask.AuthenticatePkg;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.client.view.main.service.BackgroundTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class AuthenticateTask extends BackgroundTask {

    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    private String username;
    /**
     * The user's password.
     */
    private String password;

    User userInApp;
    AuthToken authToken;


    protected AuthenticateTask(Handler messageHandler, String username, String password) {
        super(messageHandler);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void processTask() {
        Pair<User, AuthToken> registerResult = executeTask();

        userInApp = registerResult.getFirst();
        authToken = registerResult.getSecond();
    }

    private Pair<User, AuthToken> executeTask() {
        userInApp = getFakeData().getFirstUser();
        authToken = getFakeData().getAuthToken();
        return new Pair<>(userInApp, authToken);
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, userInApp);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }
}
