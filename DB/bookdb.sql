-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- 생성 시간: 24-12-03 06:44
-- 서버 버전: 10.4.32-MariaDB
-- PHP 버전: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `bookdb`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `book`
--

CREATE TABLE `book` (
  `title` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `genre` varchar(50) NOT NULL,
  `book_id` varchar(50) NOT NULL,
  `jjim` tinyint(4) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- 테이블의 덤프 데이터 `book`
--

INSERT INTO `book` (`title`, `author`, `genre`, `book_id`, `jjim`) VALUES
('누가 내 머리에 똥 쌌어?', '베르너 홀츠바르트', '동화', 'COM_1', 0),
('보자기야 놀자', '이송은', '동화', 'COM_10', 0),
('7년 동안의 잠', '박완서', '동화', 'COM_11', 0),
('몬드리안 놀이터', '진수아', '동화', 'COM_12', 0),
('애니캔', '은경', '동화', 'COM_13', 0),
('악플전쟁', '이규희', '동화', 'COM_14', 0),
('강아지 걸음으로', '황선미', '동화', 'COM_15', 0),
('불편한 이웃', '유승희', '동화', 'COM_16', 0),
('별똥 맛의 비밀', '은이결', '동화', 'COM_17', 0),
('연동동의 비밀', '이현', '동화', 'COM_18', 0),
('고래와 은하수', '박영주', '동화', 'COM_19', 0),
('책먹는 여우', '프란치스카 비어만', '동화', 'COM_2', 0),
('하룻밤', '이금이', '동화', 'COM_20', 0),
('아낌없이 주는 나무', '쉘 실버스타인', '동화', 'COM_3', 0),
('진짜 진짜 행복해', '애나벨 세구라 란츠', '동화', 'COM_4', 0),
('세금 내는 아이들2', '옥효진', '동화', 'COM_5', 0),
('소리 질러, 운동장', '진형민', '동화', 'COM_6', 0),
('기쁜 눈물', '이도', '동화', 'COM_7', 0),
('감정호텔', '리디아 브란코비치', '동화', 'COM_8', 0),
('요건 내 떡', '동화가 있는 집', '동화', 'COM_9', 0),
('요리는 소스빨', '소연남', '요리', 'COO_1', 0),
('김밀란 파스타', '김밀란', '요리', 'COO_10', 0),
('제4의 식탁', '임재양', '요리', 'COO_11', 0),
('최강록의 요리노트', '최강록', '요리', 'COO_12', 0),
('갈레트', '김영현', '요리', 'COO_13', 0),
('커피 레시피', '김지현', '요리', 'COO_14', 0),
('미스터 요리왕', '혼죠 케이', '요리', 'COO_15', 0),
('음식과 요리', '해럴드 맥기', '요리', 'COO_16', 0),
('이윤경 요리', '이윤경', '요리', 'COO_17', 0),
('채소바이블', '최명규', '요리', 'COO_18', 0),
('요리 본능', '리처드 랭엄', '요리', 'COO_19', 0),
('1분요리 뚝딱이형', '뚝딱이형', '요리', 'COO_2', 0),
('셰프의 탄생', '마이클 룰먼', '요리', 'COO_20', 0),
('장선용의 평생요리책', '장선용', '요리', 'COO_3', 0),
('분자요리', '채현석', '요리', 'COO_4', 0),
('중국요리', '여경래, 여경옥', '요리', 'COO_5', 0),
('평범해서 특별한 밥상', '이밥차 요리연구소', '요리', 'COO_6', 0),
('자취요리왕', '가위', '요리', 'COO_7', 0),
('더 푸드 랩', '임현수', '요리', 'COO_8', 0),
('채소 식탁', '김경민', '요리', 'COO_9', 0),
('김대균의 토익킹', '김대균', '교육', 'EDU_1', 0),
('허쌤의 수업놀이', '허승환', '교육', 'EDU_10', 0),
('가족생활교육', '박경아', '교육', 'EDU_11', 0),
('교육공학과 수업', '박성익', '교육', 'EDU_12', 0),
('영유아문학교육', '강재희', '교육', 'EDU_13', 0),
('나쁜 교육', '조너선 화이트', '교육', 'EDU_14', 0),
('유아사회교육', '김용숙', '교육', 'EDU_15', 0),
('회복되는 교실', '김훈태', '교육', 'EDU_16', 0),
('현대교육평가', '성태제', '교육', 'EDU_17', 0),
('교육사회화', '주동범', '교육', 'EDU_18', 0),
('교장의 일', '징검다리교육', '교육', 'EDU_19', 0),
('초급 일본어', '이은미', '교육', 'EDU_2', 0),
('소망의 나무', '교육부', '교육', 'EDU_20', 0),
('스타트 잉글리시', '이보영', '교육', 'EDU_3', 0),
('5차원 전면 교육', '원동연', '교육', 'EDU_4', 0),
('초급 중국어', '송지현', '교육', 'EDU_5', 0),
('이것이 자바다', '신용권, 임경균', '교육', 'EDU_6', 0),
('교육심리학', '신명희', '교육', 'EDU_7', 0),
('특수아동교육', '이소현', '교육', 'EDU_8', 0),
('부모교육', '정옥분', '교육', 'EDU_9', 0),
('저속노화 식사법', '정희원', '건강', 'HEL_1', 0),
('혈통만사', '남동욱', '건강', 'HEL_10', 0),
('수면 혁명', '호리 다이스케', '건강', 'HEL_11', 0),
('기적의 자세요정', '자세요정', '건강', 'HEL_12', 0),
('당뇨코드', '제이슨 펑', '건강', 'HEL_13', 0),
('짠맛의 힘', '김은숙 외', '건강', 'HEL_14', 0),
('당독소 쇼크', '박명규 외', '건강', 'HEL_15', 0),
('김명섭의 헬스 교실', '김명섭', '건강', 'HEL_16', 0),
('아프지 마요, 엄마', '이민경 외', '건강', 'HEL_17', 0),
('완전 소화', '류은경', '건강', 'HEL_18', 0),
('비만코드', '제이슨 펑', '건강', 'HEL_19', 0),
('내 몸 혁명', '박용우', '건강', 'HEL_2', 0),
('내 몸 리셋', '이경실', '건강', 'HEL_20', 0),
('내면소통', '김주환', '건강', 'HEL_3', 0),
('환자 혁명', '조한경', '건강', 'HEL_4', 0),
('정신건강론', '이혜경', '건강', 'HEL_5', 0),
('보디 리셋', '전홍준', '건강', 'HEL_6', 0),
('완전배출', '조승우', '건강', 'HEL_7', 0),
('염증 해방', '정세연', '건강', 'HEL_8', 0),
('달리기의 모든 것', '남혁우', '건강', 'HEL_9', 0),
('용의자 X의 헌신', '히가시노 게이고', '추리', 'HOB_1', 0),
('ABC살인사건', '애거서 크리스티', '추리', 'HOB_10', 0),
('악의', '히가시노 게이고', '추리', 'HOB_11', 0),
('13.67', '찬호께이', '추리', 'HOB_12', 0),
('저주토끼', '정보라', '추리', 'HOB_13', 0),
('봉제인형 살인사건', '다니엘 콜', '추리', 'HOB_14', 0),
('살인재능', '피터 스완슨', '추리', 'HOB_15', 0),
('트리플세븐', '이사카 고타로', '추리', 'HOB_16', 0),
('방주', '유키 하루오', '추리', 'HOB_17', 0),
('용의자들', '정해연', '추리', 'HOB_18', 0),
('mymy', '강진아', '추리', 'HOB_19', 0),
('녹나무 파수꾼', '히가시노 게이고', '추리', 'HOB_2', 0),
('고백', '미나토 가나에', '추리', 'HOB_20', 0),
('나미야 잡화점의 기적', '히가시노 게이고', '추리', 'HOB_3', 0),
('라플라스의 마녀', '히가시노 게이고', '추리', 'HOB_4', 0),
('그리고 아무도 없었다', '애거서 크리스티', '추리', 'HOB_5', 0),
('녹나무의 여신', '히가시노 게이고', '추리', 'HOB_6', 0),
('철과 흙', '리지명', '추리', 'HOB_7', 0),
('내 눈에 비친 악마', '루스 렌델', '추리', 'HOB_8', 0),
('쥐 덫', '애거서 크리스티', '추리', 'HOB_9', 0),
('기도수첩', '생명 편집부', '잡지', 'MAG_1', 0),
('스타퍼즐(2024년 10월)', '특급미디어 편집부', '잡지', 'MAG_10', 0),
('중급 중국어(2024년 10월)', '홍상욱', '잡지', 'MAG_11', 0),
('바자(2024년 9월)', '허스트중앙 편집부', '잡지', 'MAG_12', 0),
('맥심(2024년 9월)', '맥심', '잡지', 'MAG_13', 0),
('과학소년(20204년 9월)', '과학소년 편집부', '잡지', 'MAG_14', 0),
('보그(2024년 9월호)', '두산매거진 편집부', '잡지', 'MAG_15', 0),
('현대시(2024년 9월호)', '한국문연 편집부', '잡지', 'MAG_16', 0),
('루미나(2024년 9월호)', '키즈몽드 편집부', '잡지', 'MAG_17', 0),
('월간 미술(2024년 9월호)', '월간미술 편집부', '잡지', 'MAG_18', 0),
('거래가격(2024년 9월호)', '대한건설협회 편집부', '잡지', 'MAG_19', 0),
('엘르(2024년 10월)', '허스트중앙 편집부', '잡지', 'MAG_2', 0),
('현대문학(2024년 9월호)', '현대문학 편집부', '잡지', 'MAG_20', 0),
('스켑틱(39호)', '스켑틱 편집부', '잡지', 'MAG_3', 0),
('게이머즈(2024년 9월)', '게임문화 편집부', '잡지', 'MAG_4', 0),
('뉴턴(2024년 9월)', '아이뉴턴 편집부', '잡지', 'MAG_5', 0),
('보그(2024년 9월)', '두산매거진 편집부', '잡지', 'MAG_6', 0),
('까사리빙(2024년 9월)', '시공사 편집부', '잡지', 'MAG_7', 0),
('중급 일본어(2024년 10월)', '원미령', '잡지', 'MAG_8', 0),
('아레나 2024년 10월', '아레나옴므 편집부', '잡지', 'MAG_9', 0),
('작별하지 않는다', '한강', '소설', 'NOV_1', 0),
('모순', '양귀자', '소설', 'NOV_10', 0),
('면도날', '서머싯 몸', '소설', 'NOV_11', 0),
('싯다르타', '헤르만 헤세', '소설', 'NOV_12', 1),
('데미안', '헤르만 헤세', '소설', 'NOV_13', 0),
('인간 실격', '다자이 오사무', '소설', 'NOV_14', 0),
('흐르는 강물처럼', '셀리 리드', '소설', 'NOV_15', 0),
('구의 증명', '최진영', '소설', 'NOV_16', 0),
('남은 인생 10년', '고사카 루카', '소설', 'NOV_17', 0),
('파과', '구병모', '소설', 'NOV_18', 0),
('노르웨이의 숲', '무라카미 하루키', '소설', 'NOV_19', 0),
('불편한 편의점', '김호연', '소설', 'NOV_2', 0),
('1984', '조지 오웰', '소설', 'NOV_20', 0),
('파친코', '이민진', '소설', 'NOV_3', 0),
('하얼빈', '김훈', '소설', 'NOV_4', 0),
('나의 돈키호테', '김호연', '소설', 'NOV_5', 0),
('홍학의 자리', '정해연', '소설', 'NOV_6', 0),
('빛이 이끄는 곳으로', '백희성', '소설', 'NOV_7', 0),
('영원한 천국', '정유정', '소설', 'NOV_8', 0),
('이중 하나는 거짓말', '김애란', '소설', 'NOV_9', 0),
('나는 메트로 경비원입니다', '패트릭 브링리', '에세이', 'POE_1', 0),
('불안', '정영목', '에세이', 'POE_10', 0),
('제철 행복', '김신지', '에세이', 'POE_11', 0),
('즐거운 어른', '이옥선', '에세이', 'POE_12', 0),
('마음 똑똑', '박승숙', '에세이', 'POE_13', 0),
('어떤 섬세함', '이석원', '에세이', 'POE_14', 0),
('그럴 수 있어', '양희은', '에세이', 'POE_15', 0),
('사랑은 없다', '쇼펜하우어', '에세이', 'POE_16', 0),
('장면들', '손석희', '에세이', 'POE_17', 0),
('맨먼스 미신', '강중빈', '에세이', 'POE_18', 0),
('과거의 문턱', '김남시', '에세이', 'POE_19', 0),
('행복한 사람', '나태주', '에세이', 'POE_2', 0),
('좋은 이별', '김형경', '에세이', 'POE_20', 0),
('당신에게 좋은일만', '이슬비', '에세이', 'POE_3', 0),
('꽃길이 꽃인것을', '오평선', '에세이', 'POE_4', 0),
('법학 에세이', '곽한영', '에세이', 'POE_5', 0),
('진짜 나를 찾아라', '법정', '에세이', 'POE_6', 0),
('가벼운 고백', '김영민', '에세이', 'POE_7', 0),
('수필 쓰기', '이정림', '에세이', 'POE_8', 0),
('허송세월', '김훈', '에세이', 'POE_9', 0),
('읽는 기도', '무명의 기도자', '종교', 'REL_1', 0),
('티벳사자의 서', '파드마 삼바바', '종교', 'REL_10', 0),
('일과 영성', '팀 켈러', '종교', 'REL_11', 0),
('여섯 걸음', '원유경', '종교', 'REL_12', 0),
('생각한다는 것', '강연안', '종교', 'REL_13', 0),
('예수님의 은혜', '햇살콩', '종교', 'REL_14', 0),
('침묵', '엔도 슈사쿠', '종교', 'REL_15', 0),
('왕의 지도력', '홍성건', '종교', 'REL_16', 0),
('깨달음의 지혜', '아잔 차', '종교', 'REL_17', 0),
('불회떨1감', '김양재', '종교', 'REL_18', 0),
('성경 스펙트럼', '김예환', '종교', 'REL_19', 0),
('새벽 순종', '송준기', '종교', 'REL_2', 0),
('매일 기도', '조정민', '종교', 'REL_20', 0),
('중간사 수업', '박양규', '종교', 'REL_3', 0),
('하나님의 DNA', '최상훈', '종교', 'REL_4', 0),
('하나님의 열심', '박영선', '종교', 'REL_5', 0),
('고백의 언어들', '김기석', '종교', 'REL_6', 0),
('잠언 읽고 잠언 쓰자', '신은경', '종교', 'REL_7', 0),
('틱낫한 인터빙', '틱낫한', '종교', 'REL_8', 0),
('나와 너', '마르틴 부버', '종교', 'REL_9', 0),
('폭염살인', '왕수민', '과학', 'SCI_1', 0),
('불완전한 인간', '김유경', '과학', 'SCI_10', 0),
('보이지 않는', '김희봉', '과학', 'SCI_11', 0),
('지금 과학', '이덕환', '과학', 'SCI_12', 0),
('뇌의 흑역사', '이은정', '과학', 'SCI_13', 0),
('버자이너', '제효영', '과학', 'SCI_14', 0),
('초전도체', '김기덕', '과학', 'SCI_15', 0),
('의식이라는 꿈', '문규민', '과학', 'SCI_16', 0),
('코스미그래픽', '지웅배', '과학', 'SCI_17', 0),
('참나무라는 우주', '김숲', '과학', 'SCI_18', 0),
('가이아', '홍욱희', '과학', 'SCI_19', 0),
('영혼 다시 쓰기', '최보문', '과학', 'SCI_2', 0),
('아파트 속 과학', '김홍재', '과학', 'SCI_20', 0),
('제네시스', '김정훈', '과학', 'SCI_3', 0),
('살아보니 지능', '정재승 외 3명', '과학', 'SCI_4', 0),
('과학의 눈', '변정현', '과학', 'SCI_5', 0),
('꿈의 분자', '김우재', '과학', 'SCI_6', 0),
('당신은 화성에 갈수없다', '장윤주', '과학', 'SCI_7', 0),
('1분 과학 2', '이재범', '과학', 'SCI_8', 0),
('종의 소멸', '이미옥', '과학', 'SCI_9', 0),
('1cm 다이빙', '태수 문정', '성장', 'TEE_1', 0),
('독학력', '고요엘', '성장', 'TEE_10', 0),
('언스턱', '박선령', '성장', 'TEE_11', 0),
('리프레임', '한정훈', '성장', 'TEE_12', 0),
('매일을 나아가는법', '김나현', '성장', 'TEE_13', 0),
('멘탈의 공식', '허선영', '성장', 'TEE_14', 0),
('코나투스', '유영만', '성장', 'TEE_15', 0),
('컨셉 라이팅', '노윤주', '성장', 'TEE_16', 0),
('1년의 미라클', '류지연', '성장', 'TEE_17', 0),
('타임 박싱', '이영래', '성장', 'TEE_18', 0),
('나와의 워크숍', '김해리', '성장', 'TEE_19', 0),
('아몬드', '손원평', '성장', 'TEE_2', 0),
('파워 씽킹', '김병완', '성장', 'TEE_20', 0),
('완득이', '김려령', '성장', 'TEE_3', 0),
('우리들의 일그러진 영웅', '이문열', '성장', 'TEE_4', 0),
('겸손의 힘', '신예용', '성장', 'TEE_5', 0),
('성장을 위한 마음의 편지', '김용년', '성장', 'TEE_6', 0),
('나는 포기를 모른다', '정지현', '성장', 'TEE_7', 0),
('마인드 박스', '김익한', '성장', 'TEE_8', 0),
('일센스 99', '장혜영', '성장', 'TEE_9', 0);

-- --------------------------------------------------------

--
-- 테이블 구조 `diarytb`
--

CREATE TABLE `diarytb` (
  `diary_id` int(11) NOT NULL,
  `diary_title` varchar(255) DEFAULT NULL,
  `diary_date` varchar(50) DEFAULT NULL,
  `diary_content` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- 테이블의 덤프 데이터 `diarytb`
--

INSERT INTO `diarytb` (`diary_id`, `diary_title`, `diary_date`, `diary_content`) VALUES
(31, '누가 내 머리에 똥 쌌어?', '1119', '두더지가 해가 떴나 안떴나 보려고 땅 위로 고개를 쑥 내밀었는데 갑자기 두더지의 머리에 똥이 떨어졌어요.화가 난 두더지는 자기 머리 위에 싼 똥의 주인을 찿아 동물 친구들을 만나며 그 친구들의 똥을 하나하나 확인 했지요.그러다가 똥을 먹고 있는 파리를 만나 자기 머리 위의 똥이 정육점집 개 뚱뚱이 한스의 똥이라는 것을 알고 한스의 집 위로 올라가 작고 까만 곶감씨 같은 똥을 한스의 이마에 떨어 뜨리고는 그제서야 기분좋게 웃으며 땅 속으로 사라졌대요.두더지의 기분 좋은 복수죠.너무 귀여워요'),
(32, '작별하지 않는다', '', '');

-- --------------------------------------------------------

--
-- 테이블 구조 `reviews`
--

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `review` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- 테이블의 덤프 데이터 `reviews`
--

INSERT INTO `reviews` (`id`, `title`, `review`, `created_at`) VALUES
(1, '작별하지 않는다', 'good', '2024-11-26 06:51:47'),
(2, '작별하지 않는다', 'holy', '2024-11-26 06:51:52'),
(3, '작별하지 않는다', 'good', '2024-11-26 06:52:49'),
(4, '작별하지 않는다', 'dfdf', '2024-11-26 07:07:43'),
(5, '작별하지 않는다', 'dfdfdfdfdf', '2024-11-26 07:07:46'),
(7, '데미안', 'daebak', '2024-11-26 07:55:08'),
(8, '데미안', 'dfdfdf', '2024-11-26 07:57:48'),
(10, '불편한 편의점', 'dfdfdfefefdcd', '2024-11-26 08:00:20'),
(11, '불편한 편의점', 'efefdfdfefef', '2024-11-26 08:00:22');

-- --------------------------------------------------------

--
-- 테이블 구조 `users`
--

CREATE TABLE `users` (
  `userID` varchar(50) NOT NULL,
  `userPassword` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `userAge` int(50) NOT NULL,
  `userGender` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- 테이블의 덤프 데이터 `users`
--

INSERT INTO `users` (`userID`, `userPassword`, `userName`, `userAge`, `userGender`) VALUES
('12', '12', '121', 2, '여자'),
('123', '123', 'rr', 123, '남자'),
('555', '555', 'wew', 55, '남자'),
('ddd', '123', 'tt123132', 99, '여자'),
('hi', '1234', 'rdd', 12, '남자'),
('jj', '777', 'jj', 9, '남자'),
('ssd', '123', 'taeho', 23, '남자');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`);

--
-- 테이블의 인덱스 `diarytb`
--
ALTER TABLE `diarytb`
  ADD PRIMARY KEY (`diary_id`);

--
-- 테이블의 인덱스 `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`);

--
-- 테이블의 인덱스 `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `diarytb`
--
ALTER TABLE `diarytb`
  MODIFY `diary_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- 테이블의 AUTO_INCREMENT `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
