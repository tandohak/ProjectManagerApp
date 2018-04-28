package kr.or.dgit.bigdata.projectmanagerapp.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by DGIT3-9 on 2018-04-26.
 */

public class HttpFileUploadTask extends AsyncTask<String, Void, String> {
    Context context;

    String imgPath;
    private Handler handler;
    int resultNum;
    private static final String TAG = "HttpFileUploadTask";

    public HttpFileUploadTask(Context context, String imgPath, Handler handler, int resultNum) {
        this.context = context;
        this.imgPath = imgPath;
        this.handler=handler;
        this.resultNum = resultNum;
    }


    protected void onPreExecute() {}

    protected void onPostExecute(String result) {
        Log.d(TAG,result);

        Message msg = handler.obtainMessage();
        msg.what = resultNum;
        msg.obj = result;
        handler.sendMessage(msg);

    }
    protected String doInBackground(String... strs) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        HttpURLConnection con = null;
        String line = null;
        try {
           /* URL url = new URL(strs[0]);
            con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);*/

            File file = new File(imgPath);
            if(!file.isFile()){
                return  "파일이 아닙니다.";
            }

            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                    .build();

            String url = "http://...";
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(strs[0])
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            okhttp3.Response response = client.newCall(request).execute();
            Log.d(TAG,"file path - " + imgPath);
            Log.d(TAG,"file path - " + file.getName());
            Log.d(TAG,"access - " + response);
            String res = response.body().string();
            sb.append(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { br.close(); con.disconnect(); } catch (Exception e) { }
        }
        return sb.toString();
    }

}
