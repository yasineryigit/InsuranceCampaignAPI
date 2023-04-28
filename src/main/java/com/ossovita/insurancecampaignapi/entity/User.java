package com.ossovita.insurancecampaignapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private long userId;

    @NotNull
    @Size(min = 4, max = 255)
    @Email(message = "Email should be valid")
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{ossovita.constraint.password.Pattern.message}")
    @JsonIgnore
    @Column(name = "user_password")
    private String userPassword;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_first_name")
    private String userFirstName;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_last_name")
    private String userLastName;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Campaign> campaignList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id", insertable = false, updatable = false)
    private UserRole userRole;

    @Column(name = "user_role_id")
    private long userRoleId;

    private boolean enabled;

    private boolean locked;

}
