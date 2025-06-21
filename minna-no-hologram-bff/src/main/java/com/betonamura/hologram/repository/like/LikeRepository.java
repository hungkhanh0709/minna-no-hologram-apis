package com.betonamura.hologram.repository.like;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LikeRepository {
    // Dummy in-memory store for demo (should use DB or cache in real app)
    private final Set<String> likedContent = new HashSet<>();

    /**
     * Checks if the content of the given type and id has already been liked by the
     * client.
     *
     * @param type     The type of content (e.g., "video", "diy").
     * @param id       The unique identifier of the content.
     * @param clientId The ID of the client liking the content (can be null).
     * @return true if already liked, false otherwise.
     */
    public boolean alreadyLiked(final String type, final String id, final String clientId) {
        String key = type + ":" + id + ":" + (clientId != null ? clientId : "");
        return likedContent.contains(key);
    }

    /**
     * Likes the content of the given type and id for the client.
     *
     * @param type     The type of content (e.g., "video", "diy").
     * @param id       The unique identifier of the content.
     * @param clientId The ID of the client liking the content (can be null).
     * @return true if the like was successful, false if already liked or invalid.
     */
    public boolean like(final String type, final String id, final String clientId) {
        if (!StringUtils.hasText(type) || !StringUtils.hasText(id)) {
            return false;
        }
        final String key = type + ":" + id + ":" + (clientId != null ? clientId : "");
        synchronized (likedContent) {
            if (likedContent.contains(key)) {
                return false; // Already liked
            }
            likedContent.add(key);
            return true;
        }
    }
}