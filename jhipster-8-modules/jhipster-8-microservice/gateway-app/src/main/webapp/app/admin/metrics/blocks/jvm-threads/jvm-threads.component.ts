import { Component, inject, Input } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { Thread, ThreadState } from 'app/admin/metrics/metrics.model';
import { MetricsModalThreadsComponent } from '../metrics-modal-threads/metrics-modal-threads.component';

@Component({
  standalone: true,
  selector: 'jhi-jvm-threads',
  templateUrl: './jvm-threads.component.html',
  imports: [SharedModule],
})
export class JvmThreadsComponent {
  threadStats = {
    threadDumpAll: 0,
    threadDumpRunnable: 0,
    threadDumpTimedWaiting: 0,
    threadDumpWaiting: 0,
    threadDumpBlocked: 0,
  };

  @Input()
  set threads(threads: Thread[] | undefined) {
    this._threads = threads;

    threads?.forEach(thread => {
      if (thread.threadState === ThreadState.Runnable) {
        this.threadStats.threadDumpRunnable += 1;
      } else if (thread.threadState === ThreadState.Waiting) {
        this.threadStats.threadDumpWaiting += 1;
      } else if (thread.threadState === ThreadState.TimedWaiting) {
        this.threadStats.threadDumpTimedWaiting += 1;
      } else if (thread.threadState === ThreadState.Blocked) {
        this.threadStats.threadDumpBlocked += 1;
      }
    });

    this.threadStats.threadDumpAll =
      this.threadStats.threadDumpRunnable +
      this.threadStats.threadDumpWaiting +
      this.threadStats.threadDumpTimedWaiting +
      this.threadStats.threadDumpBlocked;
  }

  get threads(): Thread[] | undefined {
    return this._threads;
  }

  private _threads: Thread[] | undefined;

  private modalService = inject(NgbModal);

  open(): void {
    const modalRef = this.modalService.open(MetricsModalThreadsComponent);
    modalRef.componentInstance.threads = this.threads;
  }
}
