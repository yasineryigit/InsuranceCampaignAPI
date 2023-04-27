package com.ossovita.insurancecampaignapi.entity;

import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "insurance_campaigns")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceCampaign {

    @Id
    @SequenceGenerator(name = "insurance_campaign_seq", allocationSize = 1)
    @GeneratedValue(generator = "insurance_campaign_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "insurance_campaign_id")
    private long insuranceCampaignId;

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


}
