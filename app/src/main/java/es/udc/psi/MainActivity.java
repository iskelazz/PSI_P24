package es.udc.psi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import es.udc.psi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String KEY_ARTICLE = "article";
    public static String KEY_POSITION = "position";
    private static final int REQUEST_CODE_DELETE_ARTICLE = 1;
    private static final int REQUEST_CODE_OK_ARTICLE = 2;
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

        mAdapter.setClickListener((view, position) -> {
            Log.d("TAG", " item " + position);

            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(KEY_ARTICLE,mAdapter.getItem(position));
            intent.putExtra(KEY_POSITION, position);
            startActivityForResult(intent, REQUEST_CODE_OK_ARTICLE);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OK_ARTICLE && resultCode == RESULT_OK) {
            int position = data.getIntExtra(KEY_POSITION, -1);
            Article editedArticle = data.getParcelableExtra(KEY_ARTICLE);
            if (editedArticle == null){
            mAdapter.deleteItem(position+1);
            Toast.makeText(this, "Artículo eliminado", Toast.LENGTH_SHORT).show();
        } else {
                mAdapter.updateItem(position, editedArticle);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.buttClean){
            for (int i=mAdapter.getItemCount();i>0; i--)
                mAdapter.deleteItem(i);
        }
        if (v == binding.buttNew){
            // Crea un diálogo personalizado
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Numero de campos");
            builder.setMessage("Cuantos campos nuevos se agregaran?");

            // Agrega un campo de texto para que el usuario ingrese su número
            final EditText editText = new EditText(MainActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(editText);

            // Agrega los botones Aceptar y Cancelar
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String numeroString = editText.getText().toString();
                    int numeroDeCampos = Integer.parseInt(numeroString);
                    for (int i=0;i<numeroDeCampos; i++)
                        mAdapter.addItem(new Article(i));
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            // Muestra el diálogo
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        }

}