package com.dlproject.zync.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ZyncToReadItems {

    //An array of sample (dummy) items.
    public static List<ZyncToReadItem> ITEMS = new ArrayList<ZyncToReadItem>();

    // A map of sample (dummy) items, by ID.
    public static Map<String, ZyncToReadItem> ITEM_MAP = new HashMap<String, ZyncToReadItem>();

    
    static {
        // Add 3 sample items.
        //addItem(new ZyncToReadItem("1", "New York Times Example", "http://www.nytimes.com/2011/10/08/business/how-steve-jobs-infused-passion-into-a-commodity.html", "zync_seg_4_1_50"));
        //addItem(new ZyncToReadItem("2", "Blog Example", "http://blog.mediumequalsmessage.com/javascript-books-to-take-your-skills-to-the-next-level", "zync_seg_4_1_50"));
        //addItem(new ZyncToReadItem("3", "ifanr Example", "http://www.ifanr.com/news/240210", "zync_seg_4_1_50"));
    	addItem(new ZyncToReadItem("0", "For Null Pointer Exception", "", ""));
    }

    public static void addItem(ZyncToReadItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    
    public static void removeAllItems() {
    	ITEMS.clear();
    	ITEM_MAP.clear();
    }

    // The Item class
    public static class ZyncToReadItem {
        public String id;
        public String title;
        public String url;
        public String zync_code;
        public String is_read;
        public String created_at;
        public String last_read_at;

        public ZyncToReadItem(String id, String title, String url, String zync_code) {
            this.id = id;
            this.title = title;
            this.url = url;
            this.zync_code = zync_code;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
