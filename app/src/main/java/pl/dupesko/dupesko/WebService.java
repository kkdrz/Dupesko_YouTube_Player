package pl.dupesko.dupesko;

import java.util.ArrayList;
import pl.dupesko.dupesko.Model.Song;
import pl.dupesko.dupesko.Model.SongCategory;
import retrofit.Callback;
import retrofit.http.GET;

public interface WebService {

    @GET("/music/")
    void getMusicList(Callback<ArrayList<Song>> response);

    @GET("/categories/")
    void getCategoriesList(Callback<ArrayList<SongCategory>> response);
}
