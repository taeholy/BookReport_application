<?php

error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php'); // 데이터베이스 연결 파일

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
{
    $name = $_POST['name'];
    $rate = $_POST['rate'];
    $userID = $_POST['userID'];
    $review = $_POST['review'];

    if(empty($name)){
        $errMSG = "name is required";
    }
    else if(empty($userID)){
        $errMSG = "userID is required";
    }
    else if(empty($rate)){
        $errMSG = "rate is required";
    }
    else if(empty($review)){
        $errMSG = "review is required";
    }

    if(!isset($errMSG))
    {
        try {
            $stmt = $conn->prepare('INSERT INTO review(name, rate, userID, review) VALUES(:name, :rate, :userID, :review)');
            $stmt->bindParam(':name', $name);
            $stmt->bindParam(':rate', $rate);
            $stmt->bindParam(':userID', $userID);
            $stmt->bindParam(':review', $review);

            if($stmt->execute())
            {
                $successMSG = "Review uploaded successfully.";
            }
            else
            {
                $errMSG = "Error uploading review.";
            }

        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage());
        }
    }
}

if (isset($errMSG)) echo $errMSG;
if (isset($successMSG)) echo $successMSG;

?>
