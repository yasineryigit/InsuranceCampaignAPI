package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.CampaignEvent;
import com.ossovita.insurancecampaignapi.repository.CampaignEventRepository;
import com.ossovita.insurancecampaignapi.service.CampaignEventService;
import org.springframework.stereotype.Service;

@Service
public class CampaignEventServiceImpl implements CampaignEventService {

    CampaignEventRepository campaignEventRepository;

    public CampaignEventServiceImpl(CampaignEventRepository campaignEventRepository) {
        this.campaignEventRepository = campaignEventRepository;
    }

    @Override
    public CampaignEvent save(CampaignEvent campaignEvent) {
        return campaignEventRepository.save(campaignEvent);
    }
}
