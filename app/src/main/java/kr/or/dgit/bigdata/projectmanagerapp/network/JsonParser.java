package kr.or.dgit.bigdata.projectmanagerapp.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;


public abstract class JsonParser extends AsyncTask<String, Void, List<Object>> {

    public ProgressDialog progressDialog;
    Context context;
    public JsonParser(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Wait", "order.xml & order.json Making ...");
    }

    @Override
    protected void onPostExecute(List<Object> items) {
        progressDialog.dismiss();

    }

    @Override
    protected List<Object> doInBackground(String... params) {
        StringBuffer sb = new StringBuffer();
        String str = null;
        try {
            BufferedReader fis = new BufferedReader(new InputStreamReader(context.openFileInput(params[0])));
            while ((str = fis.readLine()) != null) {
                sb.append(str);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsingXml(sb.toString());
    }

    public abstract  List<Object>  parsingXml(String xml);
}
