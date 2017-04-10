package mainpackage.playstertest.listener;

import java.util.List;

import mainpackage.playstertest.model.Story;

/**
 * Created by deeppandya on 2017-04-09.
 */

public interface GetNewsFeedsFromWebsiteListener {

    void beforeGetNewsFeedsFromWebsite();
    void afterGetNewsFeedsFromWebsite(List<Story> storyList);

}
