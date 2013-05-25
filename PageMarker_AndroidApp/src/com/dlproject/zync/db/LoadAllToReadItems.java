package com.dlproject.zync.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dlproject.zync.model.ZyncToReadItems;
import com.dlproject.zync.model.ZyncToReadItems.ZyncToReadItem;
import com.dlproject.zync.util.JSONParser;

import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Background Async Task to Load all product by making HTTP Request
 * */
public class LoadAllToReadItems extends AsyncTask<String, String, String> {
	
	JSONParser jParser = new JSONParser();
	JSONArray to_read_items = null;
	public ListFragment current_frag;
	
	/**
	 * Before starting background thread Show Progress Dialog
	 * */

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		/*pDialog = new ProgressDialog(AllProductsActivity.this);
		pDialog.setMessage("Loading products. Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();*/
	}

	/**
	 * getting All products from url
	 * */
	protected String doInBackground(String... args) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// getting JSON string from URL
		JSONObject json = jParser.makeHttpRequest("http://dlproject.net46.net/dlproject/get_all_items.php", "GET", params);

		// Check your log cat for JSON response
		Log.d("All items: ", json.toString());

		try {
			// Checking for SUCCESS TAG
			int success = json.getInt("success");

			if (success == 1) {
				
				ZyncToReadItems.removeAllItems();				
				
				// items found
				// Getting Array of items
				to_read_items = json.getJSONArray("to_read_items");
				
				// looping through All Products
				for (int i = 0; i < to_read_items.length(); i++) {
					JSONObject c = to_read_items.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString("id");
					String title = c.getString("title");
					String url = c.getString("url");
					String zync_code = c.getString("zync_code");
					
					ZyncToReadItems.addItem(new ZyncToReadItem(id, title, url, zync_code));
				}
			} else {
				// no items found
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog after getting all products
		// pDialog.dismiss();
		
		// updating UI from Background Thread
		current_frag.getActivity().runOnUiThread(new Runnable() {
			public void run() {				
				ArrayAdapter<ZyncToReadItems.ZyncToReadItem> myAdapter = new ArrayAdapter<ZyncToReadItems.ZyncToReadItem>(
						current_frag.getActivity(),
		                android.R.layout.simple_list_item_activated_1,
		                android.R.id.text1,
		                ZyncToReadItems.ITEMS);
		        
		        // TODO: replace with a real list adapter.
				current_frag.setListAdapter(myAdapter);
			}
		});
	}
}
