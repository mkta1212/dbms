作業寫不出來卻沒人可以一起討論？期中快到了卻沒人可以一起衝刺？如果你有這些困擾不用緊張，趕緊上「I’min」尋找你的最佳戰友！
「I’m in」是一個提供給某大學學生發起讀書會的平台，主要目的是幫助該校學生在特定日期和時段內，透過租借教室、召開一次性讀書會，以解決修課時，若遇到課程問題需要及時媒合同學討論卻苦無方法的窘境。所謂的「一次性讀書會」是指一種特定形式的學習活動，專為大學學生提供的平台上的主要活動類型。在這種讀書會活動中，每次讀書會都是獨立的，組成該讀書會的成員可以是同一群人或不同；每次討論的課程內容也可以相同或不同。但是無論組成成員或討論內容是否相同，只要每次討論就需要重新發起一個新的讀書會，每個讀書會的編號也都不同。換言之，本系統中的「讀書會」是一個活動而非組織。
根據不同的功能及掌控權限，「I’m in」系統的用戶可以分為兩種身分，分別是User 及 Admin。若作為一般使用者，身分別定義為User，可依照自身需求，選擇發起讀書會的活動，或是參與平台上由其他人發起的讀書會。若選擇發起讀書會，則可以透過介面輸入讀書會的內容、讀書會預計招攬的人數上限，以及想討論的是哪堂課的課程等資訊，接著選擇想租借的教室，並確定預約日期與時段。如果使用者只想參與其他人發起的讀書會，則可瀏覽平台上現有的讀書會，並選擇感興趣的進行加入。而Admin 則是「I’m in」系統的管理角色，主要負責管理課程及租借教室的資訊，並且可查詢所有使用者的活動紀錄，包括該使用者曾經發起及參與的讀書會有哪些、該讀書會的詳細資訊為何等等，並據此做分析以優化平台營運。

1. 系統功能
1.1. 關於讀書會相關設定
系統上會提供教室開放時間的時段，以24小時制的整數讓使用者選擇，可選範圍則是系統設定的教室開放時間1，目前設定為8到21。系統限定每一個時段訂為一小時，亦即若使用者在系統上預約13點，則表示可從下午一點使用到下午兩點，其他依此類推。系統目前開放的最小預約時段以一小時為單位，一次預約至多三個時段，且這些時段必須連續。例如若某使用者發起一場編號為A的讀書會，接著他想為這場讀書會的活動預約日期在2023/10/12，並選擇教室名稱「共同301」，此時系統會顯示這間教室目前可供預約時段，假設共有13、14、15、16、19五個可預約時段，則此時讀書會A可以預約14、15、16 三個時段，也可以預約13、14、15三個時段，但不能預約13、14、15、16，也不能預約16和19，因為後兩者都會違背至多三個時段且連續的限制。
1.2 給 User 的功能
在本系統中，User 可以執行以下功能：
  (1) 新增讀書會：使用者能透過設定參與人數上限以及輸入讀書會內容、想討論的課程名稱等相關資訊來發起一場讀書會，一旦發起讀書會，系統便會給定一個屬於該讀書會的編號，接著會列出目前可租借的教室資訊及可用的日期及時段供使用者選擇。待使用者選擇並確定後，系統會將這筆活動新增到資料庫。
  (2) 參加讀書會：使用者若想參加任一場讀書會，系統會將使用者選擇要參加的讀書會，作為一筆新的參加資料，新增至資料庫。
  (3) 刪除讀書會：使用者如果臨時不想參加，可刪除已參與但未結束的讀書會的參加資訊。
  (4) 查詢使用者曾經舉辦過的讀書會：使用者可以查詢自身曾建立過的讀書會，包括已結束與正在進行中的。
  (5) 查詢使用者曾經參與過的讀書會：使用者可以查詢自身曾參與過的讀書會，包括已結束與正在進行中的。
  (6) 查詢目前平台上的讀書會：使用者可查詢尚未結束且可參與的讀書會。
1.3 給 Admin 的功能
在本系統中，Admin 可以執行以下功能：
  (1) 管理課程：管理員可對課程資訊進行增刪改查的操作。
  (2) 管理教室：管理員可對租借教室的資訊進行增刪改查的操作。
  (3) 查詢使用者資訊：管理員可查詢所有使用者的活動紀錄，包括該使用者曾經發起及參與的讀書會有哪些。
  (4) 查詢讀書會資訊：管理員可查詢這些讀書會的詳細資訊。
