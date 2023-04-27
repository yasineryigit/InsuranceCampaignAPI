package com.ossovita.insurancecampaignapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @SequenceGenerator(name = "user_role_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_role_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "user_role_id")
    private long userRoleId;

    @Column(name = "user_role")
    private String userRole;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    private List<User> users;



}
