import React from 'react'
import ReactDOM from 'react-dom/client'

import { Provider } from 'react-redux'
import { store } from './store/store'
import  authConfig  from './authConfig'
import App from './App'
import { AuthProvider } from 'react-oauth2-code-pkce'
// if ("serviceWorker" in navigator) {
//   window.addEventListener("load", () => {
//     navigator.serviceWorker.register("/service-worker.js");
//   });
// }

// As of React 18
const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <AuthProvider authConfig={authConfig} 
                loadingComponent={<div>Loading....</div>}>
  <Provider store={store}>
    <App />
  </Provider>
  </AuthProvider>,
);