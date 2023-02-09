package ru.skypro.ads.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class UserRecord {
    private Long id;
    private String phone;
    private String lastName;
    private String firstName;
    private String email;
    private String city;
    private String regDate;

    public UserRecord(Long id, String phone, String lastName, String firstName, String email, String city, String regDate) {
        this.id = id;
        this.phone = phone;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.city = city;
        this.regDate = regDate;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", regDate='" + regDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecord userRecord = (UserRecord) o;
        return Objects.equals(id, userRecord.id) && Objects.equals(phone, userRecord.phone) && Objects.equals(lastName, userRecord.lastName) && Objects.equals(firstName, userRecord.firstName) && Objects.equals(email, userRecord.email) && Objects.equals(city, userRecord.city) && Objects.equals(regDate, userRecord.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, lastName, firstName, email, city, regDate);
    }
}
