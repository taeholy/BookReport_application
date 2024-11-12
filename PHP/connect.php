<?php
$host = '3.106.143.212';  // 또는 EC2 인스턴스 IP
$username = 'ec2-user';
$password = 'l123456789*';
$dbname = 'bookdb';
$port = 3306;  // 기본 MySQL 포트

$conn = new mysqli($host, $username, $password, $dbname, $port);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully";
?>


