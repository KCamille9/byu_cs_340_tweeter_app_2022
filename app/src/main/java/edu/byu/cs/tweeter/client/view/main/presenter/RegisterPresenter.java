package edu.byu.cs.tweeter.client.view.main.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter implements UserService.RegisterObserver {

    private static final String LOG_TAG = "RegisterFragment";

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        void registerSuccessful(User user, AuthToken authToken);
        void registerUnsuccessful(String message);
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public RegisterPresenter(View view) {
        // An assertion would be better, but Android doesn't support Java assertions
        if(view == null) {
            throw new NullPointerException();
        }
        this.view = view;
    }

    public void validateRegistration(String firstName, String lastName, String alias,
                                     String password, Bitmap imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (imageToUpload == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }



    /**
     * Initiates the login process.
     *
     * @param username the user's username.
     * @param password the user's password.
     */
    public void initiateRegister(String firstName, String lastName, String username, String password,
        Bitmap image)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();

        // Intentionally, Use the java Base64 encoder so it is compatible with M4.
        String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

        UserService userService = new UserService();
        userService.register(firstName, lastName, username, password, imageBytesBase64, this);
    }

    @Override
    public void handleSuccess(User user, AuthToken authToken) {

        Cache.getInstance().setCurrUser(user);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        view.registerSuccessful(user, authToken);

    }

    @Override
    public void handleFailure(String message) {
        String errorMessage = "Failed to register: " + message;
        Log.e(LOG_TAG, errorMessage);
        view.registerUnsuccessful(errorMessage);
    }

    @Override
    public void handleException(Exception exception) {
        String errorMessage = "Failed to register because of exception: " + exception.getMessage();
        Log.e(LOG_TAG, errorMessage, exception);
        view.registerUnsuccessful(errorMessage);
    }
}
