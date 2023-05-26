package com.uade.ad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuarios")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String avatar;

    public User(String id, String name, String email, String password, String avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }
    public User(String id, String email, String password) {
        this(id, "", email, password, "");
    }

    public User toDto() {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        return user;
    }

}
