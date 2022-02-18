package edu.byu.cs.tweeter.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.ParseException;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.main.presenter.MainActivityPresenter;
import edu.byu.cs.tweeter.client.view.main.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenterUnitTest {

    private MainActivityPresenter.View mockView;
    private StatusService mockStatusService;

    private MainActivityPresenter mainActivityPresenterSpy;

    private AuthToken mockAuthToken;

    private Cache mockCache;
    private User mockUser;

    @Before
    public void setUp(){
        // Create mocks
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockAuthToken = Mockito.mock(AuthToken.class);
        mockCache = Mockito.mock(Cache.class);
        mockUser = Mockito.mock(User.class);

        mainActivityPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView));
        Mockito.when(mainActivityPresenterSpy.getStatusService()).thenReturn(mockStatusService);

        Cache.setInstance(mockCache);
        mockCache.setCurrUser(mockUser);

        Mockito.when(mockCache.getCurrUserAuthToken()).thenReturn(mockAuthToken);

    }

    public void assertRightType(InvocationOnMock invocation)
    {
        Assert.assertEquals(invocation.getArgument(0).getClass(), AuthToken.class);
        Assert.assertEquals(invocation.getArgument(1).getClass(), Status.class);
        Assert.assertEquals(invocation.getArgument(2).getClass(), MainActivityPresenter.PostStatusObserver.class);

        // Assert if right string
        Status status = invocation.getArgument(1);
        Assert.assertEquals("This is a test post", status.post);
    }

    public void useMockito(Answer<Void> answer) throws ParseException {
        String mPost = "This is a test post";

        Mockito.doAnswer(answer).when(mockStatusService).GetPostStatusTask(Mockito.any(), Mockito.any(), Mockito.any());

        mainActivityPresenterSpy.executePostStatusTask(mPost);
    }


    @Test
    public void testPostStatus_postStatusSuccessful() throws ParseException {

        // This thing gets called when the spy calls the method :v

        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                assertRightType(invocation);

                MainActivityPresenter.PostStatusObserver postStatusObserver = invocation.getArgument(2, MainActivityPresenter.PostStatusObserver.class);
                postStatusObserver.handleSuccess();

                return null;
            }
        };

        useMockito(answer);

        // Verify the method in view gets called
        Mockito.verify(mockView).postStatusSuccess();
    }

    @Test
    public void testPostStatus_postStatusFailedWithMessage() throws ParseException {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                assertRightType(invocation);

                MainActivityPresenter.PostStatusObserver postStatusObserver = invocation.getArgument(2, MainActivityPresenter.PostStatusObserver.class);
                postStatusObserver.handleFailure("the error message");

                return null;
            }
        };

        useMockito(answer);

        // Verify the method in view gets called
        Mockito.verify(mockView).displayErrorMessage("Failed to post status: " + "the error message");
    }

    @Test
    public void testPostStatus_postStatusFailedWithException() throws ParseException {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                assertRightType(invocation);

                MainActivityPresenter.PostStatusObserver postStatusObserver = invocation.getArgument(2, MainActivityPresenter.PostStatusObserver.class);
                postStatusObserver.handleException(new Exception("the error message"));

                return null;
            }
        };

        useMockito(answer);

        // Verify the method in view gets called
        Mockito.verify(mockView).displayErrorMessage("Failed to post status because of exception: "
                + "the error message");
    }
}
