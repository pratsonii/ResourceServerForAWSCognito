# ResourceServerForAWSCognito
Authenticates and authorises requests which have Id_token generated using aws cognito. Uses Spring Security and Resources server


# How to get OIDC Urls ?
1. Create user-pool in AWS Cognito
2. Hit this url : https://cognito-idp.(region).amazonaws.com/(User pool ID)/.well-known/openid-configuration#
3. This will have all the details
4. Set jwks_uri value from above details in application.properties file for jwk-set-uri
5. Start the server
  
  
# How to get ID token for local testing(without UI) :

1) Auth Code Grant type flow
  Use Postman to generate token. 
  Follow steps here : https://github.com/pratsonii/ResourceServerGoogleAuth#resourceservergoogleauth


2) Implicit Grant type flow

1. Hit this url https://(Cognito-domain)/login?client_id=(client_id)&response_type=token&scope=email+openid+phone+profile&redirect_uri=(callback url) 
    Cognito-domain path :  AWS Cognito > Userpool > App Integration tab  
2. Do login
3. Response will have id and access token
  
Use ID token in the header for api  


# Useful Links : 
- https://www.youtube.com/watch?v=y3z9pkoAlws&ab_channel=WalrusCode
- https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html
- https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-using-tokens-verifying-a-jwt.html
- https://kevcodez.de/posts/2020-03-26-secure-spring-boot-app-with-oauth2-aws-cognito/
- https://medium.com/cloud-base/resource-server-with-cognito-b7fbfbee0155
- https://blog.marcusjanke.de/spring-boot-2-3-oauth-2-jwt-security-using-amazon-cognito-d10e4e40ac05
- https://dev.to/toojannarong/spring-security-with-jwt-the-easiest-way-2i43
  
  
