import React from 'react';
import './PaymentSuccessComponent.css'

class PaymentSuccessComponent extends React.Component{
    render(){
        return(
            <div id="payment-success-icon">
                <svg viewBox="0 0 100 100" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M2,50a48,48 0 1,0 96,0a48,48 0 1,0 -96,0"/>
                </svg>
            </div>
        );
    }
}

export default PaymentSuccessComponent;