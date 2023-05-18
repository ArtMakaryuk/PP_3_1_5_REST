package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long> implements UserDetails {

    private static final long serialVersionUID = 2715270014679085151L;
    @Column(name = "firstName")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 to 30")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Column(name = "age")
    @Positive(message = "Age should not be empty")
    private byte age;

    @Column(unique = true)
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    private Set<Role> roles = new HashSet<>();

    public User() {
    }
    public User(String firstName, String lastName, byte age, String email, String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(int roleId) {
        if (null == roles || 0 == roles.size()) {
            return false;
        }
        Optional<Role> findRole = roles.stream().filter(role -> roleId == role.getId()).findFirst();
        return findRole.isPresent();
    }
    @Override
    public String toString() {
        return String.format("User [id = %d; firstName = %s; lastName = %s; email = %s; password = %s; roles = (%s)]",
                this.getId(), firstName, lastName, email, password, Collections.singletonList(roles));
    }

    public boolean hasRole(String roleName) {
        if (null == roles || 0 == roles.size()) {
            return false;
        }
        Optional<Role> findRole = roles.stream().filter(role -> roleName.equals(role.getName())).findFirst();
        return findRole.isPresent();
    }
}
