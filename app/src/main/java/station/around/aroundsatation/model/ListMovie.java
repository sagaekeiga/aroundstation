package station.around.aroundsatation.model;

import java.util.List;

/**
 * Created by keiga on 2017/06/05.
 */

public class ListMovie {
    public String en_title;
    public String ja_title;
    public String en_content;
    public String ja_content;
    public String present;
    public String destination;
    public Url url;


    public List<String> movie;

    public List<String> getListImages() {
        return movie;
    }

    public String getEn_title() {
        return en_title;
    }
    public String getJa_title() {
        return ja_title;
    }
    public String getEn_content() {
        return en_content;
    }
    public String getJa_content() {
        return ja_content;
    }
    public String getPresent() {
        return present;
    }
    public String getDestination() {
        return destination;
    }

    public Url getUrl() {
        return url;
    }

    public void setListMovies(List<String> image) {
        this.movie = movie;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }
}
