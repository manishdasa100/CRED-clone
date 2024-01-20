import React from 'react';
import logo from '../../assets/credlogo.png';
import './Footer.css';

class Footer extends React.Component{

    render(){
        return(
            <footer>
                    <div id="footer-container">
                        <div id="footer-left">
                            <img src={logo} alt="logo" width="57px"/>
                            <p>
                                <b>Registered office address:</b><br/>
                                Surekha Square,<br/> 
                                Nelson Road, Rehabari,<br/> 
                                Guwahati, 781008<br/>
                                Assam, India<br/>
                                Tel: 999-999-999<br/>
                            </p>
                        </div>
                        <div id="footer-right">
                            <div>
                                <p><b>about CRED</b></p>
                                <p>
                                    CRED is a members only credit card bill payment platform that rewards its members for clearing 
                                    their credit card bills on time. CRED members get access to exclusive rewards and experiences 
                                    from premier brands upon clearing their credit card bills on CRED.
                                </p>
                            </div>
                            <div>
                                <p><b>banks supported on CRED</b></p>
                                <p>
                                    CRED supports credit card bill payment for American Express, Standard Chartered, Citibank, HSBC, 
                                    HDFC, ICICI, SBI, AXIS, Kotak, RBL, PNB and other top Indian banks. We support VISA, MasterCard, 
                                    American express and RuPay cards.
                                </p>
                            </div>
                            <p>for product feedback, reach out to us at feedback@cred.club</p>
                        </div>
                    </div>
                </footer>
        );
    }
}

export default Footer;