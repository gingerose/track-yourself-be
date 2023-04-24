package com.track.yourself.controllers;

import com.track.yourself.models.Collection;
import com.track.yourself.models.CollectionItem;
import com.track.yourself.models.dto.FindCollectionItemsRequest;
import com.track.yourself.models.dto.FindCollectionsRequest;
import com.track.yourself.repository.CollectionItemRepository;
import com.track.yourself.repository.CollectionRepository;
import com.track.yourself.repository.UserRepository;
import com.track.yourself.service.CollectionItemService;
import com.track.yourself.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/users/collections")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CollectionsController {
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CollectionItemRepository collectionItemRepository;
    @Autowired
    private CollectionItemService collectionItemService;

    @PostMapping
    public ResponseEntity<?> createCollection(@RequestBody Collection collection) {
        if (userRepository.findById(collection.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        collection.setDate(new Date());
        return ResponseEntity.ok(collectionRepository.save(collection));
    }

    @PutMapping
    public ResponseEntity<?> updateCollection(@RequestBody Collection collection) {
        if(collectionRepository.findById(collection.getCollectionId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection not found!");
        }
        return ResponseEntity.ok(collectionRepository.save(collection));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCollection(@RequestParam Integer collectionId) {
        if(collectionRepository.findById(collectionId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection not found!");
        }
        collectionRepository.deleteById(collectionId);
        return ResponseEntity.ok("Collection was deleted");
    }

    @GetMapping
    public ResponseEntity<?> getCollections(@RequestBody FindCollectionsRequest findCollectionsRequest) {
        if (userRepository.findById(findCollectionsRequest.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        return ResponseEntity.ok(collectionService.searchCollectionsByParams(findCollectionsRequest));
    }

    @PostMapping("/{collectionId}/items")
    public ResponseEntity<?> createCollectionItem(@RequestBody CollectionItem collectionItem, @PathVariable int collectionId) {
        if (collectionRepository.findById(collectionId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection not found!");
        }

        return ResponseEntity.ok(collectionItemRepository.save(collectionItem));
    }

    @PutMapping("/{collectionId}/items")
    public ResponseEntity<?> updateCollectionItem(@RequestBody CollectionItem collectionItem, @PathVariable int collectionId) {
        if(collectionRepository.findById(collectionId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection not found!");
        }
        return ResponseEntity.ok(collectionItemRepository.save(collectionItem));
    }

    @DeleteMapping("/{collectionId}/items")
    public ResponseEntity<?> deleteCollectionItem(@RequestParam Integer collectionItemId, @PathVariable int collectionId) {
        if(collectionRepository.findById(collectionId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection not found!");
        }
        collectionItemRepository.deleteById(collectionItemId);
        return ResponseEntity.ok("Collection Item was deleted");
    }

    @GetMapping("/{collectionId}/items")
    public ResponseEntity<?> getCollectionItem(@RequestBody FindCollectionItemsRequest collectionItemsRequest,
                                               @PathVariable int collectionId) {
        if(collectionRepository.findById(collectionId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection not found!");
        }
        return ResponseEntity.ok(collectionItemService.searchCollectionItemsByParams(collectionItemsRequest, collectionId));
    }
}
