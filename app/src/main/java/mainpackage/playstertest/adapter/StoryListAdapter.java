package mainpackage.playstertest.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import mainpackage.playstertest.R;
import mainpackage.playstertest.iviews.IMainView;
import mainpackage.playstertest.model.Story;

/**
 * Created by deeppandya on 2017-04-09.
 */

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.StoryViewHolder> {

    private Context mContext;
    private List<Story> storyList;
    private IMainView mainView;

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        private TextView title, pubDate,author;
        private ImageView imgStory;
        private View mainView;

        private StoryViewHolder(View view) {
            super(view);

            mainView=view;

            title = (TextView) view.findViewById(R.id.txtTitle);
            pubDate = (TextView) view.findViewById(R.id.txtPubDate);
            author = (TextView) view.findViewById(R.id.txtAuthor);

            imgStory = (ImageView) view.findViewById(R.id.imgStory);
        }
    }


    public StoryListAdapter(Context mContext,IMainView mainView) {
        this.mContext = mContext;
        this.mainView=mainView;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_card, parent, false);

        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StoryViewHolder holder, int position) {
        final Story story = storyList.get(position);

        holder.title.setText(holder.title.getText()+" "+ story.getTitle());
        holder.pubDate.setText(holder.pubDate.getText()+" "+ story.getPubDate());
        holder.author.setText(holder.author.getText() +" "+story.getAuthor());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Glide.with(mContext).load(story.getImgUrl()).placeholder(mContext.getDrawable(R.mipmap.playster_logo)).into(holder.imgStory);
        }else{
            Glide.with(mContext).load(story.getImgUrl()).into(holder.imgStory);
        }

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.openStoryInWebView(story);
            }
        });

    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public void setStoryList(List<Story> storyList){
        this.storyList=storyList;
    }

}
