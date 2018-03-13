/// <reference path="../../../../typings/globals/react-global/index.d.ts" />
/// <reference path="../app.d.ts"/>

import * as constants from "../misc/constants"

export class TaskItem extends React.Component<TaskItemProps, TaskItemState> {
	constructor(props : TaskItemProps){
		super(props);
		this.state = {completed: props.todo.isCompleted(), editing: false, editText: this.props.todo.title};
	}

	public edit() {
		this.setState({editing: true});
	}

	public onBlur() {
		this.setState({editing: false}, () => {
			this.props.todo.title = this.state.editText;
			this.update();
		});
	}

	public onEditTextChange(event: __React.FormEvent) {
		var input: any = event.target;
		this.setState({editText: input.value});
	}

	public onKeyDown(event: __React.KeyboardEvent) {
		if (event.keyCode === constants.ESCAPE_KEY) {
			this.setState({editing: false, editText: this.props.todo.title});
		} else if (event.keyCode === constants.ENTER_KEY) {
			this.onBlur();
		}
	}

	public onStatusChange() {
		this.setState({completed: !this.state.completed}, ()=> {
			this.props.todo.setStatus(this.state.completed ? "CLOSE" : "OPEN");
			this.update();
		});
	}

	public update() {
		let todo = this.props.todo;

		if(todo.title) {
			$.ajax({
				url: this.props.url,
				dataType: 'json',
				type: 'POST',
				data: this.props.todo.toJson(),
				success: function (task) {

				}.bind(this),
				error: function (xhr, status, err) {
					console.error(this.props.url, status, err.toString());
				}.bind(this)
			});
		} else {
			this.props.onDestroy();
		}
	}

	public shouldComponentUpdate(nextProps : TaskItemProps, nextState : TaskItemState) {
		return (
			nextProps.todo !== this.props.todo ||
			nextState.editing !== this.state.editing ||
			nextState.editText !== this.state.editText ||
			nextState.completed !== this.state.completed
		);
	}

	public componentDidUpdate(prevProps : TaskItemProps, prevState: TaskItemState) {
		if (!prevState.editing && this.state.editing) {
			var node = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["editField"]);
			node.focus();
			node.setSelectionRange(node.value.length, node.value.length);
		}
	}

	public render() {
		return(
			<li className={classNames({
				completed: this.state.completed,
				editing: this.state.editing
			})}>
				<div className="view">
					<input
						ref="statusField"
						className="toggle"
						type="checkbox"
						defaultChecked={this.state.completed}
					  onChange={this.onStatusChange.bind(this)}
					/>
					<label onDoubleClick={this.edit.bind(this)}>
						{this.state.editText}<span>{moment(this.props.todo.createdAt).fromNow()}</span>
					</label>
					<button className="destroy" onClick={this.props.onDestroy} />
				</div>
				<input
					ref="editField"
					className="edit"
					value={this.state.editText}
					onBlur={this.onBlur.bind(this)}
					onChange={this.onEditTextChange.bind(this)}
					onKeyDown={this.onKeyDown.bind(this) }
				/>
			</li>
		);
	}
}
