package mainpackage.playstertest.iviews;

import android.content.Context;

import java.util.List;

import mainpackage.playstertest.model.Story;

/**
 * Created by deeppandya on 2017-04-10.
 */

public interface IMainView {
    Context getContext();
    void setStoryListAdapter(List<Story> storyList);
    void showProgressDialog(String progressDialogTitle);
    void hideProgressDialog();
    void openStoryInWebView(Story story);
}
