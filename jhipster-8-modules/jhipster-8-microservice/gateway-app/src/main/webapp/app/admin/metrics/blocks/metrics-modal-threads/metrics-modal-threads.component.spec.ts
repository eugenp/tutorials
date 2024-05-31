import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ThreadState } from '../../metrics.model';
import { MetricsModalThreadsComponent } from './metrics-modal-threads.component';

describe('MetricsModalThreadsComponent', () => {
  let comp: MetricsModalThreadsComponent;
  let fixture: ComponentFixture<MetricsModalThreadsComponent>;
  let mockActiveModal: NgbActiveModal;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MetricsModalThreadsComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(MetricsModalThreadsComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MetricsModalThreadsComponent);
    comp = fixture.componentInstance;
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('ngOnInit', () => {
    it('should count threads on init', () => {
      // GIVEN
      comp.threads = [
        {
          threadName: '',
          threadId: 1,
          blockedTime: 1,
          blockedCount: 1,
          waitedTime: 1,
          waitedCount: 1,
          lockName: 'lock1',
          lockOwnerId: 1,
          lockOwnerName: 'lock1',
          daemon: true,
          inNative: true,
          suspended: true,
          threadState: ThreadState.Blocked,
          priority: 1,
          stackTrace: [],
          lockedMonitors: [],
          lockedSynchronizers: [],
          lockInfo: null,
        },
        {
          threadName: '',
          threadId: 2,
          blockedTime: 2,
          blockedCount: 2,
          waitedTime: 2,
          waitedCount: 2,
          lockName: 'lock2',
          lockOwnerId: 2,
          lockOwnerName: 'lock2',
          daemon: false,
          inNative: false,
          suspended: false,
          threadState: ThreadState.Runnable,
          priority: 2,
          stackTrace: [],
          lockedMonitors: [],
          lockedSynchronizers: [],
          lockInfo: null,
        },
        {
          threadName: '',
          threadId: 3,
          blockedTime: 3,
          blockedCount: 3,
          waitedTime: 3,
          waitedCount: 3,
          lockName: 'lock3',
          lockOwnerId: 3,
          lockOwnerName: 'lock3',
          daemon: false,
          inNative: false,
          suspended: false,
          threadState: ThreadState.TimedWaiting,
          priority: 3,
          stackTrace: [],
          lockedMonitors: [],
          lockedSynchronizers: [],
          lockInfo: null,
        },
        {
          threadName: '',
          threadId: 4,
          blockedTime: 4,
          blockedCount: 4,
          waitedTime: 4,
          waitedCount: 4,
          lockName: 'lock4',
          lockOwnerId: 4,
          lockOwnerName: 'lock4',
          daemon: false,
          inNative: false,
          suspended: false,
          threadState: ThreadState.Waiting,
          priority: 4,
          stackTrace: [],
          lockedMonitors: [],
          lockedSynchronizers: [],
          lockInfo: null,
        },
      ];

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.threadDumpRunnable).toEqual(1);
      expect(comp.threadDumpWaiting).toEqual(1);
      expect(comp.threadDumpTimedWaiting).toEqual(1);
      expect(comp.threadDumpBlocked).toEqual(1);
      expect(comp.threadDumpAll).toEqual(4);
    });
  });

  describe('getBadgeClass', () => {
    it('should return a success badge class for runnable thread state', () => {
      // GIVEN
      const threadState = ThreadState.Runnable;

      // WHEN
      const badgeClass = comp.getBadgeClass(threadState);

      // THEN
      expect(badgeClass).toEqual('bg-success');
    });

    it('should return an info badge class for waiting thread state', () => {
      // GIVEN
      const threadState = ThreadState.Waiting;

      // WHEN
      const badgeClass = comp.getBadgeClass(threadState);

      // THEN
      expect(badgeClass).toEqual('bg-info');
    });

    it('should return a warning badge class for time waiting thread state', () => {
      // GIVEN
      const threadState = ThreadState.TimedWaiting;

      // WHEN
      const badgeClass = comp.getBadgeClass(threadState);

      // THEN
      expect(badgeClass).toEqual('bg-warning');
    });

    it('should return a danger badge class for blocked thread state', () => {
      // GIVEN
      const threadState = ThreadState.Blocked;

      // WHEN
      const badgeClass = comp.getBadgeClass(threadState);

      // THEN
      expect(badgeClass).toEqual('bg-danger');
    });

    it('should return an empty string for others threads', () => {
      // GIVEN
      const threadState = ThreadState.New;

      // WHEN
      const badgeClass = comp.getBadgeClass(threadState);

      // THEN
      expect(badgeClass).toEqual('');
    });
  });

  describe('getThreads', () => {
    it('should return blocked threads', () => {
      // GIVEN
      const thread1 = {
        threadName: '',
        threadId: 1,
        blockedTime: 1,
        blockedCount: 1,
        waitedTime: 1,
        waitedCount: 1,
        lockName: 'lock1',
        lockOwnerId: 1,
        lockOwnerName: 'lock1',
        daemon: true,
        inNative: true,
        suspended: true,
        threadState: ThreadState.Blocked,
        priority: 1,
        stackTrace: [],
        lockedMonitors: [],
        lockedSynchronizers: [],
        lockInfo: null,
      };
      const thread2 = {
        threadName: '',
        threadId: 2,
        blockedTime: 2,
        blockedCount: 2,
        waitedTime: 2,
        waitedCount: 2,
        lockName: 'lock2',
        lockOwnerId: 1,
        lockOwnerName: 'lock2',
        daemon: false,
        inNative: false,
        suspended: false,
        threadState: ThreadState.Runnable,
        priority: 2,
        stackTrace: [],
        lockedMonitors: [],
        lockedSynchronizers: [],
        lockInfo: null,
      };
      comp.threads = [thread1, thread2];
      comp.threadStateFilter = ThreadState.Blocked;

      // WHEN
      const threadsFiltered = comp.getThreads();

      // THEN
      expect(threadsFiltered).toEqual([thread1]);
    });

    it('should return an empty array of threads', () => {
      // GIVEN
      comp.threads = [];
      comp.threadStateFilter = ThreadState.Blocked;

      // WHEN
      const threadsFiltered = comp.getThreads();

      // THEN
      expect(threadsFiltered).toEqual([]);
    });

    it('should return all threads if there is no filter', () => {
      // GIVEN
      const thread1 = {
        threadName: '',
        threadId: 1,
        blockedTime: 1,
        blockedCount: 1,
        waitedTime: 1,
        waitedCount: 1,
        lockName: 'lock1',
        lockOwnerId: 1,
        lockOwnerName: 'lock1',
        daemon: true,
        inNative: true,
        suspended: true,
        threadState: ThreadState.Blocked,
        priority: 1,
        stackTrace: [],
        lockedMonitors: [],
        lockedSynchronizers: [],
        lockInfo: null,
      };
      const thread2 = {
        threadName: '',
        threadId: 2,
        blockedTime: 2,
        blockedCount: 2,
        waitedTime: 2,
        waitedCount: 2,
        lockName: 'lock2',
        lockOwnerId: 1,
        lockOwnerName: 'lock2',
        daemon: false,
        inNative: false,
        suspended: false,
        threadState: ThreadState.Runnable,
        priority: 2,
        stackTrace: [],
        lockedMonitors: [],
        lockedSynchronizers: [],
        lockInfo: null,
      };
      comp.threads = [thread1, thread2];
      comp.threadStateFilter = undefined;

      // WHEN
      const threadsFiltered = comp.getThreads();

      // THEN
      expect(threadsFiltered).toEqual(comp.threads);
    });

    it('should return an empty array if there are no threads to filter', () => {
      // GIVEN
      comp.threads = undefined;
      comp.threadStateFilter = ThreadState.Blocked;

      // WHEN
      const threadsFiltered = comp.getThreads();

      // THEN
      expect(threadsFiltered).toEqual([]);
    });
  });

  describe('dismiss', () => {
    it('should call dismiss function for modal on dismiss', () => {
      // GIVEN
      jest.spyOn(mockActiveModal, 'dismiss').mockReturnValue(undefined);

      // WHEN
      comp.dismiss();

      // THEN
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
