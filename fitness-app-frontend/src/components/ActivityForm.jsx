import {
  InputLabel,
  MenuItem,
  TextField,
  Box,
  Button,
  FormControl,
  Select,
  Card,
  CardContent,
  Typography,
  Grid
} from "@mui/material";
import React, { useState } from "react";
import { addActivity } from "../services/api";

const ActivityForm = ({ onActivityAdded }) => {
  const [activity, setActivity] = useState({
    type: "RUNNING",
    duration: "",
    caloriesBurnt: "",
    additionalMetrics: {
      distance: "",
      averageSpeed: "",
      maxHeartRate: ""
    }
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addActivity(activity);
      onActivityAdded();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Card sx={{ mb: 4 }}>
      <CardContent>
        <Typography variant="h5" gutterBottom>
          Hello! Welcome back!
        </Typography>
        <Typography variant="h6" gutterBottom>
          Add New Activity
        </Typography>

        <Box component="form" onSubmit={handleSubmit}>
          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Activity Type</InputLabel>
            <Select
              value={activity.type}
              onChange={(e) =>
                setActivity({ ...activity, type: e.target.value })
              }
            >
              {[
                "RUNNING",
                "WALKING",
                "CYCLING",
                "SWIMMING",
                "WEIGHT_TRAINING",
                "YOGA",
                "CARDIO",
                "HIIT",
                "STRETCHING",
                "OTHERS"
              ].map((type) => (
                <MenuItem key={type} value={type}>
                  {type.replace("_", " ")}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Duration (minutes)"
                type="number"
                value={activity.duration}
                onChange={(e) =>
                  setActivity({ ...activity, duration: e.target.value })
                }
              />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Calories Burnt"
                type="number"
                value={activity.caloriesBurnt}
                onChange={(e) =>
                  setActivity({ ...activity, caloriesBurnt: e.target.value })
                }
              />
            </Grid>

            <Grid item xs={12} sm={4}>
              <TextField
                fullWidth
                label="Distance (km)"
                type="number"
                value={activity.additionalMetrics.distance}
                onChange={(e) =>
                  setActivity({
                    ...activity,
                    additionalMetrics: {
                      ...activity.additionalMetrics,
                      distance: e.target.value
                    }
                  })
                }
              />
            </Grid>

            <Grid item xs={12} sm={4}>
              <TextField
                fullWidth
                label="Avg Speed (km/h)"
                type="number"
                value={activity.additionalMetrics.averageSpeed}
                onChange={(e) =>
                  setActivity({
                    ...activity,
                    additionalMetrics: {
                      ...activity.additionalMetrics,
                      averageSpeed: e.target.value
                    }
                  })
                }
              />
            </Grid>

            <Grid item xs={12} sm={4}>
              <TextField
                fullWidth
                label="Max Heart Rate"
                type="number"
                value={activity.additionalMetrics.maxHeartRate}
                onChange={(e) =>
                  setActivity({
                    ...activity,
                    additionalMetrics: {
                      ...activity.additionalMetrics,
                      maxHeartRate: e.target.value
                    }
                  })
                }
              />
            </Grid>
          </Grid>

          <Button sx={{ mt: 3 }} type="submit" variant="contained" fullWidth>
            Add Activity
          </Button>
        </Box>
      </CardContent>
    </Card>
  );
};

export default ActivityForm;
















// import { InputLabel, MenuItem, TextField } from '@mui/material';
// import React, { useState } from 'react'
// import Box from '@mui/material/Box';
// import Button from "@mui/material/Button";
// import FormControl from "@mui/material/FormControl";
// import Select from "@mui/material/Select";
// import { addActivity } from '../services/api';


// const ActivityForm = ({onActivityAdded}) => {

//     const[activity, setActivity] = useState({
//         type:"RUNNING", duration:'',caloriesBurnt:'',additionalMetrics:{},
//     });

//     const handleSubmit = async (e) => {
//     e.preventDefault();

//     try{
//         await addActivity(activity);
//         onActivityAdded();
//         setActivity({ type: "RUNNING",
//         duration: "",
//         caloriesBurnt: "",
//         additionalMetrics: {
//             distance: '',
//             averageSpeed: '',
//             maxHeartRate: ''
//         },
//      });
//     }catch(error){
//         console.error(error);
//     }
// };
//   return (
//     <Box component="form" onSubmit={handleSubmit} sx={{mb:4}}>
//         <FormControl fullWidth sx={{mb: 2}}>
//             <InputLabel>Activity Type</InputLabel>
//             <Select value={activity.type}
//             onChange={(e) => setActivity({...activity, type: e.target.value})}>
//                 <MenuItem value="RUNNING"> Running</MenuItem>
//                 <MenuItem value="WALKING"> Walking</MenuItem>
//                 <MenuItem value="CYCLING"> Cycling</MenuItem>
//                 <MenuItem value="SWIMMING"> Swimming</MenuItem>
//                 <MenuItem value="WEIGHT_TRAINING"> Weight Training</MenuItem>
//                 <MenuItem value="YOGA"> Yoga</MenuItem>
//                 <MenuItem value="CARDIO"> Cardio</MenuItem>
//                 <MenuItem value="HIIT"> HIIT</MenuItem>
//                 <MenuItem value="STRETCHING"> Stretching</MenuItem>
//                 <MenuItem value="OTHERS"> Others</MenuItem>
//             </Select>
//         </FormControl>
//         <TextField fullWidth
//                     label="Duration in minutes"
//                     type='number'
//                     sx={{mb: 2}}
//                     value={activity.duration}
//                     onChange={(e) => setActivity({...activity, duration: e.target.value})}></TextField>

//         <TextField fullWidth
//                     label="Calories Burnt"
//                     type='number'
//                     sx={{mb: 2}}
//                     value={activity.caloriesBurnt}
//                     onChange={(e) => setActivity({...activity, caloriesBurnt: e.target.value})}></TextField>

//         <TextField
//                     fullWidth
//                     label="Distance (km)"
//                     type="number"
//                     sx={{ mb: 2 }}
//                     value={activity.additionalMetrics.distance}
//                     onChange={(e) =>
//                     setActivity({
//                     ...activity,
//                     additionalMetrics: {
//                     ...activity.additionalMetrics,
//                     distance: e.target.value
//                  }
//                  })
//              }
//         />

//         <TextField
//                 fullWidth
//                 label="Average Speed (km/h)"
//                 type="number"
//                  sx={{ mb: 2 }}
//                  value={activity.additionalMetrics.averageSpeed}
//                  onChange={(e) =>
//                 setActivity({
//                 ...activity,
//                 additionalMetrics: {
//                 ...activity.additionalMetrics,
//                 averageSpeed: e.target.value
//                 }
//                 })
//             }
//         />

//         <TextField
//                 fullWidth
//                 label="Max Heart Rate"
//                 type="number"
//                 sx={{ mb: 2 }}
//                 value={activity.additionalMetrics.maxHeartRate}
//                 onChange={(e) =>
//                 setActivity({
//                 ...activity,
//                  additionalMetrics: {
//                 ...activity.additionalMetrics,
//                 maxHeartRate: e.target.value
//                 }
//                 })
//             }
//         />      
       
       
       
//         <Button type='submit' variant='contained'>
//             Add Activity
//         </Button>

//     </Box>
//   )
// }

// export default ActivityForm