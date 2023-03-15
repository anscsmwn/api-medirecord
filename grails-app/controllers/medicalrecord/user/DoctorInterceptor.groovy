package medicalrecord.user
import grails.converters.JSON
import javax.servlet.http.HttpServletResponse
import io.jsonwebtoken.Jwts

class DoctorInterceptor {
    DoctorInterceptor(){
        matchAll().excludes(controller: 'doctor').excludes(uri: '/')
    }
    boolean before() {
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        String token

        if(request.getHeader('Authorization') != null){
            token = request.getHeader('Authorization').replaceFirst('Bearer ', '')
        }
        if(!token) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.message = 'No token provided'
            render (response as JSON, status: HttpServletResponse.SC_UNAUTHORIZED)
            return false
        }
        try {
            // Check if the token is not expired
            String secretKey = grailsApplication.config.myapp.JWT_SECRET_KEY
            Jwts.parser().setSigningKey(secretKey.getBytes('UTF-8')).parseClaimsJws(token).getBody()
            return true
        } catch (Exception e) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.message = 'Failed to authenticate token'
            render (response as JSON, status: HttpServletResponse.SC_FORBIDDEN)
            return false
        }
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
