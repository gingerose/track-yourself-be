package com.track.yourself.service;

import com.track.yourself.models.dto.CollectionDto;
import com.track.yourself.models.dto.FindCollectionsRequest;
import com.track.yourself.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.track.yourself.util.Util.*;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    public List<CollectionDto> searchCollectionsByParams(FindCollectionsRequest collectionsRequest) {
        Date defaultDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(defaultDate);
        if (collectionsRequest.getFirstDate() == null) {
            calendar.add(Calendar.YEAR, -1);
            collectionsRequest.setFirstDate(calendar.getTime());
        }

        if (collectionsRequest.getSecondDate() == null) {
            calendar.add(Calendar.YEAR, 2);
            collectionsRequest.setSecondDate(calendar.getTime());
        }

        if (collectionsRequest.getPage() == null)
            collectionsRequest.setPage(defaultPage);
        else if (collectionsRequest.getAmount() == null)
            collectionsRequest.setAmount(defaultCollectionsAmount);

        collectionsRequest.setTitle(getParam(collectionsRequest.getTitle()));

        List<Object[]> resultList = collectionRepository.findLimited(
                collectionsRequest.getUserId(),
                collectionsRequest.getFirstDate(),
                collectionsRequest.getSecondDate(),
                collectionsRequest.getTitle(),
                PageRequest.of(collectionsRequest.getPage(), collectionsRequest.getAmount()));

        List<CollectionDto> collections = new ArrayList<>();
        for (Object[] result : resultList) {
            CollectionDto collectionDto = new CollectionDto(
                    (Integer) result[0],
                    (String) result[1],
                    (Long) result[2],
                    (Long) result[3]
            );
            collections.add(collectionDto);
        }
        return collections;
    }
}
