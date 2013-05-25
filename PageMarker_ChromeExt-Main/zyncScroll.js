//***********************************************************
// Codes to scroll the page to a certain segmentation
//***********************************************************

window.getParameterByName = function(n) {
	var half = location.search.split(n+'=')[1];
	return half? decodeURIComponent(half.split('&')[0]):null;
}

//a typical url: http://tomtunguz.com/the-idea-factory?a=1&zync_seg_code=zync_seg_3_50	
var zync_seg_code = window.getParameterByName("zync_seg_code");

if (zync_seg_code != null)
{
	//$('body').animate({scrollTop: $("#"+zync_seg_code).offset().top-100}, 2000);
	$('body').animate({scrollTop: $("#"+zync_seg_code).position().top - 100}, 1000);
	//$('body').scrollTop($("#"+zync_seg_code).position().top);
	
	$("#"+zync_seg_code).css("font-weight","bold").css("background","yellow");
}