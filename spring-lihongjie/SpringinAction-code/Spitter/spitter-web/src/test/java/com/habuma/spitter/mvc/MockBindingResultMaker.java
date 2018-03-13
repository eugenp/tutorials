package com.habuma.spitter.mvc;

import static org.mockito.Mockito.*;

import org.springframework.validation.BindingResult;

public class MockBindingResultMaker {

  public static BindingResult createMockBindingResult(boolean hasErrors) {
    BindingResult result = mock(BindingResult.class);
    when(result.hasErrors()).thenReturn(hasErrors);
    return result;
  }
}
