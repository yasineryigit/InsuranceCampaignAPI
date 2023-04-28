package com.ossovita.insurancecampaignapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "campaigns")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Campaign {

    @Id
    @SequenceGenerator(name = "campaign_seq", allocationSize = 1)
    @GeneratedValue(generator = "campaign_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "campaign_id")
    private long campaignId;

    @Column(name = "campaign_title")
    private String campaignTitle;

    @Column(name = "campaign_description")
    private String campaignDescription;

    @Column(name = "campaign_status")
    @Enumerated(EnumType.STRING)
    private CampaignStatus campaignStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_category_id", insertable = false, updatable = false)
    private CampaignCategory campaignCategory;

    @Column(name = "campaign_category_id")
    private long campaignCategoryId;

    @OneToMany(mappedBy = "campaign")
    @JsonIgnore
    private List<CampaignEvent> campaignEventList;


}
