package ujjwal.gl.choice.tmdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ujjwal.gl.choice.model.MovieResponce;

public interface TMDbApi {

    @GET("movie/popular")
    Call<MovieResponce> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/upcoming")
    Call<MovieResponce> getLatestMovies(
            @Query("api_key") String apiKey
    );
}