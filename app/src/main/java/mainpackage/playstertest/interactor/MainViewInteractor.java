package mainpackage.playstertest.interactor;

import java.util.List;

import mainpackage.playstertest.asynctasks.GetNewsFeedsFromWebsiteAsyncTask;
import mainpackage.playstertest.listener.GetNewsFeedsFromWebsiteListener;
import mainpackage.playstertest.model.Story;

/**
 * Created by deeppandya on 2017-04-10.
 */

public class MainViewInteractor implements IMainViewInteractor {

    @Override
    public void getNewsStory(GetNewsFeedsFromWebsiteListener getNewsFeedsFromWebsiteListener) {

        GetNewsFeedsFromWebsiteAsyncTask getNewsFeedsFromWebsiteAsyncTask = new GetNewsFeedsFromWebsiteAsyncTask(getNewsFeedsFromWebsiteListener);
        getNewsFeedsFromWebsiteAsyncTask.execute();
    }
}
