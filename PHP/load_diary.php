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

// 데이터 가져오기
$sql = "SELECT * FROM diaryTB";
$result = $conn->query($sql);

$diaryData = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $diaryData[] = $row;
    }
}

echo json_encode($diaryData);

$conn->close();
?>
