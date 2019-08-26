package ujjwal.gl.choice.tmdb;

import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ujjwal.gl.choice.R;
import ujjwal.gl.choice.callback.MovieCallback;
import ujjwal.gl.choice.constant.Constants;
import ujjwal.gl.choice.model.MovieResponce;

import static ujjwal.gl.choice.constant.Constants.BASE_URL_MOVIE;
import static ujjwal.gl.choice.constant.Constants.LANGUAGE;

public class MoviesRepository {

    private static MoviesRepository repository;

    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_MOVIE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    public void getMovies(final MovieCallback callback,String type) {
        if(type.equals("pop")) {
            api.getPopularMovies(Constants.API_KEY, LANGUAGE, 1)
                    .enqueue(new Callback<MovieResponce>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieResponce> call, @NonNull Response<MovieResponce> response) {
                            if (response.isSuccessful()) {
                                MovieResponce moviesResponse = response.body();
                                if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                    callback.onSuccess(moviesResponse.getMovies());
                                } else {
                                    callback.onError();
                                }
                            } else {
                                callback.onError();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponce> call, Throwable t) {
                            callback.onError();
                        }
                    });
        }else if(type.equals("new")){
            api.getLatestMovies(Constants.API_KEY)
                    .enqueue(new Callback<MovieResponce>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieResponce> call, @NonNull Response<MovieResponce> response) {
                            if (response.isSuccessful()) {
                                MovieResponce moviesResponse = response.body();
                                if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                    callback.onSuccess(moviesResponse.getMovies());
                                } else {
                                    callback.onError();
                                }
                            } else {
                                callback.onError();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponce> call, Throwable t) {
                            callback.onError();
                        }
                    });
        }
    }
}