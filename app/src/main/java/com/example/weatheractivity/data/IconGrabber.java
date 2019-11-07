package com.example.weatheractivity.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class IconGrabber extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private ImageView imageView;

    public IconGrabber(String iconImg, ImageView imageView) {
        this.url = iconImg;
        this.imageView = imageView;
    }

        @Override
        protected Bitmap doInBackground (Void... params){


            try {
                URL iconURL = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) iconURL.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);

            } catch (Exception e) {
                System.out.println("Error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
    }

