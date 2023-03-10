package es.udc.psi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
    }

    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
    private static OnItemClickListener clickListener;
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    private final ArrayList<Article> mDataset;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tile_title_tv);
            view.setOnClickListener((View.OnClickListener) this);
        }
        public void bind(Article article) {
            title.setText(article.getTitle());
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
        mDataset.remove(pos);
        notifyItemRemoved(pos);
    }
}
