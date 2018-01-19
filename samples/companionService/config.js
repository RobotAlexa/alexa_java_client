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
    clientId: "amzn1.application-oa2-client.8d1b0cf3189a44308869ff0025292200",
    clientSecret: "f008abc3c4a4163132dbc6eb4369ba8da7b2076b0baa547484347f44810e0bfe",
    redirectUrl: 'https://localhost:3000/authresponse',
    lwaRedirectHost: "amazon.com",
    lwaApiHost: "api.amazon.com",
    validateCertChain: true,
    sslKey: "/home/pi/avs/amazon_avs/samples/javaclient/certs/server/node.key",
    sslCert: "/home/pi/avs/amazon_avs/samples/javaclient/certs/server/node.crt",
    sslCaCert: "/home/pi/avs/amazon_avs/samples/javaclient/certs/ca/ca.crt",
    products: {
        "YansheeC091": ["b8:27:eb:1f:67:2c"],
    },
};

module.exports = config;
