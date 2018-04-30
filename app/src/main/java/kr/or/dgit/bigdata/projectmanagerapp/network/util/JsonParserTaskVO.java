package kr.or.dgit.bigdata.projectmanagerapp.network.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskVO;

/**
 * Created by ghddb on 2018-04-29.
 */

public class JsonParserTaskVO extends JsonParserUtil<TaskVO> {
    private  final String TAG = "JsonParserTaskVO";

    @Override
    public TaskVO itemParse(JSONObject order) throws JSONException {
        TaskVO vo = new TaskVO();
        Log.d(TAG,order.toString());
        vo.setTaskno(order.getInt("taskno"));
        vo.setTlno(order.getInt("tlno"));
        vo.setMassno(order.getInt("massno"));

        if(order.getString("explanation") != "null") {
            vo.setExplanation(order.getString("explanation"));
        }

        vo.setTaskname(order.getString("taskname"));
        if(order.getString("regDate") != "null") {
            vo.setRegDate(new Date(order.getLong("regDate")));
        }

        if(order.getString("startDate") != "null") {
            vo.setStartDate(new Date(order.getLong("startDate")));
        }

        if(order.getString("endDate") != "null") {
            vo.setEndDate(new Date(order.getLong("endDate")));
        }

        if(order.getString("finishDate") != "null") {
            vo.setFinishDate(new Date(order.getLong("finishDate")));
        }
        vo.setWriter(order.getString("writer"));

        if(order.getString("colorLabel") != "null") {
            vo.setColorLabel(order.getString("colorLabel"));
        }

        vo.setStatus(order.getInt("status"));

        return vo;
    }
}
