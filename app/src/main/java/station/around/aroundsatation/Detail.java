package station.around.aroundsatation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/*

Index.javaのリスト情報の詳細を表示する画面です。
一回目は現在地の決定、二回目は目的地の決定に利用されます。
二回目のリンクはNavigation.javaへの遷移リンクになります。

*/


public class Detail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //インテントからのパラメータ取得
        int index = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) index = extras.getInt("index");


        //レイアウトの生成
        FrameLayout layout = new FrameLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        layout.setId(R.id.details);
        setContentView(layout);

        //アクティビティへのフラグメントの配置(8)
        DetailsFragment fragment = DetailsFragment.newInstance(index);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.details, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();


    }

    //詳細フラグメントの生成(7)
    public static class DetailsFragment extends Fragment {

        public  ArrayList<String> en_titles;
        public  ArrayList<String> ja_titles;
        public  ArrayList<String> en_contents;
        public  ArrayList<String> ja_contents;
        public  ArrayList<String> urls;
        public  int page;
        public  String url;
        public  String present;


        //インスタンス生成時に呼ばれる(6)
        public static DetailsFragment newInstance(int index) {
            DetailsFragment fragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", index);
            fragment.setArguments(bundle);//(8)
            return fragment;
        }

        //フラグメントのビュー生成時に呼ばれる
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle bundle) {
            Intent i = getActivity().getIntent();
            page = i.getIntExtra("page", 0);

            //現在地選択時
            View rootView = inflater.inflate(R.layout.round_activity_detail, container, false);

            //目的地選択時
            if (page == 2) {
                rootView = inflater.inflate(R.layout.rect_activity_detail, container, false);
            }

            if (container == null) return null;
            TextView textView = new TextView(getActivity());
            textView.setText("ページ"+
                    getArguments().getInt("index", 0)+"の詳細");//(8)
            textView.setTextSize(24);

            ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);

            en_titles = i.getStringArrayListExtra("英タイトル");
            ja_titles = i.getStringArrayListExtra("日タイトル");
            en_contents = i.getStringArrayListExtra("英コンテンツ");
            ja_contents = i.getStringArrayListExtra("日コンテンツ");
            urls = i.getStringArrayListExtra("URL");



            Uri uri = Uri.parse(en_titles.get(getArguments().getInt("index", 0)).toString());

            Uri.Builder builder = uri.buildUpon();
            AsyncTaskHttpRequest task = new AsyncTaskHttpRequest(imageView);
            task.execute(builder);




            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();

            /*

             選択した情報の受け渡し、次ページのリンクを生成します。

            */

            Context context = getActivity().getApplication();



            if (page == 1) { //現在選択時

                Button sendButton = (Button) getActivity().findViewById(R.id.send_button);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), Index.class);
                        // 現在地選択から目的地選択へ移行する際のサイン
                        intent.putExtra("page", 2);
                        intent.putExtra("Present", en_titles.get(getArguments().getInt("index", 0)).toString());

                        startActivity(intent);
                    }
                });
            } else if (page == 2) { //目的地選択時
                Button sendButton = (Button) getActivity().findViewById(R.id.goal_button);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Navigation.class);
                        intent.putExtra("Destination", en_titles.get(getArguments().getInt("index", 0)).toString());
                        Intent i = getActivity().getIntent();
                        present = i.getStringExtra("Present");
                        intent.putExtra("Present", present);
                        System.out.println("ポイント2:ナビゲーションへ遷移中。presentとdestinationに値あり");
                        System.out.println("present");
                        System.out.println(present);
                        System.out.println("destination");
                        System.out.println(en_titles.get(getArguments().getInt("index", 0)).toString());
                        startActivity(intent);
                    }
                });
            }

        }

    }


}
