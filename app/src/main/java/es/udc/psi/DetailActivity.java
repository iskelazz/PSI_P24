package es.udc.psi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    if(bundle != null){
        Article article = bundle.getParcelable(MainActivity.KEY_ARTICLE);
        Log.d("_TAG", article.toString());
    }

}
}
