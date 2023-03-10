package es.udc.psi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import es.udc.psi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String KEY_ARTICLE = "article";
    private ActivityMainBinding binding;
    private ArticlesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttClean.setOnClickListener(this);
        binding.buttNew.setOnClickListener(this);

        ArrayList<Article> initialData = new ArrayList<>();
        for (int i=0; i< 10; i++)
            initialData.add(new Article(i));
        initRecycler(initialData);
    }

    private void initRecycler(ArrayList<Article> articles) {
        mAdapter = new ArticlesAdapter (articles);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.articlesRv.setLayoutManager(linearLayoutManager);
        binding.articlesRv.setAdapter(mAdapter);

        mAdapter.setClickListener(new ArticlesAdapter.OnItemClickListener()) {
            @Override
            public onClick
            Log.d("TAG", " item " + position, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(KEY_ARTICLE,mAdapter.getItemId(position));
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.buttClean){
            for (int i=mAdapter.getItemCount();i==0; i--)
                mAdapter.deleteItem(i);
        }
        if (v == binding.buttNew){
            for (int i=0;i<0; i++)
                mAdapter.addItem(new Article(i));
        }
    }
}