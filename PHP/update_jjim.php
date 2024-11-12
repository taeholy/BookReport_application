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

// POST 데이터 수신
$book_id = $_POST['book_id'];
$is_jjim = $_POST['jjim']; // 0 또는 1

// 데이터 업데이트
$sql = "UPDATE book SET jjim = ? WHERE book_id = ?";
$stmt = $conn->prepare($sql);

// 바인딩할 변수 수정
$stmt->bind_param("is", $is_jjim, $book_id); // $is_jjim을 사용해야 함
$stmt->execute();

if ($stmt->affected_rows > 0) {
    error_log("book_id: " . $book_id . " jjim: " . $is_jjim);
    echo "Success";
} else {
    // 추가적인 오류 정보를 제공
    echo "Error: " . $stmt->error; // 오류 메시지 출력
}

$stmt->close();
$conn->close();
?>
