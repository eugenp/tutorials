import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

import { Log } from './log.model';
import { LogsService } from './logs.service';

@Component({
    selector: 'jhi-logs',
    templateUrl: './logs.component.html',
})
export class LogsComponent implements OnInit {

    loggers: Log[];
    filter: string;
    orderProp: string;
    reverse: boolean;

    constructor (
        private jhiLanguageService: JhiLanguageService,
        private logsService: LogsService
    ) {
        this.filter = '';
        this.orderProp = 'name';
        this.reverse = false;
        this.jhiLanguageService.setLocations(['logs']);
    }

    ngOnInit() {
        this.logsService.findAll().subscribe(loggers => this.loggers = loggers);
    }

    changeLevel (name: string, level: string) {
        let log = new Log(name, level);
        this.logsService.changeLevel(log).subscribe(() => {
            this.logsService.findAll().subscribe(loggers => this.loggers = loggers);
        });
    }
}
