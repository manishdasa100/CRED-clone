import React from 'react';
import './Creditcard.css';
import {formatCreditCardNnumber} from '../../utils';

class Creditcard extends React.Component {
    render() { 
        var expDate = this.props.value.expiryDate;
        expDate = expDate.split("-")[1] + " / " + expDate.split("-")[0].substring(2,4);
        return (
            <div className="card-container">
                <p className="card-title">Credit Card</p>
                <br/>
                <br/>
                <pre className="card-number">{formatCreditCardNnumber(this.props.value.cardNumber,"  ")}</pre>
                <p className="card-exp-date">{expDate}</p>
                <p className="card-holder-name">{this.props.value.nameOnCard}</p>
                <p className="card-provider-name">{this.props.value.cardProvider}</p>
            </div>
        );
    }
}
 
export default Creditcard;