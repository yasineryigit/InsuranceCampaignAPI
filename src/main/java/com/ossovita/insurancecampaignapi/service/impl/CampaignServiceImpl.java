package com.ossovita.insurancecampaignapi.service.impl;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.entity.CampaignEvent;
import com.ossovita.insurancecampaignapi.enums.CampaignEventType;
import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import com.ossovita.insurancecampaignapi.error.exception.IdNotFoundException;
import com.ossovita.insurancecampaignapi.error.exception.RepetitiveCampaignException;
import com.ossovita.insurancecampaignapi.payload.CampaignDto;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByCompanyRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignStatusRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateMultipleCampaignStatus;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.payload.response.StatisticsResponse;
import com.ossovita.insurancecampaignapi.repository.CampaignRepository;
import com.ossovita.insurancecampaignapi.service.CampaignCategoryService;
import com.ossovita.insurancecampaignapi.service.CampaignEventService;
import com.ossovita.insurancecampaignapi.service.CampaignService;
import com.ossovita.insurancecampaignapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    CampaignRepository campaignRepository;
    CampaignCategoryService campaignCategoryService;
    CampaignEventService campaignEventService;
    UserService userService;
    ModelMapper modelMapper;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignCategoryService campaignCategoryService, CampaignEventService campaignEventService, UserService userService, ModelMapper modelMapper) {
        this.campaignRepository = campaignRepository;
        this.campaignCategoryService = campaignCategoryService;
        this.campaignEventService = campaignEventService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    //admin, company
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Company')")
    @Override
    public CampaignResponse createCampaign(CampaignDto campaignDto) {
        Campaign campaign = modelMapper.map(campaignDto, Campaign.class);
        //check repetitive campaign
        if (campaignRepository.isCampaignExists(campaign)) {
            campaign.setCampaignStatus(CampaignStatus.REPETITIVE);
        } else { // if campaign is unique
            //validate userId //TODO | remove?
            campaign.setUserId(userService.findByUserId(campaignDto.getUserId()).getUserId());
            setCampaignStatusDependsOnRequiresApprovement(campaign);
        }
        Campaign savedCampaign = campaignRepository.save(campaign);
        saveCampaignEvent(savedCampaign, CampaignEventType.CREATE);
        return modelMapper.map(savedCampaign, CampaignResponse.class);
    }

    //admin, company
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Company')")
    @Override
    public CampaignResponse deactivateCampaignStatus(long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IdNotFoundException("Campaign not found by given id: " + campaignId));
        //Repetitive Campaign can not be updated
        setCampaignStatusIfNotRepetitive(campaign, CampaignStatus.DEACTIVATED);
        //The user can "Deactivate" the "Active" status or "Pending Approval" status.
        if (campaign.getCampaignStatus().equals(CampaignStatus.PENDING_APPROVAL) || campaign.getCampaignStatus().equals(CampaignStatus.ACTIVE)) {
            campaign.setCampaignStatus(CampaignStatus.DEACTIVATED);
        }
        saveCampaignEvent(campaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(campaign), CampaignResponse.class);


    }

    //admin
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public CampaignResponse updateCampaignByAdmin(UpdateCampaignByAdminRequest updateCampaignByAdminRequest) {
        Campaign updatedCampaign = updateCampaign(modelMapper.map(updateCampaignByAdminRequest, Campaign.class));
        updatedCampaign.setCampaignStatus(updateCampaignByAdminRequest.getCampaignStatus());
        saveCampaignEvent(updatedCampaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(updatedCampaign), CampaignResponse.class);
    }

    //company
    @PreAuthorize("hasAuthority('Company')")
    @Override
    public CampaignResponse updateCampaignByCompany(UpdateCampaignByCompanyRequest updateCampaignByAdminRequest) {
        Campaign updatedCampaign = updateCampaign(modelMapper.map(updateCampaignByAdminRequest, Campaign.class));
        setCampaignStatusDependsOnRequiresApprovement(updatedCampaign);
        saveCampaignEvent(updatedCampaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(updatedCampaign), CampaignResponse.class);
    }

    //admin
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public CampaignResponse updateCampaignStatus(UpdateCampaignStatusRequest updateCampaignStatusRequest) {
        Campaign campaign = campaignRepository.findById(updateCampaignStatusRequest.getCampaignId())
                .orElseThrow(() -> new IdNotFoundException("Campaign not found by given id: " + updateCampaignStatusRequest.getCampaignId()));
        //Repetitive Campaign can not be updated
        campaign = setCampaignStatusIfNotRepetitive(campaign, updateCampaignStatusRequest.getCampaignStatus());
        saveCampaignEvent(campaign, CampaignEventType.UPDATE);
        return modelMapper.map(campaignRepository.save(campaign), CampaignResponse.class);
    }

    //admin
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public List<CampaignResponse> updateMultipleCampaignStatus(UpdateMultipleCampaignStatus updateMultipleCampaignStatus) {
        List<Campaign> campaignList = campaignRepository.findAllById(updateMultipleCampaignStatus.getCampaignIdList());
        List<Campaign> updatedCampaignList = campaignList.stream()
                .map(campaign -> {
                    //Repetitive Campaign can not be updated
                    return setCampaignStatusIfNotRepetitive(campaign, updateMultipleCampaignStatus.getCampaignStatus());
                }).toList();
        updatedCampaignList.forEach(updatedCampaign -> saveCampaignEvent(updatedCampaign, CampaignEventType.UPDATE));
        return campaignRepository.saveAll(updatedCampaignList).stream()
                .map(campaign -> modelMapper.map(campaign, CampaignResponse.class)).toList();
    }

    //admin
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public StatisticsResponse getStatisticsForCampaigns() {
        return campaignRepository.getStatisticsForCampaigns();
    }


    public Campaign updateCampaign(Campaign campaignTemplate) {
        //check campaignId existence
        Campaign campaign = campaignRepository.findById(campaignTemplate.getCampaignId())
                .orElseThrow(() -> new IdNotFoundException("Campaign not found by given id: " + campaignTemplate.getCampaignId()));
        setCampaignStatusIfNotRepetitive(campaign, campaignTemplate.getCampaignStatus());
        //check campainCategoryId existence
        campaign.setCampaignCategoryId(campaignCategoryService.findById(campaignTemplate.getCampaignCategoryId()).getCampaignCategoryId());
        campaign.setCampaignTitle(campaignTemplate.getCampaignTitle());
        campaign.setCampaignDescription(campaignTemplate.getCampaignDescription());
        return campaign;

    }

    //Repetitive Campaign can not be updated
    public Campaign setCampaignStatusIfNotRepetitive(Campaign campaign, CampaignStatus campaignStatus) {
        if (!campaign.getCampaignStatus().equals(CampaignStatus.REPETITIVE)) {
            campaign.setCampaignStatus(campaignStatus);
            return campaign;
        } else {
            throw new RepetitiveCampaignException("Repetitive Campaign with id: " + campaign.getCampaignId() + " cannot be updated");
        }
    }


    public Campaign setCampaignStatusDependsOnRequiresApprovement(Campaign campaign) {
        // check isCampaignCategoryRequiresApprovement
        if (campaignCategoryService.findById(campaign.getCampaignCategoryId()).isCampaignCategoryRequiresApprovement()) {
            campaign.setCampaignStatus(CampaignStatus.PENDING_APPROVAL);
        } else {
            campaign.setCampaignStatus(CampaignStatus.ACTIVE);
        }
        return campaign;
    }

    public void saveCampaignEvent(Campaign campaign, CampaignEventType campaignEventType) {
        CampaignEvent campaignEvent = CampaignEvent.builder()
                .campaignId(campaign.getCampaignId())
                .campaignEventTime(LocalDateTime.now())
                .campaignEventType(campaignEventType)
                .campaignEventDetails(modelMapper.map(campaign, CampaignResponse.class).toString())
                .build();

        campaignEventService.save(campaignEvent);
    }


}
