curl --location "http://localhost:8081/api/articles/oop-best-practices/comments" ^
--header "Content-Type: application/json" ^
--data "{\"articleAuthor\": \"Andrey the Author\", \"comment\": \"Great article!\", \"commentAuthor\": \"Richard the Reader\"}"
