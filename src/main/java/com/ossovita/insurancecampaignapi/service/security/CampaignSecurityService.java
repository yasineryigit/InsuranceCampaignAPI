package com.ossovita.insurancecampaignapi.service.security;

import com.ossovita.insurancecampaignapi.security.CustomUserDetails;
import com.ossovita.insurancecampaignapi.service.CampaignService;
import org.springframework.stereotype.Service;

@Service
public class CampaignSecurityService {

    CampaignService campaignService;

    public CampaignSecurityService(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    public boolean isAllowedToCreateCampaign(long userId, CustomUserDetails loggedInUser) {
        boolean isAllowed = false;
        //allow admin all the time
        if (loggedInUser.getUser().getUserRole().getUserRole().equals("Admin")) {
            isAllowed = true;
            //allow company if and only if user Id belongs itself
        } else if (loggedInUser.getUser().getUserRole().getUserRole().equals("Company")) {
            isAllowed = (userId == loggedInUser.getUser().getUserId());
        }

        return isAllowed;

    }


    public boolean isAllowedToUpdateCampaign(long campaignId, CustomUserDetails loggedInUser) {
        boolean isAllowed = false;
        //allow admin all the time
        if (loggedInUser.getUser().getUserRole().getUserRole().equals("Admin")) {
            isAllowed = true;
        } else if (loggedInUser.getUser().getUserRole().getUserRole().equals("Company")) {
            isAllowed = (campaignService.findById(campaignId).getUserId() == loggedInUser.getUser().getUserId());
        }
        return isAllowed;
    }
}
