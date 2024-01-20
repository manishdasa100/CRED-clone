import React from 'react';
import {Route} from 'react-router-dom';
import {Redirect} from 'react-router-dom';
import auth from '../../auth';

class ProtectedRoute extends React.Component{

    render(){

        let {component:Component, componentProps, ...rest} = this.props;


        return (
            <Route {...rest}>
                {this.isLoggedIn()?<Component {...componentProps}/>:<Redirect to="/authenticate"/>}
            </Route>
        );
    }

    isLoggedIn() {
        const authStatus = auth.isAuthentcated();
        return authStatus;
    }
        
}

export default ProtectedRoute;