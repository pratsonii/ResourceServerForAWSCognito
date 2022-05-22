# ResourceServerForAWSCognito
Authenticates and authorises requests which have Id_token generated using aws cognito. Uses Spring Security and Resources server


# How to get OIDC Urls ?
1. Create user-pool in AWS Cognito
2. Hit this url : https://cognito-idp.<region>.amazonaws.com/<User pool ID>/.well-known/openid-configuration#
3. This will have all the details
4. Set jwks_uri value from above details in application.properties file for jwk-set-uri
5. Start the server
  
  
# How to get ID token for local testing(without UI) :
1. Hit this url https://<Cognito-domain>/login?client_id=<client_id>&response_type=token&scope=email+openid+phone+profile&redirect_uri=<callback url> 
-  Cognito-domain path :  AWS Cognito > Userpool > App Integration tab  
2. Do login
3. Response will have id and access token
  
Use ID token in the header for api  
  
  
