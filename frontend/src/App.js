import React, { Component } from "react";
import { Routes, Route, Link, Router } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import Profile from "./components/profile.component";
import BoardUser from "./components/board-user.component";
import BoardModerator from "./components/board-moderator.component";
import BoardAdmin from "./components/board-admin.component";

// import AuthVerify from "./common/auth-verify";
import EventBus from "./common/EventBus";
import SearchPage from "searchPage";
import CreateEvent from "event/createEvent";
import MyEvents from "event/myEvent";
import MyParticipation from "event/myParticipation";
import BookedClassroom from "event/bookedClassroom";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        showModeratorBoard: user.roles.includes("ROLE_MODERATOR"),
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }
    
    EventBus.on("logout", () => {
      this.logOut();
    });
  }

  componentWillUnmount() {
    EventBus.remove("logout");
  }

  logOut() {
    AuthService.logout();
    this.setState({
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    });
  }

  render() {
    // let isTabHidden = false;
    // function clearUser(){
    //   localStorage.removeItem("user");
    // }
   
    // document.addEventListener('visibilitychange', () => {
    //   if (document.visibilityState === 'hidden') {
    //       isTabHidden = true;
    //   }
    //  });
    // window.addEventListener('beforeunload',()=>{
    //   if(isTabHidden){
    //     localStorage.removeItem("user")} });
    const { currentUser, showModeratorBoard, showAdminBoard } = this.state;
    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/home"} className="nav-link">
                Home
              </Link>
            </li>
            {currentUser && (
              <li className="nav-item">
                <Link to={"/events"} className="nav-link">
                  活動
                </Link>
              </li>
            )}
            {currentUser && (
              <li className="nav-item">
                <Link to={"/chooseClassroom"} className="nav-link">
                  創建活動
                </Link>
              </li>
            )}
            {currentUser && (
              <li className="nav-item">
                <Link to={"/myEvents"} className="nav-link">
                  我舉辦的活動
                </Link>
              </li>
            )}
            {currentUser && (
              <li className="nav-item">
                <Link to={"/myParticipation"} className="nav-link">
                  我參加的活動
                </Link>
              </li>
            )}
          </div>

          {currentUser ? (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/profile"} className="nav-link">
                  {currentUser.username}
                </Link>
              </li>
              <li className="nav-item">
                <a href="/login" className="nav-link" onClick={this.logOut}>
                  LogOut
                </a>
              </li>
            </div>
          ) : (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/login"} className="nav-link">
                  Login
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/register"} className="nav-link">
                  Sign Up
                </Link>
              </li>
            </div>
          )}
        </nav>

        <div className="container mt-3">
          {/* <Router> */}
            <Routes>
              <Route exact path="/" element={<Home />} />
              <Route exact path="/home" element={<Home />} />
              <Route exact path="/login" element={<Login /> } />
              <Route exact path="/register" element={<Register />} />
              <Route exact path="/profile" element={<Profile />} />
              <Route path="/events" element={<SearchPage />} />
              <Route path="/createEvents" element={<CreateEvent />} />
              <Route path="/myEvents" element={<MyEvents />} />
              <Route path="/myParticipation" element={<MyParticipation />} />
              <Route path="chooseClassroom" element = {<BookedClassroom />} />
              <Route path="board" element = {<BoardAdmin />} />
              {/* <Route exact path={["/", "/home"]} component={Home} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
              <Route exact path="/profile" component={Profile} />
               */}
            </Routes>
          {/* </Router> */}
        </div>

        { /*<AuthVerify logOut={this.logOut}/> */ }
      </div>
    );
  }
}

export default App;
