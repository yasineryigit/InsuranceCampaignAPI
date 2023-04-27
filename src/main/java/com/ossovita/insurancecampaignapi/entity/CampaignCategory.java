package com.ossovita.insurancecampaignapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "campaign_category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignCategory {

    @Id
    @SequenceGenerator(name = "campaign_category_seq", allocationSize = 1)
    @GeneratedValue(generator = "campaign_category_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "campaign_category_id")
    private long campaignCategoryId;

    @Column(name = "campaign_category_name")
    private String campaignCategoryName;

    @Column(name = "campaign_category_requires_approvement")
    private boolean campaignCategoryRequiresApprovement;

    @OneToMany(mappedBy = "campaignCategory", fetch = FetchType.LAZY)
    private List<InsuranceCampaign> insuranceCampaignList;


}
