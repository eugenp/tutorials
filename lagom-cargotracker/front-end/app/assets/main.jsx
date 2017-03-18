var Route = ReactRouter.Route;
var IndexRoute = ReactRouter.IndexRoute;
var Link = ReactRouter.Link;

function sendJson(params) {
    params.data = JSON.stringify(params.data);
    params.contentType = "application/json";
    return $.ajax(params);
}

function createStream(path, onopen) {
    return {
        connect: function(onData) {
            var stream = new WebSocket("ws://" + location.host + path);
            if (onopen) {
                stream.onopen = function(event) {
                    onopen(stream, event);
                }.bind(this);
            }
            stream.onmessage = function(event) {
                var data = JSON.parse(event.data);
                onData(data);
            }.bind(this);
            return {
                close: function() {
                    stream.close();
                }
            }
        }
    };
}

function createCargoStream() {
    return createStream("/api/registration/live");
}

var Cargo = React.createClass({
    render: function() {
        return (
            <div className="cargo">
                {this.props.children}
                <hr />
            </div>
        );
    }
});

var CargoForm = React.createClass({
    getInitialState: function() {
        return {name: "", description: "", owner: "", destination: ""};
    },
    handleChange: function(field) {
        var outerThis = this;
        return function(e) {
          var s = outerThis.state;
          s[field] = e.target.value;
          outerThis.setState(s);
        }
    },
    handleSubmit: function(e) {
        e.preventDefault();
        var dataToSend = this.state;
        dataToSend["id"] = Math.floor(Math.random() * 1000000);
        sendJson({
            url: "/api/registration",
            type: 'POST',
            data: dataToSend,
            success: function() {
                this.setState({name: "", description: "", owner: "", destination: ""});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    render: function() {
        return (
            <form className="cargoForm" onSubmit={this.handleSubmit}>
                <div>
                  <input type="text"
                         placeholder="Cargo name..."
                         value={this.state.name}
                         onChange={this.handleChange("name")}
                         maxLength="30"
                  />
                  <input type="text"
                         placeholder="description"
                         value={this.state.description}
                         onChange={this.handleChange("description")}
                         maxLength="30"
                  />
                  <input type="text"
                         placeholder="owner"
                         value={this.state.owner}
                         onChange={this.handleChange("owner")}
                         maxLength="30"
                  />
                  <input type="text"
                         placeholder="destination"
                         value={this.state.destination}
                         onChange={this.handleChange("destination")}
                         maxLength="30"
                  />
                </div>
                <input type="submit" value="Post" />
            </form>
        );
    }
});

var Section = React.createClass({
    render: function() {
        return (
            <section className="fw-wrapper feature">
                <div className="row">
                    {this.props.children}
                </div>
            </section>
        );
    }
});

var CargoStream = React.createClass({
    getInitialState: function() {
        return {cargos: []};
    },
    componentDidMount: function() {
        this.stream = this.props.stream.connect(function(cargo) {
            var newCargo = [cargo].concat(this.state.cargos);
            this.setState({cargos: newCargo});
        }.bind(this));
    },
    componentWillUnmount: function() {
        this.stream.close();
    },
    render: function() {
        var cargoNodes = this.state.cargos.map(function(cargo) {
            return (
                <Cargo key={cargo.id}>
                  <table>
                  <tbody>
                    <tr>
                      <th>Cargo ID</th>
                      <th>Cargo name</th>
                      <th>Description</th>
                      <th>Owner</th>
                      <th>Destination</th>
                    </tr>
                    <tr>
                      <td>{cargo.id}</td>
                      <td>{cargo.name}</td>
                      <td>{cargo.description}</td>
                      <td>{cargo.owner}</td>
                      <td>{cargo.destination}</td>
                    </tr>
                    </tbody>
                  </table>
                </Cargo>
            );
        }.bind(this));
        return (
            <div className="cargoStream">
                <hr />
                {cargoNodes}
            </div>

        );
    }
});

var RegistrationStream = React.createClass({
    getInitialState: function() {
        return {cargos: {}};
    },
    render: function() {
        return (
            <ContentLayout subtitle="Cargo feed">
                <Section>
                    <div className="small-12 columns">
                        <CargoForm />
                        <CargoStream stream={createCargoStream()} />
                    </div>
                </Section>
            </ContentLayout>
        );
    }
});

var PageLayout = React.createClass({
    render: function() {

        var links = <div className="tertiary-nav"></div>;

        return (
             <div id="clipped">
                 <div id="site-header">
                     <div className="row">
                         <div className="small-3 columns">
                             <Link to="/" id="logo">Cargotracker</Link>
                         </div>
                         <div className="small-9 columns">
                             <nav>
                                 <div className="tertiary-nav">
                                     {links}
                                 </div>
                             </nav>
                         </div>
                     </div>
                 </div>
                 {this.props.children}
            </div>
        );
    }
});

var ContentLayout = React.createClass({
   render: function() {
       return (
           <div id="page-content">
               <section id="top">
                   <div className="row">
                       <header className="large-12 columns">
                            <h1>{this.props.subtitle}</h1>
                       </header>
                   </div>
               </section>
               {this.props.children}
           </div>
       );
   }
});

var App = React.createClass({
    getInitialState: function() {
        return {};
    },
    render: function() {
        return (
          <PageLayout>
              {this.props.children}
          </PageLayout>
        );
    }
});

ReactDOM.render(
    <ReactRouter.Router history={History.createHistory()}>
        <Route path="/" component={App}>
          <IndexRoute component={RegistrationStream}/>
        </Route>
    </ReactRouter.Router>,
    document.getElementById("content")
);
