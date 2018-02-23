package com.example.tansen.goparty1.Firebase;


public class FirebaseUserEntity {

    private String uId;

    private String email;

    private String name;

    private String country;

    private String phone;


    private String hobby;

    public FirebaseUserEntity(){
    }

    public FirebaseUserEntity(String uId, String email, String name, String country, String phone, String hobby) {
        this.uId = uId;
        this.email = email;
        this.name = name;
        this.country = country;
        this.phone = phone;

        this.hobby = hobby;
    }

    public String getuId() {
        return uId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }


    public String getHobby() {
        return hobby;
    }
}
