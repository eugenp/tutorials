import { TestBed } from '@angular/core/testing';

import { DataUtils } from './data-util.service';

describe('Data Utils Service Test', () => {
  let service: DataUtils;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DataUtils],
    });
    service = TestBed.inject(DataUtils);
  });

  describe('byteSize', () => {
    it('should return the bytesize of the text', () => {
      expect(service.byteSize('Hello JHipster')).toBe(`10.5 bytes`);
    });
  });

  describe('openFile', () => {
    it('should open the file in the new window', () => {
      const newWindow = { ...window };
      newWindow.document.write = jest.fn();
      window.open = jest.fn(() => newWindow);
      window.URL.createObjectURL = jest.fn();
      // 'JHipster' in base64 is 'SkhpcHN0ZXI='
      const data = 'SkhpcHN0ZXI=';
      const contentType = 'text/plain';
      service.openFile(data, contentType);
      expect(window.open).toHaveBeenCalledTimes(1);
    });
  });
});
