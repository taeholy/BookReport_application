<?php
$servername = "localhost";
$username = "root";
$password = "";  // MySQL 비밀번호
$dbname = "bookdb";

$con = new mysqli($servername, $username, $password, $dbname); // mysqli로 연결

// 연결 확인
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
?>
