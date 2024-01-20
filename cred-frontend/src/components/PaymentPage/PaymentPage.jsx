import React from 'react';
import RaisedButtonType2 from '../CustomMaterialUI/RaisedButtonType2/RaisedButtonType2';
import './PaymentPage.css';
import PaymentSuccessComponent from '../CustomMaterialUI/PaymentSuccessComponent/PaymentSuccessComponent';
import {formatCreditCardNnumber} from '../../utils';

class PaymentPage extends React.Component{

    state = {
        amount:0,
        message:null,
        paymentSuccessful:false
    }

    render(){
        return (
            <div id="payment-page">
                <div id="payment-window">
                    <p id="payment-window-heading">Payment</p>
                    <div id="payment-window-body">
                        <p id="err-msg">{(this.state.message)?this.state.message:null}</p>
                        <div id="payment-window-main-section">
                            <div id="payment-window-left-section">
                                <label id="total-due-label">Total due</label>
                                <p id="total-due-amount">&#x20B9; {this.props.dueAmount}</p>
                            </div>
                            <div id="payment-window-right-section">
                                <label id="card-number-label">Card number</label>
                                <p id="card-number">{formatCreditCardNnumber(this.props.cardNumber, "-")}</p>
                                <label id="payable-amount-label">Amount</label>
                                <p id="payable-amount">&#x20B9;<input type="number" id="amount" autoFocus onInput={()=>this.resizeWidth("amount")} onChange={(event)=>this.handleInputChange("amount", event.target.value)}/></p>
                                <div id="payment-window-btn-grp">
                                    <RaisedButtonType2 value="Confirm payment" onClick={this.handlePayment}/>
                                    <RaisedButtonType2 value="Cancel payment" onClick={this.props.onCancelPayment}/>
                                </div>
                            </div>
                        </div>

                        {(this.state.paymentSuccessful)?
                            <div id="payment-sucess">
                                <PaymentSuccessComponent/>
                                <p id="payment-successful-text">Transaction completed</p>
                            </div>
                            :null
                        }
                    </div>
                </div>
            </div>
        );
    }

    resizeWidth(inputId){
        const input = document.getElementById(inputId);
        input.style.width = (input.value.length) + "ch";
    }

    handleInputChange(field, value){
        this.setState({[field]:value});
    }

    handlePayment = () => {
        console.log("Handle payment clicked");
        console.log(`cardNumber ${this.state.cardNumber}. amount ${this.state.amount}`);

        console.log(`Todays date ${typeof new Date().toISOString().split('T')[0]}`)
        
        if (this.state.amount > 0) {
            fetch('http://localhost:8080/api/payBill',{
                method:'POST',
                headers:new Headers({
                    'Authorization':'Bearer ' + localStorage.getItem('token'),
                    'Content-type':'application/json'
                }),
                body:JSON.stringify({
                    "amount":this.state.amount,
                    "type":"CREDIT",
                    "dateOfTransaction":new Date().toISOString().split('T')[0],
                    "cardNumber":this.props.cardNumber
                })
            })
            .then(response => {
                if (response.ok) {
                    this.setState({paymentSuccessful:true});
                    setTimeout(()=>{
                        this.props.onCancelPayment();
                    }, 2500);
                } else {
                    response.json().then(json => this.setState({message:json.messageString}));
                }
            })
        } else {
            this.setState({message:'Please enter an amount greater than zero'})
        }
           
    }

}

export default PaymentPage;