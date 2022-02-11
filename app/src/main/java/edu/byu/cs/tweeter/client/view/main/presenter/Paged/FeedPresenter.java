package edu.byu.cs.tweeter.client.view.main.presenter.Paged;

import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedView;
import edu.byu.cs.tweeter.client.view.main.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status> {

    StatusService statusService;


    @Override
    protected void getItems(AuthToken authToken, User targetUser, int pageSize, Status lastItem) {
        statusService.getStory(authToken, targetUser, pageSize, lastItem, new FeedObserver() );
    }


    public FeedPresenter(PagedView<Status> view) {
        super(view);
        statusService = new StatusService();
    }



    public void executeUserTask(String userAlias) {
        getUser(userAlias);
    }

    public class FeedObserver extends GetItemsObserver
    {

        @Override
        public String getDescription() {
            return "feed";
        }
    }






}
