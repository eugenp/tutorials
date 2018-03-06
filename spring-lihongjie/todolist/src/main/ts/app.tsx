/// <reference path="../../../typings/globals/react-global/index.d.ts" />
/// <reference path="./app.d.ts"/>

import * as constants from "./misc/constants"
import utils from "./misc/utils"
import { TaskItem } from "./components/task";
import TaskImpl from "./model/task";
import TaskManagerImpl from "./model/manager";

declare var Router :any;

class TodoApp extends React.Component<AppProps, AppState> {
	token: string;
	from: string;
	pageTitleMap: { [key:string]:string; } = {};

	constructor(props) {
		super(props);

		console.log("v0.2.38-SNAPSHOT");

		this.initState();
		this.initMoment();
		this.initPageTitleMap();
		this.initManager();
		this.setupAjax();
	}

	private initState() {
		this.state = { selected: "DAILY" };
	}

	private initRouter() {
		let setState = this.setState;
		let router = Router({
			'/': [setState.bind(this, { selected: "DAILY" }), this.focusNewField.bind(this)],
			'/short': [setState.bind(this, { selected: "SHORTTERM" }), this.focusNewField.bind(this)],
			'/long': [setState.bind(this, { selected: "LONGTERM" }), this.focusNewField.bind(this)],
			'/completed': setState.bind(this, { selected: "completed" })
		});

		router.init('/');
	}

	private initMoment() {
		moment.locale('zh-cn');
	}

	private initManager() {
		this.props.manager.changed = () => {
			this.forceUpdate();
		}
	}

	private initPageTitleMap() {
		this.pageTitleMap["DAILY"] = "今日事项";
		this.pageTitleMap["SHORTTERM"] = "近期目标";
		this.pageTitleMap["LONGTERM"] = "长期目标";
		this.pageTitleMap["completed"] = "完成事项";
	}

	private setupAjax() {
		this.token = $('.todoapp').attr("token");
		this.from = $('.todoapp').attr("from");

		$.ajaxSetup({
			headers: { 'Token': this.token, 'From': this.from }
		});
	}

	private getPageTitle() {
		return this.pageTitleMap[this.state.selected];
	}

	public componentDidMount() {
		this.initRouter();
		this.sync();
		if(this.props.pollInterval > 0) {
			setInterval(this.sync.bind(this), this.props.pollInterval);
		}
	}

	public sync() {
		console.log("sync...  "+ Date.now())
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			type: "GET",
			cache: false,
			success: function (data) {
				this.setState({username: data.user.name, email: data.user.email})
				this.setState({avatar: data.user.avatar == "" ? "/public/misc/user-10308319.png" : data.user.avatar});

				let tasks = data.active.concat(data.completed);
				this.props.manager.sync(tasks.map(each=>{return new TaskImpl(each)}), data.completedCount);
				this.forceUpdate();
			}.bind(this),
			error: function (xhr, status, err) { console.error(this.props.url, status, err.toString()); }.bind(this),
			statusCode: { 401: () => { console.log("401"); } }
		});
	}

	private focusNewField() {
		var node = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]);
		if(node != null) {
			node.focus();
			node.setSelectionRange(node.value.length, node.value.length);
		}
	}

	public handleNewTaskKeyDown(event) {
		if (event.keyCode !== constants.ENTER_KEY) return;
		event.preventDefault();

		var val = ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value.trim();
		if(val) {
			this.create(new TaskImpl({ id: new Date().getTime(), title: val, status: "OPEN", category: this.state.selected}));
			ReactDOM.findDOMNode<HTMLInputElement>(this.refs["newField"]).value = "";
		}
	}

	public create(task: Task) {
		this.props.manager.create(task);
	}

	public destroy(task: Task) {
		this.props.manager.delete(task);
	}

	render() {
		console.log("render: "+Date.now());

		let cx = classNames;
		let selected = this.state.selected;
		let mgr = this.props.manager;

		let sidebar = (
			<div className="nav-menu">
				<ul className="nav nav-pills nav-stacked">
					<li className="text-center user-menu">
						<img src={this.state.avatar} alt="avatar" className="img-responsive img-circle img-avatar center-block" />
						<a className="avatar-name" href="#" title="user">{this.state.username}<span className="caret"></span></a>
					</li>

					<li className={cx({active: selected === "DAILY"})}><a href="#/" title="today" className="menu-text">今日<span className="badge pull-right red-color">{mgr.count("DAILY")}</span></a></li>
					<li className={cx({active: selected === "SHORTTERM"})}><a href="#/short" title="mid-term" className="menu-text">近期<span className="badge pull-right blue-color">{mgr.count("SHORTTERM")}</span></a></li>
					<li className={cx({active: selected === "LONGTERM"})}><a href="#/long" title="long-term" className="menu-text">长期<span className="badge pull-right green-color">{mgr.count("LONGTERM")}</span></a></li>
					<li className={cx({active: selected === "completed"})}><a href="#/completed" title="long-term" className="menu-text">已完成<span className="badge pull-right gray-color">{mgr.completedCount}</span></a></li>
				</ul>
			</div>
		);

		let footer = (
			<div className="footer">
				{this.state.selected === "completed" && mgr.hasMoreCompletedPage() ?
				<button className="btn btn-default" type="submit" onClick={mgr.getMoreCompleted.bind(mgr)}>更多完成事项</button>
					: null}
			</div>
		);

		let header = (
			<div>
				<header className="header">
					<h2>{this.getPageTitle()}<small>{moment().format('YYYY年M月D日 dddd LT')}</small></h2>

					{this.state.selected === "completed" ? null :
						<input
							ref="newField"
							className="new-todo"
							placeholder="新增待办事项，按回车键保存..."
							onKeyDown={ e => this.handleNewTaskKeyDown(e) }
							autoFocus={true} />
					}
				</header>
			</div>
		);

		let taskItems = mgr.filter(this.state.selected).map(function (each) {
			return (
				<TaskItem
					url={utils.join(this.props.url, each.id)}
					key={each.id}
					todo={each}
					onDestroy={this.destroy.bind(this, each)}
				/>
			);
		}, this);

		let main = (
			<div className="main-content">
				{header}
				<section className="main">
					{this.state.selected === "completed" ? null : <input type="checkbox" className="toggle-all" value="on" />}
					<ul className="todo-list">
						{taskItems}
					</ul>
				</section>
				{footer}
			</div>
		);

		return (
			<div className="full-screen">
				<div className="container no-padding">
					{sidebar}
					{main}
				</div>
			</div>
		);
	}
}

ReactDOM.render(
	<TodoApp manager={new TaskManagerImpl()} url="/api/tasks" pollInterval={5*60*1000} />,
	$('.todoapp').get(0)
);
