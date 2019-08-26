package ujjwal.gl.choice.callback;

import java.util.List;

import ujjwal.gl.choice.model.Movie;

public interface MovieCallback {
    void onSuccess(List<Movie> movies);

    void onError();
}
