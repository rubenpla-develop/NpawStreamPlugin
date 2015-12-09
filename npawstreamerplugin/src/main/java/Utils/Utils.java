package Utils;

/**
 * Created by Ruben on 17/11/2015.
 */
public class Utils
{
    public final static String TAG = Utils.class.getSimpleName();



//    public static void uriToMediaSourceAsync(final Context context, Uri uri, MediaSourceAsyncCallbackHandler callback) {
//        LoadMediaSourceAsyncTask loadingTask = new LoadMediaSourceAsyncTask(context, callback);
//
//        try {
//            loadingTask.execute(uri).get();
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage(), e);
//        }
//    }

//    private static class LoadMediaSourceAsyncTask extends AsyncTask<Uri, Void, String>
//    {

//        private Context mContext;
//        private MediaSourceAsyncCallbackHandler mCallbackHandler;
//        private String mMediaSource;
//        private Exception mException;
//
//        public LoadMediaSourceAsyncTask(Context context, MediaSourceAsyncCallbackHandler callbackHandler) {
//            mContext = context;
//            mCallbackHandler = callbackHandler;
//        }
//
//        @Override
//        protected String doInBackground(Uri... params) {
//            try {
//                mMediaSource = Utils.uriToMediaSource(mContext, params[0]);
//                return mMediaSource;
//            } catch (Exception e) {
//                mException = e;
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String mediaSource) {
//            if(mException != null) {
//                mCallbackHandler.onException(mException);
//            } else {
//                mCallbackHandler.onMediaSourceLoaded(mMediaSource);
//            }
//        }
//    }

//    public static interface MediaSourceAsyncCallbackHandler {
//        void onMediaSourceLoaded(String mediaSource);
//        void onException(Exception e);
//    }
}
