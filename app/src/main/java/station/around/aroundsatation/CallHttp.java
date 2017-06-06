package station.around.aroundsatation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keiga on 2017/06/02.
 */


/*

コールバックインターフェースと関数を定義します。

*/



public class CallHttp {

    //コールバックインターフェース作成
    public interface CallHttpCallbacks {
        public void callbackMethod(ArrayList<ArrayList<String>> jsons);
        public void callbackNavigate(ArrayList<ArrayList<String>> jsons, String present, String destination);
    }

    //コールバック対象クラス指定用インスタンス生成
    private CallHttpCallbacks _callHttpCallbacks;

    //コールバック対象クラス指定メソッド
    public void setCallbacks(CallHttpCallbacks callHttpCallbacks) {
        _callHttpCallbacks = callHttpCallbacks;
    }

}
