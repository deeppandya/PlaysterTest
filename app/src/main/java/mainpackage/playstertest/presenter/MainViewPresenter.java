package mainpackage.playstertest.presenter;

import java.util.List;

import mainpackage.playstertest.R;
import mainpackage.playstertest.interactor.IMainViewInteractor;
import mainpackage.playstertest.interactor.MainViewInteractor;
import mainpackage.playstertest.iviews.IMainView;
import mainpackage.playstertest.listener.GetNewsFeedsFromWebsiteListener;
import mainpackage.playstertest.model.Story;
import mortar.Presenter;
import mortar.bundler.BundleService;

/**
 * Created by deeppandya on 2017-04-10.
 */

public class MainViewPresenter extends Presenter<IMainView> {

    private IMainViewInteractor mainViewIntegrator;

    public MainViewPresenter() {
        mainViewIntegrator=new MainViewInteractor();
    }

    @Override
    protected BundleService extractBundleService(IMainView view) {
        return BundleService.getBundleService(view.getContext());
    }

    public void getNewsStory(){
        GetNewsFeedsFromWebsiteListener getNewsFeedsFromWebsiteListener = new GetNewsFeedsFromWebsiteListener() {
            @Override
            public void beforeGetNewsFeedsFromWebsite() {
                getView().showProgressDialog(getView().getContext().getResources().getString(R.string.getting_news));
            }

            @Override
            public void afterGetNewsFeedsFromWebsite(List<Story> storyList) {
                getView().hideProgressDialog();
                setStoryListAdapter(storyList);
            }
        };

        mainViewIntegrator.getNewsStory(getNewsFeedsFromWebsiteListener);

    }

    private void setStoryListAdapter(List<Story> storyList) {
        getView().setStoryListAdapter(storyList);
    }

}
