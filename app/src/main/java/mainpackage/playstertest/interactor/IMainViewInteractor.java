package mainpackage.playstertest.interactor;

import java.util.List;

import mainpackage.playstertest.listener.GetNewsFeedsFromWebsiteListener;
import mainpackage.playstertest.model.Story;

/**
 * Created by deeppandya on 2017-04-10.
 */

public interface IMainViewInteractor {
    void getNewsStory(GetNewsFeedsFromWebsiteListener getNewsFeedsFromWebsiteListener);
}
