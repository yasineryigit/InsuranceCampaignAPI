package com.ossovita.insurancecampaignapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
public class RefreshToken {

    @Id
    @SequenceGenerator(name = "refresh_token_seq", allocationSize = 1)
    @GeneratedValue(generator = "refresh_token_seq")
    @Column(name = "refresh_token_id")
    private long refreshTokenId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @Column(nullable = false, unique = true)
    private String token;


    @Column(nullable = false)
    private Instant expiryDate;

}
