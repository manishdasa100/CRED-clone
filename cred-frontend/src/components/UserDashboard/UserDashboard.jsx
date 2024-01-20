import React from 'react';
import Creditcard from '../Creditcard/Creditcard';
import CardSummary from '../CardSummary/CardSummary';
import './UserDashboard.css';
import RaisedButtonType3 from '../CustomMaterialUI/RaisedButtonType3/RaisedButtonType3';
import RaisedButtonType2 from '../CustomMaterialUI/RaisedButtonType2/RaisedButtonType2';
import PaymentPage from '../PaymentPage/PaymentPage';
import auth from '../../auth';
import {withRouter} from 'react-router-dom';

class UserDashboard extends React.Component {
    
    state = { 
        userCards:[],
        isPaymentModalOpen:false,
        cardNumberForPayment:null,
        dueAmountForPayment:null
    }

    getCards() {

        console.log(`from UserDashboard ${JSON.stringify(this.state.userCards)}`);

        if (this.state.userCards.length > 0) {
            return this.state.userCards.map((card) => (
                <div className="card-row">
                    <Creditcard value={card}/>
                    <CardSummary value={card} onPaymentClicked={this.displayPaymentModal}/>
                </div>
            ));
        } else {
            return <p id="no-cards-msg">You have not added any cards yet. You can add them by clicking on the add card button.</p>
        }

    }

    componentDidMount() {

        fetch('http://localhost:8080/api/userCards',{
            method:'GET',
            headers:new Headers({
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
        })
        .then(response => response.json())
        .then(json => this.setState({userCards:json}))

        // this.props.handleGetUserCards()
        // .then(response => response.json())
        // .then(json => this.setState({userCards:json}))

    }

    render() { 
        console.log("Props history");
        console.log(this.props.history);
        return ( 
            <> 
                <div className="dashboard-container">
                    <div id="top-container">
                        <span id="your-cards">Your cards</span>
                        <a href="/add-card"><RaisedButtonType3  value='Add Card'/></a>
                        <RaisedButtonType2 value='Logout' className="logout-btn" onClick={()=>auth.logoutUser(()=>this.props.history.replace("/"))}/>
                    </div>
                    
                    <div id="user-cards-container">
                        {this.getCards()}
                    </div>
                </div>
                {(this.state.isPaymentModalOpen)?<PaymentPage cardNumber={this.state.cardNumberForPayment} dueAmount={this.state.dueAmountForPayment} onCancelPayment={()=>this.setState({isPaymentModalOpen:false})}/>:null}
            </>
        );
    }

    displayPaymentModal = (cardNumber, outstandingAmt) => {
        this.setState({isPaymentModalOpen:true, cardNumberForPayment:cardNumber, dueAmountForPayment:outstandingAmt});
    }
}
 
export default withRouter(UserDashboard);