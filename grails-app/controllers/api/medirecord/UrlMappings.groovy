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
        "/api/profile"(controller: "doctor", parseRequest: true, action: "getProfile"){
            method = "GET"
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
        "/api/patient/medical-numbers"(controller: "patient", parseRequest: true, action: "getMedicalNumberPatientsQuery"){
            method = "GET"
        }
        "/api/patient/medical-record/$id"(controller: "patient", parseRequest: true, action: "getMedicalRecordPatient"){
            method = "GET"
        }
        "/api/patient/medications/$id"(controller: "patient", parseRequest: true, action: "getMedications"){
            method = "GET"
        }

        "/api/medical-record"(controller: "medicalRecord", parseRequest: true, action: "getMedicalRecordsByDoctor"){
            method = "GET"
        }
        "/api/medical-record"(controller: "medicalRecord", parseRequest: true, action: "getMedicalRecordsByDoctor"){
            method = "GET"
        }
        "/api/medical-record/$id"(controller: "medicalRecord", parseRequest: true, action: "getMedicalRecord"){
            method = "GET"
        }
        "/api/medical-record"(controller: "medicalRecord", parseRequest: true, action: "addMedicalRecord"){
            method = "POST"
        }
        "/api/medical-record/$id"(controller: "medicalRecord", parseRequest: true, action: "updateMedicalRecord"){
            method = "PUT"
        }
        "/api/medical-record/$id"(controller: "medicalRecord", parseRequest: true, action: "deleteMedicalRecord"){
            method = "DELETE"
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
