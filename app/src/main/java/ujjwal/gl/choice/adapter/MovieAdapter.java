package ujjwal.gl.choice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ujjwal.gl.choice.R;
import ujjwal.gl.choice.constant.Constants;
import ujjwal.gl.choice.model.Movie;
import ujjwal.gl.choice.preferences.Preference;

import static ujjwal.gl.choice.constant.Constants.BASE_URL_IMAGE;
import static ujjwal.gl.choice.constant.Constants.WATCH_LATER;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    Context mContext;
    List<Movie> mMovies;

    public MovieAdapter(Context mContext, List<Movie> mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_movie,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        CircleImageView poster;
        TextView releaseDate;
        TextView title;
        TextView rating;
        ImageButton favBtn;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.item_movie_poster);
            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            title = itemView.findViewById(R.id.item_movie_title);
            rating = itemView.findViewById(R.id.item_movie_rating);
            favBtn = itemView.findViewById(R.id.fav_btn);
        }

        public void bind(final Movie movie) {
            Glide.with(mContext).asBitmap().load(BASE_URL_IMAGE+movie.getPosterPath()).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    poster.setImageBitmap(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
            });
            releaseDate.setText(movie.getReleaseDate().split("-")[0]);
            title.setText(movie.getTitle());
            rating.setText("Rating : "+String.valueOf(movie.getRating()));
            if(WATCH_LATER.contains(movie.getTitle())){
                favBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_blue_24dp));
            }
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(WATCH_LATER.contains(movie.getTitle())){
                        favBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_border_blue_24dp));
                        WATCH_LATER.remove(movie.getTitle());
                        StringBuilder sb = new StringBuilder();
                        for(String s : WATCH_LATER){
                            sb.append(s).append("\n");
                        }

                        String s = sb.toString();

                        Toast.makeText(mContext, String.valueOf(WATCH_LATER.size()), Toast.LENGTH_SHORT).show();
                        Preference.setSharedPreference(mContext,"watch",s);
                    }else {
                        favBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_blue_24dp));
                        WATCH_LATER.add(movie.getTitle());
                        StringBuilder sb = new StringBuilder();
                        for(String s : WATCH_LATER){
                            sb.append(s).append("\n");
                        }
                        String s = sb.toString();

                        Toast.makeText(mContext, String.valueOf(WATCH_LATER.size()), Toast.LENGTH_SHORT).show();
                        Preference.setSharedPreference(mContext,"watch",s);
                    }
                }
            });

        }
    }
}
