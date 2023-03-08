package medicalrecord.user

import org.springframework.data.mongodb.core.mapping.Document
import org.mindrot.jbcrypt.BCrypt
import io.jsonwebtoken.Jwts

@Document("doctors")
class Doctor {
    String name
    String email
    String password
    static mapWith = "mongo"

    static constraints = {
        name nullable: false
        email nullable: false
        password nullable: false
    }

    def beforeInsert() {
        password = BCrypt.hashpw(password, BCrypt.gensalt())
    }

    static Long getIdFromToken(String token) {
        def claims = Jwts.parser()
                .setSigningKey('secretKey'.getBytes('UTF-8'))
                .parseClaimsJws(token)
                .getBody()
        return Long.parseLong(claims.getSubject())
    }

}
