package edu.byu.cs.tweeter.client.view.main.presenter.Authenticate;

import edu.byu.cs.tweeter.client.view.main.presenter.Authenticate.AuthenticatePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.Authenticate.AuthenticateView;
import edu.byu.cs.tweeter.client.view.main.service.UserService;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter extends AuthenticatePresenter {

    private static final String LOG_TAG = "LoginPresenter";



    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LoginPresenter(AuthenticateView view) {
        super(view);
        // An assertion would be better, but Android doesn't support Java assertions
        if(view == null) {
            throw new NullPointerException();
        }
//        this.view = view;
    }

    public void validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    /**
     * Initiates the login process.
     *
     * @param username the user's username.
     * @param password the user's password.
     */
    public void initiateLogin(String username, String password) {
        UserService userService = new UserService();
        userService.login(username, password, new LoginObserver());
    }



    public class LoginObserver extends AuthenticateObserver
    {

        @Override
        public String getDescription() {
            return "login";
        }
    }


}
