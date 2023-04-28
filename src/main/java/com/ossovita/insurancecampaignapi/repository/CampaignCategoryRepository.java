package com.ossovita.insurancecampaignapi.repository;

import com.ossovita.insurancecampaignapi.entity.CampaignCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignCategoryRepository extends JpaRepository<CampaignCategory, Long> {
}
