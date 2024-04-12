import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

import { BookstoreTestModule } from '../../../test.module';
import { JhiHealthCheckComponent } from 'app/admin/health/health.component';
import { JhiHealthService } from 'app/admin/health/health.service';

describe('Component Tests', () => {
    describe('JhiHealthCheckComponent', () => {
        let comp: JhiHealthCheckComponent;
        let fixture: ComponentFixture<JhiHealthCheckComponent>;
        let service: JhiHealthService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BookstoreTestModule],
                declarations: [JhiHealthCheckComponent]
            })
                .overrideTemplate(JhiHealthCheckComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JhiHealthCheckComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JhiHealthService);
        });

        describe('baseName and subSystemName', () => {
            it('should return the basename when it has no sub system', () => {
                expect(comp.baseName('base')).toBe('base');
            });

            it('should return the basename when it has sub systems', () => {
                expect(comp.baseName('base.subsystem.system')).toBe('base');
            });

            it('should return the sub system name', () => {
                expect(comp.subSystemName('subsystem')).toBe('');
            });

            it('should return the subsystem when it has multiple keys', () => {
                expect(comp.subSystemName('subsystem.subsystem.system')).toBe(' - subsystem.system');
            });
        });

        describe('transformHealthData', () => {
            it('should flatten empty health data', () => {
                const data = {};
                const expected = [];
                expect(service.transformHealthData(data)).toEqual(expected);
            });

            it('should flatten health data with no subsystems', () => {
                const data = {
                    details: {
                        status: 'UP',
                        db: {
                            status: 'UP',
                            database: 'H2',
                            hello: '1'
                        },
                        mail: {
                            status: 'UP',
                            error: 'mail.a.b.c'
                        }
                    }
                };
                const expected = [
                    {
                        name: 'db',
                        status: 'UP',
                        details: {
                            database: 'H2',
                            hello: '1'
                        }
                    },
                    {
                        name: 'mail',
                        error: 'mail.a.b.c',
                        status: 'UP'
                    }
                ];
                expect(service.transformHealthData(data)).toEqual(expected);
            });

            it('should flatten health data with subsystems at level 1, main system has no additional information', () => {
                const data = {
                    details: {
                        status: 'UP',
                        db: {
                            status: 'UP',
                            database: 'H2',
                            hello: '1'
                        },
                        mail: {
                            status: 'UP',
                            error: 'mail.a.b.c'
                        },
                        system: {
                            status: 'DOWN',
                            subsystem1: {
                                status: 'UP',
                                property1: 'system.subsystem1.property1'
                            },
                            subsystem2: {
                                status: 'DOWN',
                                error: 'system.subsystem1.error',
                                property2: 'system.subsystem2.property2'
                            }
                        }
                    }
                };
                const expected = [
                    {
                        name: 'db',
                        status: 'UP',
                        details: {
                            database: 'H2',
                            hello: '1'
                        }
                    },
                    {
                        name: 'mail',
                        error: 'mail.a.b.c',
                        status: 'UP'
                    },
                    {
                        name: 'system.subsystem1',
                        status: 'UP',
                        details: {
                            property1: 'system.subsystem1.property1'
                        }
                    },
                    {
                        name: 'system.subsystem2',
                        error: 'system.subsystem1.error',
                        status: 'DOWN',
                        details: {
                            property2: 'system.subsystem2.property2'
                        }
                    }
                ];
                expect(service.transformHealthData(data)).toEqual(expected);
            });

            it('should flatten health data with subsystems at level 1, main system has additional information', () => {
                const data = {
                    details: {
                        status: 'UP',
                        db: {
                            status: 'UP',
                            database: 'H2',
                            hello: '1'
                        },
                        mail: {
                            status: 'UP',
                            error: 'mail.a.b.c'
                        },
                        system: {
                            status: 'DOWN',
                            property1: 'system.property1',
                            subsystem1: {
                                status: 'UP',
                                property1: 'system.subsystem1.property1'
                            },
                            subsystem2: {
                                status: 'DOWN',
                                error: 'system.subsystem1.error',
                                property2: 'system.subsystem2.property2'
                            }
                        }
                    }
                };
                const expected = [
                    {
                        name: 'db',
                        status: 'UP',
                        details: {
                            database: 'H2',
                            hello: '1'
                        }
                    },
                    {
                        name: 'mail',
                        error: 'mail.a.b.c',
                        status: 'UP'
                    },
                    {
                        name: 'system',
                        status: 'DOWN',
                        details: {
                            property1: 'system.property1'
                        }
                    },
                    {
                        name: 'system.subsystem1',
                        status: 'UP',
                        details: {
                            property1: 'system.subsystem1.property1'
                        }
                    },
                    {
                        name: 'system.subsystem2',
                        error: 'system.subsystem1.error',
                        status: 'DOWN',
                        details: {
                            property2: 'system.subsystem2.property2'
                        }
                    }
                ];
                expect(service.transformHealthData(data)).toEqual(expected);
            });

            it('should flatten health data with subsystems at level 1, main system has additional error', () => {
                const data = {
                    details: {
                        status: 'UP',
                        db: {
                            status: 'UP',
                            database: 'H2',
                            hello: '1'
                        },
                        mail: {
                            status: 'UP',
                            error: 'mail.a.b.c'
                        },
                        system: {
                            status: 'DOWN',
                            error: 'show me',
                            subsystem1: {
                                status: 'UP',
                                property1: 'system.subsystem1.property1'
                            },
                            subsystem2: {
                                status: 'DOWN',
                                error: 'system.subsystem1.error',
                                property2: 'system.subsystem2.property2'
                            }
                        }
                    }
                };
                const expected = [
                    {
                        name: 'db',
                        status: 'UP',
                        details: {
                            database: 'H2',
                            hello: '1'
                        }
                    },
                    {
                        name: 'mail',
                        error: 'mail.a.b.c',
                        status: 'UP'
                    },
                    {
                        name: 'system',
                        error: 'show me',
                        status: 'DOWN'
                    },
                    {
                        name: 'system.subsystem1',
                        status: 'UP',
                        details: {
                            property1: 'system.subsystem1.property1'
                        }
                    },
                    {
                        name: 'system.subsystem2',
                        error: 'system.subsystem1.error',
                        status: 'DOWN',
                        details: {
                            property2: 'system.subsystem2.property2'
                        }
                    }
                ];
                expect(service.transformHealthData(data)).toEqual(expected);
            });
        });

        describe('getBadgeClass', () => {
            it('should get badge class', () => {
                const upBadgeClass = comp.getBadgeClass('UP');
                const downBadgeClass = comp.getBadgeClass('DOWN');
                expect(upBadgeClass).toEqual('badge-success');
                expect(downBadgeClass).toEqual('badge-danger');
            });
        });

        describe('refresh', () => {
            it('should call refresh on init', () => {
                // GIVEN
                spyOn(service, 'checkHealth').and.returnValue(of(new HttpResponse()));
                spyOn(service, 'transformHealthData').and.returnValue(of({ data: 'test' }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.checkHealth).toHaveBeenCalled();
                expect(service.transformHealthData).toHaveBeenCalled();
                expect(comp.healthData.value).toEqual({ data: 'test' });
            });
            it('should handle a 503 on refreshing health data', () => {
                // GIVEN
                spyOn(service, 'checkHealth').and.returnValue(throwError(new HttpErrorResponse({ status: 503, error: 'Mail down' })));
                spyOn(service, 'transformHealthData').and.returnValue(of({ health: 'down' }));

                // WHEN
                comp.refresh();

                // THEN
                expect(service.checkHealth).toHaveBeenCalled();
                expect(service.transformHealthData).toHaveBeenCalled();
                expect(comp.healthData.value).toEqual({ health: 'down' });
            });
        });
    });
});
