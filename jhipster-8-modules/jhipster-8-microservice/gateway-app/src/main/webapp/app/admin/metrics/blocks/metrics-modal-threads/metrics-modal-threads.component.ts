import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { Thread, ThreadState } from 'app/admin/metrics/metrics.model';

@Component({
  standalone: true,
  selector: 'jhi-thread-modal',
  templateUrl: './metrics-modal-threads.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [SharedModule],
})
export class MetricsModalThreadsComponent implements OnInit {
  ThreadState = ThreadState;
  threadStateFilter?: ThreadState;
  threads?: Thread[];
  threadDumpAll = 0;
  threadDumpBlocked = 0;
  threadDumpRunnable = 0;
  threadDumpTimedWaiting = 0;
  threadDumpWaiting = 0;

  private activeModal = inject(NgbActiveModal);

  ngOnInit(): void {
    this.threads?.forEach(thread => {
      if (thread.threadState === ThreadState.Runnable) {
        this.threadDumpRunnable += 1;
      } else if (thread.threadState === ThreadState.Waiting) {
        this.threadDumpWaiting += 1;
      } else if (thread.threadState === ThreadState.TimedWaiting) {
        this.threadDumpTimedWaiting += 1;
      } else if (thread.threadState === ThreadState.Blocked) {
        this.threadDumpBlocked += 1;
      }
    });

    this.threadDumpAll = this.threadDumpRunnable + this.threadDumpWaiting + this.threadDumpTimedWaiting + this.threadDumpBlocked;
  }

  getBadgeClass(threadState: ThreadState): string {
    if (threadState === ThreadState.Runnable) {
      return 'bg-success';
    } else if (threadState === ThreadState.Waiting) {
      return 'bg-info';
    } else if (threadState === ThreadState.TimedWaiting) {
      return 'bg-warning';
    } else if (threadState === ThreadState.Blocked) {
      return 'bg-danger';
    }
    return '';
  }

  getThreads(): Thread[] {
    return this.threads?.filter(thread => !this.threadStateFilter || thread.threadState === this.threadStateFilter) ?? [];
  }

  dismiss(): void {
    this.activeModal.dismiss();
  }
}
