package com.example.testlitepal;

import org.litepal.annotation.Column;

public class Words {


        @Column(unique = true, defaultValue = "unknown")
        private Integer id;
        private String word;
        private String chineses;

        public Integer getId() {
            return id;
        }

        public String getWord() {
            return word;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public void setChineses(String chineses) {
            this.chineses = chineses;
        }

        public String getChineses() {
            return chineses;
        }
// generated getters and setters.



}
