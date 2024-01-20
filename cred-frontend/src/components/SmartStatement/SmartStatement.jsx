import React from 'react';
import './SmartStatement.css';

class SmartStatement extends React.Component{

    getSubjectRows(){
        
        let totalTransactionAmtSum = this.props.transactionsBy.reduce(
            (sum, currTransaction) => sum+currTransaction.totalTransactionAmount, 0);

        return this.props.transactionsBy.map((transactionBy) => (
            <div className="subject-row">
                <span>{(this.props.type === "VENDOR")?transactionBy.merchantName:transactionBy.category}</span>
                <span>{((transactionBy.totalTransactionAmount/totalTransactionAmtSum)*100).toFixed(0)}%</span>
            </div>
        ));
    }

    render(){
        return(
            <div id="smart-statement-card">
                <p id="smart-statement-card-heading">{(this.props.type === "VENDOR")?"Top Vendors":"Top Categories"}</p>
                {this.getSubjectRows()}
            </div>
        );
    }
}

export default SmartStatement;