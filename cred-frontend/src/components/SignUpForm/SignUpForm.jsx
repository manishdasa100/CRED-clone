import React from 'react';
import InputTextBox from '../CustomMaterialUI/InputTextBox/InputTextBox';
import './SignUpForm.css';
import logo from './../../assets/credlogo.png';
import RaisedButtonType1 from '../CustomMaterialUI/RaisedButtonType1/RaisedButtonType1';
import LoadingDots from '../CustomMaterialUI/LoadingDots/LoadingDots';

class SignUpForm extends React.Component {

    componentWillUnmount(){
        this.props.flushStateOnUnmount();
    }
    
    render() { 
        return (
            <div className="signup-container">
                <div className="header">
                    <img src={logo} alt="header-logo" className="header-img" width="52px" height="69px"></img>
                    <p className="header-title">Create your account</p>
                </div>
                <p className={(this.props.message)?'message-text-visible':'message-text-hidden'}>{this.props.message}</p>
                <div className="signup-form">
                    <InputTextBox placehoderValue="Username" type="text" forField="userName" onInputChange={this.props.onInputChange}/>
                    <InputTextBox placehoderValue="Full name" type="text" forField="fullName" onInputChange={this.props.onInputChange}/>
                    <InputTextBox placehoderValue="Password" type="password" forField="password" onInputChange={this.props.onInputChange}/>
                    <br/>
                    {(this.props.connectingToBackend)?<LoadingDots/>:<RaisedButtonType1 value="Register" onClick={this.props.onSignUp}/>}
                </div>
                <p className="go-to-login">Already have account?<span onClick={() => this.props.changeChoice()}>Log In</span></p>    
            </div>
        );
    }
}
 
export default SignUpForm;