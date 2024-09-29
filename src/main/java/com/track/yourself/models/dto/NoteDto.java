package com.track.yourself.models.dto;

import com.track.yourself.models.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class NoteDto {

  private Integer categoryId;
  private String title;
  private List<Note> notes = new ArrayList<>();

  public void addToList(Note note) {
    this.notes.add(note);
  }
}
