package com.ossovita.insurancecampaignapi.repository;

import com.ossovita.insurancecampaignapi.entity.InsuranceCampaign;
import com.ossovita.insurancecampaignapi.payload.InsuranceCampaignDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InsuranceCampaignRepository extends JpaRepository<InsuranceCampaign, Long> {

    @Query("select case when count(ic) > 0 then true else false end from InsuranceCampaign ic where ic.campaignTitle = :#{#insuranceCampaign.campaignTitle} and ic.campaignDescription = :#{#insuranceCampaign.campaignDescription} and ic.campaignCategoryId = :#{#insuranceCampaign.campaignCategoryId}")
    boolean isInsuranceCampaignExists(@Param("insuranceCampaign") InsuranceCampaign insuranceCampaign);

}
