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
    clientId: "amzn1.application-oa2-client.ddb144fb4f2342a688dc1f319c6690ef",
    clientSecret: "d924716af8e85e4ba1fab4031bfa1c5065accbf315d87d458aa1a95be078c1c4",
    redirectUrl: 'https://localhost:3000/authresponse',
    lwaRedirectHost: "amazon.com",
    lwaApiHost: "api.amazon.com",
    validateCertChain: true,
    sslKey: "/home/pi/zlk38avs/amazon_avs/samples/javaclient/certs/server/node.key",
    sslCert: "/home/pi/zlk38avs/amazon_avs/samples/javaclient/certs/server/node.crt",
    sslCaCert: "/home/pi/zlk38avs/amazon_avs/samples/javaclient/certs/ca/ca.crt",
    products: {
        "Yanshee1": ["123456789"],
    },
};

module.exports = config;
