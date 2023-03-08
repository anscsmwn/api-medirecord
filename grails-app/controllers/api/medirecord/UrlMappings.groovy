package api.medirecord

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

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
