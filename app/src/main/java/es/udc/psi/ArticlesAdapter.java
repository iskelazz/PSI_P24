package es.udc.psi;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> implements Parcelable {

    protected ArticlesAdapter(Parcel in) {
        mDataset = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Creator<ArticlesAdapter> CREATOR = new Creator<ArticlesAdapter>() {
        @Override
        public ArticlesAdapter createFromParcel(Parcel in) {
            return new ArticlesAdapter(in);
        }

        @Override
        public ArticlesAdapter[] newArray(int size) {
            return new ArticlesAdapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedList(mDataset);
    }

    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
    private static OnItemClickListener clickListener;
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    private final ArrayList<Article> mDataset;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView subtitle;
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tile_title_tv);
            subtitle = view.findViewById(R.id.tile_subtitle_tv);
            view.setOnClickListener(this);
        }

        public void bind(Article article) {
            title.setText(article.getTitle());
            subtitle.setText(article.getSubTitle());
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    public ArticlesAdapter(ArrayList<Article> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_tile, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mDataset.get(position));
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(Article article) {
        mDataset.add(article);
        notifyItemInserted(mDataset.size()-1);
    }

    public void deleteItem(int pos){
        mDataset.remove(pos-1);
        notifyItemRemoved(pos-1);
    }

    public void updateItem(int position, Article article){
        mDataset.set(position,article);
        notifyDataSetChanged();
    }
    public Article getItem(int position){
        return mDataset.get(position);
    }
}
