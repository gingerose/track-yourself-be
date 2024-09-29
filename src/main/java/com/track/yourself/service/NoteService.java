package com.track.yourself.service;

import com.track.yourself.models.HabitItem;
import com.track.yourself.models.Note;
import com.track.yourself.models.dto.*;
import com.track.yourself.repository.CollectionRepository;
import com.track.yourself.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.track.yourself.util.Util.getParam;

@Service
public class NoteService {
  @Autowired
  private NoteRepository noteRepository;

  public List<NoteDto> searchNotesByParams(FindNotesRequest findNotesRequest) {
    Date defaultDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(defaultDate);
    if (findNotesRequest.getFirstDate() == null) {
      calendar.add(Calendar.YEAR, -1);
      findNotesRequest.setFirstDate(calendar.getTime());
    }

    if (findNotesRequest.getSecondDate() == null) {
      calendar.add(Calendar.YEAR, 2);
      findNotesRequest.setSecondDate(calendar.getTime());
    }

    findNotesRequest.setTitle(getParam(findNotesRequest.getTitle()));
    findNotesRequest.setCategory(getParam(findNotesRequest.getCategory()));

    List<Object[]> resultList = noteRepository.findLimited(
      findNotesRequest.getUserId(),
      findNotesRequest.getFirstDate(),
      findNotesRequest.getSecondDate(),
      findNotesRequest.getTitle(),
      findNotesRequest.getCategory());

    Map<String, NoteDto> categoryToNoteDto = new HashMap<>();
    for (Object[] result : resultList) {
      Note note = new Note(
        (Integer) result[1],
        (Integer) result[2],
        (String) result[3],
        (Date) result[4],
        (String) result[5],
        (Integer) result[6]);
      if (categoryToNoteDto.get((String) result[0]) != null) {
        categoryToNoteDto.get((String) result[0]).addToList(note);
      } else {
        NoteDto noteDto = new NoteDto();
        noteDto.setCategoryId((Integer) result[6]);
        noteDto.setTitle((String) result[0]);
        noteDto.addToList(note);
        categoryToNoteDto.put(noteDto.getTitle(), noteDto);
      }
    }
    List<NoteDto> notes = new ArrayList<>(categoryToNoteDto.values());
    return notes;
  }
}
