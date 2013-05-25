//***********************************************************
// Codes to record current segmentation when user click "mark here"
//***********************************************************

// Define a function to write to the query string of current url
function updateQueryString(key, value, url) {
    if (!url) url = window.location.href;
    var re = new RegExp("([?|&])" + key + "=.*?(&|#|$)", "gi");

    if (url.match(re)) {
        if (value)
            return url.replace(re, '$1' + key + "=" + value + '$2');
        else
            return url.replace(re, '$2');
    }
    else {
        if (value) {
            var separator = url.indexOf('?') !== -1 ? '&' : '?',
                hash = url.split('#');
            url = hash[0] + separator + key + '=' + value;
            if (hash[1]) url += '#' + hash[1];
            return url;
        }
        else
            return url;
    }
}

// when the web page gets the message from extension "mark here"
chrome.extension.onMessage.addListener(
  function(request, sender, sendResponse) {
	
	var marked_zync_seg = "";
	var current_zync_seg = "";
	var min_scrollTop = 1000;
	var zync_segs = $(".zync_seg");
	
	// check all the segmentations
	for (var i = 0; i < zync_segs.length; i++)
	{
		current_zync_seg = $(".zync_seg").eq(i);
		
		// pick the one which is in the screen and closest to the top border of the browser window
		if ((Math.abs(scrollY-current_zync_seg.offset().top) < min_scrollTop) && (Math.abs(scrollY-current_zync_seg.offset().top) > 0))
		{
			min_scrollTop = Math.abs(scrollY-current_zync_seg.offset().top);
			marked_zync_seg = current_zync_seg.attr("id");
		}
	}
	
    if (request.mark == "now"){
      	sendResponse({title: document.title, url: document.URL, zync_code: marked_zync_seg});
    }
  });