import React, { Component, useState, useEffect} from "react";
import { Routes, Route, Link, Router } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./authService/authService";
import { useDispatch, useSelector } from "react-redux";
import Login from "./authService/login";
import Register from "./authService/register";
import Home from "./components/home.component";
import Profile from "./components/profile.component";
import { styled, useTheme } from '@mui/material/styles';
import SearchPage from "searchPage";
import CreateEvent from "createEventService/createEvent";
import MyEvents from "event/myEvent";
import MyParticipation from "event/myParticipation";
import BookedClassroom from "createEventService/bookedClassroom";
import eventBus from './authService/eventBus';
import AuthVerify from './authService/authVerify';
import { AppBar, Box, Button, Paper} from '@mui/material';
import Toolbar from '@mui/material/Toolbar';
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import Drawer from '@mui/material/Drawer';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import LoginIcon from '@mui/icons-material/Login';
import Divider from '@mui/material/Divider';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import authService from "services/auth.service";
import Container from '@mui/material/Container';
import { useCallback } from "react";


const drawerWidth = 240;

const DrawerHeader = styled('div')(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
  justifyContent: 'flex-end',
}));

// export default function App() {
//   const [open, setOpen] = useState(false);
//   const { user: currentUser } = useSelector((state) => state.auth);
//   const dispatch = useDispatch();

//   const handleDrawerOpen = () => {
//     setOpen(true);
//   };

//   const handleDrawerClose = () => {
//     setOpen(false);
//     // const decodedJwt = JSON.parse(atob(user.accessToken.split(".")[1]));

//     // console.log(decodedJwt.exp  )
//   };
  
//   const [verified, setVerified] = useState(false);
//   const [user, setUser] = useState(false);
//   const [admin, setAdmin] = useState(false);
//   const [provider, setProvider] = useState(false);

//   const logOut = useCallback(() => {
//       dispatch(AuthService.logout())
//   },[dispatch]);


//   useEffect(() => {
//     const user = AuthService.getCurrentUser();
//     console.log(user)
//     if (user) {
//       setVerified(true);
//       if(user.roles.includes('ROLE_USER')){
//         setUser(true)
//       }
//       else if(user.roles.includes('ROLE_ADMIN')){
//         setAdmin(true)
//       }
//       else if(user.roles.includes('ROLE_PROVIDER')){
//         setProvider(true)
//       }
//     }

//     eventBus.on("logout", () => {
//       logOut();
//     });

//     return () => {
//       eventBus.remove("logout");
//     };
//   }, [user,logOut]);

 

//   return (
    
//     <>
//     <AppBar position="fixed" open={open}>
//       <Toolbar>
//         <IconButton
//           color="inherit"
//           aria-label="open drawer"
//           onClick={handleDrawerOpen}
//           edge="start"
//           sx={{ mr: 2, ...(open && { display: 'none' }) }}
//         >
//           <MenuIcon />
//         </IconButton>
//         <Typography variant="h6" noWrap component="div" >
//           I'm in
//         </Typography>
//         {verified?
//         // <Box display='flex' justifyContent='right'>
//           <Button  display='flex' variant="h6" onClick={logOut} sx={{ marginLeft: "auto" }}>
//             Logout
//           </Button>:""
//         // </Box>
//       }
//       </Toolbar>
//     </AppBar>
//     <Drawer
//         sx={{
//           width: drawerWidth,
//           flexShrink: 0,
//           '& .MuiDrawer-paper': {
//             width: drawerWidth,
//             boxSizing: 'border-box',
//           },
//         }}
//         variant="persistent"
//         anchor="left"
//         open={open}
//       >
//         <DrawerHeader>
//           <IconButton onClick={handleDrawerClose}>
//             {<ChevronLeftIcon /> }
//           </IconButton>
//         </DrawerHeader>
//         <Divider />
//         <List>
//             {verified?"":(
//               <>
//             <ListItem  disablePadding >
//               <ListItemButton to='/register'>
//                 <ListItemIcon>
                  
//                 </ListItemIcon>
//                 <ListItemText primary={"Register"} />
//               </ListItemButton>
//             </ListItem>
//             <ListItem  disablePadding >
//               <ListItemButton to='/login'>
//                 <ListItemIcon>
//                   {<LoginIcon></LoginIcon>}
//                 </ListItemIcon>
//                 <ListItemText primary={"Login"} />
//               </ListItemButton>
//             </ListItem>
//             </>
//             )}
//             {user?(
//               <>
//             <ListItem  disablePadding >
//               <ListItemButton to='/home'>
//                 <ListItemIcon>
                  
//                 </ListItemIcon>
//                 <ListItemText primary={"Home"} />
//               </ListItemButton>
//             </ListItem>
//             <ListItem  disablePadding >
//               <ListItemButton to="/events">
//                 <ListItemIcon>
              
//                 </ListItemIcon>
//                 <ListItemText primary={"活動"} />
//               </ListItemButton>
//             </ListItem>
//             <ListItem  disablePadding >
//               <ListItemButton to="/chooseClassroom">
//                 <ListItemIcon>
              
//                 </ListItemIcon>
//                 <ListItemText primary={"創建活動"} />
//               </ListItemButton>
//             </ListItem>
//             <ListItem  disablePadding >
//               <ListItemButton to="/myEvents">
//                 <ListItemIcon>
              
//                 </ListItemIcon>
//                 <ListItemText primary={"我舉辦的活動"} />
//               </ListItemButton>
//             </ListItem>
//             <ListItem  disablePadding >
//               <ListItemButton to="/myParticipation">
//                 <ListItemIcon>
              
//                 </ListItemIcon>
//                 <ListItemText primary={"我參加的活動"} />
//               </ListItemButton>
//             </ListItem>
//             </>
//             ):""}
          
            
//         </List>
//         <Divider />
//     </Drawer>
//     <DrawerHeader />
//     <Container>
//           {/* <Box  display="flex"
//               justifyContent="center"
//               alignItems="center"> */}
//             <Routes>
//                 <Route exact path="/" element={<Home />} />
//                 <Route exact path="/home" element={<Home />} />
//                 <Route exact path="/login" element={<Login /> } />
//                 <Route exact path="/register" element={<Register />} />
//                 <Route exact path="/profile" element={<Profile />} />
//                 <Route path="/events" element={<SearchPage />} />
//                 <Route path="/createEvents" element={<CreateEvent />} />
//                 <Route path="/myEvents" element={<MyEvents />} />
//                 <Route path="/myParticipation" element={<MyParticipation />} />
//                 <Route path="chooseClassroom" element = {<BookedClassroom />} />
//                 {/* <Route exact path={["/", "/home"]} component={Home} />
//                 <Route exact path="/register" component={Register} />
//                 <Route exact path="/profile" component={Profile} />
//                 */}
//             </Routes>
//             {/* </Box> */}
//         </Container>
//         <AuthVerify logOut={logOut}/>
//       </>
//   );
// }




export default class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
    this.handleDrawerOpen = this.handleDrawerOpen.bind(this);
    this.handleDrawerClose = this.handleDrawerClose.bind(this);

    this.state = {
      verified: false,
      user: false,
      admin: false,
      open:false
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        verified: true,
        user: user.roles.includes("ROLE_USER"),
        admin: user.roles.includes("ROLE_ADMIN"),
      });
    }
    
    eventBus.on("logout", () => {
      this.logOut();
    });
  }

  componentWillUnmount() {
    eventBus.remove("logout");
  }
  handleDrawerClose(e) {
    this.setState({
      open:false
    });
  }
  handleDrawerOpen(e){
    this.setState({
      open:true
    });
  }
  logOut() {
    AuthService.logout();
    this.setState({
      verified: undefined,
      user: false,
      admin: false,
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
    const { verified, user, admin, open } = this.state;
    return (
    
      <>
      <AppBar position="fixed" open={open}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={this.handleDrawerOpen}
            edge="start"
            sx={{ mr: 2, ...(open && { display: 'none' }) }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div" >
            I'm in
          </Typography>
          {verified?
          // <Box display='flex' justifyContent='right'>
            <Button  display='flex' variant="h6" onClick={this.logOut} sx={{ marginLeft: "auto" }}>
              Logout
            </Button>:""
          // </Box>
        }
        </Toolbar>
      </AppBar>
      <Drawer
          sx={{
            width: drawerWidth,
            flexShrink: 0,
            '& .MuiDrawer-paper': {
              width: drawerWidth,
              boxSizing: 'border-box',
            },
          }}
          variant="persistent"
          anchor="left"
          open={open}
        >
          <DrawerHeader>
            <IconButton onClick={this.handleDrawerClose}>
              {<ChevronLeftIcon /> }
            </IconButton>
          </DrawerHeader>
          <Divider />
          <List>
              {verified?"":(
                <>
              <ListItem  disablePadding >
                <ListItemButton to='/register'>
                  <ListItemIcon>
                    
                  </ListItemIcon>
                  <ListItemText primary={"Register"} />
                </ListItemButton>
              </ListItem>
              <ListItem  disablePadding >
                <ListItemButton to='/login'>
                  <ListItemIcon>
                    {<LoginIcon></LoginIcon>}
                  </ListItemIcon>
                  <ListItemText primary={"Login"} />
                </ListItemButton>
              </ListItem>
              </>
              )}
              {user?(
                <>
              <ListItem  disablePadding >
                <ListItemButton to='/home'>
                  <ListItemIcon>
                    
                  </ListItemIcon>
                  <ListItemText primary={"Home"} />
                </ListItemButton>
              </ListItem>
              <ListItem  disablePadding >
                <ListItemButton to="/events">
                  <ListItemIcon>
                
                  </ListItemIcon>
                  <ListItemText primary={"活動"} />
                </ListItemButton>
              </ListItem>
              <ListItem  disablePadding >
                <ListItemButton to="/chooseClassroom">
                  <ListItemIcon>
                
                  </ListItemIcon>
                  <ListItemText primary={"創建活動"} />
                </ListItemButton>
              </ListItem>
              <ListItem  disablePadding >
                <ListItemButton to="/myEvents">
                  <ListItemIcon>
                
                  </ListItemIcon>
                  <ListItemText primary={"我舉辦的活動"} />
                </ListItemButton>
              </ListItem>
              <ListItem  disablePadding >
                <ListItemButton to="/myParticipation">
                  <ListItemIcon>
                
                  </ListItemIcon>
                  <ListItemText primary={"我參加的活動"} />
                </ListItemButton>
              </ListItem>
              </>
              ):""}
              {admin?"":""

              }
              
          </List>
          <Divider />
      </Drawer>
      <DrawerHeader />
      <Container>
            {/* <Box  display="flex"
                justifyContent="center"
                alignItems="center"> */}
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
                  {/* <Route exact path={["/", "/home"]} component={Home} />
                  <Route exact path="/register" component={Register} />
                  <Route exact path="/profile" component={Profile} />
                  */}
              </Routes>
              {/* </Box> */}
          </Container>
          <AuthVerify logOut={this.logOut}/>
        </>
    );
  }
}