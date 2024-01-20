import React from 'react';
import RaisedButtonType2 from '../CustomMaterialUI/RaisedButtonType2/RaisedButtonType2';
import RaisedButtonType1 from '../CustomMaterialUI/RaisedButtonType1/RaisedButtonType1';
import './CardSummary.css';
import { RC4 } from 'crypto-js';

class CardSummary extends React.Component{

    render() {
        return(
            <div className="card-summary-container">
                <div className="card-info-section">
                    <p className="summary">Summary</p>
                    
                    <div className="card-info-group">
                        Card Balance
                        <p className="card-balance">&#x20B9;{this.props.value.currentBalance}</p>
                    </div>
                    
                    <div className="card-info-group">
                        Total Due
                        <p className="total-due">&#x20B9;{this.props.value.outstandingAmt}</p>
                    </div>
                    
                    <div className="card-info-group">
                        Due Date
                        <p className="due-date">{(this.props.value.dueDate==null||this.props.value.dueDate==="")?"--":this.props.value.dueDate}</p>
                    </div>
                    
                </div>
                
                <div className="btn-grp">
                    {/* <RaisedButtonType2 value="View Statements" onClick={this.props.onGetStatement.bind(this, this.props.value.cardNumber)}/> */}
                    <a href={"/statement/"+this.props.value.cardNumber}><RaisedButtonType2 value="View Statements" onClick={this.props.onGetStatement}/></a>
                    {/* {(this.props.value.outstandingAmt>0)?<a href={"/pay-bill/"+this.props.value.cardNumber+"/"+this.props.value.outstandingAmt}><RaisedButtonType1 value="Pay Now" onClick={()=>this.props.displayPaymentModal(this.props.value.cardNumber, this.props.value.outstandingAmt)}/></a>:<p className="fully-paid">Fully paid</p>} */}
                    {(this.props.value.outstandingAmt>0)?<RaisedButtonType1 value="Pay Now" onClick={()=>this.props.onPaymentClicked(this.props.value.cardNumber, this.props.value.outstandingAmt)}/>:<p className="fully-paid">Fully paid</p>}
                </div>
            </div>
        );
    }
}

export default CardSummary;