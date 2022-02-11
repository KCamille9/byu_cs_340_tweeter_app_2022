package edu.byu.cs.tweeter.client.view.main.presenter.Paged;

import java.util.List;

import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.model.domain.User;

public interface PagedView<T> extends BaseView {
    void setLoading(boolean isLoading);
    void addItems(List<T> items);
    void navigateToUser(User user);
}
