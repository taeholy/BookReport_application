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

// 데이터베이스에서 데이터 가져오기
$sql = "SELECT title, author, genre, book_id, CONCAT('/book_img/',book_id, '.png') AS img FROM book WHERE genre = '동화'";
$result = $conn->query($sql);

// 결과를 배열로 변환
$data = array();
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        // 경로 수정
        $row['img'] = 'http://192.168.33.198' . str_replace('\\', '/', $row['img']);
        $data[] = $row;
    }
}
                
// JSON 형식으로 출력
header('Content-Type: application/json; charset=utf-8');
echo json_encode($data, JSON_UNESCAPED_UNICODE);

// 연결 종료
$conn->close();
?>
