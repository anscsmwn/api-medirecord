package api.medirecord

import grails.converters.JSON

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/auth/register"(controller: "doctor", parseRequest: true, action: "register"){
            method = "POST"
        }
        "/api/auth/login"(controller: "doctor", parseRequest: true, action: "login"){
            method = "POST"
        }
        "/api/patient"(controller: "patient", parseRequest: true, action: "addPatientToDoctor"){
            method = "POST"
        }
        "/api/patient"(controller: "patient", parseRequest: true, action: "getPatientsByDoctor"){
            method = "GET"
        }
        "/api/patient/$id"(controller: "patient", parseRequest: true, action: "getPatientById"){
            method = "GET"
        }
        "/api/patient/$id"(controller: "patient", parseRequest: true, action: "deletePatient"){
            method = "DELETE"
        }
        "/api/patient/$id"(controller: "patient", parseRequest: true, action: "updatePatient"){
            method = "PUT"
        }
        "/"(view:"/index")
        "500"(view:'/error')

    }
    static responseConfiguration = {
        notFound {
            def error = [
                    method: request.method,
                    endpoint: request.forwardURI,
                    message: "The requested endpoint does not exist",
                    status: 404
            ]
            render(contentType: 'application/json', text: error as JSON)
        }
    }
}
