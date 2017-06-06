package station.around.aroundsatation.client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import station.around.aroundsatation.model.ListMovie;

/**
 * Created by keiga on 2017/06/05.
 */

public interface MovieClient {
    @GET("/api/v1/movies")
    Call<List<ListMovie>> listMovies();
}