<?php 
header('Content-Type: application/json');

$servername = "localhost";
$username = "root";
$password = ""; // MySQL 비밀번호
$dbname = "bookdb";

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    $response = array("success" => false, "error" => "Database connection failed: " . $conn->connect_error);
    echo json_encode($response);
    exit();
}

mysqli_query($conn, 'SET NAMES utf8');

// POST 요청으로부터 데이터 받기
$userID = $_POST["userID"];
$userPassword = $_POST["userPassword"];
$userName = $_POST["userName"];
$userAge = $_POST["userAge"];
$userGender = $_POST["userGender"];

// 사용자 정보 업데이트 쿼리
$statement = $conn->prepare("UPDATE users SET userPassword=?, userName=?, userAge=?, userGender=? WHERE userID=?");
if ($statement === false) {
    $response = array("success" => false, "error" => "Prepare statement failed: " . $conn->error);
    echo json_encode($response);
    exit();
}

$statement->bind_param("ssiss", $userPassword, $userName, $userAge, $userGender, $userID);
if ($statement->execute()) {
    $response = array("success" => true);
} else {
    $response = array("success" => false, "error" => "Execute statement failed: " . $statement->error);
}

$statement->close();
$conn->close();

echo json_encode($response);
?>
