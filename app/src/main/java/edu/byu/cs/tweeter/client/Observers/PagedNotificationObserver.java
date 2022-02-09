package edu.byu.cs.tweeter.client.Observers;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface PagedNotificationObserver<T> extends ServiceObserver{
    void handleSuccess(List<T> items, boolean hasMorePages);
}
