package edu.byu.cs.tweeter.client.view.main.presenter.Authenticate;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.view.main.presenter.Authenticate.AuthenticatePresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.Authenticate.AuthenticateView;
import edu.byu.cs.tweeter.client.view.main.service.UserService;

public class RegisterPresenter extends AuthenticatePresenter {

    private static final String LOG_TAG = "RegisterFragment";



    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public RegisterPresenter(AuthenticateView view) {
        super(view);
        // An assertion would be better, but Android doesn't support Java assertions
        if(view == null) {
            throw new NullPointerException();
        }
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
        userService.register(firstName, lastName, username, password, imageBytesBase64, new RegisterObserver());
    }

    public class RegisterObserver extends AuthenticateObserver
    {

        @Override
        public String getDescription() {
            return "register";
        }
    }
}
