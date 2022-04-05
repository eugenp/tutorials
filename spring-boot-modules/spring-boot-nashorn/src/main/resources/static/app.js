var App = React.createClass({displayName: "App",
	
    handleSubmit: function () {
    	var last = this.state.data[this.state.data.length-1];
    	var secondLast = this.state.data[this.state.data.length-2];
        $.ajax({
            url: '/next/'+last+'/'+secondLast,
            dataType: 'text',
            success: function (msg) {
                var series = this.state.data;
                series.push(msg);
                this.setState({data: series});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error("/next", status, err.toString());
            }.bind(this)
        });
    },
    
    componentDidMount: function() {
    	this.setState({data: this.props.data});
    },
	
    getInitialState: function () {
        return {data: []};
    },
	
    render: function () {
        return (
            React.createElement("div", {className: "app"},
            	React.createElement("h2", null, "Fibonacci Generator"),
            	React.createElement("h2", null, this.state.data.toString()),
                React.createElement("input", {type: "submit", value: "Next", onClick: this.handleSubmit})
            )     
        );
    }
});