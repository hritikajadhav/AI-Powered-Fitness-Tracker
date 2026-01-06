import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { getActivities } from "../services/api";
import { Card, CardContent, Typography, Grid } from "@mui/material";

export const ActivityList = () => {
  const [activities, setActivity] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getActivities().then((res) => setActivity(res.data));
  }, []);

  return (
    <Grid container spacing={2}>
      {activities.map((activity) => (
        <Grid item xs={12} sm={6} md={4} key={activity.id}>
          <Card
            sx={{
              cursor: "pointer",
              transition: "0.3s",
              "&:hover": { transform: "translateY(-4px)", boxShadow: 6 }
            }}
            onClick={() => navigate(`/activities/${activity.id}`)}
          >
            <CardContent>
              <Typography variant="h6">{activity.type}</Typography>
              <Typography variant="body2">
                Duration: {activity.duration} min
              </Typography>
              <Typography variant="body2">
                Calories: {activity.caloriesBurnt}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};



// import React, { useEffect, useState } from 'react'
// import { useNavigate } from 'react-router';
// import { getActivities } from '../services/api';
// import { Card, CardContent, Typography } from '@mui/material';
// import Grid from "@mui/material/Grid";

// export const ActivityList = () => {

//     const [activities, setActivity] = useState([]);
//     const navigate = useNavigate();

//     const fetchActivities = async() => {
//         try{
//             const response = await getActivities();
//             setActivity(response.data);
//         }catch(error){
//             console.error(error);
//         }
//     }

//   useEffect(() => {
//     fetchActivities();
//   }, []);  

//   return (
//     <Grid container spacing ={2}>
//         {activities.map((activity) => (
//             <Grid container spacing={{ xs:2, md:3 }} columns={{ xs: 4, sm: 8, md:12}}> 
//                 <Card sx={{cursor: 'pointer'}}
//                 onClick = {() => navigate(`/activities/${activity.id}`)}>
//                     <CardContent>
//                         <Typography variant='h6'> {activity.type}</Typography>
//                         <Typography> Duration:{activity.duration}</Typography>
//                         <Typography> Calories: {activity.caloriesBurnt}</Typography>
//                     </CardContent>  
//                 </Card>
//             </Grid>
//         ))}

//     </Grid>
//   )
// }
