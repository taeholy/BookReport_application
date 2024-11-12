<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "bookdb";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// MySQL 인코딩 설정
$conn->set_charset("utf8");

header('Content-Type: application/json; charset=utf-8');

if (isset($_GET['book_id'])) {
    $bookId = $_GET['book_id'];
    error_log("Received Book ID: " . $bookId); // 로그 추가

    // book_id를 기준으로 책 정보와 jjim 값을 가져옴
    $sql = "SELECT title, author, genre, jjim, CONCAT('http://192.168.33.198/book_img/', book_id, '.png') AS img FROM book WHERE book_id = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $bookId); // 'i'를 's'로 변경 (문자열일 경우)
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $book = $result->fetch_assoc();
        echo json_encode($book, JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES); // book 정보와 함께 jjim 값 포함
    } else {
        echo json_encode(array("error" => "Book not found"));
    }

    $stmt->close();
} else {
    echo json_encode(array("error" => "Invalid request"));
}

$conn->close();
?>
