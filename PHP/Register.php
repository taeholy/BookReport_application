<?php 
$servername = "localhost";
$username = "root";
$password = ""; // MySQL 비밀번호
$dbname = "bookdb";

$conn = new mysqli($servername, $username, $password, $dbname);     
    mysqli_query($conn,'SET NAMES utf8');
 
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $userGender = $_POST["userGender"];
    $statement = mysqli_prepare($conn, "INSERT INTO users VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssis", $userID, $userPassword, $userName, $userAge, $userGender);
    mysqli_stmt_execute($statement);
 
 
    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);
?>