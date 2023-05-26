package com.uade.ad.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id_user")
    private String idUser;
    private String name;
    private String email;
    private String password;
    private String avatar;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user")
            , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private List<Roles> roles = new ArrayList<>();


    public User toDto() {
        User user = new User();
        user.setIdUser(this.idUser);
        user.setEmail(this.email);
        user.setRoles(this.roles);
        return user;
    }

}

