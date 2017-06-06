package station.around.aroundsatation.model;

/**
 * Created by keiga on 2017/06/01.
 */

import java.util.ArrayList;
import java.util.List;

public class ListImage {
    public String en_title;
    public String ja_title;
    public String en_content;
    public String ja_content;
    public Url url;


    public List<String> image;

    public List<String> getListImages() {
        return image;
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

    public Url getUrl() {
        return url;
    }

    public void setListImages(List<String> image) {
        this.image = image;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }


}
