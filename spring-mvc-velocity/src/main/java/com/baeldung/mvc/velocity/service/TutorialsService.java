package com.baeldung.mvc.velocity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.mvc.velocity.domain.Tutorial;

@Service
public class TutorialsService {
 
   public List<Tutorial> listTutorials() {
       List<Tutorial> list = new ArrayList<Tutorial>();
 
       list.add(new Tutorial(1, "Guava", "Introduction to Guava","GuavaAuthor"));
       list.add(new Tutorial(2, "Android", "Introduction to Android","AndroidAuthor"));
       return list;
   }
}
