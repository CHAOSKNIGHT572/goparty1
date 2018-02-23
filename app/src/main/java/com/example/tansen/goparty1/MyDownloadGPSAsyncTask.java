package com.example.tansen.goparty1;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by tansen on 8/18/17.
 */

public class MyDownloadGPSAsyncTask extends AsyncTask<String, String, String> {
//        private final WeakReference<ImageView> imageViewReference;
        LruCache mImgMemoryCache;
        public MyDownloadGPSAsyncTask(String url) {
//            imageViewReference = new WeakReference<ImageView>(imageView);
//
//
//            if (mImgMemoryCache == null) {
//                final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//                final int cacheSize = maxMemory / 8;
//
//                mImgMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
//                    @Override
//                    protected int sizeOf(String key, Bitmap bitmap) {
//                        return bitmap.getByteCount() / 1024;
//                    }
//                };
//            }
        }


        //Download
        @Override
        protected String doInBackground(String... urls) {
            String string = null;

            for (String url : urls) {
                 string = MyUtility.downloadJSONusingHTTPGetRequest(url);
                System.out.println(url);
            }

            return string;
        }

        //Set
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (imageViewReference != null && bitmap != null) {
//                final ImageView imageView = imageViewReference.get();
//                if (imageView != null) {
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
}
