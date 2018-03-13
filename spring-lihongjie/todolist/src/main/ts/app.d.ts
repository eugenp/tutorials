// Defines the interface of the structure of a task
interface Task {
	statusChanged: (Task) => void;

	id: number,
	title: string;
	status: string;
	category: string;
	createdAt?: Date;
	closedAt?: Date;
	setStatus(status: string);
	isCompleted(): boolean;
	toJson(): any;
}

interface TaskManager {
	changed: () => void;

	tasks: Array<Task>;
	completedCount: number;

	sync(tasks: Array<Task>, compeleted: number);
	filter(category: string) : Array<Task>;
	count(category: string) : number;
	create(task: Task);
	delete(task:Task);

	getMoreCompleted();
	hasMoreCompletedPage(): boolean;
}

// Defines the interface of the properties of the TodoItem component
interface TaskItemProps {
	url: string;
	todo : Task;
	onDestroy: () => void;
}

// Defines the interface of the state of the TodoItem component
interface TaskItemState {
	editing?: boolean;
	completed?: boolean;
	editText?: string;
}

// Defines the interface of the properties of the App component
interface AppProps {
	manager: TaskManager;
	url: string;
	pollInterval: number;
}

// Defines the interface of the state of the App component
interface AppState {
	// model
	username?: string;
	email?: string;
	avatar?: string;
	completedCount?: number;

	// page state
	selected?: string;
}
