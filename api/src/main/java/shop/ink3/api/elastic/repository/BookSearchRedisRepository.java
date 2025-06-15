package shop.ink3.api.elastic.repository;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import shop.ink3.api.common.config.ElasticsearchConfig;

@ConditionalOnBean(ElasticsearchConfig.class)
@RequiredArgsConstructor
@Repository
public class BookSearchRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String VIEW_COUNT_KEY = "book:view_counts";
    private static final String SEARCH_COUNT_KEY = "book:search_counts";

    public void incrementViewCount(long bookId) {
        redisTemplate.opsForHash().increment(VIEW_COUNT_KEY, String.valueOf(bookId), 1);
    }

    public void incrementSearchCount(long bookId) {
        redisTemplate.opsForHash().increment(SEARCH_COUNT_KEY, String.valueOf(bookId), 1);
    }

    public Map<Object, Object> getAllViewCounts() {
        return redisTemplate.opsForHash().entries(VIEW_COUNT_KEY);
    }

    public Map<Object, Object> getAllSearchCounts() {
        return redisTemplate.opsForHash().entries(SEARCH_COUNT_KEY);
    }

    public void clearAllCounts() {
        redisTemplate.delete(VIEW_COUNT_KEY);
        redisTemplate.delete(SEARCH_COUNT_KEY);
    }
}
