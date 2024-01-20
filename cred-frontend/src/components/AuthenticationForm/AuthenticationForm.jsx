import React from 'react';
import LogInForm from '../LogInForm/LogInForm';
import SignUpForm from '../SignUpForm/SignUpForm';
import './AuthenticationForm.css';
import logo from './../../assets/credlogo.png';
import auth from '../../auth';
import {withRouter} from 'react-router-dom';

class AuthenticationForm extends React.Component {
    state = {
        choice:1,
        userName:null,
        password:null,
        fullName:null,
        messageString:null,
        connectingToBackend:false
    }
    render() {
        console.log("Authentication form rendering");
        return (
            <div className="main-container">
                <div className="left-container">
                    <div className="message-container">
                        <img src={logo} alt="cred logo"/>
                        <p className="message">You are few steps away from making your life much simpler</p>
                    </div>
                </div>

                <div className="right-container">
                    {(this.state.choice === 1)?
                        <LogInForm 
                            changeChoice={this.handleChoice} 
                            onLogin={this.handleLogin} 
                            onInputChange={this.handleInputChange} 
                            message={this.state.messageString}
                            flushStateOnUnmount = {this.flushStateProp}
                            connectingToBackend = {this.state.connectingToBackend}
                        />
                        :
                        <SignUpForm 
                            changeChoice={this.handleChoice} 
                            onSignUp={this.handleSignUp} 
                            onInputChange={this.handleInputChange}
                            message={this.state.messageString}
                            flushStateOnUnmount = {this.flushStateProp}
                            connectingToBackend = {this.state.connectingToBackend}
                        />
                    }
                </div>
            </div>
        );
        
    }

    handleChoice = () => {
        let choice = this.state.choice;

        if (choice === 1) choice = 2;
        else choice = 1;
        
        this.setState({
            choice:choice
        });
    }

    handleInputChange = (field,event) =>{

        this.setState(
            {[field]:event.target.value}
        );
        
    }

    handleLogin = () => {

        const {userName, password} = this.state;

        if (userName == null || userName.trim() === "" ||
            password == null || password.trim() === "") {
                this.setState({messageString:"Please fill up the required fields"});
                return;
            }

        console.log("From handle login");

        this.setState({connectingToBackend:true});
        
        fetch('http://localhost:8080/api/login', {
            method:"POST",
            headers:new Headers({'Content-type':'application/json'}),
            body:JSON.stringify({
                "userName":userName,
                "password":password,
            })
        })
        .then(response => {
            if (response.status === 200) {
                response.json().then(json => {
                    localStorage.setItem('token', json.jwtToken);
                    this.props.history.replace("/dashboard");
                }) 
            } else if (response.status === 400){
                response.json().then(json => this.setState({messageString:json.messageString}));
            }
        })
        .catch(() => this.setState({messageString:"Something went wrong. Please try again"}))
        .then(() => this.setState({connectingToBackend:false}))

    }

    handleSignUp = () => {
        const {userName, fullName, password} = this.state;
        // const responseText = auth.signupUser(userName, fullName, password);
        // this.setState({messageString:responseText});

        if (userName == null || userName.trim() === "" ||
            password == null || password.trim() === "" ||
            fullName == null || fullName.trim() === "") {
                this.setState({messageString:"Please fill up the required fields"});
                return;
            }

        this.setState({connectingToBackend:true});
        
        fetch('http://localhost:8080/api/signup', {
            method:"POST",
            headers:new Headers({'Content-type':'application/json'}),
            body:JSON.stringify({
                "userName":userName,
                "firstName":fullName.split(' ')[0],
                "lastName":fullName.split(' ')[1],
                "password":password,
            })
        })
        .then(response => {
            if (response.status === 200) {
                this.setState({messageString:"Account created successfully. Please login to continue"})
            } else if (response.status === 400){
                response.json().then(json => this.setState({messageString:json.messageString}));
            }
        })
        .catch(() => this.setState({messageString:"Something went wrong. Please try again"}))
        .then(() => this.setState({connectingToBackend:false}))
    }

    flushStateProp = () => {
        this.setState({userName:null, password:null, fullName:null, messageString:null});
    }
}
 
export default withRouter(AuthenticationForm);