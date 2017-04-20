package pl.dupesko.dupesko.Model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;


@Parcel
@DatabaseTable(tableName = "music")
public class Song {

    @DatabaseField(columnName = "song_id", id = true)   @SerializedName("id") int id;
    @DatabaseField(columnName = "song_name")            @SerializedName("name") String title;
    @DatabaseField(columnName = "song_voteminus")       @SerializedName("voteminus") int downVote;
    @DatabaseField(columnName = "song_voteplus")        @SerializedName("voteplus") int upVote;
    @DatabaseField(columnName = "song_url")             @SerializedName("url") String URL;
    @DatabaseField(columnName = "song_views")           @SerializedName("views") int views;
    @DatabaseField(columnName = "song_category")        @SerializedName("cate") String category;

    public Song() {}

    public Song(int id, String title, String URL, int upVote, int downVote, String category, int views) {
        this.id = id;
        this.title = title;
        this.URL = URL;
        this.upVote = upVote;
        this.downVote = downVote;
        this.category = category;
        this.views = views;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        return URL.equals(song.URL);

    }

    @Override
    public int hashCode() {
        return URL.hashCode();
    }
}
