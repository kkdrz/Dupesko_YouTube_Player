package pl.dupesko.dupesko;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.dupesko.dupesko.Config.YouTubeConfig;
import pl.dupesko.dupesko.Model.Playlist;
import pl.dupesko.dupesko.Model.Song;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private static final int SELECTED_CARD_INDEX = 0;
    private static String YOUTUBE_IMAGES = "https://img.youtube.com/vi/"; //<youtube-video-id-here>/0.jpg (or 1.jpg and so on)
    private PlaylistListener mPlaylistListener;
    private ArrayList<Song> mMusicList;
    private String mPlayingPlaylistName;
    private String mDisplayedPlaylistName;
    private Context mContext;
    private int mSelectedCardIndex;

    static class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.song_title)      TextView mSongTitle;
        @BindView(R.id.song_category)   TextView mSongCategory;
        @BindView(R.id.row_card)        CardView mCardView;
        @BindView(R.id.thumbnail)       ImageView mThumbnail;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public MusicListAdapter(Context context, Playlist myDataSet, PlaylistListener playlistListener) {
        mMusicList = new ArrayList<>(myDataSet.getMusicList());
        mPlayingPlaylistName = mDisplayedPlaylistName = myDataSet.getName();
        mPlaylistListener = playlistListener;
        mContext = context;
        mSelectedCardIndex = SELECTED_CARD_INDEX;

    }
    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_song, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Song currentSong = mMusicList.get(position);
        holder.mSongTitle.setText(currentSong.getTitle());
        holder.mSongCategory.setText(currentSong.getCategory());

        if (mDisplayedPlaylistName.equals(mPlayingPlaylistName))
            holder.mCardView.setSelected(position == mSelectedCardIndex);
        else
            holder.mCardView.setSelected(false);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlaylistListener.onVideoClicked(currentSong, mDisplayedPlaylistName);
                mPlayingPlaylistName = mDisplayedPlaylistName;
                mSelectedCardIndex = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        Glide
                .with(mContext)
                .load(YOUTUBE_IMAGES + currentSong.getURL() + "/1.jpg")
                .override(300, 200)
                .crossFade()
                .into(holder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    public interface PlaylistListener {

        void onVideoClicked(Song href, String playlistName);

    }
    public void swapData(ArrayList<Song> songs, String playlistName){
        mDisplayedPlaylistName = playlistName;
        mMusicList.clear();
        mMusicList.addAll(songs);
        notifyDataSetChanged();
    }

    public String getDisplayedPlaylistName() { return mDisplayedPlaylistName;}

    public void setSelectedCard (String playlistName, int songIndex) {
        mPlayingPlaylistName = playlistName;
        mSelectedCardIndex = songIndex;
        notifyDataSetChanged();
    }
}
