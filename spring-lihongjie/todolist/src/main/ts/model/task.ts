/// <reference path="../app.d.ts"/>

export default class TaskImpl implements Task {
	statusChanged: (Task) => void;

	id: number;
	title: string;
	status: string;
	category: string;
	createdAt: Date;
	closedAt: Date;

	constructor(p:{id: number, title: string, status: string, category: string, createdAt?: Date, closedAt?: Date}) {
		this.id = p.id;
		this.title = p.title;
		this.status = p.status;
		this.category = p.category;
		this.createdAt = p.createdAt;
		this.closedAt = p.closedAt;
	}

	public setStatus(status: string) {
		if(this.status === status) return;
		this.status = status;
		if(this.statusChanged != null) this.statusChanged(this);
	}

	public isCompleted(): boolean {
		return this.status == "CLOSE";
	}

	public toJson() {
		return {
			content: this.title,
			status: this.status
		};
	}
}
