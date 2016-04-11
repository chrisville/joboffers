package org.wehavepower.joboffers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by pc on 3/17/2016.
 */
public class DecodeImageURLs extends AsyncTask<String, String, Bitmap> {

    byte[] photo;
    byte[] accImage;



    public interface AsyncResponseURLDecode {
        void processFinish(Bitmap output);
    }

    public AsyncResponseURLDecode delegate = null;

    public DecodeImageURLs (AsyncResponseURLDecode delegate){
        this.delegate = delegate;
    }

    @Override
    protected Bitmap doInBackground(String... url) {

        String URLArray = url[0];
        Bitmap image = null;


        try {
            URL imageurl = new URL(URLArray);
            image = null;
            image = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
            Log.d("query", "URL is opening");


        } catch (IOException e) {
            e.printStackTrace();
        }


        return image;
    }



    @Override
    protected void onPostExecute(Bitmap Result) {


        delegate.processFinish(Result);


    }
}



