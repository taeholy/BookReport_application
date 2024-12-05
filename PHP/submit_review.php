<?php
// JSON 형식의 응답을 위해 Content-Type 헤더 설정
header('Content-Type: application/json; charset=utf-8');

// 데이터베이스 연결 정보 설정
$servername = "localhost"; // 데이터베이스 서버 (예: localhost)
$username = "root";        // 데이터베이스 사용자 이름
$password = "";            // 데이터베이스 비밀번호
$dbname = "bookdb";        // 데이터베이스 이름

// 데이터베이스 연결
$conn = new mysqli($servername, $username, $password, $dbname);

// 연결 확인
if ($conn->connect_error) {
    die(json_encode(["error" => "Connection failed: " . $conn->connect_error]));
}

// POST 데이터를 확인
if (isset($_POST['title']) && isset($_POST['review'])) {
    $title = $conn->real_escape_string($_POST['title']); // SQL Injection 방지를 위한 데이터 이스케이프 처리
    $review = $conn->real_escape_string($_POST['review']);

    // 리뷰 데이터를 데이터베이스에 삽입
    $sql = "INSERT INTO reviews (title, review) VALUES ('$title', '$review')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode(["success" => "Review submitted successfully."]); // 성공 응답
    } else {
        echo json_encode(["error" => "Error: " . $conn->error]); // 에러 응답
    }
} else {
    echo json_encode(["error" => "Invalid input. Missing 'title' or 'review'."]); // 잘못된 입력
}

// 데이터베이스 연결 닫기
$conn->close();
?>
