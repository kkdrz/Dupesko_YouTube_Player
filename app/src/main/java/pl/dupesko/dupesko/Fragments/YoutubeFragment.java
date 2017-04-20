package pl.dupesko.dupesko.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.dupesko.dupesko.Config.YouTubeConfig;
import pl.dupesko.dupesko.Model.Playlist;
import pl.dupesko.dupesko.Model.Song;
import pl.dupesko.dupesko.R;

public class YoutubeFragment extends Fragment implements YouTubePlayer.OnInitializedListener    {

    private YouTubePlayer mYouTubePlayer;
    private NavBarListener mNavBarListener;
    private int mCurrentSongIndex = 0;
    private ArrayList<Song> mPlaylist;
    private String mPlaylistName;

    public static YoutubeFragment newInstance(@NonNull Playlist playlist, NavBarListener mNavBarListener) {
        YoutubeFragment fragment = new YoutubeFragment();
        Bundle args = new Bundle();
        args.putParcelable("PLAYLIST", Parcels.wrap(playlist));
        fragment.setArguments(args);
        fragment.mNavBarListener = mNavBarListener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Playlist playlist = Parcels.unwrap(getArguments().getParcelable("PLAYLIST"));
        if (playlist != null) {
            mPlaylist = playlist.getMusicList();
            mPlaylistName = playlist.getName();
        } else throw new NullPointerException("Playlist is NULL");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_playing_fragment, container, false);
        ButterKnife.bind(this, view);
        YouTubePlayerSupportFragment youTubePlayerFragment =
                (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.youtube_player);
        youTubePlayerFragment.initialize(YouTubeConfig.API_KEY, this);

        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        mYouTubePlayer = youTubePlayer;
        mYouTubePlayer.setShowFullscreenButton(false);
        mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        mYouTubePlayer.setPlayerStateChangeListener(new MyPlayerStateChangeListener());
        mYouTubePlayer.loadVideo(mPlaylist.get(mCurrentSongIndex).getURL());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getContext(), youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
    }

    public void playSong(Song song) {
        mCurrentSongIndex = mPlaylist.indexOf(song);
        mYouTubePlayer.loadVideo(song.getURL());
    }

    public void playSong(int songIndex) {
        mCurrentSongIndex = songIndex;
        mYouTubePlayer.loadVideo(mPlaylist.get(mCurrentSongIndex).getURL());
    }

    public String getPlaylistName() {
        return mPlaylistName;
    }

    public void setPlaylist(Playlist playlist) {
        this.mPlaylist = playlist.getMusicList();
        this.mPlaylistName = playlist.getName();
    }

    public class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener{
        public MyPlayerStateChangeListener() {
            super();
        }
        @Override
        public void onLoading() {}
        @Override
        public void onLoaded(String s) {}
        @Override
        public void onAdStarted() {}
        @Override
        public void onVideoStarted() {}
        @Override
        public void onVideoEnded() {
            nextSong();
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {}
    }

    @OnClick(R.id.next_song_button)
    public void nextSong() {
        playSong(++mCurrentSongIndex%mPlaylist.size());
        mNavBarListener.onNavButtonClicked(mPlaylistName, mCurrentSongIndex);
    }

    @OnClick(R.id.prev_song_button)
    public void prevSong() {
        mCurrentSongIndex = --mCurrentSongIndex >= 0 ? mCurrentSongIndex : mPlaylist.size() - 1;
        playSong(mCurrentSongIndex);
        mNavBarListener.onNavButtonClicked(mPlaylistName, mCurrentSongIndex);
    }

    public void playOrPause() {
        if (mYouTubePlayer.isPlaying())
            mYouTubePlayer.pause();
        else
            mYouTubePlayer.play();
    }

    public interface NavBarListener {
        void onNavButtonClicked(String playlistName, int songIndex);
    }
}
