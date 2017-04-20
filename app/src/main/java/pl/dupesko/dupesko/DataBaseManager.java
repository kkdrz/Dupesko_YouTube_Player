package pl.dupesko.dupesko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import pl.dupesko.dupesko.Model.Playlist;
import pl.dupesko.dupesko.Model.Song;

public class DataBaseManager extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;

    private Dao<Song, Integer> musicDao;
    private Dao<Playlist, Integer> playlistDao;
    private DataBaseManager dataBaseManager = null;
    private Context context;

    public DataBaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION, R.raw.ormlite_config);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Song.class);
            TableUtils.createTable(connectionSource, Playlist.class);
        } catch (SQLException e) {
            Log.e(DataBaseManager.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Song.class, true);
            TableUtils.dropTable(connectionSource, Playlist.class, true);
            onCreate(sqLiteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DataBaseManager.class.getName(), "Unable to upgrade database from version " + i + " to new "
                    + i1, e);
        }
    }

    public Dao<Song, Integer> getMusicDao() throws SQLException {
        if (musicDao == null) {
            musicDao = getDao(Song.class);
        }
        return musicDao;
    }

    public Dao<Playlist, Integer> getPlaylistDao() throws SQLException {
        if (playlistDao == null) {
            playlistDao = getDao(Playlist.class);
        }
        return playlistDao;
    }

    private DataBaseManager getDataBaseManager() {
        if (dataBaseManager == null) {
            dataBaseManager = OpenHelperManager.getHelper(context, DataBaseManager.class);
        }
        return dataBaseManager;
    }

    public void addSong(Song song) {
        try {
            final Dao<Song, Integer> speakerDao = getDataBaseManager().getMusicDao();
            speakerDao.create(song);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlaylist(Playlist playlist) {
        try {
            final Dao<Playlist, Integer> speakerDao = getDataBaseManager().getPlaylistDao();
            speakerDao.create(playlist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSong(Song song) {
        try {
            final Dao<Song, Integer> songDao = getDataBaseManager().getMusicDao();
            DeleteBuilder<Song,Integer> db = songDao.deleteBuilder();
            db.where().eq("song_url", song.getURL());
            db.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
