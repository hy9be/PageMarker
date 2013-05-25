//***********************************************************
// Codes to record current segmentation when user click "mark here"
//***********************************************************

// bind all items to pop_up page
function bindToReadItems() {
	$.getJSON('http://dlproject.net46.net/dlproject/get_all_items.php', function(data) {
		//preprocess the data
		//data = raw_data.replace(/<.*>/g, "");
		
		to_read_items = $.parseJSON(data);
		
		var items_html = [];
		
		$.each(data.to_read_items, function(index, item) {		
			var current_item = $('<li/>');

			$.each(item, function(key, value) {
				current_item.attr('data-'+key, value);
			})
			
			$('<a/>', {
				'href': current_item.attr('data-url') + '?' + 'zync_seg_code=' + current_item.attr('data-zync_code'),
				text: current_item.attr('data-title')
			}).prependTo(current_item);
			
			// delete button
			$('<span/>', {
				text: ' [X] '
			}).css('cursor', 'pointer').click(function() {
				$.post("http://dlproject.net46.net/dlproject/delete_item.php", 
					"id=" + current_item.attr('data-id'))
				.done(function() { 
					$('#msgbox').text("Item deleted!");
					
					bindToReadItems();
				}).fail(function (jqXHR, status, error) {$('#msgbox').text(error);});

			}).prependTo(current_item);
			
			items_html.push(current_item);
		});
		
		if ($('#zync_items').length>0) {
			$('#zync_items').remove();
		}
		
		$('<ul/>', {
			'id': 'zync_items',
			'class': 'zync'
		}).prependTo('body');
		
		$.each(items_html, function(index, item) {
			$('#zync_items').append(item);
		})
		
	}).done(function() { $('#msgbox').text("Items are updated!"); })
	.fail(function (jqXHR, status, error) {$('#msgbox').text(error);});
}

// send message to the page, and update the display with feedbacks
function sendMarkMessage(tab)
{
	chrome.tabs.sendMessage(tab.id, {mark: "now"}, 
		function(response) {
					
			//create a to-read-item
			$.post("http://dlproject.net46.net/dlproject/create_item.php", 
					"title=" + response.title.toString()
					+ "&url=" + response.url.toString()
					+ "&zync_code=" + response.zync_code.toString())
				.done(function() { $('#msgbox').text("Item created!"); })
				.fail(function (jqXHR, status, error) {$('#msgbox').text(error);});
			
			bindToReadItems();
		});
}

// add event handler for mark/refresh button
document.addEventListener('DOMContentLoaded', function () {
	// mark button clicked -- invoke the sendMarkMessage function
	$("#btn_mark").click(function() {
		chrome.windows.getCurrent(function(win) {
			chrome.tabs.getSelected(win.id, sendMarkMessage);
		});
	});
	
	// refresh button clicked
	$("#btn_refresh").click(bindToReadItems);
	
	bindToReadItems();
  });