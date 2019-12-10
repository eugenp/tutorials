/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagon.repository;

import com.baeldung.hexagon.model.Student;
import java.util.List;

/**
 *
 * @author NandomPC
 */

public interface StudentRepository {
     List<Student> getAllStudents();
}
