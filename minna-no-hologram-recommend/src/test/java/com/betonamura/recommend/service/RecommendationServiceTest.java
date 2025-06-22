package com.betonamura.recommend.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.betonamura.recommend.controller.RecommendationRequest;
import com.betonamura.recommend.controller.RecommendationResponse;
import com.betonamura.recommend.data.DataProvider;
import com.betonamura.recommend.data.UserHistoryData;
import com.betonamura.recommend.data.UserInteractionData;
import com.betonamura.recommend.domain.common.ContentType;
import com.betonamura.recommend.domain.diy.DIYMetadata;
import com.betonamura.recommend.domain.diy.DiyCard;
import com.betonamura.recommend.domain.user.UserHistory;
import com.betonamura.recommend.domain.user.UserInteraction;
import com.betonamura.recommend.domain.video.VideoCard;
import com.betonamura.recommend.domain.video.VideoMetadata;
import com.betonamura.recommend.repository.RecommendationRepositoryImpl;
import com.betonamura.recommend.repository.SlmRepository;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

        @Mock
        private DataProvider dataProvider;

        @Mock
        private UserHistoryData userHistoryData;

        @Mock
        private UserInteractionData userInteractionData;

        @Mock
        private SlmRepository slmService;

        @InjectMocks
        private RecommendationRepositoryImpl recommendationService;

        private List<UserHistory> mockUserHistory;
        private List<VideoMetadata> mockVideos;
        private List<UserInteraction> mockInteractions;

        @BeforeEach
        void setUp() {
                // Setup mock user history
                mockUserHistory = new ArrayList<>();
                mockUserHistory.add(UserHistory.builder()
                                .id(1L)
                                .userId("user123")
                                .videoId("video1")
                                .watchDurationSeconds(120)
                                .completed(true)
                                .watchedAt(LocalDateTime.now().minusDays(1))
                                .build());

                mockUserHistory.add(UserHistory.builder()
                                .id(2L)
                                .userId("user123")
                                .videoId("video2")
                                .watchDurationSeconds(90)
                                .completed(false)
                                .watchedAt(LocalDateTime.now().minusDays(2))
                                .build());

                // Setup mock videos
                mockVideos = new ArrayList<>();
                mockVideos.add(VideoMetadata.builder()
                                .videoId("video1")
                                .title("Test Video 1")
                                .description("Description for test video 1")
                                .thumbnailUrl("http://example.com/thumb1.jpg")
                                .categoryId("category-A")
                                .tags("tag1,tag2")
                                .contentType(ContentType.VIDEO)
                                .viewCount(100)
                                .likeCount(50)
                                .build());

                mockVideos.add(VideoMetadata.builder()
                                .videoId("video2")
                                .title("Test Video 2")
                                .description("Description for test video 2")
                                .thumbnailUrl("http://example.com/thumb2.jpg")
                                .categoryId("category-B")
                                .tags("tag2,tag3")
                                .contentType(ContentType.VIDEO)
                                .viewCount(200)
                                .likeCount(100)
                                .build());

                // Setup mock interactions
                mockInteractions = new ArrayList<>();
                mockInteractions.add(UserInteraction.builder()
                                .id(1L)
                                .userId("user123")
                                .videoId("video1")
                                .action("WATCH")
                                .watchTimeSeconds(120)
                                .interactionTime(LocalDateTime.now().minusDays(1))
                                .build());
        }

        @Test
        void testGetVideoRecommendations() {
                String currentId = "video1";
                Integer limit = 5;

                // Setup request for video category
                RecommendationRequest request = new RecommendationRequest();
                request.setCategoryId("video");
                request.setCurrentId(currentId);
                request.setLimit(limit);

                // Setup minimal required mocks
                List<String> recommendedIds = List.of("video2", "video3");
                when(dataProvider.getVideoById(currentId)).thenReturn(Optional.of(mockVideos.get(0)));
                when(slmService.getRecommendations(anyMap(), anySet(), anyInt())).thenReturn(recommendedIds);
                when(dataProvider.getVideoById("video2")).thenReturn(Optional.of(mockVideos.get(1)));

                // Call the service
                RecommendationResponse result = recommendationService.getRecommendations(request);

                // Verify response
                assertNotNull(result);
                assertNotNull(result.getContent());
                assertTrue(result.getContent().size() > 0);
                // Check that each content is a VideoCard and not the current video
                result.getContent().forEach(card -> {
                        assertTrue(card instanceof VideoCard, "Content should be a VideoCard");
                        VideoCard video = VideoCard.class.cast(card);
                        assertNotNull(video.getSimilarityScore(), "Video should have a similarity score");
                        assertNotEquals(currentId, video.getId(), "Should not return the current video");
                });
        }

        @Test
        void testGetDiyRecommendations() {
                String currentId = "diy1";
                Integer limit = 5;

                // Setup request for DIY category
                RecommendationRequest request = new RecommendationRequest();
                request.setCategoryId("diy");
                request.setCurrentId(currentId);
                request.setLimit(limit);

                // Create mock DIY data
                DIYMetadata currentDiy = DIYMetadata.builder()
                                .diyId(currentId)
                                .title("Test DIY 1")
                                .categoryId("craft")
                                .build();

                List<DIYMetadata> relatedDiys = List.of(
                                DIYMetadata.builder()
                                                .diyId("diy2")
                                                .title("Test DIY 2")
                                                .categoryId("craft")
                                                .build(),
                                DIYMetadata.builder()
                                                .diyId("diy3")
                                                .title("Test DIY 3")
                                                .categoryId("craft")
                                                .build());

                // Setup minimal required mocks
                when(dataProvider.getDIYById(currentId)).thenReturn(Optional.of(currentDiy));
                when(dataProvider.getDIYsByCategory("craft")).thenReturn(relatedDiys);

                // Call the service
                RecommendationResponse result = recommendationService.getRecommendations(request);

                // Verify response
                assertNotNull(result);
                assertNotNull(result.getContent());
                assertTrue(result.getContent().size() > 0);
                // Check that each content is a DiyCard and not the current DIY
                result.getContent().forEach(card -> {
                        assertTrue(card instanceof DiyCard, "Content should be a DiyCard");
                        DiyCard diy = DiyCard.class.cast(card);
                        assertNotNull(diy.getSimilarityScore(), "DIY should have a similarity score");
                        assertNotEquals(currentId, diy.getId(), "Should not return the current DIY");
                });
        }

        @Test
        void testRecordFeedback() {
                // Test data
                String userId = "user123";
                String videoId = "video1";
                String action = "WATCH";
                Integer watchTime = 120;

                // Call the service
                boolean result = recommendationService.recordFeedback(userId, videoId, action, watchTime);

                // Verify
                assertTrue(result);
                verify(userInteractionData).save(any(UserInteraction.class));
                verify(userHistoryData).save(any(UserHistory.class));
        }
}
