<?php
header("Content-Type: application/json");

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "api_data";

$connection = mysqli_connect($servername, $username, $password, $dbname);
$response = array();

if (!$connection) {
    echo json_encode(array("error" => "Connection Failed: " . mysqli_connect_error()));
    exit();
}

// Debugging: Print the request method
error_log("Request method: " . $_SERVER['REQUEST_METHOD']);


if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $sql = "SELECT * FROM data";
    $result = mysqli_query($connection, $sql);
    if ($result) {
        $response = array();
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = $row;
        }
        echo json_encode($response, JSON_PRETTY_PRINT);
    } else {
        echo json_encode(array("error" => "Query failed: " . mysqli_error($connection)));
    }
} 