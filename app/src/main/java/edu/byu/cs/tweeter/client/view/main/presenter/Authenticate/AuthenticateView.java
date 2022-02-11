package edu.byu.cs.tweeter.client.view.main.presenter.Authenticate;

import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticateView extends BaseView {

    void authenticateSuccessful(User user);
}
