<?php
 
/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once 'db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["id"])) {
    $id = $_GET['id'];
 
    // get a product from products table
    $result = mysql_query("SELECT *FROM to_read_items WHERE id = $id");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $to_read_item = array();
			$to_read_item["id"] = $row["id"];
			$to_read_item["title"] = $row["title"];
			$to_read_item["url"] = $row["url"];
			$to_read_item["zync_code"] = $row["zync_code"];
			$to_read_item["is_read"] = $row["is_read"];
			$to_read_item["created_at"] = $row["created_at"];
			$to_read_item["last_read_at"] = $row["last_read_at"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["product"] = array();
 
            array_push($response["to_read_item"], $to_read_item);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>