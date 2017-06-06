package station.around.aroundsatation;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



/*

Index.javaとDetail.javaから現在地と目的地のデータを受け取り、条件に合致した動画を表示します。
動画データはAPIから受け取ります。

*/

public class Navigation extends Activity implements CallHttp.CallHttpCallbacks {

    public  String present;
    public  String destination;
    public  String en_title;
    public  String ja_title;
    public  String en_content;
    public  String ja_content;
    public  String url;
    public ArrayList<ArrayList<String>> jsons = new ArrayList<ArrayList<String>>();
    public HttpConnect hc = new HttpConnect(this);
    public ArrayList<String> en_titles = new ArrayList<String>();
    public ArrayList<String> ja_titles = new ArrayList<String>();
    public ArrayList<String> en_contents = new ArrayList<String>();
    public ArrayList<String> ja_contents = new ArrayList<String>();
    public ArrayList<String> presents = new ArrayList<String>();
    public ArrayList<String> destinations = new ArrayList<String>();
    public ArrayList<String> urls = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Intent i = getIntent();

        // 現在地と目的地の取得
        present = i.getStringExtra("Present");
        destination = i.getStringExtra("Destination");
        hc.passMovies(present, destination);
    }

    @Override
    public void callbackNavigate(ArrayList<ArrayList<String>> jsons, String present, String destination){
        //HTTP通信時のコールバック関数
        setMovieContens(jsons, present, destination);
        System.out.println("ポイント5:presentとdestinationの値あり");
        System.out.println("present");
        System.out.println(present);
        System.out.println("destination");
        System.out.println(destination);
    }

    public void setMovieContens(ArrayList<ArrayList<String>> jsons, String present, String destination){
        System.out.println("ポイント6:presentとdestinationの値あり");
        System.out.println("present");
        System.out.println(present);
        System.out.println("destination");
        System.out.println(destination);

        for(int i = 0; i < jsons.size(); i++){
            en_titles.add(jsons.get(i).get(0));
            ja_titles.add(jsons.get(i).get(1));
            en_contents.add(jsons.get(i).get(2));
            ja_contents.add(jsons.get(i).get(3));
            presents.add(jsons.get(i).get(4));
            destinations.add(jsons.get(i).get(5));
            urls.add(jsons.get(i).get(6));

        }
        serchMovie(jsons, present, destination);

    }

    public void serchMovie(ArrayList<ArrayList<String>> jsons, String present, String destination){
        //  ナビゲーションページに表示する動画を検索します。ついでに、その動画情報を取得します。
        System.out.println("ポイント7:presentとdestinationの値あり");
        System.out.println("present");
        System.out.println(present);
        System.out.println("destination");
        System.out.println(destination);
        for(int i = 0; i < jsons.size(); i++){
            if (present.equals(jsons.get(i).get(4)) && destination.equals(jsons.get(i).get(5))) {
                en_title = jsons.get(i).get(0);
                ja_title = jsons.get(i).get(1);
                en_content = jsons.get(i).get(2);
                ja_content = jsons.get(i).get(3);
                url = jsons.get(i).get(6);
                break;
            }
            System.out.println(en_title);
        }
        System.out.println(en_title);
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(en_title);
    }

    @Override
    public void callbackMethod(ArrayList<ArrayList<String>> jsons){
        //　このコールバック関数は使いません。
    }
}
