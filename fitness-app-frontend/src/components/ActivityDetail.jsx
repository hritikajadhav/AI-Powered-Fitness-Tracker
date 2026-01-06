import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { getActivityDetail } from "../services/api";
import {
  Box,
  Card,
  CardContent,
  Divider,
  Typography,
} from "@mui/material";

const ActivityDetail = () => {
  const { id } = useParams();

  const [activity, setActivity] = useState(null);
  const [recommendation, setRecommendation] = useState(null);

  useEffect(() => {
    const fetchActivityDetail = async () => {
      try {
        const response = await getActivityDetail(id);
        setActivity(response.data);
        setRecommendation(response.data.recommendation);
      } catch (error) {
        console.error(error);
      }
    };

    fetchActivityDetail();
  }, [id]);

  if (!activity) {
    return (
      <Box sx={{ textAlign: "center", mt: 5 }}>
        <Typography variant="h6">Loading activity details...</Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ maxWidth: 900, mx: "auto", p: 2 }}>
      {/* ================= Activity Details ================= */}
      <Card sx={{ mb: 3 }}>
        <CardContent>
          <Typography variant="h5" gutterBottom>
            Activity Details
          </Typography>

          <Divider sx={{ mb: 2 }} />

          <Typography><strong>Type:</strong> {activity.activityType}</Typography>
          <Typography><strong>Duration:</strong> {activity.duration} minutes</Typography>
          <Typography><strong>Calories Burnt:</strong> {activity.caloriesBurnt}</Typography>
          <Typography>
            <strong>Date:</strong>{" "}
            {new Date(activity.createdAt).toLocaleString()}
          </Typography>

          {/* -------- Additional Metrics (Optional) -------- */}
          {activity.additionalMetrics &&
            Object.keys(activity.additionalMetrics).length > 0 && (
              <>
                <Divider sx={{ my: 2 }} />
                <Typography variant="h6">Additional Metrics</Typography>

                {activity.additionalMetrics.distance && (
                  <Typography>
                    Distance: {activity.additionalMetrics.distance} km
                  </Typography>
                )}

                {activity.additionalMetrics.averageSpeed && (
                  <Typography>
                    Average Speed: {activity.additionalMetrics.averageSpeed} km/h
                  </Typography>
                )}

                {activity.additionalMetrics.maxHeartRate && (
                  <Typography>
                    Max Heart Rate: {activity.additionalMetrics.maxHeartRate}
                  </Typography>
                )}
              </>
            )}
        </CardContent>
      </Card>

      {/* ================= AI Recommendation ================= */}
      {recommendation && (
        <Card>
          <CardContent>
            <Typography variant="h5" gutterBottom>
              AI Recommendation
            </Typography>

            <Divider sx={{ mb: 2 }} />

            <Typography variant="h6">Analysis</Typography>
            <Typography paragraph>{activity.recommendation}</Typography>

            {/* -------- Improvements -------- */}
            {activity.improvements?.length > 0 && (
              <>
                <Divider sx={{ my: 2 }} />
                <Typography variant="h6">Improvements</Typography>
                {activity.improvements.map((item, index) => (
                  <Typography key={index} paragraph>
                    • {item}
                  </Typography>
                ))}
              </>
            )}

            {/* -------- Suggestions -------- */}
            {activity.suggestions?.length > 0 && (
              <>
                <Divider sx={{ my: 2 }} />
                <Typography variant="h6">Suggestions</Typography>
                {activity.suggestions.map((item, index) => (
                  <Typography key={index} paragraph>
                    • {item}
                  </Typography>
                ))}
              </>
            )}

            {/* -------- Safety -------- */}
            {activity.safety?.length > 0 && (
              <>
                <Divider sx={{ my: 2 }} />
                <Typography variant="h6">Safety Guidelines</Typography>
                {activity.safety.map((item, index) => (
                  <Typography key={index} paragraph>
                    • {item}
                  </Typography>
                ))}
              </>
            )}
          </CardContent>
        </Card>
      )}
    </Box>
  );
};

export default ActivityDetail;












// import React, { useEffect, useState } from 'react'
// import { useParams } from 'react-router'
// import { getActivityDetail } from '../services/api';
// import { Box, Card, CardContent, Divider, Typography } from '@mui/material';

// const ActivityDetail = () => {

//     const { id } = useParams();
//     const[activity, setActivity] = useState(null);
//     const[recommendation, setRecommendation] = useState(null);

//     useEffect(() => {
//         const fetchActivityDetail = async() => {
//             try{
//                 const response = await getActivityDetail(id);
//                 console.log("Activity response:", response.data);
//                 setActivity(response.data);
//                 setRecommendation(response.data.recommendation);
//             }catch(error){
//                 console.error(error);
//             }
//         }

//         fetchActivityDetail();
//     }, [id]);
//     console.log("ACTIVITY OBJECT:", activity);
//     if(!activity){
//         return <Typography>Loading...</Typography>
//     }
//   return (
//     <Box sx={{ maxWidth: 800, mx: 'auto', p: 2}}>
//         <Card sx={{ mb: 2 }}>
//             <CardContent>
//                 <Typography variant="h5" gutterBottom> Activity Details</Typography>
//                 <Typography>Type: {activity.activityType}</Typography>
//                 <Typography>Duration: {activity.duration}</Typography>
//                 <Typography>Calories Burnt: {activity.caloriesBurnt}</Typography>
//                 <Typography>Date: {new Date(activity.createdAt).toLocaleString()}</Typography>

//                 {/* Optional Additional Metrics */}
//                 {activity.additionalMetrics && Object.keys(activity.additionalMetrics).length > 0 && (
//                 <Box sx={{ mt: 2 }}>
//                 <Typography variant="h6">Additional Metrics:</Typography>
//                 {activity.additionalMetrics.distance && (
//                     <Typography>Distance: {activity.additionalMetrics.distance} km</Typography>
//                 )}
//                 {activity.additionalMetrics.averageSpeed && (
//                     <Typography>Average Speed: {activity.additionalMetrics.averageSpeed} km/h</Typography>
//                 )}
//                 {activity.additionalMetrics.maxHeartRate && (
//                     <Typography>Max Heart Rate: {activity.additionalMetrics.maxHeartRate}</Typography>
//                 )}
//                 </Box>
//     )}
//             </CardContent>
//         </Card>

//         {recommendation && (
//             <Card>
//                 <CardContent>
//                     <Typography variant="h5" gutterBottom> AI Recommendation</Typography>
//                     <Typography variant="h6">Analysis</Typography>
//                     <Typography paragraph>{activity.recommendation}</Typography>

//                     <Divider sx={{ my: 2 }}></Divider>

//                     <Typography variant="h6">Improvements</Typography>
//                     {
//                         activity?.improvements?.map((improvement, index) => (
//                            <Typography key={index} paragraph>{activity.improvements}</Typography> 
//                         ))}

//                     <Divider sx={{ my: 2}}></Divider>   

//                      <Typography variants="h6">Suggestions</Typography>
//                      {
//                         activity?.suggestions?.map((suggestion, index) => (
//                             <Typography key={index} paragraph>{suggestion}</Typography>
//                         ))}
                    
//                     <Divider sx={{ my: 2}}></Divider>

//                     <Typography variants="h6"> Safety Guidelines</Typography>
//                     {
//                         activity?.safety?.map((safety, index) => (
//                             <Typography key={index} paragraph>{safety}</Typography>
//                         ))}
//                 </CardContent>
//             </Card>
//         )}
//     </Box>
//   )
// }

// export default ActivityDetail