import React from 'react';
import './StatementDetails.css';

class StatementDetails extends React.Component{

    render(){
        return (
            <div id="statement-details-card">
                <p id="statement-details-card-heading">Statement Details</p>
                <div id="statement-details-wrapper">
                    <div className="statement-info">
                        <p className="statement-info-heading">Statement Id</p>
                        <p className="statement-info-value">{this.props.statementDetails.statementId}</p>    
                    </div>
                    <div className="statement-info">
                        <p className="statement-info-heading sihr">Due date</p>
                        <p className="statement-info-value sivr">{this.props.statementDetails.dueDate}</p>    
                    </div>
                    <div className="statement-info">
                        <p className="statement-info-heading">Billing start date</p>
                        <p className="statement-info-value">{this.props.statementDetails.billStartDate}</p>    
                    </div>
                    <div className="statement-info">
                        <p className="statement-info-heading sihr">Outstanding amount</p>
                        <p className="statement-info-value sivr">&#x20B9;{this.props.statementDetails.outstandingAmount}</p>    
                    </div>
                    <div className="statement-info">
                        <p className="statement-info-heading">Billing end date</p>
                        <p className="statement-info-value">{this.props.statementDetails.billEndDate}</p>    
                    </div>
                    <div className="statement-info">
                        <p className="statement-info-heading sihr">Payment status</p>
                        <p className={(this.props.statementDetails.paymentStatus===true)?"statement-info-value sivr paid":"statement-info-value sivr unpaid"}>{(this.props.statementDetails.paymentStatus===true)?"Paid":"Unpaid"}</p>    
                    </div>
                </div>
            </div>
        );
    }
}

export default StatementDetails;