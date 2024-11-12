<?php 
$servername = "localhost";
$username = "root";
$password = ""; // MySQL 비밀번호
$dbname = "bookdb";

$conn = new mysqli($servername, $username, $password, $dbname);     
    mysqli_query($conn,'SET NAMES utf8');
 
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    
    $statement = mysqli_prepare($conn, "SELECT * FROM users WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);
 
 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword, $userName, $userAge, $userGender);
 
    $response = array();
    $response["success"] = true;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
        $response["userName"] = $userName;
        $response["userAge"] = $userAge;        
        $response["userGender"] = $userGender;
    }
 
    echo json_encode($response);
?>