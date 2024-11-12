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


// POST 요청으로 받은 데이터 처리
$diary_title = $_POST['diary_title'];
$diary_date = $_POST['diary_date'];
$diary_content = $_POST['diary_content'];

// SQL 쿼리 작성
$sql = "INSERT INTO diaryTB (diary_title, diary_date, diary_content) VALUES (?, ?, ?)";
$stmt = $conn->prepare($sql);
$stmt->bind_param("sss", $diary_title, $diary_date, $diary_content);

// 데이터 삽입
if ($stmt->execute()) {
    echo "Success";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

// 연결 종료
$stmt->close();
$conn->close();
?>
