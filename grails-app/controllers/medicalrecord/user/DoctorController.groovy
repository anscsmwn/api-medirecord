package medicalrecord.user
import org.mindrot.jbcrypt.BCrypt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import grails.converters.JSON
import javax.servlet.http.HttpServletResponse

class DoctorController {

    def doctorService

    def register() {
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        def requestBody = request.JSON

        // Validate the request body
        def errors = validateDoctor(requestBody)

        if (errors) {
            response.errors = errors
            response.status = HttpServletResponse.SC_BAD_REQUEST
            render response as JSON
            return
        }

        // Create a new doctor instance
        def doctor = new Doctor(
                name: requestBody.name,
                email: requestBody.email,
                password: requestBody.password
        )

        // Save the doctor instance
        try {
            doctorService.save(doctor)
            response.status = HttpServletResponse.SC_CREATED
        } catch (Exception e) {
            response.errors = ['Failed to save doctor']
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }

        render response as JSON
    }

    def login() {
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        def requestBody = request.JSON
        def email = requestBody.email
        def password = requestBody.password
        def doctor = Doctor.findByEmail(email)

        if (doctor && BCrypt.checkpw(password, doctor.password)) {
            def token = Jwts.builder()
                    .setSubject(doctor.id.toString())
                    .signWith(SignatureAlgorithm.HS512, 'secretKey'.getBytes('UTF-8'))
                    .compact()

                    response.status = HttpServletResponse.SC_OK
                    response.message = 'Login successful'
                    response.token = token

            render response as JSON
        } else {
            response.message = "Invalid email or password"
            response.errors = ["login": "Invalid email or password"]
            response.status = HttpServletResponse.SC_BAD_REQUEST
        }

        render response as JSON
    }

    // Validates the doctor data in the request body
    private Map validateDoctor(requestBody) {
        def errors = [:]
        if (!requestBody.name) {
            errors.name = 'Name is required'
        }
        if (!requestBody.email) {
            errors.email = 'Email is required'
        }
        if (!requestBody.password) {
            errors.password = 'Password is required'
        }
        return errors ? errors : null
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'doctor.label', default: 'Doctor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
