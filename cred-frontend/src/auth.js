class Auth{
    
    constructor(){
        if (Auth.instance == null) {
            this.authenticated = false;
            this.xhr = new XMLHttpRequest();
            Auth.instance = this;
        }
        return Auth.instance;
    }

    loginUser(userName, password, cb){
        
        if (userName == null || userName.trim() === "" ||
            password == null || password.trim() === "") return "Please fill-up the required fields.";

        let loginCredentials = {
            "userName":userName,
            "password":password,
        }
        
        var reponseText = null;
        try{
            this.xhr.open('POST','http://localhost:8080/api/login', false);

            this.xhr.setRequestHeader('Content-Type', 'application/json');

            this.xhr.onload = () => {
                if (this.xhr.status === 200) {
                    var token = JSON.parse(this.xhr.response);
                    localStorage.setItem('token', token.jwtToken);
                    cb();
                } else if (this.xhr.status === 400){
                    const reponse = JSON.parse(this.xhr.response);
                    reponseText = reponse.messageString;
                }
            }

            this.xhr.send(JSON.stringify(loginCredentials));
            
        } catch(err) {
            // alert(err.message);
            reponseText = "Something went wrong!";
        }

        return reponseText;
    }

    logoutUser(cb){
        // alert("User logged out");
        localStorage.removeItem('token');
        cb();
    }

    signupUser(userName, fullName, password){

        if (userName == null || userName.trim() === "" ||
            fullName == null || fullName.trim() === "" ||
            password == null || password.trim() === "") return "Please fill-up the required fields.";

        let signUpCredentials = {
            "userName":userName,
            "firstName":fullName.split(' ')[0],
            "lastName":fullName.split(' ')[1],
            "password":password,
        }

        var responseText = null;

        try{
            this.xhr.open('POST','http://localhost:8080/api/signup', false);

            this.xhr.setRequestHeader('Content-Type', 'application/json');

            this.xhr.onload = () => {
                if (this.xhr.status === 200) {
                    responseText = "Account created successfully. Please login to continue";
                } else if (this.xhr.status === 400){
                    const response = JSON.parse(this.xhr.response);
                    responseText = response.messageString;
                }
            }

            this.xhr.send(JSON.stringify(signUpCredentials));
            
        } catch(err) {
            responseText = "Something went wrong. Please try again";
        }

        return responseText;
    }

    isAuthentcated(){
        
        if (localStorage.getItem('token') != null) {

            try{
                this.xhr.open('GET', 'http://localhost:8080/api/greet', false);

                this.xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
                
                this.xhr.onload = () => {
                    if (this.xhr.status === 200) {
                        this.authenticated = true;
                    } else if (this.xhr.status === 403) {
                        this.authenticated = false;
                    }
                }

                this.xhr.send();
            } catch(err){
                alert(err.message);
            }
            
        } 

        return this.authenticated;
    }
}

const auth = new Auth();
export default auth;