//***********************************************************
// Codes to insert <span> tags for each segmentation of tags
//***********************************************************

// The length of a segmentation
var zync_seg_length = 50;

// A function to add the segmentation tags
function addSegmentationTags(original_text, p_id, seg_id)
{
	processedHTML = "";

	for (var i = 0; i < original_text.length; i += zync_seg_length)
	{
		// ** id has problem, needs to be re-calculate
		seg_newHTML = "<span class='zync_seg' id='zync_seg_" 
					+ p_id.toString()
					+ "_"
					+ seg_id.toString()
					+ "_"
					+ i.toString()
					+ "'>" 
					+ original_text.substring(i, Math.min(i + zync_seg_length, i + original_text.length)) 
					+ "</span>";
		
		processedHTML = processedHTML + seg_newHTML;
	}
	
	return  processedHTML;
}

// A function to add sementation tags for children texts (iteratively..) -- not iterative
function addSegmentationTagsForChild_iteration(current_node, p_id, init_seg_id)
{
	seg_id = init_seg_id;
	for (var i = 0; i < current_node.contents().length; i++)
	{
		if (current_node.contents()[i].nodeType == 3)
		{
			seg_id++;
			current_node.contents().eq(i).replaceWith($.parseHTML((addSegmentationTags(current_node.contents().eq(i).text(), p_id, seg_id))));
		}
		else if (current_node.contents()[i].nodeType == 1)
		{
			addSegmentationTagsForChild(current_node.contents().eq(i), p_id, seg_id);
		}
	}
}

function addSegmentationTagsForChild(root_node, p_id, init_seg_id)
{
	var seg_id = init_seg_id;
	var stack = new Array();
	stack.push(root_node)
	while (stack.length > 0)
	{
		var current_node = stack.pop();
		if (current_node[0].nodeType == 3)
		{
			seg_id++;
			current_node.replaceWith($.parseHTML((addSegmentationTags(current_node.text(), p_id, seg_id))));
		}
		else
		{
			for (var i = 0; i < current_node.contents().length; i++)
			{
				stack.push(current_node.contents().eq(i));
			}
		}
	}  ﻿
}


// Main code to find the main body of a html page. Currently, just simply using all <p> which having child elements
ps_mainbody = $("p:parent");

for (var i = 0; i < ps_mainbody.length; i++)
{
	addSegmentationTagsForChild(ps_mainbody.eq(i), i, 0);
}