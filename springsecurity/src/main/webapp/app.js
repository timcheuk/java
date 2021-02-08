class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {merchants: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/merchant'}).done(response => {
			this.setState({merchants: response.entity._embedded.merchants});
		});
	}

	render() {
		return (
			<MerchantList merchants={this.state.merchants}/>
		)
	}
}