# Intro

This project is a generic kafka streamer that can transform String message on a specific input topic,
call a rest endpoint that receives that message as payload of a POST method and returns a new
string as the result of the operation. The transformed message will be posted on an output topic.

# Dependent service:
The rest service to be called must have a endpoint with the POST verb.
The body is treated as String.
The result should be also String.

# Deployment.
Make sure you provide the following Env. Variables:

* APP_ID -> the kafka id to register this id against.
* APP_INCOMINGTOPIC -> Topic where messages will be read from.
* APP_OUTGOINGTOPIC -> Topic where messages will be written to.
* APP_SERVICEURL -> URL of the Rest Service endpoint to be called.

