<?php

// header("Content-Type: application/json");

// $servername = "localhost";
// $username = "root";
// $password = "";
// $dbname = "api_data";

// $connection = mysqli_connect($servername, $username, $password, $dbname);

// if (!$connection) {
//     echo json_encode(array("error" => "Connection Failed: " . mysqli_connect_error()));
//     exit();
// }

// // Check if the request method is DELETE
// if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
//     // Retrieve raw DELETE data from the request body
//     $input = json_decode(file_get_contents('php://input'), true);
    
//     // Ensure that 'id' key exists in the input
//     $id = isset($input['id']) ? (int)$input['id'] : null;

//     if ($id !== null) {
//         // Prepare SQL statement
//         $stmt = $connection->prepare("DELETE FROM `data` WHERE `id` = ?");

//         // Bind parameters
//         $stmt->bind_param("i", $id);

//         // Execute statement
//         if ($stmt->execute()) {
//             echo json_encode(array("success" => true));
//         } else {
//             echo json_encode(array("error" => "Query failed: " . $stmt->error));
//         }
//         $stmt->close();

//     } else {
//         echo json_encode(array("error" => "Invalid input"));
//     }
// } else {
//     echo json_encode(array("error" => "Unsupported request method"));
// }

// // Close database connection
// mysqli_close($connection);


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

// Check if the request method is DELETE
if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
    // Retrieve 'id' from query parameters
    parse_str($_SERVER['QUERY_STRING'], $query);
    $id = isset($query['id']) ? (int)$query['id'] : null;

    if ($id !== null) {
        // Prepare SQL statement
        $stmt = $connection->prepare("DELETE FROM `data` WHERE `id` = ?");

        // Bind parameters
        $stmt->bind_param("i", $id);

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
