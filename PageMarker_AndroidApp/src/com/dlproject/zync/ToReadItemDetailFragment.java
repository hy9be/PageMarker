package com.dlproject.zync;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.dlproject.zync.model.ZyncToReadItems;

/**
 * A fragment representing a single ToReadItem detail screen.
 * This fragment is either contained in a {@link ToReadItemListActivity}
 * in two-pane mode (on tablets) or a {@link ToReadItemDetailActivity}
 * on handsets.
 */
public class ToReadItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ZyncToReadItems.ZyncToReadItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ToReadItemDetailFragment() {
    }
    
    private Activity activity = null;
    private ProgressDialog progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ZyncToReadItems.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }
    
    private String loadJsFromAssets(String fileName) {
    	String result = "";  
        try {  
            InputStream in = getResources().getAssets().open(fileName); 
            int lenght = in.available();
            byte[]  buffer = new byte[lenght];
            in.read(buffer);  
            result = EncodingUtils.getString(buffer, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }
    
    WebView mWebView = null;
    boolean blockLoadingNetworkImage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	/*
    	// Modify the StrictMode to allow network traffic on main thread
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    	*/
    	
        View rootView = inflater.inflate(R.layout.fragment_toreaditem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.toreaditem_detail)).setText(mItem.title);
        }
        
        progressBar = ProgressDialog.show(this.getActivity(), mItem.title, "Loading...");
        
        // Show the web page
        mWebView = (WebView) rootView.findViewById(R.id.toreaditem_page);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);        
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //mWebView.getSettings().setBlockNetworkImage(true);
        blockLoadingNetworkImage=true;
        
        // Set the title of activity
        activity = this.getActivity();
        mWebView.setWebChromeClient(new WebChromeClient() {
        	
        	@Override
        	public boolean onConsoleMessage(ConsoleMessage cm) {
        	    Log.d("MyApplication", cm.message() + " -- From line "
        	                         + cm.lineNumber() + " of "
        	                         + cm.sourceId() );
        	    return true;
        	}
        	
        	@Override
            public void onProgressChanged(WebView view, int progress)
            {            	
            	activity.setTitle("Loading..." + Integer.toString(progress) + "%");
            	activity.setProgress(progress);
 
                if(progress == 100) {
                	activity.setTitle(R.string.app_name);
                	/*if(blockLoadingNetworkImage) {
                		mWebView.getSettings().setBlockNetworkImage(false);
                		blockLoadingNetworkImage=false;
	                }*/
                }
            }
        });
        
		final String zyncSeg_js = loadJsFromAssets("zyncSeg.js");
		final String jquery_js = loadJsFromAssets("jquery.min.js");
		
        // WebViewClient must be set BEFORE calling loadUrl!  
	    mWebView.setWebViewClient(new WebViewClient() {  
	        @Override  
	        public void onPageFinished(WebView view, String url)  
	        {
	        	super.onPageFinished(view, url);
	        	if (progressBar.isShowing()) {  
                	progressBar.dismiss();
                }
	        	//view.loadUrl("javascript:window.scrollBy(0,500)");
	        	
	        	//Inject local js files
	        	//Call the function to scroll
	        	view.loadUrl("javascript:" + jquery_js);
	        	view.loadUrl("javascript:" + zyncSeg_js);
	        	
	        	//Scroll
	        	view.loadUrl("javascript:$('html,body').animate({scrollTop: $('#" + mItem.zync_code + "').offset().top}, 1000);");
	        	//Highlight the resume point
	        	view.loadUrl("javascript:$('#" + mItem.zync_code + "').css('font-weight','bold').css('background','yellow');");
	        }
	        
	    });
	    
	    mWebView.loadUrl(mItem.url);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    
        return rootView;
    }
}
