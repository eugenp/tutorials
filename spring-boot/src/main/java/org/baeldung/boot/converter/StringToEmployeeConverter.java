package org.baeldung.boot.converter;

import org.springframework.core.convert.converter.Converter;

import com.baeldung.toggle.Employee;

public class StringToEmployeeConverter implements Converter<String, Employee> {

  @Override
  public Employee convert(String from) {
      String[] data = from.split(",");
      return new Employee(Long.parseLong(data[0]), Double.parseDouble(data[1]));
  }
}
