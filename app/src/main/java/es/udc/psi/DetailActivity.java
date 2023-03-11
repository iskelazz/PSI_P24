package es.udc.psi;

import static es.udc.psi.MainActivity.KEY_ARTICLE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import es.udc.psi.databinding.ActivityDetailBinding;
import es.udc.psi.databinding.ActivityMainBinding;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityDetailBinding binding;
    private Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnDelete.setOnClickListener(this);
        binding.btnOk.setOnClickListener(this);
        binding.btnEdit.setOnClickListener(v -> openEditDialog());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        article = bundle.getParcelable(KEY_ARTICLE);
        binding.tvTitleDetail.setText(article.getTitle());
        binding.tvSubtitleDetail.setText(article.getSubTitle());
        binding.tvDescriptionDetail.setText(article.getDescription());

}

    @Override
    public void onClick(View v) {
        if (v == binding.btnDelete) {
            int position = getIntent().getIntExtra(MainActivity.KEY_POSITION, -1);
            Intent data = new Intent();
            data.putExtra(MainActivity.KEY_POSITION, position);
            setResult(RESULT_OK, data);
            finish();
        }
        if (v == binding.btnOk) {
            int position = getIntent().getIntExtra(MainActivity.KEY_POSITION, -1);
            Intent data = new Intent();
            data.putExtra(MainActivity.KEY_POSITION, position);
            data.putExtra(MainActivity.KEY_ARTICLE, article);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void openEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar artículo");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit, null);
        builder.setView(view);

        EditText titleEditText = view.findViewById(R.id.edit_title);
        EditText subtitleEditText = view.findViewById(R.id.edit_subtitle);
        EditText descriptionEditText = view.findViewById(R.id.edit_description);

        titleEditText.setText(article.getTitle());
        subtitleEditText.setText(article.getSubTitle());
        descriptionEditText.setText(article.getDescription());

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTitle = titleEditText.getText().toString();
                String newSubtitle = subtitleEditText.getText().toString();
                String newDescription = descriptionEditText.getText().toString();

                article.setTitle(newTitle);
                article.setSubTitle(newSubtitle);
                article.setDescription(newDescription);

                binding.tvTitleDetail.setText(article.getTitle());
                binding.tvSubtitleDetail.setText(article.getSubTitle());
                binding.tvDescriptionDetail.setText(article.getDescription());

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
