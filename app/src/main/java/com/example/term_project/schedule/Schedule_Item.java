package com.example.term_project.schedule;

public class Schedule_Item {
    String name;
    String date;
    String time;
    String place;
    String email;
    String Id;

    public Schedule_Item(Builder builder) {
        this.name = builder.name;
        this.date = builder.date;
        this.time = builder.time;
        this.place = builder.place;
        this.email = builder.email;
        this.Id = builder.Id;
    }

    public static Builder builder() { // 객체 생성 방식을 builder 패턴으로 변경
        return new Builder();
    }
    public static class Builder {
        private String name;
        private String date;
        private String time;
        private String place;
        private String email;
        private String Id;
        private Builder() {};

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder date(String date) {
            this.date = date;
            return this;
        }
        public Builder time(String time) {
            this.time = time;
            return this;
        }
        public Builder place(String place) {
            this.place = place;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder Id(String Id) {
            this.Id = Id;
            return this;
        }
        public Schedule_Item build() {
            return new Schedule_Item(this);
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setSchedule_Date(String date) {
        this.date = date;
    }
    public String getSchedule_Date() {
        return date;
    }

    public String[] getDetail() {
        return new String[]{this.name, this.date, this.time, this.place, this.email, this.Id};
    }
}
