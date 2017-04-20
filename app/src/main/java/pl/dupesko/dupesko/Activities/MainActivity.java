package pl.dupesko.dupesko.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.session.PlaybackState;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import butterknife.ButterKnife;
import pl.dupesko.dupesko.Fragments.PlaylistFragment;
import pl.dupesko.dupesko.Fragments.YoutubeFragment;
import pl.dupesko.dupesko.Model.Playlist;
import pl.dupesko.dupesko.Model.Song;
import pl.dupesko.dupesko.MusicListAdapter;
import pl.dupesko.dupesko.R;
import pl.dupesko.dupesko.WebService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements MusicListAdapter.PlaylistListener, YoutubeFragment.NavBarListener {

    private ArrayList<Song> musicList;
    private ArrayList<Playlist> playlistList;
    private YoutubeFragment mYoutubeFragment;
    private PlaylistFragment mPlaylistFragment;
    private WebService mWebService;
    private RestAdapter mRetrofit;
    private MusicListAdapter.PlaylistListener mPlaylistListener = this;
    private YoutubeFragment.NavBarListener mNavBarListener = this;

    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat mPlaybackStateCompat;
    private MediaControllerCompat mMediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeRetrofit();
        downloadDataFromWeb();

        mPlaybackStateCompat = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE)
                .setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0)
                .build();

        MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                Toast.makeText(getApplicationContext(), "ONPLAY", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPause() {
                super.onPause();
                Toast.makeText(getApplicationContext(), "ONPAUSE", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                Toast.makeText(getApplicationContext(), "NEXT", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                Toast.makeText(getApplicationContext(), "PREV", Toast.LENGTH_SHORT).show();

            }
        };

        mMediaSession = new MediaSessionCompat(this, "MEDIA_SESSION");
        mMediaSession.setPlaybackState(mPlaybackStateCompat);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |  MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setCallback(mMediaSessionCallback);
        mMediaSession.setActive(true);

        mMediaController = new MediaControllerCompat(this, mMediaSession);
    }


//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        int action = event.getAction();
//        int keyCode = event.getKeyCode();
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_VOLUME_UP:
//                if (action == KeyEvent.ACTION_DOWN) {
//                    mYoutubeFragment.nextSong();
//                }
//                return true;
//            case KeyEvent.KEYCODE_VOLUME_DOWN:
//                if (action == KeyEvent.ACTION_DOWN) {
//                    mYoutubeFragment.prevSong();
//                }
//                return true;
//            default:
//                return super.dispatchKeyEvent(event);
//        }
//    }

    public ArrayList<Playlist> samplePlaylist() {
        ArrayList<Playlist> list = new ArrayList<>();
        list.add(new Playlist("Wszystkie", new ArrayList<>(musicList)));
        list.add(new Playlist("Ulubione", new ArrayList<>(musicList.subList(50, 70))));
        list.add(new Playlist("Bieganie", new ArrayList<>(musicList.subList(5, 10))));
        list.add(new Playlist("Smutne", new ArrayList<>(musicList.subList(11, 20))));
        return list;
    }

    @Override
    public void onVideoClicked(Song song, String playlistName) {
        int newPlaylistIndex = playlistList.indexOf(new Playlist(playlistName, null));
        if(!mYoutubeFragment.getPlaylistName().equals(playlistName)) {
            mYoutubeFragment.setPlaylist(playlistList.get(newPlaylistIndex));
        }
        mYoutubeFragment.playSong(song);
    }

    private void downloadDataFromWeb() {

        mWebService.getMusicList(new Callback<ArrayList<Song>>() {
            @Override
            public void success(ArrayList<Song> songs, Response response) {
                musicList = songs;
                playlistList = samplePlaylist();
                mYoutubeFragment = YoutubeFragment.newInstance(playlistList.get(0), mNavBarListener);
                mPlaylistFragment = PlaylistFragment.newInstance(playlistList, mPlaylistListener);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.youtube_fragment_holder, mYoutubeFragment, "YOUTUBE_HOLDER");
                transaction.replace(R.id.playlist_fragment_holder, mPlaylistFragment, "PLAYLIST_HOLDER");
                transaction.commit();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Retrofit.getMusicList", error.getLocalizedMessage());
            }
        });

    }

    private void initializeRetrofit() {
        mRetrofit = new RestAdapter.Builder()
                .setEndpoint("http://api.dupesko.pl")
                .setLogLevel(RestAdapter.LogLevel.HEADERS )
                .build();

        mWebService = mRetrofit.create(WebService.class);
    }

    @Override
    public void onNavButtonClicked(String playlistName, int songIndex) {
        mPlaylistFragment.getMusicListAdapter().setSelectedCard(playlistName, songIndex);
    }

}
