package com.ossovita.insurancecampaignapi.entity;

import com.ossovita.insurancecampaignapi.enums.CampaignEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "campaign_events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignEvent {

    @Id
    @SequenceGenerator(name = "campaign_event_seq", allocationSize = 1)
    @GeneratedValue(generator = "campaign_event_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "campaign_event_id")
    private long campaignEventId;

    @Column(name = "campaign_event_time")
    private LocalDateTime campaignEventTime;

    @Column(name = "campaign_event_type")
    @Enumerated(EnumType.STRING)
    private CampaignEventType campaignEventType;

    @Column(name = "campaign_event_details")
    private String campaignEventDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", insertable = false, updatable = false)
    private Campaign campaign;

    @Column(name = "campaign_id")
    private long campaignId;


}
