package fr.insa.h4401.gfaim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Pierre on 02/01/2016.
 */
public class BitmapGetter extends AsyncTask<URL, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(URL... params) {
        try {
           return BitmapFactory.decodeStream(params[0].openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
