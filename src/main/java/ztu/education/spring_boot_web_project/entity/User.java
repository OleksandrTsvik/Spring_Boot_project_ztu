package ztu.education.spring_boot_web_project.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false, unique = true, length = 12)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true, length = 64)
    private String email;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "enabled", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isEnabled;
}