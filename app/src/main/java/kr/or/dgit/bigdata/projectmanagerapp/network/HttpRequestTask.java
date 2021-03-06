package kr.or.dgit.bigdata.projectmanagerapp.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
    int resultNum;
    private static final String TAG = "HttpRequestTask";

    public HttpRequestTask(Context context ,String requestMethod, Uri.Builder builder,Handler handler,int resultNum) {
        this.context = context;
        this.requestMethod = requestMethod;
        this.builder = builder;
        this.handler=handler;
        this.resultNum = resultNum;
    }
    public HttpRequestTask(Context context ,String requestMethod, String requestBodyValue,Handler handler,int resultNum) {
        this.context = context;
        this.requestMethod = requestMethod;
        this.requestBodyValue = requestBodyValue;
        this.handler=handler;
        this.resultNum = resultNum;
    }
    protected void onPreExecute() {
       /*if(progressDlg != null && progressDlg.isShowing()){
            progressDlg.dismiss();
            progressDlg = null;
        }
        progressDlg = ProgressDialog.show(context, "Wait", "Uploding...");*/

    }

    protected void onPostExecute(String result) {
        /*progressDlg.dismiss();
        progressDlg = null;*/
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
            URL url = new URL(strs[0]);
            con = (HttpURLConnection) url.openConnection();
            String query = "";
            Log.d(TAG,strs[0]);

            if(requestMethod.equals("POST")){
                Log.d(TAG,"POST");
                con.setRequestMethod(requestMethod);// POST방식으로 요청한다
                con.setDoInput(true); //POST 데이터를 넘겨주겠다는 옵션을 정의
                con.setDoOutput(true); //서버로 부터 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
                query = requestBodyValue;
                Log.d(TAG,query);

                if(builder != null){
                    query = builder.build().getEncodedQuery();
                    Log.d(TAG,query);
                }

                OutputStream os = con.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }

            if(requestMethod.equals("PUT")){
                con.setRequestMethod("PUT");
                con.setDoInput(true); //POST 데이터를 넘겨주겠다는 옵션을 정의
                con.setDoOutput(true); //서버로 부터 응답 헤더와 메시지를 읽어들이겠다는 옵션을 정의
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
                query = requestBodyValue;

                OutputStream os = con.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }

            if(requestMethod.equals("GET")){
                Log.d(TAG,requestBodyValue);
                con.setRequestMethod("GET");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
            }

            if (con != null) {
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }else if(con.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST){
                    return "";
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
