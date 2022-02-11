package edu.byu.cs.tweeter.client.view.main.presenter.Paged;

import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedPresenter;
import edu.byu.cs.tweeter.client.view.main.presenter.Paged.PagedView;
import edu.byu.cs.tweeter.client.view.main.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> {



    private StatusService statusService;


    public StoryPresenter(PagedView<Status> view) {
        super(view);
        statusService = new StatusService();
    }


    @Override
    protected void getItems(AuthToken authToken, User targetUser, int pageSize, Status lastItem) {
        statusService.getStory(authToken, targetUser, pageSize, lastItem, new StoryObserver() );
    }



    public void executeUserTask(String userAlias) {
        getUser(userAlias);
    }

    public class StoryObserver extends GetItemsObserver
    {

        @Override
        public String getDescription() {
            return "story";
        }
    }










}
