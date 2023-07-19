package com.laioffer.twitch;

import com.laioffer.twitch.db.FavoriteRecordRepository;
import com.laioffer.twitch.db.ItemRepository;
import com.laioffer.twitch.db.entity.FavoriteRecordEntity;
import com.laioffer.twitch.db.entity.ItemEntity;
import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.favorite.FavoriteService;
import com.laioffer.twitch.model.ItemType;
import com.laioffer.twitch.model.TypeGroupedItemList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private FavoriteRecordRepository favoriteRecordRepository;
    @Captor
    ArgumentCaptor<FavoriteRecordEntity> favoriteRecordEntityArgumentCaptor;
    private FavoriteService favoriteService;


    @BeforeEach
    public void setup() {
        favoriteService = new FavoriteService(itemRepository, favoriteRecordRepository);
    }

    @Test
    public void whenItemNotExist_setFavoriteItem_shouldSaveItem() {
        UserEntity user = new UserEntity(1L, "user", "foo", "bar", "123456");
        ItemEntity item = new ItemEntity(null, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
        ItemEntity persisted = new ItemEntity(1L, "twitchId", "title", "url", "thumb", "broadcaster", "gameid", ItemType.VIDEO);
        Mockito.when(itemRepository.findByTwitchId("twitchId")).thenReturn(null);
        Mockito.when(itemRepository.save(item)).thenReturn(persisted);


        favoriteService.setFavoriteItem(user, item);


        Mockito.verify(itemRepository).save(item);
    }


}
