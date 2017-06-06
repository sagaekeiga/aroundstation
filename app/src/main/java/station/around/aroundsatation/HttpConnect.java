package station.around.aroundsatation;

/**
 * Created by keiga on 2017/06/01.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import station.around.aroundsatation.client.ImageClient;
import station.around.aroundsatation.client.MovieClient;
import station.around.aroundsatation.model.ListImage;
import station.around.aroundsatation.model.ListMovie;
import station.around.aroundsatation.model.Url;

import java.util.List;


/*

APIからJSON配列データを取得します。

*/



public class HttpConnect {


    public ArrayList<String> lists = new ArrayList<String>();
    public ArrayList<ArrayList<String>> jsons = new ArrayList<ArrayList<String>>();

    private CallHttp.CallHttpCallbacks callback = null;

    // コンストラクタ
    public HttpConnect(CallHttp.CallHttpCallbacks callback) {
     this.callback = callback;
    }


    // ImageのJSONデータを取得
    public Call<List<ListImage>> passImages(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://candii.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ImageClient service = retrofit.create(ImageClient.class);


        Call<List<ListImage>> call2 = service.listImages();

        call2.enqueue(new Callback<List<ListImage>>() {
            @Override
            public void onResponse(Call<List<ListImage>> call, Response<List<ListImage>> response) {
                List<ListImage> listImage = response.body();
                int s = listImage.size();


                for(int i = 0; i < s; i++){
                    ArrayList<String> lists = new ArrayList<String>();
                    lists.add(listImage.get(i).getEn_title());
                    lists.add(listImage.get(i).getJa_title());
                    lists.add(listImage.get(i).getEn_content());
                    lists.add(listImage.get(i).getJa_content());
                    lists.add(listImage.get(i).getUrl().getUrl());
                    jsons.add(lists);
                }
                callback.callbackMethod(jsons);

            }

            @Override
            public void onFailure(Call<List<ListImage>> call, Throwable t) {
                Log.d("debug4", t.getMessage());
            }
        });
        return call2;
    }


    // MovieのJSONデータを取得
    public Call<List<ListMovie>> passMovies(final String present, final String destination){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://candii.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieClient service = retrofit.create(MovieClient.class);

        System.out.println("ポイント3:presentとdestinationの値あり");
        System.out.println("present");
        System.out.println(present);
        System.out.println("destination");
        System.out.println(destination);


        Call<List<ListMovie>> call2 = service.listMovies();

        call2.enqueue(new Callback<List<ListMovie>>() {
            @Override
            public void onResponse(Call<List<ListMovie>> call, Response<List<ListMovie>> response) {
                List<ListMovie> listMovie = response.body();
                int s = listMovie.size();


                for(int i = 0; i < s; i++){
                    ArrayList<String> lists = new ArrayList<String>();
                    lists.add(listMovie.get(i).getEn_title());
                    lists.add(listMovie.get(i).getJa_title());
                    lists.add(listMovie.get(i).getEn_content());
                    lists.add(listMovie.get(i).getJa_content());
                    lists.add(listMovie.get(i).getPresent());
                    lists.add(listMovie.get(i).getDestination());
                    lists.add(listMovie.get(i).getUrl().getUrl());
                    jsons.add(lists);
                }
                callback.callbackNavigate(jsons, present, destination);
                System.out.println("ポイント4:presentとdestinationの値あり");
                System.out.println("present");
                System.out.println(present);
                System.out.println("destination");
                System.out.println(destination);
            }

            @Override
            public void onFailure(Call<List<ListMovie>> call, Throwable t) {
                Log.d("debug4", t.getMessage());
            }
        });
        return call2;
    }




}
