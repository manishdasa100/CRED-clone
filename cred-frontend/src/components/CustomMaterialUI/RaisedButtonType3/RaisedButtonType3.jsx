import React from 'react';
import './RaisedButtonType3.css';

class RaisedButtonType3 extends React.Component{

    render() { 
        return (<button className="raised-btn-type3">
            {this.props.value}
        </button>);
    }
}

export default RaisedButtonType3;