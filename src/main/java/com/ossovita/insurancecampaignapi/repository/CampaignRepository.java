package com.ossovita.insurancecampaignapi.repository;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("select case when count(ic) > 0 then true else false end from Campaign ic where ic.campaignTitle = :#{#campaign.campaignTitle} and ic.campaignDescription = :#{#campaign.campaignDescription} and ic.campaignCategoryId = :#{#campaign.campaignCategoryId}")
    boolean isCampaignExists(@Param("campaign") Campaign campaign);

    @Override
    List<Campaign> findAllById(Iterable<Long> longs);


    @Query("SELECT new com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse(" +
            "SUM(CASE WHEN campaign.campaignStatus = 'ACTIVE' THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN campaign.campaignStatus = 'PENDING_APPROVAL' THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN campaign.campaignStatus = 'DEACTIVATED' THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN campaign.campaignStatus = 'REPETITIVE' THEN 1 ELSE 0 END)" +
            ") FROM Campaign campaign")
    StatisticsResponse getStatisticsForCampaigns();









}
