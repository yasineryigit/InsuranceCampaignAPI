package com.ossovita.insurancecampaignapi.service.impl;

import com.ossovita.insurancecampaignapi.entity.CampaignCategory;
import com.ossovita.insurancecampaignapi.error.exception.IdNotFoundException;
import com.ossovita.insurancecampaignapi.repository.CampaignCategoryRepository;
import com.ossovita.insurancecampaignapi.service.CampaignCategoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CampaignCategoryServiceImpl implements CampaignCategoryService {

    CampaignCategoryRepository campaignCategoryRepository;

    public CampaignCategoryServiceImpl(CampaignCategoryRepository campaignCategoryRepository) {
        this.campaignCategoryRepository = campaignCategoryRepository;
    }

    public CampaignCategory findById(long campaignCategoryId) {
        return campaignCategoryRepository.findById(campaignCategoryId)
                .orElseThrow(() -> new IdNotFoundException("CampaignCategory not found by given id: " + campaignCategoryId));
    }
}
