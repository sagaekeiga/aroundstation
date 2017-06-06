package station.around.aroundsatation;

import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import station.around.aroundsatation.model.ListImage;


/*

APIから取得した情報のリストを表示します。
項目を選ぶと、Detail.javaに遷移します。

*/

public class Index extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.present);
    }

    //リストフラグメントの生成(2)
    public static class TitlesFragment extends ListFragment implements CallHttp.CallHttpCallbacks {
        private int pos = -1;
        public HttpConnect hc = new HttpConnect(this);
        public ArrayList<ArrayList<String>> jsons = new ArrayList<ArrayList<String>>();
        public ArrayList<String> en_titles = new ArrayList<String>();
        public ArrayList<String> ja_titles = new ArrayList<String>();
        public ArrayList<String> en_contents = new ArrayList<String>();
        public ArrayList<String> ja_contents = new ArrayList<String>();
        public ArrayList<String> urls = new ArrayList<String>();
        public ListImage li = new ListImage();
        public  int page;
        public  String present;

        //アクティビティ生成完了時に呼ばれる(3)
        @Override
        public void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);

            hc.passImages();
            setListAdapterMethod(jsons);



        }

        //リスト要素クリック時に呼ばれる
        @Override
        public void onListItemClick(ListView l, View v, int pos, long id) {
            showDetails(pos);
        }

        //詳細の表示
        private void showDetails(int index) {
            Context context = getActivity().getApplication();

            //フラグメントの切り換え(4)
            if (isTablet(context)) {
                getListView().setItemChecked(index, true);
                if (pos == index) return;
                Detail.DetailsFragment fragment =
                        Detail.DetailsFragment.newInstance(index);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                pos = index;
            }
            //アクティビティの起動
            else {
                getListView().setItemChecked(index, false);
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("index", index);
                //JSON配列データをDetailへ渡す
                intent.putExtra("英タイトル", en_titles);
                intent.putExtra("日タイトル", ja_titles);
                intent.putExtra("英コンテンツ", en_contents);
                intent.putExtra("日コンテンツ", ja_contents);
                intent.putExtra("URL", urls);
                Intent i = getActivity().getIntent();

                present = i.getStringExtra("Present");
                System.out.println("ポイント1初期値はnull,Detailから帰って来た時は値あり");
                System.out.println(present);


                if (present != null) {
                    intent.putExtra("Present", present);
                }
                page = i.getIntExtra("page", 0);

                if (page == 2) {
                    intent.putExtra("page", 2);
                } else {
                    intent.putExtra("page", 1);
                }

                getActivity().startActivity(intent);
            }
        }

        @Override
        public void callbackMethod(ArrayList<ArrayList<String>> jsons){
            //HTTP通信時のコールバック関数
            setListAdapterMethod(jsons);
        }

        @Override
        public void callbackNavigate(ArrayList<ArrayList<String>> jsons, String present, String destination){
            //このコールバック関数は使いません。
        }


        public void setListAdapterMethod(ArrayList<ArrayList<String>> jsons){
            //リストに表示される配列データを生成・セッティングします。

            for(int i = 0; i < jsons.size(); i++){
                en_titles.add(jsons.get(i).get(0));
                ja_titles.add(jsons.get(i).get(1));
                en_contents.add(jsons.get(i).get(2));
                ja_contents.add(jsons.get(i).get(3));
                urls.add(jsons.get(i).get(4));
            }

            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    en_titles));
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setBackgroundColor(Color.LTGRAY);
            if (isTablet(getActivity())) showDetails(0);

            // データを渡す為のBundleを生成し、渡すデータを内包させる
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("リスト", en_titles);
            ListFragment listfragment = new ListFragment();
            listfragment.setArguments(bundle);
        }
    }

    //タブレットかどうかの取得(5)
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout&
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

}
