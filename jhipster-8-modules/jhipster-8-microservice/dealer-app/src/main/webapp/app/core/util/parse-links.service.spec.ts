import { inject, TestBed } from '@angular/core/testing';

import { ParseLinks } from './parse-links.service';

describe('Parse links service test', () => {
  describe('parse', () => {
    beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [ParseLinks],
      });
    });

    it('should throw an error when passed an empty string', inject([ParseLinks], (service: ParseLinks) => {
      expect(function () {
        service.parse('');
      }).toThrow(new Error('input must not be of zero length'));
    }));

    it('should throw an error when passed without comma', inject([ParseLinks], (service: ParseLinks) => {
      expect(function () {
        service.parse('test');
      }).toThrow(new Error('section could not be split on ";"'));
    }));

    it('should throw an error when passed without semicolon', inject([ParseLinks], (service: ParseLinks) => {
      expect(function () {
        service.parse('test,test2');
      }).toThrow(new Error('section could not be split on ";"'));
    }));

    it('should return links when headers are passed', inject([ParseLinks], (service: ParseLinks) => {
      const links = { last: 0, first: 0 };
      expect(service.parse(' </api/audits?page=0&size=20>; rel="last",</api/audits?page=0&size=20>; rel="first"')).toEqual(links);
    }));
  });
  describe('parseAll', () => {
    beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [ParseLinks],
      });
    });

    it('should throw an error when passed an empty string', inject([ParseLinks], (service: ParseLinks) => {
      expect(function () {
        service.parseAll('');
      }).toThrow(new Error('input must not be of zero length'));
    }));

    it('should throw an error when passed without comma', inject([ParseLinks], (service: ParseLinks) => {
      expect(function () {
        service.parseAll('test');
      }).toThrow(new Error('section could not be split on ";"'));
    }));

    it('should throw an error when passed without semicolon', inject([ParseLinks], (service: ParseLinks) => {
      expect(function () {
        service.parseAll('test,test2');
      }).toThrow(new Error('section could not be split on ";"'));
    }));

    it('should return links when headers are passed', inject([ParseLinks], (service: ParseLinks) => {
      const links = { last: { page: '0', size: '20' }, first: { page: '0', size: '20' } };
      expect(service.parseAll(' </api/audits?page=0&size=20>; rel="last",</api/audits?page=0&size=20>; rel="first"')).toEqual(links);
    }));
  });
});
