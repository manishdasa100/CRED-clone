import React from 'react';
import './RaisedButtonType1.css';

class RaisedButtonType1 extends React.Component {
    state = {  }
    render() { 
        return (<button className="raised-btn-type1" onClick={()=>this.props.onClick()}>
            {this.props.value}
        </button>);
    }
}
 
export default RaisedButtonType1;