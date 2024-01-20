import React from 'react';
import './LandingPage.css';
import logo from '../../assets/credlogo.png';
import sliderImage1 from '../../assets/money-matters-bg.png';
import sliderImage2 from '../../assets/deserve-more-bg.png';
import sliderImage3 from '../../assets/security-bg.png';
import Footer from '../Footer/Footer';
import AnimatingCreditCard from '../AnimatingCeditCard/AnimatingCreditCard';

class LandingPage extends React.Component{

    render(){
        return (
            <div id="landing-page">
                <div id="container-one">
                    <div id="nav">
                        <img src={logo} alt="logo" />
                        <div id="auth-btn-grp">
                            <a href="/authenticate" className="auth-btn">Log In</a>
                            <a href="/authenticate" className="auth-btn">Sign Up</a>
                        </div>
                    </div>
                    <div id="c-o-left" data-aos="fade-right">
                        <p id="main-msg">Manage your credit cards <span id="gradient-text">most</span> effectively</p>
                        <p id="tag-line">Join 5.9M+ members already using CRED</p>
                        <a href="https://play.google.com/store/apps/details?id=com.dreamplug.androidapp" id="download-btn">Download CRED</a>
                    </div>
                    <div id="c-o-right" data-aos="fade-left">
                        <AnimatingCreditCard/>
                    </div>
                </div>

                <div id="container-two">
                    <div id="left-bar"><div></div></div>
                    <div id="feature-container">
                        <div id="feature-container-left">
                            <img id="slider-image1" src={sliderImage1} alt="sliderimage-1"/>
                            <img id="slider-image2" src={sliderImage2} alt="sliderimage-2"/>
                            <img id="slider-image3" src={sliderImage3} alt="sliderimage-3"/>
                        </div>
                        <div id="feature-container-right">
                            <div id="desc-slider1">
                                <p className="desc-header desc-header-1">we take your money matters seriously.</p>
                                <p className="desc-text desc-text-1"><span>so that you don’t have to.</span>
                                   never miss a due date with reminders to help you pay your bills on time, 
                                   instant settlements mean you never wait for your payments to go through 
                                   and statement analysis lets you know where your money goes.</p>
                            </div>
                            <div id="desc-slider2">
                                <p className="desc-header desc-header-2">feel special more often.</p>
                                <p className="desc-text desc-text-2"><span>exclusive rewards for paying your bills.</span>
                                every time you pay your credit card bills on CRED, you receive CRED coins. you 
                                can use these to win exclusive rewards or get special access to curated products
                                 and experiences. on CRED, good begets good.</p>
                            </div>
                            <div id="desc-slider2">
                                <p className="desc-header desc-header-3">security first.and second.</p>
                                <p className="desc-text desc-text-3"><span>what’s yours remains only yours.</span>
                                CRED ensures that all your personal data and transactions are encrypted, and 
                                secured so what’s yours remains only yours. there’s no room for mistakes because 
                                we didn’t leave any.</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="container-three">
                    <div>
                        <p id="story-heading" 
                            data-aos="fade-right"
                            data-aos-easing="ease-out-cubic"
                            data-aos-duration="1000">
                            The story of CRED begins with <span>trust</span>
                        </p>
                        <hr data-aos="fade-right" 
                            data-aos-duration="1000"
                            data-aos-delay="1000"/>
                        <p id="story-text"
                            data-aos="fade-left"
                            data-aos-duration="1500"
                            >
                            trust as a virtue has consistently played an essential role in every great human achievement. and 
                            consistently, its importance has been overlooked. not just by individuals, but by entire societies. 
                            we felt it was time someone gave it the spotlight it deserves. especially for the ones who live by 
                            this virtue: the trustworthy.<br/>
                            so we thought of creating a system that rewards its members for doing good and being trustworthy. 
                            this way, trust as a virtue becomes something to aspire to, just the way it should be. then we went 
                            one step ahead: we built it. we know we are on the right track because here you are.<br/>
                            if you make it to CRED, congratulations and welcome. we have a lot of things planned for you.                          
                        </p>
                    </div>
                </div>
                <Footer/>
            </div>
        );
    }
}

export default LandingPage;