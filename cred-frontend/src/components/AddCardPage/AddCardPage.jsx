import React from 'react';
import './AddCardPage.css';
import logo from '../../assets/credlogobw.png';
import {withRouter} from 'react-router-dom';
import LoadingDots from '../CustomMaterialUI/LoadingDots/LoadingDots';

class AddCardPage extends React.Component{

    state = {
        newCardNumber:[],
        message:null,
        connectingToBackend:false
    }

    componentDidMount() {
        console.log("From initial:" + this.state.newCardNumber.toString());
    }

    render() {
        console.log("rendering...");
        return (
            <div className="add-card-container">
                <div className="add-card-left-container">
                    <p id="add-your-card-title">Add your card</p>
                </div>
                <div className="add-card-right-container">
                    <img src={logo} id="cred-logo-in-add-card" alt="cred logo"/>
                    <div className="enter-card-no-section">
                        <p>Please enter your credit card number</p>
                        <div className="card-no">
                            <input type="number" id="one" onKeyUp={()=>this.shiftFocus("one","two")} onChange={(event)=>this.handleInputChange(0, event.target.value)}/>
                            <input type="number" id="two" onKeyUp={()=>this.shiftFocus("two","three")} onChange={(event)=>this.handleInputChange(1, event.target.value)}/>
                            <input type="number" id="three" onKeyUp={()=>this.shiftFocus("three","four")} onChange={(event)=>this.handleInputChange(2, event.target.value)}/>
                            <input type="number" id="four" onKeyUp={()=>this.shiftFocus("four","five")} onChange={(event)=>this.handleInputChange(3, event.target.value)}/>
                            <input type="number" id="five" onKeyUp={()=>this.shiftFocus("five","six")} onChange={(event)=>this.handleInputChange(4, event.target.value)}/>
                            <input type="number" id="six" onKeyUp={()=>this.shiftFocus("six","seven")} onChange={(event)=>this.handleInputChange(5, event.target.value)}/>
                            <input type="number" id="seven" onKeyUp={()=>this.shiftFocus("seven","eight")} onChange={(event)=>this.handleInputChange(6, event.target.value)}/>
                            <input type="number" id="eight" onKeyUp={()=>this.shiftFocus("eight","nine")} onChange={(event)=>this.handleInputChange(7, event.target.value)}/>
                            <input type="number" id="nine" onKeyUp={()=>this.shiftFocus("nine","ten")} onChange={(event)=>this.handleInputChange(8, event.target.value)}/>
                            <input type="number" id="ten" onKeyUp={()=>this.shiftFocus("ten","eleven")} onChange={(event)=>this.handleInputChange(9, event.target.value)}/>
                            <input type="number" id="eleven" onKeyUp={()=>this.shiftFocus("eleven","twelve")} onChange={(event)=>this.handleInputChange(10, event.target.value)}/>
                            <input type="number" id="twelve" onKeyUp={()=>this.shiftFocus("twelve","thirteen")} onChange={(event)=>this.handleInputChange(11, event.target.value)}/>
                            <input type="number" id="thirteen" onKeyUp={()=>this.shiftFocus("thirteen","fourteen")} onChange={(event)=>this.handleInputChange(12, event.target.value)}/>
                            <input type="number" id="fourteen" onKeyUp={()=>this.shiftFocus("fourteen","fifteen")} onChange={(event)=>this.handleInputChange(13, event.target.value)}/>
                            <input type="number" id="fifteen" onKeyUp={()=>this.shiftFocus("fifteen","sixteen")} onChange={(event)=>this.handleInputChange(14, event.target.value)}/>
                            <input type="number" id="sixteen" onKeyUp={()=>this.shiftFocus("sixteen","add-card-btn")} onChange={(event)=>this.handleInputChange(15, event.target.value)}/>
                        </div>
                        <span id="message">{(this.state.message)?this.state.message:null}</span>
                        <button id="add-card-btn" onClick={()=>this.handleAddCard()} disabled={this.state.connectingToBackend}>{(this.state.connectingToBackend)?<LoadingDots/>:'Add card'}</button>
                    </div>
                </div>
            </div>
        );
    }

    shiftFocus = (current, next) => {
        if (document.getElementById(current).value){
            document.getElementById(next).focus();
        } 
    }

    handleInputChange = (index, value) => {
        var cardNumber = this.state.newCardNumber;
        cardNumber[index] = value;
        this.setState({newCardNnumber:cardNumber})
    }

    handleAddCard = () => {
        console.log("Button clicked")
        var cardNumberStr = "";
        for (var i in this.state.newCardNumber) {
            cardNumberStr += this.state.newCardNumber[i];
        }

        if (cardNumberStr.length === 16) {
            console.log("From handle Add card in AddCardComponent"+cardNumberStr);
            this.setState({connectingToBackend:true})
            fetch('http://localhost:8080/api/addCard', {
                method:'POST',
                headers:new Headers({
                    'Authorization': 'Bearer ' + localStorage.getItem('token'),
                    'Content-Type':'application/json'
                }),
                body:JSON.stringify({'cardNumber':cardNumberStr})
            })
            .then(response => {
                if (response.status === 200) {
                    this.setState({message:'Card Added', connectingToBackend:false})
                } else if (response.status === 400) {
                    response.json()
                    .then(json => this.setState({message:json.messageString, connectingToBackend:false}));
                } else if (response.status === 403) {
                    this.props.history.replace("/authenticate");
                } 
            })
            .catch(() => this.setState({message:'Something went wrong. Please try again!', connectingToBackend:false}))
        } else {
            this.setState({message:'Card number should be 16 digits long'});
        }
    }
}

export default withRouter(AddCardPage);