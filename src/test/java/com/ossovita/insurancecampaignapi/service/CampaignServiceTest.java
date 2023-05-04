package com.ossovita.insurancecampaignapi.service;

import com.ossovita.insurancecampaignapi.entity.Campaign;
import com.ossovita.insurancecampaignapi.entity.CampaignCategory;
import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.enums.CampaignStatus;
import com.ossovita.insurancecampaignapi.payload.request.CampaignRequest;
import com.ossovita.insurancecampaignapi.payload.request.UpdateCampaignByAdminRequest;
import com.ossovita.insurancecampaignapi.payload.response.CampaignResponse;
import com.ossovita.insurancecampaignapi.repository.CampaignRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @InjectMocks
    CampaignServiceImpl campaignService;
    @Mock
    CampaignRepository campaignRepository;
    @Mock
    CampaignCategoryService campaignCategoryService;
    @Mock
    CampaignEventService campaignEventService;
    @Mock
    UserService userService;
    @Spy
    ModelMapper modelMapper;

    User user;
    Campaign campaign;
    CampaignCategory campaignCategory;
    CampaignResponse campaignResponse;
    CampaignRequest campaignRequest;

    @BeforeEach
    void setUp() {

        //Create required objects
        user = User.builder()
                .userId(2)
                .userFirstName("Yasin")
                .userLastName("Eryigit")
                .userRoleId(2)
                .userPassword("User.123")
                .build();

        campaignCategory = CampaignCategory.builder()
                .campaignCategoryId(3)
                .campaignCategoryName("Private Health Insurance")
                .campaignCategoryRequiresApprovement(true)
                .build();
        campaign = Campaign.builder()
                .campaignId(1)
                .campaignTitle("title")
                .campaignDescription("description")
                .userId(user.getUserId())
                .campaignCategoryId(campaignCategory.getCampaignCategoryId())
                .build();
        campaignRequest = CampaignRequest.builder()
                .campaignTitle("title")
                .campaignDescription("description")
                .campaignCategoryId(campaignCategory.getCampaignCategoryId())
                .userId(user.getUserId())
                .build();
        campaignResponse = CampaignResponse.builder()
                .campaignId(1)
                .campaignTitle("title")
                .campaignDescription("description")
                .campaignStatus(CampaignStatus.PENDING_APPROVAL)
                .userId(user.getUserId())
                .campaignCategoryId(campaignCategory.getCampaignCategoryId())
                .build();

    }

    @DisplayName("Test CampaignService createCampaign method")
    @Description("Test the functionality of the createCampaign method in the CampaignServiceImpl class")
    @Test
    void createCampaign_ShouldReturnCampaignResponse() {
        // Arrange

        when(campaignRepository.isCampaignExists(campaign)).thenReturn(false);
        when(userService.findByUserId(campaignRequest.getUserId())).thenReturn(user);
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        when(campaignCategoryService.findById(campaign.getCampaignCategoryId())).thenReturn(campaignCategory);
        when(modelMapper.map(campaignRequest, Campaign.class)).thenReturn(campaign);


        //Act
        CampaignResponse actualResponse = campaignService.createCampaign(campaignRequest);

        // Assert
        assertEquals(campaignResponse, actualResponse);
    }

    @DisplayName("Test CampaignService deactivateCampaignStatus method")
    @Description("Test the functionality of the deactivateCampaignStatus method in the CampaignServiceImpl class")
    @Test
    void deactivateCampaignStatus_ShouldReturnDeactivatedCampaignResponse() {
        // Arrange
       Campaign campaign = Campaign.builder()
                .campaignId(1)
                .campaignTitle("title")
                .campaignDescription("description")
                .campaignStatus(CampaignStatus.REPETITIVE)
                .userId(user.getUserId())
                .campaignCategoryId(campaignCategory.getCampaignCategoryId())
                .build();
        CampaignResponse campaignResponse = CampaignResponse.builder()
                .campaignId(1)
                .campaignTitle("title")
                .campaignDescription("description")
                .campaignStatus(CampaignStatus.DEACTIVATED)
                .userId(user.getUserId())
                .campaignCategoryId(campaignCategory.getCampaignCategoryId())
                .build();

        when(campaignRepository.findById(campaign.getCampaignId())).thenReturn(Optional.of(campaign));
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        //when(modelMapper.map(campaign, CampaignResponse.class)).thenReturn(campaignResponse);

        // Act
        CampaignResponse actualResponse = campaignService.deactivateCampaignStatus(campaign.getCampaignId());

        // Assert
        assertEquals(campaignResponse, actualResponse);
    }


    @Test
    void updateCampaignByAdmin_ShouldReturnCampaignResponse() {
        // Arrange
        UpdateCampaignByAdminRequest request = new UpdateCampaignByAdminRequest();
        request.setCampaignId(1L);
        request.setCampaignTitle("New Campaign Title");
        request.setCampaignDescription("New Campaign Description");
        request.setCampaignCategoryId(2L);
        request.setCampaignStatus(CampaignStatus.ACTIVE);

        Campaign campaign = new Campaign();
        campaign.setCampaignId(1L);
        campaign.setCampaignTitle("Old Campaign Title");
        campaign.setCampaignDescription("Old Campaign Description");
        campaign.setCampaignCategoryId(2L);
        campaign.setCampaignStatus(CampaignStatus.PENDING_APPROVAL);

        CampaignResponse campaignResponse = new CampaignResponse();
        campaignResponse.setCampaignId(campaign.getCampaignId());
        campaignResponse.setCampaignTitle(campaign.getCampaignTitle());
        campaignResponse.setCampaignDescription(campaign.getCampaignDescription());
        campaignResponse.setCampaignCategoryId(campaign.getCampaignCategoryId());
        campaignResponse.setCampaignStatus(campaign.getCampaignStatus());

        when(campaignRepository.findById(request.getCampaignId())).thenReturn(Optional.of(campaign));
        when(campaignRepository.save(campaign)).thenReturn(campaign);
        when(modelMapper.map(request, Campaign.class)).thenReturn(campaign);
        when(campaignCategoryService.findById(request.getCampaignCategoryId())).thenReturn(new CampaignCategory());
        when(modelMapper.map(campaign, CampaignResponse.class)).thenReturn(campaignResponse);

        // Act
        CampaignResponse actualResponse = campaignService.updateCampaignByAdmin(request);

        // Assert
        assertEquals(campaignResponse, actualResponse);

    }


    @Test
    void updateCampaignByCompany() {
    }

    @Test
    void updateCampaignStatus() {
    }

    @Test
    void updateMultipleCampaignStatus() {
    }

    @Test
    void getStatisticsForCampaigns() {
    }

    @Test
    void updateCampaign() {
    }

    @Test
    void setCampaignStatusIfNotRepetitive() {
    }

    @Test
    void setCampaignStatusDependsOnRequiresApprovement() {
    }

    @Test
    void saveCampaignEvent() {
    }

    @Test
    void findById() {
    }
}