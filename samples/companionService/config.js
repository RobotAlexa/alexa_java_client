/**
 * @module
 * This module defines the settings that need to be configured for a new
 * environment.
 * The clientId and clientSecret are provided when you create
 * a new security profile in Login with Amazon.  
 * 
 * You will also need to specify
 * the redirect url under allowed settings as the return url that LWA
 * will call back to with the authorization code.  The authresponse endpoint
 * is setup in app.js, and should not be changed.  
 * 
 * lwaRedirectHost and lwaApiHost are setup for login with Amazon, and you should
 * not need to modify those elements.
 */
var config = {
    clientId: "amzn1.application-oa2-client.0528caca2a594c93ade8194f0efedd99",
    clientSecret: "deca95233ca9d9ca110e0fe38c914dcd403aefcc5a64e9fc484a409356feaf37",
    redirectUrl: 'https://localhost:3000/authresponse',
    lwaRedirectHost: "amazon.com",
    lwaApiHost: "api.amazon.com",
    validateCertChain: true,
    sslKey: "/home/pi/Desktop/Alexa-java-client/samples/javaclient/certs/server/node.key",
    sslCert: "/home/pi/Desktop/Alexa-java-client/samples/javaclient/certs/server/node.crt",
    sslCaCert: "/home/pi/Desktop/Alexa-java-client/samples/javaclient/certs/ca/ca.crt",
    products: {
        "Yanshee": ["b8:27:eb:9a:ee:76"],
    },
};

module.exports = config;
