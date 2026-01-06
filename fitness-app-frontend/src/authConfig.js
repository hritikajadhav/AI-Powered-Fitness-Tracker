import { useAuthContext, AuthProvider, TAuthConfig, TRefreshTokenExpiredEvent } from "react-oauth2-code-pkce"

const authConfig = {
  clientId: 'oauth2-pkce-client',
  authorizationEndpoint: 'http://localhost:8181/realms/fitness-oauth2/protocol/openid-connect/auth',
  tokenEndpoint: 'http://localhost:8181/realms/fitness-oauth2/protocol/openid-connect/token',
  logoutEndpoint:'http://localhost:8181/realms/fitness-oauth2/protocol/openid-connect/logout',
  redirectUri: 'http://localhost:5173',
  scope: 'openid profile email offline_access',
  onRefreshTokenExpire: (event) => event.logIn(),
}

export default authConfig;