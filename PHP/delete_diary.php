<?php
// 데이터베이스 연결 정보
$servername = "localhost";
$username = "root";
$password = ""; // MySQL 비밀번호
$dbname = "bookdb";

// MySQL 연결
$conn = new mysqli($servername, $username, $password, $dbname);     

// 연결 확인
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// MySQL 인코딩 설정
$conn->set_charset("utf8");

$diary_id = $_POST['diary_id'];
error_log("Received diary_id: " . $diary_id);  // 로그로 diary_id 확인
// 데이터 삭제
$sql = "DELETE FROM diaryTB WHERE diary_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $diary_id);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo "Success";
} else {
    echo "Error";
}

$stmt->close();
$conn->close();
?>
