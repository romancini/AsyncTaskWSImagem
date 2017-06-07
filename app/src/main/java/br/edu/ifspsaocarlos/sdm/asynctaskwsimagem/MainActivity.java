package br.edu.ifspsaocarlos.sdm.asynctaskwsimagem;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button btAcessarWs;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAcessarWs = (Button)findViewById(R.id.bt_acessar_ws);
        btAcessarWs.setOnClickListener(this);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == btAcessarWs){
            buscarImagem("http://www.globelink.co.uk/images/android.jpg");
        }
    }

    private void buscarImagem(String url) {
        AsyncTask<String, Integer, Bitmap> tarefa = new AsyncTask<String, Integer, Bitmap>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                URL url;
                HttpURLConnection urlConnection;
                Bitmap imagem = null;
                try {
                    url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        imagem = BitmapFactory.decodeStream(in);
                    } else {
                        Toast.makeText(getBaseContext(), "Erro ao carregar imagem",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return imagem;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                progressBar.setVisibility(View.INVISIBLE);
                ImageView ivImagem = (ImageView)findViewById(R.id.iv_imagem);
                ivImagem.setImageBitmap(bitmap);
            }
        };
        tarefa.execute(url);
    }
}
