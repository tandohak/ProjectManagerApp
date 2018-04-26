package kr.or.dgit.bigdata.projectmanagerapp.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DGIT3-9 on 2018-04-26.
 */

public class HttpRequestTask  extends AsyncTask<String, Void, String> {
    Context context;
    ProgressDialog progressDlg;
    String requestMethod;
    String requestBodyValue;
    Uri.Builder builder = null;
    private Handler handler;

    private static final String TAG = "HttpRequestTask";

    public HttpRequestTask(Context context ,String requestMethod, Uri.Builder builder,Handler handler) {
        this.context = context;
        this.requestMethod = requestMethod;
        this.builder = builder;
        this.handler=handler;
    }
    public HttpRequestTask(Context context ,String requestMethod, String requestBodyValue,Handler handler) {
        this.context = context;
        this.requestMethod = requestMethod;
        this.requestBodyValue = requestBodyValue;
        this.handler=handler;
    }
    protected void onPreExecute() {
        progressDlg = ProgressDialog.show(context, "Wait", "Downloading...");

    }
    protected void onPostExecute(String result) {
        progressDlg.dismiss();
        Log.d(TAG,result);
        Message msg = handler.obtainMessage();
        msg.what = 0;
        msg.obj = result;
        handler.sendMessage(msg);
    }
    protected String doInBackground(String... strs) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        HttpURLConnection con = null;
        String line = null;
        try {
            URL url = new URL(strs[0]);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);// POST방식으로 요청한다

            if(requestMethod.equals("POST")){
                con.setDoInput(true); //POST 데이터를 넘겨주겠다는 옵션을 정의
            }

            con.setDoOutput(true); //서버로 부터 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의
            String query;

            if(builder == null){
                con.setRequestProperty("Content-Type", "text/html");
                query = requestBodyValue;
                Log.d(TAG,query);
            }else{
                con.setRequestProperty("Content-Type", "application/json");
                query = builder.build().getEncodedQuery();
            }

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            if (con != null) {
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }
            }
            while ((line = br.readLine()) != null) { sb.append(line); }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { br.close(); con.disconnect(); } catch (Exception e) { }
        }
        return sb.toString();
    }
}