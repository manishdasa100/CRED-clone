import React from 'react';
import './RaisedButtonType2.css';

class RaisedButtonType2 extends React.Component{
    state = {  }
    render() { 
        return (<div className="raised-btn-type2" onClick={() => this.props.onClick()}>
            {this.props.value}
        </div>);
    }
}

export default RaisedButtonType2;