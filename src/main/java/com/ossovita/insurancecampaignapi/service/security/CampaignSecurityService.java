package com.ossovita.insurancecampaignapi.service.security;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.service.CampaignService;
import org.springframework.stereotype.Service;

@Service
public class CampaignSecurityService {

    CampaignService campaignService;

    public CampaignSecurityService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    public boolean isAllowedToCreateCampaign(long userId, User loggedInUser) {
        boolean isAllowed = false;
        //allow admin all the time
        if (loggedInUser.getUserRole().getUserRole().equals("Admin")) {
            isAllowed = true;
            //allow company if and only if user Id belongs itself
        } else if (loggedInUser.getUserRole().getUserRole().equals("Company")) {
            isAllowed = (userId == loggedInUser.getUserId());
        }

        return isAllowed;

    }


    public boolean isAllowedToUpdateCampaign(long campaignId, User loggedInUser) {
        boolean isAllowed = false;
        //allow admin all the time
        if (loggedInUser.getUserRole().getUserRole().equals("Admin")) {
            isAllowed = true;
        } else if (loggedInUser.getUserRole().getUserRole().equals("Company")) {
            isAllowed = (campaignService.findById(campaignId).getUserId() == loggedInUser.getUserId());
        }
        return isAllowed;
    }
}
