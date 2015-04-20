package vandy.mooc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();
	private final Handler handler = new Handler();
	private Uri mResult;

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
    	super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
    	Intent intent = getIntent(); // gets the previously created intent
    	String url = intent.getStringExtra("url"); 


        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.

        // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.
    	new Thread(new DownloadImageTask(getApplicationContext(), url)).start();

    	
    }
    
	private class DownloadImageTask implements Runnable {
		private String mUrl;
		private Context mContext;
		public  DownloadImageTask(Context context, String url) {
			mUrl = url;
			mContext = context;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mResult = DownloadUtils.downloadImage(mContext, Uri.parse(mUrl));
			if (mResult != null) {
					
			
			handler.post(new Runnable() {
				@Override
				public void run() {
	                Intent intent = new Intent();
	                intent.putExtra("MESSAGE", mResult.toString());
	                setResult(RESULT_OK,intent);
	                finish();
				}
			});
			}
			
		}
	}

}
