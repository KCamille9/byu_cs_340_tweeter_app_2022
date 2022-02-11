package edu.byu.cs.tweeter.client.view.main.presenter;

import edu.byu.cs.tweeter.client.view.main.presenter.Authenticate.AuthenticateView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount.FollowCountView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowState.FollowStateView;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedView;

public abstract class BasePresenter {

    protected BaseView baseView;

    public BasePresenter(BaseView baseView) {
        this.baseView = baseView;
    }

    public PagedView getPagedView()
    {
        return (PagedView) baseView;
    }

    public AuthenticateView getAuthenticateView()
    {
        return (AuthenticateView) baseView;
    }

    public FollowStateView getFollowStateView()
    {
        return (FollowStateView) baseView;
    }

    public FollowCountView getFollowCountView()
    {
        return (FollowCountView) baseView;
    }
}
