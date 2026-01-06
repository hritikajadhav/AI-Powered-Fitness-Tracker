import { BrowserRouter as Router, Navigate, Route, Routes } from "react-router";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import { useDispatch } from "react-redux";
import { setCredentials } from "./store/authSlice";
import { useAuthContext } from "react-oauth2-code-pkce";
import { useEffect, useState } from "react";
import  ActivityForm from "./components/ActivityForm";
import { ActivityList } from "./components/ActivityList";
import ActivityDetail from "./components/ActivityDetail";
import { Typography, Container } from "@mui/material";

const ActivitiesPage = () => {
  return (
    <Container maxWidth="md">
      <ActivityForm onActivityAdded={() => window.location.reload()} />
      <ActivityList />
    </Container>
  );
};

function App() {
  const { token, tokenData, logIn, logOut } = useAuthContext();
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <Router>
      {!token ? (
        <Box
          sx={{
            height: "100vh",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            textAlign: "center",
            bgcolor: "#f5f5f5"
          }}
        >
          <Typography variant="h4" gutterBottom>
            Welcome to Fitness Tracker App
          </Typography>

          <Typography variant="subtitle1" sx={{ mb: 3 }}>
            Please login to access your activities
          </Typography>

          <Button variant="contained" onClick={() => logIn()}>
            Login
          </Button>
        </Box>
      ) : (
        <Box sx={{ p: 2 }}>
          <Box sx={{ display: "flex", justifyContent: "flex-end", mb: 2 }}>
            <Button variant="outlined" color="secondary" onClick={logOut}>
              Logout
            </Button>
          </Box>

          <Routes>
            <Route path="/activities" element={<ActivitiesPage />} />
            <Route path="/activities/:id" element={<ActivityDetail />} />
            <Route
              path="/"
              element={<Navigate to="/activities" replace />}
            />
          </Routes>
        </Box>
      )}
    </Router>
  );
}

export default App;
















// import { BrowserRouter as Router, Navigate, Route, Routes, useLocation } from "react-router";
// import Button from '@mui/material/Button';
// import Box from '@mui/material/Box';
// import { useDispatch } from "react-redux";
// import { setCredentials } from "./store/authSlice";
// import { useAuthContext } from "react-oauth2-code-pkce";
// import { useEffect, useState } from "react";
// import ActivityForm from "./components/ActivityForm";
// import { ActivityList } from "./components/ActivityList";
// import ActivityDetail from "./components/ActivityDetail";
// import { Typography } from "@mui/material";

// const ActivitiesPage = () => {
//   return (<Box components="section" sx={{p:2, border:'1px dashed grey'}}>
//      <ActivityForm  onActivityAdded = { () => window.location.reload()}/>
//      <ActivityList />  
//   </Box>);
// }

// function App() {

//   const{ token, tokenData, logIn, logOut, isAuthenticated } = useAuthContext();
//   const dispatch = useDispatch();
//   const[authReady, setAuthReady] = useState(false);
//   useEffect(() => {
//     if(token){
//       dispatch(setCredentials({token, user: tokenData}));
//       setAuthReady(true);
//     }

//   } , [token, tokenData, dispatch]);


//   return (
//    <Router>
//     {!token ? (

//       <Box sx={{
//         height: "100vh",
//         display: "flex",
//         flexDirection: "column",
//         alignItems: "centre",
//         justifyContent: "centre",
//         textAlign: "centre"
//       }}>

//         <Typography variant="h4" gutterBottom>
//             Welcome to Fitness Tracker App
//         </Typography>
//         <Typography varinat="subtitle1" sx={{ mb:3 }}>
//             Please Login to access your activities.
//         </Typography>
//         <Button variant="contained" color="#dc004e" 
//       onClick={ () => {
//                logIn();
//       }
//       }> Login</Button>
//       </Box>
      
//      ) : (
//       // <div>
//       //   <pre>{JSON.stringify(tokenData,null,2)}</pre>
//       //   <pre>{JSON.stringify(token,null,2)}</pre>
//       // </div>

//       <Box components="section" sx={{p:2, border:'1px dashed grey'}}>
//         <Box sx={{ p: 2, border: '1px dashed grey'}}>
//           <Button variant="contained" color="secondary" onClick={() => {
//             logOut(); 
//           }}> 
//             Logout</Button>
//         </Box>
//         <Routes>
//           <Route path="/activities" element={<ActivitiesPage />}/>
//           <Route path="/activities/:id" element={<ActivityDetail />}/>
//           <Route path="/" element={token ? <Navigate to= "/activities" replace/> : <div>Welcome! Please Login!</div>}></Route>
//         </Routes>
//       </Box>
//      )}
//    </Router>
//   );
// }

// export default App;
