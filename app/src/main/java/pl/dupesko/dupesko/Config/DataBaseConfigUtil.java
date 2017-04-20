package pl.dupesko.dupesko.Config;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import pl.dupesko.dupesko.Model.Playlist;
import pl.dupesko.dupesko.Model.Song;

public class DataBaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {Song.class, Playlist.class};

    public static void main(String[] args) throws IOException, SQLException {
        String currDirectory = "user.dir";
        String configPath = "/app/src/main/res/raw/ormlite_config.txt";
        String projectRoot = System.getProperty(currDirectory);
        String fullConfigPath = projectRoot + configPath;

        File configFile = new File(fullConfigPath);

        if(configFile.exists()) {
            configFile.delete();
            configFile = new File(fullConfigPath);
        }

        writeConfigFile(configFile, classes);
    }
}
