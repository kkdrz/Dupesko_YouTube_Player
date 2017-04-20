package pl.dupesko.dupesko.Model;

import org.parceler.Parcel;

@Parcel
public class SongCategory {
    String name;

    SongCategory() {}

    public SongCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
