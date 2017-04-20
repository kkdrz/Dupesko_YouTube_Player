package pl.dupesko.dupesko.Model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SongTest {

    private static final int SONG_ID = 10;
    private static final String SONG_TITLE = "title";
    private static final String SONG_URL = "url";
    private static final int SONG_UPVOTE = 2;
    private static final int SONG_DOWNVOTE = 5;
    private static final String SONG_CATEGORY = "category";
    private static final int SONG_VIEWS = 100;

    private Song song;

    @Before
    public void initSong() {
        song = new Song(SONG_ID, SONG_TITLE, SONG_URL, SONG_UPVOTE, SONG_DOWNVOTE, SONG_CATEGORY, SONG_VIEWS);
    }

    @Test
    public void hasID() {
        assertEquals("Wrong ID", SONG_ID, song.getId());
    }

    @Test
    public void hasTITLE() {
        assertEquals("Wrong Title", SONG_TITLE, song.getTitle());
    }

    @Test
    public void hasURL() {
        assertEquals("Wrong URL", SONG_URL, song.getURL());
    }

    @Test
    public void hasUPVOTE() {
        assertEquals("Wrong upVote", SONG_UPVOTE, song.getUpVote());
    }

    @Test
    public void hasDOWNVOTE() {
        assertEquals("Wrong downVote", SONG_DOWNVOTE, song.getDownVote());
    }

    @Test
    public void hasCATEGORY() {
        assertEquals("Wrong Category", SONG_CATEGORY, song.getCategory());
    }

    @Test
    public void hasVIEWS() {
        assertEquals("Wrong Views", SONG_VIEWS, song.getViews());
    }
}
