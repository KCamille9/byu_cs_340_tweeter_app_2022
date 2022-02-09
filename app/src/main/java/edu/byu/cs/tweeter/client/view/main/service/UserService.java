package edu.byu.cs.tweeter.client.view.main.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.Handlers.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.Handlers.UserAuthNotificationHandler;
import edu.byu.cs.tweeter.client.Observers.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.Observers.UserAuthNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatePkg.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatePkg.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Contains the business logic to support the login operation.
 */
public class UserService {


    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface LoginObserver extends UserAuthNotificationObserver {

    }

    public interface RegisterObserver extends UserAuthNotificationObserver {

    }

    public interface LogOutObserver extends SimpleNotificationObserver {

    }


    /**
     * Creates an instance.
     */
    public UserService() {
    }

    /**
     * Makes an asynchronous login request.
     *
     * @param username the user's name.
     * @param password the user's password.
     */
    public void login(String username, String password, LoginObserver observer) {
        LoginTask loginTask = getLoginTask(username, password, observer);
        BackgroundTaskUtils.runTask(loginTask);
    }



    public void register(String firstName, String lastName, String username, String password,
          String image, RegisterObserver observer) {

        RegisterTask registerTask = getRegisterTask(firstName, lastName, username, password, image, observer);
        BackgroundTaskUtils.runTask(registerTask);
    }


    public void GetLogOutTask(AuthToken currUserAuthToken, LogOutObserver logOutObserver) {
        LogoutTask logoutTask = new LogoutTask(currUserAuthToken, new SimpleNotificationHandler(logOutObserver));
        BackgroundTaskUtils.runTask(logoutTask);
    }


    /**
     * Returns an instance of {@link LoginTask}. Allows mocking of the LoginTask class for
     * testing purposes. All usages of LoginTask should get their instance from this method to
     * allow for proper mocking.
     *
     * @return the instance.
     */
    LoginTask getLoginTask(String username, String password, LoginObserver observer) {
        return new LoginTask(username, password, new UserAuthNotificationHandler(observer));
    }




    RegisterTask getRegisterTask(String firstName, String lastName, String username, String password,
                                 String image, RegisterObserver observer) {
        return new RegisterTask(firstName, lastName ,username, password, image, new UserAuthNotificationHandler(observer));
    }


}
