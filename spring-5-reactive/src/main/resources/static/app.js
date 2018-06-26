let Clock = React.createClass({

    getInitialState: function() {
        const self = this;
        const ev = new EventSource("http://localhost:8080/sse/events");

        self.setState({currentDate : moment(new Date()).format("MMMM Do YYYY, h:mm:ss a")});

        ev.addEventListener("date", function(e) {
            self.setState({currentDate :  moment(JSON.parse(e.data).date).format("MMMM Do YYYY, h:mm:ss a")});
        }, false);

        return {currentDate: moment(new Date()).format("MMMM Do YYYY, h:mm:ss a") };
    },

    render() {
        return (
            <p>
                Current time: {this.state.currentDate}
            </p>
        );
    }

});

let App = React.createClass({
    render() {
        return (
            <div>
                <Clock/>
            </div>
        );
    }
});

ReactDOM.render(<App />, document.getElementById('root') );