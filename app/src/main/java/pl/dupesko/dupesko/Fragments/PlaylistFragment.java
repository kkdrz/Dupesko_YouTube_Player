package pl.dupesko.dupesko.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.dupesko.dupesko.Model.Playlist;
import pl.dupesko.dupesko.Model.Song;
import pl.dupesko.dupesko.MusicListAdapter;
import pl.dupesko.dupesko.R;
import pl.dupesko.dupesko.SpinnerAdapter;

public class PlaylistFragment extends Fragment {
    @BindView(R.id.recyclerView_currentPlaylist)    RecyclerView mCurrentPlaylist;
    @BindView(R.id.playlist_spinner)                Spinner mPlaylistSpinner;
    private List<Playlist> mPlaylists;
    private MusicListAdapter.PlaylistListener mPlaylistListener;
    private int current_playlist = 0;
    private MusicListAdapter mMusicListAdapter;

    public static PlaylistFragment newInstance(ArrayList<Playlist> playlists, MusicListAdapter.PlaylistListener playlistListener) {
        Bundle args = new Bundle();
        args.putParcelable("PLAYLISTS", Parcels.wrap(playlists));
        PlaylistFragment fragment = new PlaylistFragment();
        fragment.mPlaylistListener = playlistListener;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlaylists = Parcels.unwrap(getArguments().getParcelable("PLAYLISTS"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playlist_fragment, container, false);
        ButterKnife.bind(this, view);
        mMusicListAdapter = new MusicListAdapter(getContext(), mPlaylists.get(current_playlist), mPlaylistListener);
        mCurrentPlaylist.setAdapter(mMusicListAdapter);
        mCurrentPlaylist.setLayoutManager(new LinearLayoutManager(getContext()));
        mCurrentPlaylist.setHasFixedSize(true);

        ArrayAdapter<Playlist> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mPlaylists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlaylistSpinner.setOnItemSelectedListener(new SpinnerListener());
        mPlaylistSpinner.setAdapter(spinnerAdapter);
        return view;
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Playlist clickedPlaylist = (Playlist) adapterView.getItemAtPosition(i);
            if (!clickedPlaylist.getName().equals(mMusicListAdapter.getDisplayedPlaylistName())) {
                mMusicListAdapter.swapData(clickedPlaylist.getMusicList(), clickedPlaylist.getName());
                Toast.makeText(getContext(), "Playlists just for tests", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    }

    public MusicListAdapter getMusicListAdapter() {
        return mMusicListAdapter;
    }

}
