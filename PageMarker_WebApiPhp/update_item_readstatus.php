<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['id']) && isset($_POST['is_read']) && isset($_POST['last_read_at'])) {
 
    $id = $_POST['id'];
    $is_read = $_POST['is_read'];
    $last_read_at = $_POST['last_read_at'];
 
    // include db connect class
    require_once 'db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE products SET is_read = '$is_read', last_read_at = '$last_read_at' WHERE id = $id");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "To-Read item successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>