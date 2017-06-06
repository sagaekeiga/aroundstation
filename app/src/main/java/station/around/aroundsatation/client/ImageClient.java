package station.around.aroundsatation.client;

/**
 * Created by keiga on 2017/06/01.
 */
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import station.around.aroundsatation.model.ListImage;
import station.around.aroundsatation.model.ListMovie;

import java.util.List;

public interface ImageClient {
    @GET("/api/v1/images")
    Call <List<ListImage>> listImages();
}
