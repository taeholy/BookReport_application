<?php
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

if (isset($_POST['diary_date']) && isset($_POST['diary_title']) && isset($_POST['diary_content'])) {
    $diary_date = $_POST['diary_date'];
    $diary_title = $_POST['diary_title'];
    $diary_content = $_POST['diary_content'];

    $sql = "UPDATE diaryTB SET diary_title = ?, diary_content = ? WHERE diary_date = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sss", $diary_title, $diary_content, $diary_date);

    if ($stmt->execute()) {
        echo json_encode(array("success" => "Data updated successfully"));
    } else {
        echo json_encode(array("error" => "Failed to update data"));
    }

    $stmt->close();
} else {
    echo json_encode(array("error" => "Invalid request"));
}

$conn->close();
?>
