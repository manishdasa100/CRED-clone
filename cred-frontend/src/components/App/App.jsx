import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import AddCardPage from '../AddCardPage/AddCardPage';
import AuthenticationForm from '../AuthenticationForm/AuthenticationForm';
import LandingPage from '../LandingPage/LandingPage';
import UserDashboard from '../UserDashboard/UserDashboard';
import ProtectedRoute from '../ProtectedRoute/ProtectedRoute';
import './App.css';
import StatementPage from '../StatementPage/StatementPage';

class App extends React.Component {

    state={
        cardNumber:""
    }

    render() {
        return (
            <Router>
                <Switch>
                    <Route exact path="/authenticate">
                        <AuthenticationForm/>
                    </Route>
                    
                    <ProtectedRoute 
                        exact 
                        path="/dashboard" 
                        component={UserDashboard} 
                        componentProps={
                            {
                                handleGetStatement:this.handleGetStatement
                            }
                        }
                    />
                        
                    <ProtectedRoute 
                        exact 
                        path="/add-card" 
                        component={AddCardPage} 
                        // componentProps={
                        //     {
                        //         handleAddCard:this.handleAddCard
                        //     }
                        // }
                    />

                    <ProtectedRoute
                        exact
                        path="/statement/:cardNumber"
                        component={StatementPage}
                        // componentProps={
                        //     {
                        //         cardNumber:this.state.cardNumber
                        //     }
                        // }
                    />
                    
                    <Route exact path="/">
                        <LandingPage/>
                    </Route>
                    
                    <Route path="*">
                        <h1>404 Error</h1>
                    </Route>
                </Switch>
            </Router>
            // <PaymentSuccessComponent/>
        );
    }

    handleGetStatement(cardNumber){
        console.log("Get statement clicked ");
        this.setState({cardNumber:cardNumber})
    }
}

export default App;