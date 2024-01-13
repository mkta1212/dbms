import axios from "axios";
import Cookies from "universal-cookie";
import { jwtExp } from "./authVerify";
import moment from "moment";

const API_URL = "http://localhost:8080/api/auth/";

    
const register = (username, email, password) => {
  return axios.post(API_URL + "signup", {
    username,
    email,
    password,
  });
};

const login = (username, password) => {  
  return axios
    .post(API_URL + "signin", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
        
      }
      const cookies = new Cookies(null,{path:"/"})
      const age = jwtExp(response.data.jwt)
      cookies.set("jwt",response.data.jwt,{expires:new Date(moment(age*1000).format('YYYY-MM-DDTHH:mm'))})
      cookies.set("roles",response.data.roles,{expires:new Date(age*1000)})
      return response.data;
    });
};

const logout = () => {
  const cookies = new Cookies(null,{path:"/"})
  cookies.remove("jwt")
  cookies.remove("roles")
  localStorage.removeItem("user");
};

const getCurrentRoles = () => {
  const cookies = new Cookies(null,{path:"/"})
  console.log(cookies)
  if(cookies.get("roles")){
    return cookies.get("roles")
  }
  return undefined
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  register,
  login,
  logout,
  getCurrentRoles,
};

export default AuthService;
  
