package com.epam.huntingService.entity;

import java.io.InputStream;
import java.util.Objects;

public class User {

    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String document;
    private InputStream uploadingDocument;
    private Integer roleID;
    private String role;

    public InputStream getUploadingDocument() {
        return uploadingDocument;
    }

    public void setUploadingDocument(InputStream uploadingDocument) {
        this.uploadingDocument = uploadingDocument;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                phone.equals(user.phone) &&
                Objects.equals(document, user.document) &&
                Objects.equals(uploadingDocument, user.uploadingDocument) &&
                roleID.equals(user.roleID) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, email, phone, document, uploadingDocument, roleID, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", document='" + document + '\'' +
                ", uploadingDocument=" + uploadingDocument +
                ", roleID=" + roleID +
                ", role='" + role + '\'' +
                '}';
    }
}
