<?php
header('Content-Type: application/json; charset=utf-8');

// 데이터베이스 연결
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "bookdb";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(["error" => "Connection failed: " . $conn->connect_error]));
}

if (isset($_GET['title'])) {
    $title = $conn->real_escape_string($_GET['title']);
    $sql = "SELECT review FROM reviews WHERE title = '$title'";
    $result = $conn->query($sql);

    $reviews = [];
    while ($row = $result->fetch_assoc()) {
        $reviews[] = $row['review'];
    }
    echo json_encode($reviews, JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(["error" => "No title provided"]);
}

$conn->close();
?>
