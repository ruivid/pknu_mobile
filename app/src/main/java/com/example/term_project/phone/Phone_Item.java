package com.example.term_project.phone;

import java.util.concurrent.atomic.AtomicInteger;

public class Phone_Item {
    String name;
    String phone_number;
    String email;
    int imageId;
    String Id;

    public Phone_Item(Builder builder) {
        this.name = builder.name;
        this.phone_number = builder.phone_number;
        this.email = builder.email;
        this.imageId = builder.imageId;
        this.Id = builder.Id;
    }

    public static Builder builder() { // 객체 생성 방식을 builder 패턴으로 변경
        return new Builder();
    }
    public static class Builder {
        private String name;
        private String phone_number;
        private String email;
        private int imageId;
        private String Id;
        private Builder() {};

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder phone_number(String phone_number) {
            this.phone_number = phone_number;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder imageId(int imageId) {
            this.imageId = imageId;
            return this;
        }
        public Builder Id(String Id) {
            this.Id = Id;
            return this;
        }
        public Phone_Item build() {
            return new Phone_Item(this);
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setImageIdId(int imageId) {
        this.imageId = imageId;
    }
    public int getImageIdId() {
        return imageId;
    }
    public String getId() { return Id; }

    public String[] getDetail() {
        return new String[]{this.name, this.phone_number, this.email, this.Id};
    }
}
