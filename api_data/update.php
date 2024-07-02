<?php
header("Content-Type: application/json");

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "api_data";

$connection = mysqli_connect($servername, $username, $password, $dbname);

if (!$connection) {
    echo json_encode(array("error" => "Connection Failed: " . mysqli_connect_error()));
    exit();
}


// Debugging: Print the request method
error_log("Request method: " . $_SERVER['REQUEST_METHOD']);

// Check if the request method is PUT
if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
    $id = isset($_GET['id']) ? (int)$_GET['id'] : null;
    $name = isset($_GET['name']) ? $_GET['name'] : null;
    $age = isset($_GET['age']) ? (int)$_GET['age'] : null;
    $email = isset($_GET['email']) ? $_GET['email'] : null;

    // Validate input
    if ($id !== null && $name && $age !== null && $email) {
        $stmt = $connection->prepare("UPDATE data SET name=?, age=?, email=? WHERE id=?");
        $stmt->bind_param("sisi", $name, $age, $email, $id);

        if ($stmt->execute()) {
            echo json_encode(array("success" => true));
        } else {
            echo json_encode(array("error" => "Query failed: " . $stmt->error));
        }
        $stmt->close();
    } else {
        echo json_encode(array("error" => "Invalid input"));
    }
} else {
    echo json_encode(array("error" => "Unsupported request method"));
}