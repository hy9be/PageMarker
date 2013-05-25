<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once 'db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT * FROM to_read_items ORDER BY id DESC") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["to_read_items"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $to_read_item = array();
        $to_read_item["id"] = $row["id"];
        $to_read_item["title"] = $row["title"];
        $to_read_item["url"] = $row["url"];
		$to_read_item["zync_code"] = $row["zync_code"];
		$to_read_item["is_read"] = $row["is_read"];
        $to_read_item["created_at"] = $row["created_at"];
        $to_read_item["last_read_at"] = $row["last_read_at"];
 
        // push single product into final response array
        array_push($response["to_read_items"], $to_read_item);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No to-read items found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>