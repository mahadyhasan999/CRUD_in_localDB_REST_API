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

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Retrieve POST data
    $input = json_decode(file_get_contents('php://input'), true);

    $name = isset($input['name']) ? $input['name'] : null;
    $age = isset($input['age']) ? (int)$input['age'] : null;
    $email = isset($input['email']) ? $input['email'] : null;


    // Validate input
    if ($name && $age !== null && $email) {
        // Prepare SQL statement
        $stmt = $connection->prepare("INSERT INTO `data` (name, age, email) VALUES (?, ?, ?)");

        // $stmt = $connection->prepare("INSERT INTO data (name, age, email) VALUES ('$name', '$age', '$email')");

        // Bind parameters
        $stmt->bind_param("sis", $name, $age, $email);

        // Execute statement
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
// Close database connection
mysqli_close($connection);

