package pl.dupesko.dupesko.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
@DatabaseTable(tableName = "playlists")
public class Playlist   {
    @DatabaseField(columnName = "playlist_name", id = true)     String mName;
    @DatabaseField(columnName = "playlist_songs")               ArrayList<Song> mMusicList;

    public Playlist() {}

    public Playlist(String name, ArrayList<Song> musicList) {
        this.mName = name;
        mMusicList = musicList;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Song> getMusicList() {
        return mMusicList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Playlist playlist = (Playlist) o;

        return mName.equals(playlist.mName);

    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    @Override
    public String toString() {
        return mName;
    }

}
