package com.baeldung.spring.jdbc.joins;

class ArticleWithAuthor {

        private String title;

        private String authorFirstName;

        private String authorLastName;

        public ArticleWithAuthor(String title, String authorFirstName, String authorLastName) {
                this.title = title;
                this.authorFirstName = authorFirstName;
                this.authorLastName = authorLastName;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getAuthorFirstName() {
                return authorFirstName;
        }

        public void setAuthorFirstName(String authorFirstName) {
                this.authorFirstName = authorFirstName;
        }

        public String getAuthorLastName() {
                return authorLastName;
        }

        public void setAuthorLastName(String authorLastName) {
                this.authorLastName = authorLastName;
        }

}
