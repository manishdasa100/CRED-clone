import React from 'react';
import './InputTextBox.css';

class InputTextBox extends React.Component {
    render() { 
        return (<label className="input-textbox">
            <input type={this.props.type} required onChange={(event)=>this.props.onInputChange(this.props.forField, event)}/>
            <span className="placeholder">{this.props.placehoderValue}</span>
        </label>);
    }
}
 
export default InputTextBox;