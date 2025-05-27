curl --location "http://localhost:8080/api/articles/oop-best-practices/comments" ^
--header "Content-Type: application/json" ^
--data "{\"articleAuthor\": \"Andrey the Author\", \"text\": \"Great article!\", \"commentAuthor\": \"Richard the Reader\"}"