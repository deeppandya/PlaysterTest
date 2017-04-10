package mainpackage.playstertest.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import mainpackage.playstertest.R;
import mainpackage.playstertest.adapter.StoryListAdapter;
import mainpackage.playstertest.iviews.IMainView;
import mainpackage.playstertest.model.Story;
import mainpackage.playstertest.presenter.MainViewPresenter;
import mainpackage.playstertest.support.Scopes;
import mainpackage.playstertest.support.Utils;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class MainActivity extends AppCompatActivity implements IMainView {

    private RecyclerView storyListRecyclerView;
    private StoryListAdapter storyListAdapter;
    private MainViewPresenter mainViewPresenter;

    public ProgressDialog mProgressDialog;


    private final BroadcastReceiver mNetworkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean bInternetWorking = Utils.isNetworkAvailable(MainActivity.this);
            if (!bInternetWorking) {
                Utils.showNoInternetDialog(MainActivity.this);
            } else {
                Utils.closeNoInternetDialog();
                mainViewPresenter.getNewsStory();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPresenter = (MainViewPresenter) getSystemService(Scopes.MAINVIEW);
        mainViewPresenter.takeView(this);

        setViews();

        storyListAdapter = new StoryListAdapter(this, this);

    }

    private void setViews() {
        storyListRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        storyListRecyclerView.setLayoutManager(mLayoutManager);
        storyListRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewPresenter.dropView(this);
    }

    @Override
    protected void onStart() {
        registerReceiver(mNetworkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkChangeReceiver);
        hideProgressDialog();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setStoryListAdapter(List<Story> storyList) {
        if (storyListAdapter != null) {
            storyListAdapter.setStoryList(storyList);
            storyListRecyclerView.setAdapter(storyListAdapter);
        }
    }

    @Override
    public void showProgressDialog(String progressDialogTitle) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            SpannableString ss2 = new SpannableString(progressDialogTitle);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, ss2.length(), 0);
            mProgressDialog.setMessage(ss2);
        }

        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void openStoryInWebView(Story story) {
        if (Utils.isNetworkAvailable(MainActivity.this))
            new FinestWebView.Builder(getContext()).titleDefault(story.getTitle())
                    .show(story.getLink());
        else
            Utils.showNoInternetDialog(MainActivity.this);
    }

    @Override
    public Object getSystemService(@NonNull String name) {

        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), Scopes.MAINVIEW);

        if (activityScope == null) {
            activityScope = MortarScope.buildChild(getApplicationContext()) //
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(Scopes.MAINVIEW, new MainViewPresenter())
                    .build(Scopes.MAINVIEW);
        }

        return activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }
}
