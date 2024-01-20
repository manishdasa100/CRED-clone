import React from 'react';
import './LogInForm.css';
import InputTextBox from './../CustomMaterialUI/InputTextBox/InputTextBox';
import RaisedButtonType1 from './../CustomMaterialUI/RaisedButtonType1/RaisedButtonType1';
import LoadingDots from '../CustomMaterialUI/LoadingDots/LoadingDots';
import logo from './../../assets/credlogo.png';

class LogInForm extends React.Component {

    componentWillUnmount(){
        this.props.flushStateOnUnmount();
    }

    render() { 
        console.log("Login form rendering");
        return (
            <div className="login-container">
                <div className="header">
                    <img src={logo} alt="header-logo" className="header-img" width="52px" height="69px"></img>
                    <p className="header-title">Login to your account</p>
                </div>
                <p className={(this.props.message)?'message-text-visible':'message-text-hidden'}>{this.props.message}</p>
                <div className="login-form">
                    <InputTextBox placehoderValue="Username" type="text" forField="userName" onInputChange={this.props.onInputChange}/>
                    <InputTextBox placehoderValue="Password" type="password" forField="password" onInputChange={this.props.onInputChange}/>
                    <br/>
                    {(this.props.connectingToBackend)?<LoadingDots/>:<RaisedButtonType1 value="Log In" onClick={this.props.onLogin} />}
                </div>
                <p className="go-to-signup">Do not have have account?<span onClick={() => this.props.changeChoice()}>Sign Up</span></p> 
            </div>
        );
    }
}
 
export default LogInForm;