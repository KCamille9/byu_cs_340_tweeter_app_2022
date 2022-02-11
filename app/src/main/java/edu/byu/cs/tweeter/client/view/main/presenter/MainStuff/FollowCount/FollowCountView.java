package edu.byu.cs.tweeter.client.view.main.presenter.MainStuff.FollowCount;

import edu.byu.cs.tweeter.client.view.main.presenter.BaseView;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityView;

public interface FollowCountView extends MainActivityView {
//    void countSuccessful(int count);

    void updateFollowerCount(int count);
}
