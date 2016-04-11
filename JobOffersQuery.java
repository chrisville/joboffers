package org.wehavepower.joboffers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pc on 3/17/2016.
 */
public class JobOffersQuery extends AsyncTask<Void, Void, String> {

    public interface AsyncResponseJobOffersQuery {
        void processFinish(ArrayList output);
    }
    public AsyncResponseJobOffersQuery delegate = null;

    public JobOffersQuery (AsyncResponseJobOffersQuery delegate){
        this.delegate= delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected String doInBackground(Void... params) {
        String reg_url = "http://192.168.0.13/jobquery/jobquery.php";
        try{
            URL url = new URL(reg_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }


            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return response;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d("query", String.valueOf(s));

        try {
            JSONObject jsonArray = new JSONObject(s);
            JSONArray obj1 = jsonArray.getJSONArray("result");

            for (int i = 0; i < obj1.length(); i++) {
                JSONObject obj2 = obj1.getJSONObject(i);
                String nombreEmpresa = obj2.getString("nombre_empresa");
                String puestoTrabajo = obj2.getString("puesto_trabajo");
                String requisitos = obj2.getString("requisitos");
                String contacto = obj2.getString("contacto");
                String fechaListado = obj2.getString("fecha_listado");
                String imageUrl = obj2.getString("url_imagen");

                Log.d("URL", imageUrl);

                ArrayList<String> offerArray = new ArrayList<String>();
                offerArray.add(nombreEmpresa);
                offerArray.add(requisitos);
                offerArray.add((puestoTrabajo));
                offerArray.add(contacto);
                offerArray.add(fechaListado);
                offerArray.add(imageUrl);

                delegate.processFinish(offerArray);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }

}

