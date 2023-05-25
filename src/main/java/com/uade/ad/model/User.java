package com.uade.ad.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class User {

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
