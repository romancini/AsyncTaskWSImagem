package br.edu.ifspsaocarlos.sdm.asynctaskwsimagem;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button btAcessarWs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAcessarWs = (Button)findViewById(R.id.bt_acessar_ws);
        btAcessarWs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btAcessarWs){
            buscarImagem("http://192.168.103.128/logo_ifsp.png");
        }
    }

    private void buscarImagem(String url) {
        AsyncTask<String, Void, Bitmap> tarefa = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                HttpGet httpGet = new HttpGet(params[0]);
                HttpClient httpClient = new DefaultHttpClient();
                Bitmap imagem = null;
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpResponse.getEntity());
                        InputStream inputStream = bufferedHttpEntity.getContent();
                        imagem = BitmapFactory.decodeStream(inputStream);
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return imagem;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                ImageView ivImagem = (ImageView)findViewById(R.id.iv_imagem);
                ivImagem.setImageBitmap(bitmap);
            }
        };
        tarefa.execute(url);
    }
}
