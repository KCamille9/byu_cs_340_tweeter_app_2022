package edu.byu.cs.tweeter.client.Handlers;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.Observers.PagedNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.AuthenticatedPkg.PagedTasks.PagedTask;
import edu.byu.cs.tweeter.client.view.main.service.BackgroundTask;
import edu.byu.cs.tweeter.client.view.main.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public class PagedNotificationHandler<T> extends BackgroundTaskHandler<PagedNotificationObserver> {

    public PagedNotificationHandler(PagedNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, PagedNotificationObserver observer) {
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);

        observer.handleSuccess(items, hasMorePages);
    }


}
