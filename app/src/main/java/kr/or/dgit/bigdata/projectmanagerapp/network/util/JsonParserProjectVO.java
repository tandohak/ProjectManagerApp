package kr.or.dgit.bigdata.projectmanagerapp.network.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;

/**
 * Created by ghddb on 2018-04-29.
 */

public class JsonParserProjectVO extends JsonParserUtil<ProjectVO> {
    private  final String TAG = "JsonParserProjectVO";

    @Override
    public ProjectVO itemParse(JSONObject order) throws JSONException {
        ProjectVO vo = new ProjectVO();
        Log.d(TAG,order.toString());

        vo.setPno(order.getInt("pno"));
        vo.setWcode(order.getString("wcode"));
        vo.setTitle(order.getString("title"));
        vo.setMaker(order.getInt("maker"));
        vo.setExplanation(order.getString("explanation"));
        vo.setVisibility(order.getBoolean("visibility"));


        if (order.getString("regDate") !="null") {
            Long regDate = order.getLong("regDate");
            vo.setRegDate(new Date(regDate));
        }
        if (order.getString("startDate") !="null") {
            Long startDate = order.getLong("startDate");
            vo.setStartDate(new Date(startDate));
        }

        if (order.getString("endDate")  !="null") {
            Long endDate = order.getLong("endDate");
            vo.setEndDate(new Date(endDate));
        }

        if (order.getString("finishDate") !="null") {
            Long finishDate = order.getLong("finishDate");
            vo.setFinishDate(new Date(finishDate));
        }

        vo.setStatus(order.getInt("status"));
        vo.setAuthority(order.getInt("authority"));
        vo.setLocker(order.getBoolean("locker"));

        return vo;
    }
}
