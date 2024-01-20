import React from 'react';
import SmartStatement from '../SmartStatement/SmartStatement';
import StatementDetails from '../StatementDetails/StatementDetails'
import './StatementPage.css';
import {withRouter} from 'react-router-dom';
import {formatCreditCardNnumber} from '../../utils';

class StatementPage extends React.Component{

    state={
        statementDetails:null,
        errorMsg:null
    }

    componentDidMount(){

        fetch('http://localhost:8080/api/statement?' + new URLSearchParams({cardNumber:this.props.match.params.cardNumber}), {
            method:"GET",
            headers:new Headers({
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
        })
        .then(response => {
            if (response.status === 404) {
                response.json().then(json => this.setState({errorMsg:json.messageString}))
            } else if (response.status === 200) {
                response.json().then(json => this.setState({statementDetails:json}))
            }
        })
    }

    render(){

        return(
            <div id="statement-page">
                <p id="your-statement">Your Statement</p>
                {this.getStatementDetails()}
            </div>
        )
        

    }

    getStatementDetails(){

        if (this.state.statementDetails != null) {
            const {smartStatementDetails, ...rest} = this.state.statementDetails;
            return (
                <div id="statement-container">
                    <p id="statement-msg">Your latest statement for card no. <span id="card-number">{formatCreditCardNnumber(this.props.match.params.cardNumber, "-")}</span></p>
                    <div id="statement-cards-wrapper">
                        <div className="smart-statement-wrapper1">
                            <SmartStatement type="VENDOR" transactionsBy={smartStatementDetails.transactionsByVendor}/>
                        </div>
                        <div className="statement-details-wrapper"><StatementDetails statementDetails={{...rest}}/></div>
                        <div className="smart-statement-wrapper2">
                            <SmartStatement type="CATEGORY" transactionsBy={smartStatementDetails.transactionsByCategory}/>
                        </div>
                    </div>
                    <button id="search-statement-btn">Search statement</button>
                </div>
            );
        } else if (this.state.errorMsg != null){
            return (<p id="error-msg">{this.state.errorMsg}</p>)
        } else {
            return (<p>Loading...</p>)
        }
       
    }
}

export default withRouter(StatementPage);