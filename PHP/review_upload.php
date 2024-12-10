<?php

error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');  // dbcon.php에서 mysqli로 연결

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
{
    // POST 데이터 받기
    $name = $_POST['name'];
    $rate = $_POST['rate'];
    $userID = $_POST['userID'];
    $review = $_POST['review'];

    // 데이터 유효성 검사
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

    // 모든 필드가 채워졌을 경우에만 데이터 삽입
    if(!isset($errMSG))
    {
        try {
            // 데이터베이스에 연결하여 데이터를 삽입
            $stmt = $con->prepare('INSERT INTO review(name, rate, userID, review) VALUES(?, ?, ?, ?)');
            $stmt->bind_param("ssss", $name, $rate, $userID, $review);

            // 삽입 실행
            if($stmt->execute())
            {
                $successMSG = "Review uploaded successfully.";
            }
            else
            {
                $errMSG = "Error uploading review.";
            }

        } catch(Exception $e) {
            // 예외 처리
            die("Database error: " . $e->getMessage());
        }
    }
}

<?php
// 데이터를 받았는지 확인
echo "Name: $name, Rate: $rate, UserID: $userID, Review: $review";  // 디버깅용
// 결과 출력
if (isset($errMSG)) echo $errMSG;
if (isset($successMSG)) echo $successMSG;

?>
