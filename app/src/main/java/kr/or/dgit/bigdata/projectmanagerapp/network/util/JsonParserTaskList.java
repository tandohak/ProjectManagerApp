package kr.or.dgit.bigdata.projectmanagerapp.network.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskListVO;

/**
 * Created by ghddb on 2018-04-29.
 */

public class JsonParserTaskList extends JsonParserUtil<TaskListVO> {
    private  final String TAG = "JsonParserTaskList";

    @Override
    public TaskListVO itemParse(JSONObject order) throws JSONException {
        Log.d(TAG,order.toString());
        TaskListVO vo = new TaskListVO();
        vo.setTlno(order.getInt("tlno"));
        vo.setPno(order.getInt("pno"));
        vo.setName(order.getString("name"));
        vo.setList_order(order.getInt("list_order"));
        return vo;
    }
}
