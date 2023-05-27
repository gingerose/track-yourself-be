package com.track.yourself.service;

import com.track.yourself.models.CollectionItem;
import com.track.yourself.models.dto.FindCollectionItemsRequest;
import com.track.yourself.repository.CollectionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.track.yourself.util.Util.getParam;

@Service
public class CollectionItemService {
  @Autowired
  private CollectionItemRepository collectionItemRepository;
  public List<CollectionItem> searchCollectionItemsByParams(FindCollectionItemsRequest collectionItemRequest, int collectionId) {
    collectionItemRequest.setDescription(getParam(collectionItemRequest.getDescription()));
    collectionItemRequest.setStatus(getParam(collectionItemRequest.getStatus()));

    List<Object[]> resultList = collectionItemRepository.findLimited(
      collectionId,
      collectionItemRequest.getDescription(),
      collectionItemRequest.getStatus());

    List<CollectionItem> collectionItems = new ArrayList<>();
    for (Object[] result : resultList) {
      CollectionItem collectionItem = new CollectionItem(
        (Integer) result[0],
        (Integer) result[1],
        (String) result[3],
        (String) result[2]
      );
      collectionItems.add(collectionItem);
    }
    return collectionItems;
  }
}
