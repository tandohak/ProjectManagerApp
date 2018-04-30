package kr.or.dgit.bigdata.projectmanagerapp.network.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kr.or.dgit.bigdata.projectmanagerapp.domain.MemberVO;

/**
 * Created by ghddb on 2018-04-29.
 */

public class JsonParserMemberVO extends JsonParserUtil<MemberVO> {
    private  final String TAG = "JsonParserMemberVO";

    @Override
    public MemberVO itemParse(JSONObject order) throws JSONException {
        MemberVO vo = new MemberVO();
        Log.d(TAG,order.toString());
        if (order.getString("mno") != "null")
            vo.setMno((Integer) order.get("mno"));

        if (order.getString("uno") != "null")
            vo.setUno((Integer) order.get("uno"));

        if (order.getString("wcode") != "null")
            vo.setWcode((String) order.get("wcode"));

        if (order.getString("memGrade") != "null")
            vo.setMemGrade((Integer) order.get("memGrade"));

        if (order.getString("name") != "null")
            vo.setName((String) order.get("name"));

        if (order.getString("firstName") != "null")
            vo.setFirstName((String) order.get("firstName"));

        if (order.getString("lastName") != "null")
            vo.setLastName((String) order.get("lastName"));

        if (order.getString("photoPath") != "null")
            vo.setPhotoPath((String) order.get("photoPath"));

        if (order.getString("email") != "null")
            vo.setEmail((String) order.get("email"));

        if (order.getString("massno") != "null")
            vo.setMassno((Integer) order.get("massno"));

        if (order.getString("memAssGrade") != "null")
            vo.setMemAssGrade((Integer) order.get("memAssGrade"));

        return vo;
    }
}
